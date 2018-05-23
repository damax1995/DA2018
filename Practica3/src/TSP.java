import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class TSP {

    private static int[][] matrizDist;
    private static int nNodos;
    private static Ensayo mejorEnsayo;
    static int index=0; //Cuantos estados hemos visitado
    private static int auxPeso = 0;
    private static ArrayList<Nodo> listaNodos;

    private static TSP myTSP;

    private TSP(){

    }

    public static TSP getMyTSP(){
        if(myTSP == null){
            myTSP = new TSP();
        }
        return myTSP;
    }

    public static void readFile() throws FileNotFoundException {
        File f = null;
        int k = 0;

        f = new File("input8.txt");
        List<String> lines = new BufferedReader(new FileReader(f)).lines().collect(Collectors.toList()); //Leemos el txt
        for(String s : lines){
            if(k==5){
                nNodos = Integer.valueOf(s.split(" ")[0]);
                matrizDist = new int[nNodos][nNodos];
                listaNodos = new ArrayList<Nodo>();

                for(int j = 0; j<nNodos; j++){
                    Nodo nodo = new Nodo(j);
                    listaNodos.add(nodo);
                }

                for (int i = 0; i < matrizDist.length; i++) {
                    for (int j = 0; j < matrizDist.length; j++) {
                            matrizDist[i][j] = -1;
                    }
                }
            }
            else if(k>5){
                int origen = Integer.valueOf(s.split(" ")[0]);
                int destino = Integer.valueOf(s.split(" ")[1]);
                int valor = Integer.valueOf(s.split(" ")[2]);
                if (valor == 0){
                    valor = -1;
                }
                matrizDist[origen][destino] = valor;
                matrizDist[destino][origen] = valor;
            }
            k++;
        }
    }

    public static void TSP(){
        PriorityQueue<Ensayo> cola = new PriorityQueue<>();
        //Queue<Ensayo> cola = new LinkedList<>();

        int lowerbound;
        mejorEnsayo = new Ensayo(nNodos, Integer.MAX_VALUE, null, null, Integer.MAX_VALUE);
        int[] ensayoAct = new int[nNodos];
        ensayoAct[0] = 1;

        cola.add(new Ensayo(0, 0, ensayoAct, new ArrayList(), 0));

        //ahora comienza el branch and bound
        //mientras queden ensayos posibles en la cola...
        while (!cola.isEmpty()){
            Ensayo actual = cola.remove();

            //si el lowebound de actual, es peor que el que tenemos guardado, no lo estudiamos
            if(actual.coste < mejorEnsayo.coste){
                //le añadimos al camino, su siguiente nodo, que era 'destino'.
                actual.camino.add(actual.destino);

                //Para cada nodo 'i'...
                for(int i = 0; i<nNodos; i++){
                    //...que sea adyacente a destino, y no este visitado
                    if(actual.destino != i && matrizDist[actual.destino][i] != -1 && actual.visitados[i] == 0){
                        //visitamos el nodo 'i'
                        actual.visitados[i] = 1;
                        //y lo añadimos al camino actual
                        actual.camino.add(i);
                        //aumentamos en 1 la cantidad de ensayos estudiados
                        index++;

                        //calculamos el MST de los no visitados
                        //int mstNoVisit = Kruskal.getMyKruskal().MSTKruskal(actual.visitados, matrizDist, nNodos, actual.destino);
                        //System.out.println("...........");
                        //System.out.println(actual.camino);
                        int mstNoVisit = Kruskal.getMyKruskal().MSTKruskal(listaNodos, actual.visitados, matrizDist, nNodos, actual.destino);
                        //System.out.println(actual.destino);
                        //System.out.println("MST no visitados: "+mstNoVisit);
                        int acumCamino = actual.acumCamino + matrizDist[actual.destino][i];
                        //System.out.println(acumCamino);
                        //System.out.println("CosteMinOR: "+Kruskal2.getMyKruskal().getMinDistOr());
                        //System.out.println("CosteMinDest: "+Kruskal2.getMyKruskal().getMinDistDest());
                        lowerbound = mstNoVisit + Kruskal.getMyKruskal().getMinDistOr() + Kruskal.getMyKruskal().getMinDistDest() + acumCamino;
                        //System.out.println(lowerbound);
                        if (actual.camino.size() == nNodos) {
                            //  mejorEnsayo.coste > acumCamino + matrizDist[actual.camino.get(actual.camino.size()-1)][0]
                            if(mejorEnsayo.coste > acumCamino + matrizDist[i][0] && matrizDist[i][0] != -1) {
                                //mejorEnsayo.coste = actual.coste;
                                mejorEnsayo.coste = acumCamino + matrizDist[actual.camino.get(actual.camino.size()-1)][0];
                                mejorEnsayo.camino = (ArrayList) actual.camino.clone();
                            }
                            //mejorEnsayo = actual;
                        } else if (lowerbound < mejorEnsayo.coste) {
                            //actual.visitados[i] = 1;
                            actual.camino.remove(actual.camino.size() - 1);
                            int[] v = actual.visitados.clone();
                            ArrayList c = (ArrayList) actual.camino.clone();
                            cola.add(new Ensayo(i, lowerbound, v, c, acumCamino));
                            //cola.add(actual);
                            //System.out.println("Ensayo con nodo: " + i + "\ncoste (LB)= " + lowerbound + "\ncamino: " + tmp + "\nacumulado en el camino: " + acumCamino);
                            //System.out.println(".........");
                        }
                        actual.visitados[i] = 0;
                    }
                }
                actual.camino.remove(Integer.valueOf(actual.destino));
            }
        }
        System.out.println("_____________________");
        System.out.println("Cant ensayos: "+index);
        System.out.println("Peso optimo: "+mejorEnsayo.coste);
        System.out.println("Camino optimo: "+mejorEnsayo.camino);

        getMyTSP().writeFile();
    }

    public void writeFile(){
        BufferedWriter writer = null;
        try {
            //create a temporary file
            File logFile = new File("ficheroTSP.txt");

            // This will output the full path where the file will be written to...
            System.out.println("TU FICHERO 'ficheroTSP.txt' ESTARÁ EN: "+logFile.getCanonicalPath());

            writer = new BufferedWriter(new FileWriter(logFile));

            if(mejorEnsayo.coste != Integer.MAX_VALUE) {
                for (int i : mejorEnsayo.camino) {
                    String s = Integer.toString(i);
                    writer.write(s+"\n");
                }
                String s = "0"+"\n";
                writer.write(s);
                s = Integer.toString(mejorEnsayo.coste);
                writer.write(s+"\n");
                s = Integer.toString(index);
                writer.write(s);
            }
            else{
                writer.write("INSATISFACTIBLE.");
            }

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

    public static void main(String[] args) throws FileNotFoundException {
        readFile();
        TSP();
    }

}
