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

        listaAdy = new LinkedList[Integer.parseInt(auxL[0])];
        n = listaAdy.length;
        a = Integer.parseInt(auxL[1]);

        while(k<n){ //Inicializamos todas las LinkedLists
            listaAdy[k] = new LinkedList<>();
            k++;
        }

        while(i<auxL.length){
            String s = auxL[i];
            Integer nodoX = Integer.parseInt(s.split(",")[0])-1;
            Integer nodoY = Integer.parseInt(s.split(",")[1])-1;
            float p = Integer.parseInt(s.split(",")[2]);

            Nodo nx = new Nodo(nodoY, p);
            listaAdy[nodoX].add(nx);
            i++;
        }
    }

    public void printGrafo(){
        int k = 0;
        for(LinkedList<Nodo> l : listaAdy){
            System.out.println("\n[*]Vecinos del nodo "+k+":");
            for(Nodo n : l){
                System.out.print("v: "+n.getValor()+", p: "+n.getPeso()+" | ");
            }
            k++;
        }
    }

}
