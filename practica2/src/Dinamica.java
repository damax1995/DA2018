import java.io.*;
import java.sql.Array;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Dinamica {

    private Solicitante[] lSolic;
    private int[][][] matriz; //plazas X pesos X solicitantes
    private int[][][] matrizRec; // -1 no visitado, 1 visitado

    private int[] listaDinero;
    private int[] listaPesos;
    private Solicitante[] lOptima;
    private int index = 0;

    private int plazasMax;
    private int pesoMax;

    private int benefMax = 0;
    private static Dinamica myDinamica;

    private Dinamica(){
    }

    public static Dinamica getMyDinamica(){
        if(myDinamica == null){
            myDinamica = new Dinamica();
        }
        return myDinamica;
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
        listaPesos = new int[num];
        listaDinero = new int[num];
        lOptima = new Solicitante[plazasMax];

        lines.remove(0);
        for(String s : lines){
            int d = Integer.parseInt(s.split(" ")[0]);
            int p = Integer.parseInt(s.split(" ")[1]);
            Solicitante solic = new Solicitante(d, p, k);
            lSolic[k] = solic;
            listaDinero[k] = d;
            listaPesos[k] = p;
            k++;
        }

    }

    public int mBenef() throws FileNotFoundException {
        readFile();
        int[] mLDinero = listaDinero;
        int[] mLPesos = listaPesos;

        benefMax = mayorBeneficio(mLPesos, mLDinero, plazasMax, pesoMax);

        //EL ESCRIBIR_FICHERO() EN DINAMICA NO FUNCIONA, ASI QUE LO MUESTRO POR PANTALLA
        /*for(Solicitante s : lOptima){
            if(s != null){
                System.out.println("Solicitante "+s.getId()+"-> d: "+s.getDinero()+", p:"+s.getPeso());
            }
        }*/

        escribirTxt();
        return benefMax;
    }

    public int mayorBeneficio(int[] mLPesos, int[] mLDinero, int maxPlaz, int maxPeso){
        matriz = new int[maxPlaz+1][maxPeso+1][mLPesos.length+1];
        matrizRec = new int[maxPlaz+1][maxPeso+1][mLPesos.length+1];

        // Inicializar la matriz con valores a -1 (sin visitar)
        for(int p=0; p<=maxPlaz; p++){
            for(int peso=0; peso<=maxPeso; peso++){
                for(int solic=0; solic<=mLPesos.length; solic++){
                    matriz[p][peso][solic]=-1;
                }
            }
        }
        //Inicializar casos basicos
        //viaje sin plazas
        for(int pes=0; pes<=maxPeso; pes++){
            for(int solic=0; solic<=mLPesos.length; solic++){
                matriz[0][pes][solic]=0;
            }
        }
        //viaje sin peso
        for(int p=0; p<=maxPlaz; p++){
            for(int solic=0; solic<=mLPesos.length; solic++){
                matriz[p][0][solic]=0;
            }
        }
        //viaje sin solicitantes
        for(int p=0; p<=maxPlaz; p++){
            for(int peso=0; peso<=maxPeso; peso++){
                matriz[p][peso][0]=0;
            }
        }
        //si la ultima posicion está sin visitar, implica que no se ha rellenado la tabla
        if(matriz[maxPlaz][maxPeso][mLPesos.length]==-1){
            rellenar(maxPlaz,maxPeso,mLPesos.length,mLPesos,mLDinero);
        }

        //recuperacion
        int[] listaSolic = new int[mLPesos.length]; //esta lista tendra el tamaño de todos los solicitantes, asi que volcaremos los resultados de matrizRec (-1/1) para saber si forma o no parte de la solucion
        int cantPes = pesoMax;
        int cantP = maxPlaz;
        for(int k=mLPesos.length; k>0; k--){
            listaSolic[k-1]=matrizRec[cantP][cantPes][k];
            if(listaSolic[k-1]==1){//si lo hemos seleccionado
                cantPes=cantPes-mLPesos[k-1];//restamos su cantidad de peso al total
                cantP--;//disminuimos en uno las plazas restantes
                lOptima[index] = lSolic[k-1];
                index++;
            }//si no es = 1, se tomara como un solicitante no seleccionado
        }

        return matriz[maxPlaz][maxPeso][mLPesos.length];
    }

    private void rellenar(int p, int peso, int solic, int[] mLPesos, int[] mLDinero) {

        //si la posicion estaba sin visitar, la rellenamos
        if (matriz[p][peso][solic-1]==-1){
            rellenar(p, peso, solic-1, mLPesos, mLDinero);
        }
        matriz[p][peso][solic]=matriz[p][peso][solic-1];
        //si el dinero que nos ofrece el solicitante, es menor o igual que el maximo que podemos acumular ahora mismo
        if(mLPesos[solic-1]<=peso){
            //calculamos el resultado sin añadir al solicitante
            if(matriz[p-1][peso-mLPesos[solic-1]][solic-1]==-1){
                rellenar(p-1, peso-mLPesos[solic-1], solic-1, mLPesos,
                        mLDinero);
            }
            //y aqui comprobamos si seria mayor añadiendolo, en cuyo caso actualizamos la informacion
            if(mLDinero[solic-1]+matriz[p-1][peso-mLPesos[solic-1]][solic-
                    1]>matriz[p][peso][solic-1]){
                matriz[p][peso][solic]=mLDinero[solic-1]+matriz[p-1][peso-mLPesos[solic-
                        1]][solic-1];
                matrizRec[p][peso][solic]=1;
            }
        }
    }

    public Solicitante[] getlOptima() {
        return lOptima;
    }

    public void escribirTxt(){
        BufferedWriter writer = null;
        try {
            //create a temporary file
            File logFile = new File("dinamic.txt");

            // This will output the full path where the file will be written to...
            System.out.println("TU FICHERO 'dinamic.txt' ESTARÁ EN: "+logFile.getCanonicalPath());

            writer = new BufferedWriter(new FileWriter(logFile));
            int k=0;
            for(Solicitante s : lOptima){
                if(s != null){
                    k++;
                }
            }

            writer.write(k+"\n");

            for(Solicitante s : lOptima){
                if(s != null){
                    writer.write(s.getId()+"\n");
                }
            }
            writer.write(benefMax+"\n");

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
}
