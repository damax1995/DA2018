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
    private static int auxPeso = 0;

    public static void main(String[] args){
        Instant starts = Instant.now();

        String pathName = "prueba8.txt";
        cargar(pathName);

        TSP();
        guardar(mejorEnsayo, "resultado.txt");
        Instant ends = Instant.now();
        System.out.println(Duration.between(starts, ends));
    }



    private static void guardar(Ensayo ensayo, String nombre) {
        File logfile = new File(nombre);
        try (Writer writer = Files.newBufferedWriter(logfile.toPath())) {
            System.out.println("TU FICHERO 'resultado.txt' ESTARÁ EN: "+logfile.getCanonicalPath());
            if (mejorEnsayo.coste < (Integer.MAX_VALUE - 100)){
                for (int i = 0; i < ensayo.camino.size(); i++) {
                    writer.write(ensayo.camino.get(i) + "\n");
                }
                writer.write("0 \n");
                writer.write("Coste: "+auxPeso+"\n");
                writer.write("Ensayos analizados: "+index);
            }else{
                writer.write("Insatisfactible \n");
                writer.write("Ensayos analizados: "+index);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void TSP() {
        PriorityQueue<Ensayo> cola = new PriorityQueue<>();
        //Queue<Ensayo> cola = new LinkedList<Ensayo>();
        int lowerbound;
        mejorEnsayo = new Ensayo(nNodos, Integer.MAX_VALUE, null, null,0);

        int[] ensayoAct = new int[nNodos];
        ensayoAct[0] = 1;

        cola.add(new Ensayo(0, 0, ensayoAct, new ArrayList(),0));

        //branch and bound
        //mientras queden ensayos posiblen en la cola...
        while (!cola.isEmpty()) {
            //obtenemos el primer ensayo
            Ensayo actual = cola.remove();
            //System.out.println(actual.camino);
            //System.out.println("lowerBound: "+actual.coste);

            //si el coste del ensayo a tener en cuenta, ya es peor que el mejor que tenemos, parar.
            if (actual.coste < mejorEnsayo.coste){

                actual.camino.add(actual.destino);
                //System.out.println("se va a añadir: "+actual.destino);
                //System.out.println("_______");

                //para cada nodo 'i'...
                for (int i = 0; i < nNodos; i++) {
                    //si el destino de 'actual' no es el nodo 'i' en el que estamos,
                    //y existe arista desde el destino de 'actual' al nodo 'i' en el que estamos,
                    //y el nodo 'i' en el que estamos, aun no ha sido visitado en 'actual'.
                    if (actual.destino != i && matrizDist[actual.destino][i] < Integer.MAX_VALUE && actual.visitados[i] == 0) {
                        //System.out.println("Entro con: "+i);
                        actual.visitados[i]=1; //1 == visitado || 0 == no visitado
                        actual.camino.add(i); //añadimos el nodo al recorrido actual

                        index++; //aumentamos en 1 los ensayos creados.

                        //MST con los nodos no visitados
                        //Kruskal.resetFlags();
                        int mstV_S = Kruskal.Krusk(matrizDist, actual.visitados, nNodos, actual.destino);
                        //al peso del camino acumulado, le sumamos el peso del destino de 'actual', hasta el nodo 'i' en el que estamos
                        int acumCamino = actual.acumCamino + matrizDist[actual.destino][i];
                        System.out.println(actual.camino);
                        System.out.println(acumCamino);
                        //if(Kruskal.minCosteDeA != -1 && Kruskal.minCosteDeB != -1) {
                            lowerbound = mstV_S + Kruskal.minCosteDeA + Kruskal.minCosteDeB + acumCamino;
                        /*}
                        else{
                            System.out.println("HOLAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
                            lowerbound = -1;
                        }*/
                            System.out.println(lowerbound);
                            System.out.println("_______");

                            //si hemos recorrido tantos nodos como los que tiene el grafo
                            if (actual.camino.size() == nNodos) {
                                mejorEnsayo.coste = actual.coste;
                                mejorEnsayo.camino = (ArrayList) actual.camino.clone();  //actualizamos el camino del mejor ensayo
                                //auxPeso = matrizDist[actual.camino.get(actual.camino.size() - 1)][0] + actual.coste;
                                System.out.println("POSIBLE SOLUCION!");
                                System.out.println(mejorEnsayo.camino);
                                //System.out.println("peso: " + auxPeso);
                                System.out.println("peso: " + mejorEnsayo.coste);
                            }
                            //si el coste minimo estimado del ensayo actual, es menor que el del mejor ensayo
                            else if (lowerbound < mejorEnsayo.coste) {
                                actual.camino.remove(actual.camino.size() - 1); //eliminamos el ultimo nodo para seguir con el siguiente nodo
                                int[] visi = actual.visitados.clone(); //clonamos la lista de visitados
                                ArrayList tmp = (ArrayList) actual.camino.clone(); //guardamos en tmp una copia del ensayo actual
                                cola.add(new Ensayo(i, lowerbound, visi, tmp, acumCamino)); //añadimos un ensayo con estas caracteristicas guardadas, a la 'cola' para volver a evaluarlo
                            }
                            actual.visitados[i] = 0;//marcamos el nodo 'i' como no visitado
                        //}
                    }
                }
                actual.camino.remove(Integer.valueOf(actual.destino));
            }
        }
        System.out.println("_____________________");
        System.out.println("Cant ensayos: "+index);
        //System.out.println("Peso optimo:" +auxPeso);
        System.out.println("Peso optimo: "+mejorEnsayo.coste);
        System.out.println("Camino optimo: "+mejorEnsayo.camino);
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