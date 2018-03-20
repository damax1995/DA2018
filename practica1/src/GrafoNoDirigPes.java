import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.LinkedList;

public class GrafoNoDirigPes {

    private LinkedList<Nodo>[] listaAdy; //lista de adyacencia
    private ArrayList<Nodo> listaNodos;
    private int n; //número de nodos
    private int a; //número de aristas
    private ArrayList<Arista> listaAristas; //En este caso he utilizado un ArrayList, porque he reciclado el MergeSort realizado en la primera practica,
                                            //y este estaba implementado con esta estructura.
    public GrafoNoDirigPes(){
        n = 0;
        a = 0;
    }

    public void crearGrafo(){
        int i = 2;
        int k = 0;
        String[] auxL = GestorFichero.getMyGestorFichero().getContenidoTxt();

        listaAdy = new LinkedList[GestorFichero.getMyGestorFichero().getContenidoTxt().length+1];
        n = listaAdy.length;
        a = Integer.parseInt(auxL[1]);
        listaAristas = new ArrayList<>();

        while(i<auxL.length){
            String s = auxL[i];
            Integer nodoX = Integer.parseInt(s.split(" ")[0]);
            Integer nodoY = Integer.parseInt(s.split(" ")[1]);
            float peso = Float.parseFloat(s.split(" ")[2]);

            Nodo nx = new Nodo(nodoX, peso);
            Nodo ny = new Nodo(nodoY, peso);
            if(listaAdy[nodoX] == null){
                listaAdy[nodoX] = new LinkedList<>();
                listaAdy[nodoX].addFirst(nx);
            }
            if(listaAdy[nodoY] == null){
                listaAdy[nodoY] = new LinkedList<>();
                listaAdy[nodoY].addFirst(ny);
            }

            listaAdy[nodoX].add(ny);
            listaAdy[nodoY].add(nx);
            i++;
        }
    }

    public void resetVisitados(){
        for(LinkedList<Nodo> l : listaAdy){
            if(l.size() > 1){
                for(Nodo n : l){
                    n.setNoVisitado();
                }
            }
        }
    }

    public boolean tieneCicloND(){
        boolean res = false;
        boolean primera = true;

        for(LinkedList<Nodo> l : listaAdy){
            if(!res) {
                if (l.size() > 1) {
                    System.out.println("[*]Comprobamos si tiene ciclo en el nodo: " + l.getFirst().getValor());
                    res = profundidadCicloNoDirig(l.getFirst(), l.getFirst().getValor(), primera, -1);
                }
            }
        }

        return res;
    }

    public boolean profundidadCicloNoDirig(Nodo nodo, int elem, boolean primera, int padre){
        boolean res = false;
        nodo.setVisitado();

        for(Nodo n : listaAdy[nodo.getValor()]){
            if(listaAdy[nodo.getValor()].size() > 2) {
                if (n.getValor() != listaAdy[nodo.getValor()].getFirst().getValor() && n.getValor() != padre && !n.visitado() && !res) {
                    System.out.println("\tcomprobamos nodo: " + n.getValor() + ". Y ciclo con: " + elem);
                    if (n.getValor() == elem) {
                        res = true;
                    } else {
                        res = profundidadCicloNoDirig(n, elem, false, nodo.getValor());
                    }
                }
            }
        }
        resetVisitados();
        return res;
    }

    public Integer getIndiceNodo(Integer valor){
        boolean flag = false;
        int k = 0;
        for(Nodo n : listaNodos){
            if(!flag){
                if(n.getValor() == valor){
                    flag = true;
                }
                else{
                    k++;
                }
            }
        }
        return k;
    }

