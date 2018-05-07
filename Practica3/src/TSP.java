import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class TSP {

    private static TSP myTSP;
    private LinkedList<Nodo>[] listaAdy;
    private Nodo[] mejorEnsayo;
    private int mejorPeso;
    int n;
    int a;

    private TSP(){

    }

    public static TSP getMyTSP(){
        if(myTSP == null){
            myTSP = new TSP();
        }
        return myTSP;
    }

    public void readFile() throws FileNotFoundException {
        int k = 0;

        File f = new File("lista80.txt");

        List<String> lines = new BufferedReader(new FileReader(f)).lines().collect(Collectors.toList()); //Leemos el txt

        String l = lines.get(5);
        n = Integer.parseInt(l.split(" ")[0]);
        a = Integer.parseInt(l.split(" ")[1]);

        for(LinkedList<Nodo> lista : listaAdy){
            if(lista == null){
                lista = new LinkedList<Nodo>();
            }
        }

        int i = 6;
        while(i<lines.size()){
            String s = lines.get(i);
            Nodo nodo = new Nodo(Integer.parseInt(s.split(" ")[0]), Integer.parseInt(s.split(" ")[1]), Integer.parseInt(s.split(" ")[2]));
            listaAdy[Integer.parseInt(s.split(" ")[0])].add(nodo);
            i++;
        }

        mejorEnsayo = null;
        mejorPeso = 999999999;

    }

    public void iniciarTSP(){

        Nodo[] ensayoAct = null;
        TSP(0, 0, 0, ensayoAct, 0);
    }

    public void TSP(int nOrigen, int nodosVisit, int ultimoNodo, Nodo[] ensayoAct, int pesoAct) {

        if(nodosVisit == n){ //si hemos visitado tantos nodos como nodos totales en el grafo, es una posible solucion
            Nodo nodoAux = buscarNodoListaAdy(nOrigen, ultimoNodo);
            if(nodoAux != null){ // si el ultimo nodo visitado, es adyacente del origen, es posible solucion
                if(pesoAct + nodoAux.getPeso() < mejorPeso){ // si el peso de la posible solucion, es mas optimo que el que teniamos, es mejor solucion.
                    mejorPeso = pesoAct + nodoAux.getPeso();
                    mejorEnsayo = ensayoAct;
                }
            }
        }
        else{ //si no es posible solucion
            for(Nodo auxN : listaAdy[ultimoNodo]){ //iteramos por todos los nodos adyacentes al ultimo
                if(!auxN.visitado() && pesoAct + auxN.getPeso() < mejorPeso){
                    auxN.setVisitado(); //seteamos visitado el nodo actual
                    ensayoAct[nodosVisit] = auxN; //añadimos el nodo actual al ensayo
                    TSP(0, nodosVisit+1, auxN.getDestino(), ensayoAct, pesoAct + auxN.getPeso());
                    auxN.setNoVisitado(); //revertimos el cambio para que en vez de auxN, sea auxN + 1 el siguiente nodo seleccionado, y estudiamos lo que ocurre
                }
            }
        }

    }

    public Nodo buscarNodoListaAdy(int n1, int n2){ //buscar si n2 es adyacente de n1.
        boolean flag = false;
        Nodo auxN = null;
        for(Nodo nodo : listaAdy[n1]){
            if(!flag){
                if(nodo.getDestino() == n2){
                    flag = true;
                    auxN = nodo;
                }
            }
        }
        if(flag) {
            return auxN;
        }
        else{
            return null;
        }
    }

}
