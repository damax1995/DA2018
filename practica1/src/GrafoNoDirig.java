import java.util.LinkedList;

public class GrafoNoDirig {

    private LinkedList<Integer>[] listaAdy; //lista de adyacencia
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
        n = listaAdy.length;
        a = Integer.parseInt(auxL[1]);

        while(k<n){ //Inicializamos todas las LinkedLists
            listaAdy[k] = new LinkedList<>();
            k++;
        }

        while(i<auxL.length){
            String s = auxL[i];
            Integer nodoX = Integer.parseInt(s.split(" ")[0]);
            Integer nodoY = Integer.parseInt(s.split(" ")[1]);

            listaAdy[nodoX].add(nodoY);
            listaAdy[nodoY].add(nodoX);
            i++;
        }
    }

    public void printGrafo(){
        int k = 0;
        for(LinkedList<Integer> l : listaAdy){
            if(l.size()>0) {
                System.out.println("\n[*]Vecinos del nodo " + k + ":");
                for (Integer n : l) {
                    System.out.print(n + " | ");
                }
            }
            k++;
        }
        System.out.println();
    }


}
