import java.util.ArrayList;
import java.util.LinkedList;

public class GrafoNoDirig {

    private LinkedList<Nodo>[] listaAdy; //lista de adyacencia
    private int n; //número de nodos
    private int a; //número de aristas

    public GrafoNoDirig(){
        n = 0;
        a = 0;
    }

    public void crearGrafo(){
        int i = 2;
        int k = 0;
        String[] auxL = GestorFichero.getMyGestorFichero().getContenidoTxt();

        //listaAdy = new LinkedList[Integer.parseInt(auxL[0])+1];
        listaAdy = new LinkedList[GestorFichero.getMyGestorFichero().getContenidoTxt().length];
        n = GestorFichero.getMyGestorFichero().getContenidoTxt().length;
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

            Nodo nx = new Nodo(nodoY);
            Nodo ny = new Nodo(nodoX);

            listaAdy[nodoX].add(nx);
            listaAdy[nodoY].add(ny);
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

    public boolean tieneCicloND(){
        boolean res = false;
        boolean primera = true;

        for(LinkedList<Nodo> l : listaAdy){
            if(!res) {
                if (l.size() > 1) {
                    System.out.println("[*]mandamos para el ciclo el nodo: " + l.getFirst().getValor());
                    res = profundidadCicloNoDirig(l.getFirst(), l.getFirst().getValor(), primera, -1);
                }
            }
        }
        System.out.println();
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
        resetVisitadoCiclos();
        return res;
    }
     public ArrayList<Nodo> getListaNodos(){
        ArrayList<Nodo> lista = new ArrayList<>();
        for(LinkedList<Nodo> l : listaAdy){
            if(l.size()>1 && l != null){ //Si hay algun elemento mas a parte de él mismo, pertenece al grafo,
                            //si solo esta el propio nodo en su lista, quiere decir que ese valor no corresponde a ningun grafo, y no sera considerado como nodo
                Nodo nodo = new Nodo(l.getFirst().getValor(), l.getFirst().getPeso());
                lista.add(nodo);
            }

        }
        return lista;
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