    public Nodo getNodoPorValor(int valor){
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

    public void mostrarNodos(){
        for(Nodo n : listaNodos){
            System.out.println("Nodo "+n.getValor()+". Con padre: "+n.getPadre()+". Con root: "+getRoot(n));
        }
    }

    public void MSTKruskal(){
        listaNodos = GestorPpal.getMyGestorPpal().getListaNodos();

        for(Nodo n : listaNodos){ //Esto corresponderia a la llamada a makeset() ya que decimos que el padre del nodo es el propio nodo y su rank es 0, por lo qu ese creara un arbol con un unico elemento por cada nodo
            n.setPadre(n.getValor());
            n.setRank(0);
        }

        System.out.println("INICIAL");
        mostrarNodos();

        setListaAristas();
        ordenarListaAristas();

        ArrayList<Arista> recorrido = new ArrayList<>();



        for(Arista ar : listaAristas){
            Nodo nx = getNodoPorValor(ar.getNodoX().getValor()); //Obtenemos el nodo de listaNodos cuyo indice es indiceX
            Nodo ny = getNodoPorValor(ar.getNodoY().getValor()); //Obtenemos el nodo de listaNodos cuyo indice es indiceY

            System.out.println("Miramos-> root de "+nx.getValor()+": "+getRoot(nx)+"| root de "+ny.getValor()+": "+getRoot(ny));

            if(getRoot(nx) != getRoot(ny)){
                recorrido.add(ar);
                union(nx, ny);
            }
        }

        writeFile(recorrido);
    }

    public void union(Nodo nodoX, Nodo nodoY){
        Integer rootX = getRoot(nodoX);
        Integer rootY = getRoot(nodoY);
        Nodo nx = getNodoPorValor(rootX);
        Nodo ny = getNodoPorValor(rootY);

        System.out.println("rootX: "+rootX+" | rootY: "+rootY);
        System.out.println("Nodo x: "+nx.getValor()+" | Nodo y: "+ny.getValor());

        if(nx.getRank() > ny.getRank()){
            nx.setPadre(nodoY.getValor());
        }
        else{
            ny.setPadre(nodoX.getValor());
            System.out.println(ny.getPadre()+"!!!!!!!");
            System.out.println(nx.getPadre()+"!!!!!!!!!");
            if(nx.getRank() == ny.getRank()){
                ny.aumRank();
            }
        }
        mostrarNodos();
    }

    public float getSumaPesos(ArrayList<Arista> l){
        float suma = 0;
        for(Arista a : l){
            suma += a.getPeso();
        }
        return suma;
    }

    public void writeFile(ArrayList<Arista> lista){
        BufferedWriter writer = null;
        try {
            //create a temporary file
            File logFile = new File("MSTKruskal.txt");

            // This will output the full path where the file will be written to...
            System.out.println("TU FICHERO 'MSTKruskal.txt' ESTARÁ EN: "+logFile.getCanonicalPath());

            writer = new BufferedWriter(new FileWriter(logFile));
            writer.write(lista.size()+"\n");
            writer.write(Float.toString(getSumaPesos(lista))+"\n");
            for(Arista a : lista){
                writer.write(a.getNodoX().getValor()+"->"+a.getNodoY().getValor()+":"+a.getPeso()+"\n");
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


    public int getRoot(Nodo n){
        if(n.getPadre() == n.getValor()){
            return n.getValor();
        }
        else{
            return getRoot(getNodoPorValor(n.getPadre()));
        }
    }

    public void setListaAristas(){
        ArrayList<Integer> revisados = new ArrayList<>();

        for(LinkedList<Nodo> l : listaAdy){
            if(l != null) {
                for (Nodo n : l) {
                    if (l.getFirst() != n && !revisados.contains(n.getValor())) { //La comprobacion de revisamos la hacemos para no tener el doble de aristas
                        Arista auxA = new Arista(l.getFirst(), n, n.getPeso());     //ya que el grafo es no dirigido.
                        listaAristas.add(auxA);
                    }
                }
                revisados.add(l.getFirst().getValor());
            }
        }
    }

    public void ordenarListaAristas(){ //Nos ordenara la lista de aristas de menor a mayor peso
        if(listaAristas.size()>1){
            this.mergeSort(listaAristas, 0, listaAristas.size()-1);
        }
    }

    private void mergeSort(ArrayList<Arista> l, int inicio, int fin){
        if(inicio<fin){
            mergeSort(l, inicio, (inicio+fin)/2);
            mergeSort(l, ((inicio+fin)/2)+1, fin);
            mezcla(l, inicio, (inicio+fin)/2, fin);
        }
    }

    private void mezcla(ArrayList<Arista> l, int inicio, int medio, int fin){
        Arista[] auxL = new Arista[l.size()];
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


    public void printGrafo(){
        int k = 0;
        for(LinkedList<Nodo> l : listaAdy){
            if(l != null) {
                System.out.println("\n[*]Vecinos del nodo " + k + ":");
                for (Nodo n : l) {
                    System.out.print("v: " + n.getValor() + ", p: " + n.getPeso() + " | ");
                }
            }
            k++;
        }
        System.out.println();
    }

}
