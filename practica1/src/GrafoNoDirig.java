import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class GrafoNoDirig {

    private LinkedList<Nodo>[] listaAdy; //lista de adyacencia
    private int n; //número de nodos
    private int a; //número de aristas
    private ArrayList<Nodo> listaNodos;

    public GrafoNoDirig(){
        n = 0;
        a = 0;
    }

    public void crearGrafo(){
        int i = 2;
        int k = 0;
        String[] auxL = GestorFichero.getMyGestorFichero().getContenidoTxt();

        //listaAdy = new LinkedList[Integer.parseInt(auxL[0])+1];
        listaAdy = new LinkedList[GestorFichero.getMyGestorFichero().getContenidoTxt().length+1];
        System.out.println(GestorFichero.getMyGestorFichero().getContenidoTxt().length+"********************");
        n = GestorFichero.getMyGestorFichero().getContenidoTxt().length;
        a = Integer.parseInt(auxL[1]);

        while(i<auxL.length){
            String s = auxL[i];
            Integer nodoX = Integer.parseInt(s.split(" ")[0]);
            Integer nodoY = Integer.parseInt(s.split(" ")[1]);

            Nodo nx = new Nodo(nodoX);
            Nodo ny = new Nodo(nodoY);
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


        //listaNodos = GestorPpal.getMyGestorPpal().getListaNodos();
    }

    public void resetVisitados(){
        for(LinkedList<Nodo> l : listaAdy){
            if(l != null){
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
        resetVisitados();
        return res;
    }
     public ArrayList<Nodo> getListaNodos(){    //TENGO QUE RESOLVER LA DUDA DE RELLENAR EL ARRAY HASTA EL NUM MAS ALTO DEL TXT!!!!!!!!!!!!!!!!!!!!!!!!
        ArrayList<Nodo> lista = new ArrayList<>();
        for(LinkedList<Nodo> l : listaAdy){
            if(l != null){
                Nodo nodo = new Nodo(l.getFirst().getValor(), l.getFirst().getPeso());
                lista.add(nodo);
            }
        }
        return lista;
     }

    public LinkedList<Integer>[] componentesConexas(){
        listaNodos = GestorPpal.getMyGestorPpal().getListaNodos();
        LinkedList<Integer>[] compCon = new LinkedList[getListaNodos().size()];
        int k = 0;
        int pos = 0;
        resetVisitadosNodos();
        Queue<Nodo> recorridoAct = new LinkedList<Nodo>();

        while(pos < listaNodos.size()){
            compCon[pos] = new LinkedList<>();
            pos++;
        }

        for(Nodo n : listaNodos){
            if(!n.visitado()){
                n.setVisitado();
                recorridoAct.add(n);
                visitarConexas(n, compCon, k, recorridoAct);
                k++;
            }
        }

        System.out.println("_______");
        for(LinkedList<Integer> l : compCon){
            if(l.size()>0) {
                for (Integer i : l) {
                    System.out.println(i);
                }
                System.out.println("_______");
            }
        }
        return compCon;
    }

    public void resetVisitadosNodos(){
        for(Nodo n: listaNodos){
            n.setNoVisitado();
        }
    }

    public void visitarNodo(Nodo n){
        for(Nodo nodo : listaNodos){
            if(n.getValor() == nodo.getValor()){
                nodo.setVisitado();
            }
        }
    }

    public boolean estaVisitado(Nodo n){
        for(Nodo nodo : listaNodos){
            if (nodo.getValor() == n.getValor()) {
                return nodo.visitado();
            }
        }
        return true;
    }

    public void visitarConexas(Nodo n, LinkedList<Integer>[] compCon, int k, Queue<Nodo> q){
        compCon[k].add(n.getValor());
        while(!q.isEmpty()){
            Nodo u = q.remove();
            for(Nodo nodo : listaAdy[u.getValor()]){
                if(nodo.getValor() != listaAdy[u.getValor()].getFirst().getValor()) {
                    Nodo auxN = getNodoPorValor(nodo.getValor());
                    if (!estaVisitado(auxN)) {
                        compCon[k].add(auxN.getValor());
                        q.add(nodo);
                        visitarNodo(auxN);
                    }
                }
            }
        }
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

    public void printGrafo(){

        for(LinkedList<Nodo> l : listaAdy){
            if(l != null) {
                System.out.println("\n[*]Vecinos del nodo " + l.getFirst().getValor() + ":");
                for (Nodo n : l) {
                    System.out.print(n.getValor() + " | ");
                }
            }

        }
        System.out.println();
    }


}
