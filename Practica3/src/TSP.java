import java.io.*;
import java.nio.file.Files;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
//import java.util.PriorityQueue;

public class TSP {

    public static int[][] matrizDist;
    public static int nNodos;
    static Ensayo mejorEnsayo;
    static int index=0;//Cuantos estados hemos visitado

    public static void main(String[] args){
        Instant starts = Instant.now();

        String pathName = "input/prueba7.txt";
        cargar(pathName);
        System.out.println(Arrays.deepToString(matrizDist));

        TSP();
        guardar(mejorEnsayo, "result.txt");
        Instant ends = Instant.now();
        System.out.println(Duration.between(starts, ends));
    }

    private static void guardar(Ensayo est, String nombreGuardar) {
        File file = new File(nombreGuardar);
        try (Writer writer = Files.newBufferedWriter(file.toPath())) {
            if (mejorEnsayo.coste < (Integer.MAX_VALUE - 100)){
                writer.write("Camino: ");
                for (int i = 0; i < est.camino.size(); i++) {
                    writer.write(est.camino.get(i) + ", ");
                }
                writer.write("0 \n");
                writer.write("Coste: "+mejorEnsayo.coste+"\n");
                writer.write("Ensayos analizados: "+index);
            }else{
                writer.write("Insatisfactible \n");
                writer.write("Ensayos analizados: "+index);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void crearPrueba(String path, int numElems){
        File file = new File(path+"/input"+numElems+".txt");
        try (Writer writer = Files.newBufferedWriter(file.toPath())) {
            int numAristas = numElems * numElems;
            for (int i = 0; i < 5; i++) writer.write("c"+"\n");
            writer.write(numElems + " "+numAristas+"\n"); // Num vertices, num aristas
            for (int i = 0; i < numElems; i++)
                for (int j = 0; j < numElems; j++) {
                    int peso = ThreadLocalRandom.current().nextInt(1, 100);
                    if (i==j) writer.write(i+" "+j+" "+0+"\n");
                    else writer.write(i+" "+j+" "+peso+"\n");
                }

        } catch (IOException e) {e.printStackTrace();}

    }

    private static void TSP() {
        PriorityQueue<Ensayo> q = new PriorityQueue<>();
        int lowerbound;
        mejorEnsayo = new Ensayo(nNodos, Integer.MAX_VALUE, null, null,0);

        //Respecto al libro hacemos la mitad xk empezamos a explorar por el estado F
        int[] ensayoAct = new int[nNodos];
        ensayoAct[0] = 1;

        q.add(new Ensayo(0, 0, ensayoAct, new ArrayList(),0));

        //branch and bound
        while (!q.isEmpty()) {
            Ensayo actual = q.remove();
            if (actual.coste < mejorEnsayo.coste){

                actual.camino.add(actual.id);

                //para cada nodo 'i'...
                for (int i = 0; i < nNodos; i++) {
                    //si el id de 'actual' no es el nodo 'i' en el que estamos,
                    //y existe arista desde el id de 'actual' al nodo 'i' en el que estamos,
                    //y el nodo 'i' en el que estamos, aun no ha sido visitado en 'actual'.
                    if (actual.id != i && matrizDist[actual.id][i] < Integer.MAX_VALUE && actual.visitados[i] == 0) {
                        actual.visitados[i]=1; //1 == visitado || 0 == no visitado
                        actual.camino.add(i); //añadimos el nodo al recorrido actual

                        index++; //aumentamos en 1 los ensayos creados.

                        //MST con todos los nodos - visitados
                        int mstV_S = Kruskal.Krusk(matrizDist, actual.visitados, nNodos, actual.id);

                        //a la matrizDistancia acumulada, le sumamos la matrizDistancia del id de 'actual', hasta el nodo 'i' en el que estamos
                        int acumCamino = actual.acumCamino + matrizDist[actual.id][i];
                        int hcost = mstV_S;
                        lowerbound = hcost + Kruskal.minCosteDeA + Kruskal.minCosteDeB + acumCamino;

                        //si hemos recorrido tantos nodos como los que tiene el grafo
                        if (actual.camino.size() == nNodos) {//+1 por el camino que acabo de borrar
                            mejorEnsayo.coste = actual.coste; //actualizamos el valor del coste del mejor ensayo
                            mejorEnsayo.camino = (ArrayList) actual.camino.clone();  //actualizamos el camino del mejor ensayo
                        }
                        //si el coste actual es menor que el coste del mejor ensayo actual
                        else if (lowerbound < mejorEnsayo.coste) {
                            actual.camino.remove(actual.camino.size()-1); //eliminamos eliminamos el anteultimo nodo, ya que el ultimo es el mismo que el primero
                            int[] visi = actual.visitados.clone(); //clonamos la lista de visitados
                            visi[i] = 1; //marcamos como visitado el nodo 'i'
                            ArrayList tmp = (ArrayList) actual.camino.clone(); //guardamos en tmp una copia del ensayo actual
                            q.add(new Ensayo(i, lowerbound, visi, tmp , acumCamino)); //añadimos un ensayo con estas caracteristicas guardadas, a la pila 'q' para volver a evaluarlo
                        }
                        actual.visitados[i]=0;//marcamos el nodo 'i' como no visitado

                    }
                }
                actual.camino.remove(Integer.valueOf(actual.id));
            }
        }
        System.out.println(index);
        System.out.println(mejorEnsayo.coste);
        System.out.println(mejorEnsayo.camino);

    }


    private static void cargar(String pathFile) {
        String[] split;
        String line;
        try {
            FileInputStream fstream = new FileInputStream(pathFile);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            for (int i = 0; i < 5; i++) br.readLine();

            split = br.readLine().split("\\s");
            nNodos = Integer.valueOf(split[0]);
            int numAristas = Integer.valueOf(split[1]);

            matrizDist = new int[nNodos][nNodos];

            while ((line = br.readLine()) != null) {
                split = line.split("\\s");
                int source = Integer.valueOf(split[0]);
                int destin = Integer.valueOf(split[1]);
                int value = Integer.valueOf(split[2]);
                if (value == 0) value = Integer.MAX_VALUE;
                matrizDist[source][destin] = value;
                matrizDist[destin][source] = value;
            }
            for (int i = 0; i < matrizDist.length; i++) {
                for (int j = 0; j < matrizDist.length; j++) {
                    if (matrizDist[i][j]==0) matrizDist[i][j] = Integer.MAX_VALUE;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}