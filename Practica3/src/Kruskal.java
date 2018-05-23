import java.util.ArrayList;

public class Kruskal {

    private static Kruskal myKruskal;
    private int minDistOr;
    private int minDistDest;

    private Kruskal(){

    }

    public static Kruskal getMyKruskal(){
        if(myKruskal == null){
            myKruskal = new Kruskal();
        }
        return myKruskal;
    }

    public int getMinDistOr(){
        return minDistOr;
    }

    public int getMinDistDest(){
        return minDistDest;
    }

    public int MSTKruskal(ArrayList<Nodo> listaNodos, int[] visitados, int[][] matrizDist, int nNodos, int destino){
        minDistDest = Integer.MAX_VALUE;
        minDistOr = Integer.MAX_VALUE;

        ArrayList<Edge> listaAristas = new ArrayList<>();

        for(int i = 0; i < nNodos; i++){
            for(int j = i + 1; j < nNodos; j++) {
                if(visitados[i] == 0 && visitados[j] == 0){
                    if (matrizDist[i][j] != -1){
                        if (matrizDist[0][j] < minDistOr && visitados[j]==0 && matrizDist[0][j] != -1){
                            minDistOr = matrizDist[0][j];
                        }
                        if (matrizDist[destino][j] < minDistDest && visitados[j]==0 && matrizDist[destino][j] != -1){
                            minDistDest = matrizDist[destino][j];
                        }
                        listaAristas.add(new Edge(i, j, matrizDist[i][j]));
                    }
                }
            }
        }

        ordenarListaAristas(listaAristas);

        if(listaAristas.size() == 0){
            for(int j = 0; j<nNodos; j++){
                if(matrizDist[0][j] < minDistOr && visitados[j] == 0){
                    minDistOr = matrizDist[0][j];
                }
                if(matrizDist[destino][j] < minDistDest && visitados[j] == 0){
                    minDistDest = matrizDist[destino][j];
                }
            }
        }



        for(Nodo n : listaNodos){ //Esto corresponderia a la llamada a makeset() ya que decimos que el padre del nodo es el propio nodo y su rank es 0, por lo qu ese creara un arbol con un unico elemento por cada nodo
            n.setPadre(n.getValor());
            n.setRank(0);
        }

        ArrayList<Edge> recorrido = new ArrayList<>();

        for(Edge ar : listaAristas){
            Nodo nx = getNodoPorValor(ar.getOr(), listaNodos); //Obtenemos el nodo de listaNodos cuyo indice es indiceX
            Nodo ny = getNodoPorValor(ar.getDest(), listaNodos); //Obtenemos el nodo de listaNodos cuyo indice es indiceY

            if(getRoot(nx, listaNodos) != getRoot(ny, listaNodos)){
                recorrido.add(ar);
                union(nx, ny, listaNodos);
            }
        }

        int suma = 0;
        //System.out.println("aristas del mst:");
        for(Edge e : recorrido){
            //System.out.println(e.or+"-"+e.dest+":"+e.getPeso());
            suma += e.getPeso();
        }
        return suma;
    }

    public void union(Nodo nodoX, Nodo nodoY, ArrayList<Nodo> listaNodos){
        Integer rootX = getRoot(nodoX, listaNodos);
        Integer rootY = getRoot(nodoY, listaNodos);
        Nodo nx = getNodoPorValor(rootX, listaNodos);
        Nodo ny = getNodoPorValor(rootY, listaNodos);

        if(nx.getRank() > ny.getRank()){
            nx.setPadre(nodoY.getValor());
        }
        else{
            ny.setPadre(nodoX.getValor());
            if(nx.getRank() == ny.getRank()){
                ny.aumRank();
            }
        }
    }

    public int getRoot(Nodo n, ArrayList<Nodo> listaNodos){
        if(n.getPadre() == n.getValor()){
            return n.getValor();
        }
        else{
            return getRoot(getNodoPorValor(n.getPadre(), listaNodos), listaNodos);
        }
    }

    public Nodo getNodoPorValor(int valor, ArrayList<Nodo> listaNodos){
        boolean flag = false;
        Nodo auxN = null;
        for(Nodo n : listaNodos){
            if(!flag){
                auxN = n;
                if(n.getValor() == valor){
                    flag = true;
                }
            }
        }
        return auxN;
    }

    public void ordenarListaAristas(ArrayList<Edge> listaAristas){ //Nos ordenara la lista de aristas de menor a mayor peso
        if(listaAristas.size()>1){
            this.mergeSort(listaAristas, 0, listaAristas.size()-1);
        }
    }

    private void mergeSort(ArrayList<Edge> l, int inicio, int fin){
        if(inicio<fin){
            mergeSort(l, inicio, (inicio+fin)/2);
            mergeSort(l, ((inicio+fin)/2)+1, fin);
            mezcla(l, inicio, (inicio+fin)/2, fin);
        }
    }

    private void mezcla(ArrayList<Edge> l, int inicio, int medio, int fin){
        Edge[] auxL = new Edge[l.size()];
        int izq = inicio; //izq no podra pasar de medio
        int der = medio+1; //der no podra pasar del limite
        int k = 0;
        //Tenemos el array dividido en 2, con un indicador en el principio de cada mitad.
        while(izq<=medio && der<=fin){      //Aquí comparamos cual de los dos numeros es menor, y lo colocamos en la lista auxiliar.
            if(l.get(izq).getPeso() < l.get(der).getPeso()){
                auxL[k] = l.get(izq);
                izq++;
            }
            else{
                auxL[k] = l.get(der);
                der++;
            }
            k++;
        }
        //Llegados a este punto, izq o der estaran en el limite,
        if(izq > medio){                    //asi que quedaria volcar el contenido restante a la lista auxiliar
            while(der<=fin){
                auxL[k] = l.get(der);
                der++;
                k++;
            }
        }
        else{
            while(izq<=medio){
                auxL[k] = l.get(izq);
                izq++;
                k++;
            }
        }

        k=0;
        for(int j=inicio; j<=fin; j++){     //Sustituimos el array desordenado que se recibe, por el auxiliar ordenado que hemos obtenido.
            l.set(j, auxL[k]);
            k++;
        }
    }


    static class Edge {
        int or, dest, peso;

        public Edge(int or, int dest, int peso) {
            this.or = or;
            this.dest = dest;
            this.peso = peso;
        }

        @Override
        public String toString() {
            return "(" + or + ", " + dest + ", " + peso + ")";
        }

        public int getPeso(){
            return peso;
        }

        public int getOr() {
            return or;
        }

        public int getDest() {
            return dest;
        }
    }


}
