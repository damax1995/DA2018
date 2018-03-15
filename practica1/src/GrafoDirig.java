import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class GrafoDirig {

    private LinkedList<Nodo>[] listaAdy; //lista de adyacencia
    private int numNodos; //número de nodos
    private int a; //número de aristas
    int pos = Integer.parseInt(GestorFichero.getMyGestorFichero().getContenidoTxt()[0]); //sera el indice utilizado para el orden topologico

    public GrafoDirig(){
        numNodos = 0;
        a = 0;
    }

    public void crearGrafo(){
        int i = 2;
        int k = 0;
        String[] auxL = GestorFichero.getMyGestorFichero().getContenidoTxt();

        listaAdy = new LinkedList[GestorFichero.getMyGestorFichero().getContenidoTxt().length];
        numNodos = listaAdy.length;
        a = Integer.parseInt(auxL[1]);

        while(k<numNodos){ //Inicializamos todas las LinkedLists
            listaAdy[k] = new LinkedList<>();
            listaAdy[k].addFirst(new Nodo(k)); //añadimos el propio nodo como primer adyacente
            k++;
        }

        while(i<auxL.length){
            String s = auxL[i];
            Integer nodoX = Integer.parseInt(s.split(" ")[0]);
            Integer nodoY = Integer.parseInt(s.split(" ")[1]);

            //Nodo npadre = new Nodo(nodoX);
            Nodo nx = new Nodo(nodoY);
            listaAdy[nodoX].add(nx);
            //nx.setPadre(npadre);
            i++;
        }
    }

    public void resetVisitadoCiclos(){
        for(LinkedList<Nodo> l : listaAdy){
            if(l.size() > 1){
                for(Nodo n : l){
                    n.setNoVisitado();
                }
            }
        }
    }

    public boolean tieneCicloD(){
        boolean res = false;

        for(LinkedList<Nodo> l : listaAdy){
            if(!res) {
                if (l.size() > 1) {
                    System.out.println("[*]Comprobamos si tiene ciclo en el nodo: " + l.getFirst().getValor());
                    res = profundidadCicloDirig(l.getFirst(), l.getFirst().getValor());
                }
            }
        }
        System.out.println();
        return res;
    }

    public boolean profundidadCicloDirig(Nodo nodo, int elem){
        boolean res = false;
        nodo.setVisitado();

        for(Nodo n : listaAdy[nodo.getValor()]){
            if(n != listaAdy[nodo.getValor()].getFirst() && !n.visitado() && !res) {
                System.out.println("\tcomprobamos nodo: "+n.getValor()+". Y ciclo con: "+elem);
                if (n.getValor() == elem) {
                    res = true;
                } else {
                    res = profundidadCicloDirig(n, elem);
                }
            }
        }
        resetVisitadoCiclos();
        return res;
    }

    public int[] ordenTopologico(){
        int[] res = new int[pos];
        ArrayList<Nodo> auxLista = GestorPpal.getMyGestorPpal().getListaNodos();

        for(Nodo nodo : auxLista){
            nodo.setNoVisitado();
        }

        for(Nodo n : auxLista){
            if(!n.visitado()){
                ordenTopo(res, auxLista, n);
            }
        }

        return res;
    }

    public void ordenTopo(int[] res, ArrayList<Nodo> auxLista, Nodo n){
        auxLista.get(auxLista.indexOf(n)).setVisitado();

        for(Nodo auxNodo : listaAdy[n.getValor()]){
            int pos = 0;
            boolean flag = false;
            for(Nodo n2 : auxLista){
                if(!flag) {
                    if (n2.getValor() != auxNodo.getValor()){
                        pos++;
                    }
                    else{
                        flag = true;
                    }
                }
            }

            Nodo n3 = auxLista.get(pos);
            if(!n3.visitado()){
                ordenTopo(res, auxLista, n3);
            }
        }
        res[pos-1] = n.getValor();
        pos--;
    }

    public void printGrafo(){
        int k = 0;
        for(LinkedList<Nodo> l : listaAdy){
            if(l.size()>1) {
                System.out.println("\n[*]Vecinos del nodo " + k + ":");
                for (Nodo n : l) {
                    System.out.print(n.getValor() + " | ");
                }
            }
            k++;
        }
        System.out.println();
    }

}
