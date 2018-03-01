import java.util.LinkedList;

public class GrafoDirigPes {

    private LinkedList<Nodo>[] listaAdy; //lista de adyacencia
    private int n; //número de nodos
    private int a; //número de aristas

    public GrafoDirigPes(){
        n = 0;
        a = 0;
    }

    public void crearGrafo(){
        int i = 2;
        int k = 0;
        String[] auxL = GestorFichero.getMyGestorFichero().getContenidoTxt();

        //listaAdy = new LinkedList[Integer.parseInt(auxL[0])];
        listaAdy = new LinkedList[GestorFichero.getMyGestorFichero().getContenidoTxt().length];
        n = listaAdy.length;
        a = Integer.parseInt(auxL[1]);

        while(k<n){ //Inicializamos todas las LinkedLists
            listaAdy[k] = new LinkedList<>();
            listaAdy[k].addFirst(new Nodo(k));
            k++;
        }

        while(i<auxL.length){
            String s = auxL[i];
            Integer nodoX = Integer.parseInt(s.split(" ")[0]);
            Integer nodoY = Integer.parseInt(s.split(" ")[1]);
            float p = Integer.parseInt(s.split(" ")[2]);

            Nodo nx = new Nodo(nodoY, p);
            listaAdy[nodoX].add(nx);
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
                    System.out.println("[*]mandamos para el ciclo el nodo: " + l.getFirst().getValor());
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
            if(l.size()>1) {
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
