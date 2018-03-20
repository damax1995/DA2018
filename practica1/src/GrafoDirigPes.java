import java.util.ArrayList;
import java.util.LinkedList;

public class GrafoDirigPes {

    private LinkedList<Nodo>[] listaAdy; //lista de adyacencia
    private int n; //número de nodos
    private int a; //número de aristas
    private ArrayList<Arista> listaAristas;

    public GrafoDirigPes(){
        n = 0;
        a = 0;
    }

    public void crearGrafo(){
        int i = 2;
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
            i++;
        }
    }

    public void resetVisitadoCiclos(){
        for(LinkedList<Nodo> l : listaAdy){
            if(l != null){
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
