import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

public class BacktrackN {

    private int plazasMax;
    private int pesoMax;
    private Solicitante[] lSolic;
    private int cargaTotal;
    private Solicitante[] recorridoOpt;
    private int dineroTotal;
    private int maximoBenef = 0;
    private int index = 0;

    private static BacktrackN myBacktrack;



    public static BacktrackN getMyBacktrack(){
        if(myBacktrack == null){
            myBacktrack = new BacktrackN();
        }
        return myBacktrack;
    }

    public void readFile() throws FileNotFoundException {
        File f = null;
        int k = 0;

        f = new File("lista30.txt");

        List<String> lines = new BufferedReader(new FileReader(f)).lines().collect(Collectors.toList()); //Leemos el txt

        String l = lines.get(0);
        plazasMax = Integer.parseInt(l.split(" ")[0]);
        pesoMax = Integer.parseInt(l.split(" ")[1]);
        int num = Integer.parseInt(l.split(" ")[2]);
        lSolic = new Solicitante[num];
        recorridoOpt = new Solicitante[plazasMax];

        lines.remove(0);
        for(String s : lines){
            int d = Integer.parseInt(s.split(" ")[0]);
            int p = Integer.parseInt(s.split(" ")[1]);
            Solicitante solic = new Solicitante(d, p, k);
            lSolic[k] = solic;
            k++;
            cargaTotal = cargaTotal + solic.getPeso();
            dineroTotal = dineroTotal + solic.getDinero();
        }

    }

    private void ordenarLista(Solicitante[] l, int inicio, int fin){
        if(inicio<fin){
            ordenarLista(l, inicio, (inicio+fin)/2);
            ordenarLista(l, ((inicio+fin)/2)+1, fin);
            mezcla(l, inicio, (inicio+fin)/2, fin);
        }
    }

    private void mezcla(Solicitante[] l, int inicio, int medio, int fin){
        Solicitante[] auxL = new Solicitante[l.length];
        int izq = inicio; //izq no podra pasar de medio
        int der = medio+1; //der no podra pasar del limite
        int k = 0;
        //Tenemos el array dividido en 2, con un indicador en el principio de cada mitad.
        while(izq<=medio && der<=fin){      //Aquí comparamos cual de los dos numeros es menor, y lo colocamos en la lista auxiliar.
            if(l[izq].getPeso() < l[der].getPeso()){
                auxL[k] = l[izq];
                izq++;
            }
            else{
                auxL[k] = l[der];
                der++;
            }
            k++;
        }
        //Llegados a este punto, izq o der estaran en el limite,
        if(izq > medio){                    //asi que quedaria volcar el contenido restante a la lista auxiliar
            while(der<=fin){
                auxL[k] = l[der];
                der++;
                k++;
            }
        }
        else{
            while(izq<=medio){
                auxL[k] = l[izq];
                izq++;
                k++;
            }
        }

        k=0;
        for(int j=inicio; j<=fin; j++){     //Sustituimos el array desordenado que se recibe, por el auxiliar ordenado que hemos obtenido.
            l[j] = auxL[k];
            k++;
        }
    }

    public int backtrack() throws FileNotFoundException {
        readFile();
        ordenarLista(lSolic, 0, lSolic.length-1);
        Solicitante[] ensayoAct = new Solicitante[plazasMax];
        listaOptima(ensayoAct,0, 0, 0, 0, 0);

        escribirTxt();
        return maximoBenef;
    }

    public void listaOptima(Solicitante[] ensayoAct, int sumaTotal, int sumaAct, int pos, int pesoAct, int plazasAct){
        //while(pos < lSolic.length){
            //System.out.println(pos);
            Solicitante s = lSolic[pos];
            sumaTotal = sumaTotal + s.getDinero();
            if(plazasAct < plazasMax && pesoAct < pesoMax ) {
                if (pesoAct + s.getPeso() <= pesoMax && plazasAct < plazasMax) { //si es aceptable
                    if (sumaAct + s.getDinero() + (dineroTotal - sumaTotal) > maximoBenef) { //y aspira a ser mejor que lo que ya tenemos
                        ensayoAct[plazasAct] = s;
                        listaOptima(ensayoAct, sumaTotal, sumaAct + s.getDinero(), pos + 1, pesoAct + s.getPeso(), plazasAct + 1);
                        ensayoAct[plazasAct] = null;
                        listaOptima(ensayoAct, sumaTotal, sumaAct, pos + 1, pesoAct, plazasAct);
                    }
                }
            }
            else if(plazasAct == plazasMax || pesoAct == pesoMax ){
                comprobarArrays(ensayoAct);
                index++;
            }
            //pos++;
       // }
    }

    public void comprobarArrays(Solicitante[] lista){
        int sum = 0;

        for(Solicitante s : lista){
            if(s != null){
                sum = sum + s.getDinero();
            }
        }
        if(sum > maximoBenef){
            maximoBenef = sum;
            recorridoOpt = new Solicitante[plazasMax];
            int i= 0;
            for(Solicitante s : lista){
                recorridoOpt[i] = s;
                /*if(s != null) {
                    System.out.println("añadido solic con peso: " + s.getPeso() + ", y dinero: " + s.getDinero());
                }*/
                i++;
            }
            //System.out.println("____");
        }
    }

    public void escribirTxt(){
        BufferedWriter writer = null;
        try {
            //create a temporary file
            File logFile = new File("Bactrack1N.txt");

            // This will output the full path where the file will be written to...
            System.out.println("TU FICHERO 'dinamica.txt' ESTARÁ EN: "+logFile.getCanonicalPath());

            writer = new BufferedWriter(new FileWriter(logFile));

            int k = 0;
            for(Solicitante s : recorridoOpt){
                if(s != null){
                    k++;
                }
            }
            writer.write(k+"\n");

            for (Solicitante s : recorridoOpt){
                if(s != null) {
                    writer.write(s.getId() + "\n");
                }
            }

            writer.write(maximoBenef+"\n");

            writer.write(index+"\n");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Close the writer regardless of what happens...
                writer.close();
            } catch (Exception e) {
            }
        }
    }

    public int getNumEnsayos(){
        return index;
    }

}
