import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Dinamica {

    private Solicitante[] lSolic;
    private int plazasMax;
    private int pesoMax;
    private Solicitante[] recorrido;
    private static Dinamica myDinamica;

    private Dinamica(){

    }

    public static Dinamica getMyDinamica(){
        if(myDinamica == null){
            myDinamica = new Dinamica();
        }
        return myDinamica;
    }

    public Solicitante[] getRecorrido() {
        return recorrido;
    }

    public void readFile() throws FileNotFoundException {
        File f = null;
        int k = 0;

        f = new File("prueba1.txt");

        List<String> lines = new BufferedReader(new FileReader(f)).lines().collect(Collectors.toList()); //Leemos el txt

        String l = lines.get(0);
        plazasMax = Integer.parseInt(l.split(" ")[0]);
        pesoMax = Integer.parseInt(l.split(" ")[1]);
        int num = Integer.parseInt(l.split(" ")[2]);
        lSolic = new Solicitante[num];

        lines.remove(0);
        for(String s : lines){
            int d = Integer.parseInt(s.split(" ")[0]);
            int p = Integer.parseInt(s.split(" ")[1]);
            Solicitante solic = new Solicitante(d, p);
            lSolic[k] = solic;
            k++;
        }
    }

    public double mBenef() throws FileNotFoundException {
        readFile();
        Solicitante[] recorridoAct = new Solicitante[plazasMax];
        float pesoAct = 0;
        int plazasAct = 0;
        int pos = 0; //indica el inicio de lSolic

        return getSuma(mayorBeneficio(lSolic, pesoAct, plazasAct, recorridoAct, pos));
    }

    public Solicitante[] mayorBeneficio(Solicitante[] listaS, float pesoAct, int plazasAct, Solicitante[] recorridoAct, int pos){
        Solicitante s = listaS[pos];
        pos++;
        if(plazasAct<plazasMax && (pesoAct+s.getPeso())<=pesoMax && pos<lSolic.length){ //Si el solicitante es aceptable
            Solicitante[] auxRec = recorridoAct;
            auxRec[plazasAct] = s;//De esta forma tengo en recorridoAct el recorrido sin añadir 's', y en auxRec con 's' añadido.

            Solicitante[] l1 = mayorBeneficio(lSolic, pesoAct, plazasAct, recorridoAct, pos);
            Solicitante[] l2 = mayorBeneficio(lSolic, pesoAct-s.getPeso(), plazasAct+1, auxRec, pos);
            double res1 = getSuma(l1);
            double res2 = getSuma(l2);
            if(res1 < res2){
                if((pesoAct-s.getPeso() == 0 || pos > lSolic.length || plazasAct+1 == plazasMax) && getSuma(getRecorrido())<res2 ){
                    recorrido = l2;
                    for(Solicitante sol : l2){
                        System.out.println(sol.getPeso());
                    }
                }
            }
            else{
                if(pos > lSolic.length && getSuma(getRecorrido())<=res1) {
                    recorrido = l1;
                    for(Solicitante sol : l2){
                        System.out.println(sol.getPeso());
                    }
                }
            }
        }

        return recorrido;
    }

    public double getSuma(Solicitante[] recor){
        double sum = 0;
        if(recor != null) {
            for (Solicitante s : recor) {
                if (s != null) {
                    sum += s.getDinero();
                }
            }
        }
        return sum;
    }

}
