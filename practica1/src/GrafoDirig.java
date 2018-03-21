import java.lang.reflect.Array;
import java.util.*;

public class GrafoDirig {

    private LinkedList<Nodo>[] listaAdy; //lista de adyacencia
    private int numNodos; //número de nodos
    private int a; //número de aristas
    int pos = 0; //sera el indice utilizado para el orden topologico
    int index = 0;
    int posFC = 0;

    public GrafoDirig(){
        numNodos = 0;
        a = 0;
    }

    public void crearGrafo(){
        int i = 2;
        int k = 0;
        String[] auxL = GestorFichero.getMyGestorFichero().getContenidoTxt();

        numNodos = Integer.parseInt(auxL[0]);

        pos = numNodos-1;

        a = Integer.parseInt(auxL[1]);
        listaAdy = new LinkedList[numNodos];

        while(k<numNodos){
            listaAdy[k] = new LinkedList<>();
            listaAdy[k].add(new Nodo(k));
            k++;
        }

        while(i<auxL.length){
            String s = auxL[i];
            Integer nodoX = Integer.parseInt(s.split(" ")[0]);
            Integer nodoY = Integer.parseInt(s.split(" ")[1]);

            Nodo ny = new Nodo(nodoY);

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
                if (l != null) {
                    //System.out.println("[*]Comprobamos si tiene ciclo en el nodo: " + l.getFirst().getValor());
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
                //System.out.println("\tcomprobamos nodo: "+n.getValor()+". Y ciclo con: "+elem);
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
        int[] res = new int[numNodos];

        for(LinkedList<Nodo> l : listaAdy){
            l.getFirst().setNoVisitado();
        }

        for(LinkedList<Nodo> l :listaAdy){
            Nodo n = l.getFirst();
            if(!n.visitado()){
                ordenTopo(res, n);
            }
        }

        return res;
    }

    public void ordenTopo(int[] res, Nodo n){
        listaAdy[n.getValor()].getFirst().setVisitado();

        for(Nodo auxNodo : listaAdy[n.getValor()]){
            if(auxNodo != listaAdy[n.getValor()].getFirst()) {
                if (!listaAdy[auxNodo.getValor()].getFirst().visitado()) {
                    ordenTopo(res, auxNodo);
                }
            }
        }
        res[pos] = n.getValor();
        pos--;
    }


    public LinkedList<Integer>[] componentesFuertementeConexas(){
        Stack<Integer> s = new Stack<>();
        LinkedList<Integer>[] lista = new LinkedList[numNodos];
        for(LinkedList<Nodo> l : listaAdy){
            Nodo n = l.getFirst();
            if(n.getIndex() == -1){
                scc(n, lista, s);
            }
        }
        return lista;
    }

    public void scc(Nodo n, LinkedList<Integer>[] lista, Stack<Integer> s){
        listaAdy[n.getValor()].getFirst().setIndex(index);
        listaAdy[n.getValor()].getFirst().setLowlink(index);
        index++;

        s.push(n.getValor());

        for(Nodo auxN : listaAdy[n.getValor()]){
            if(auxN != listaAdy[n.getValor()].getFirst()){
                if(auxN.getIndex() == -1){
                    scc(listaAdy[auxN.getValor()].getFirst(), lista, s);
                    listaAdy[n.getValor()].getFirst().setLowlink(minimo(listaAdy[n.getValor()].getFirst().getValor(), listaAdy[auxN.getValor()].getFirst().getValor()));
                }
                else if(s.contains(auxN.getValor())){
                    listaAdy[n.getValor()].getFirst().setLowlink(minimo(listaAdy[n.getValor()].getFirst().getLowlink(), listaAdy[n.getValor()].getFirst().getIndex()));
                }
            }
        }

        if(listaAdy[n.getValor()].getFirst().getLowlink() == listaAdy[n.getValor()].getFirst().getIndex()){
            LinkedList<Integer> auxL = new LinkedList<>();
            while(!s.isEmpty() && s.peek()!= n.getValor()){
                auxL.add(s.pop());
            }
            lista[posFC] = auxL;
            posFC++;
        }
    }

    public int minimo(int a, int b){
        if(a>b){
            return a;
        }
        else{
            return b;
        }
    }

    public void printGrafo(){
        int k = 0;
        for(LinkedList<Nodo> l : listaAdy){
            if(l != null) {
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
