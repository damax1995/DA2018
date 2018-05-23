import java.util.ArrayList;

public class Ensayo implements Comparable<Ensayo>, Cloneable{

    //a será el origen, asi que su valor sera 0
    int destino;//b
    int coste;
    int[] visitados;//S
    ArrayList<Integer> camino;
    int acumCamino;

    public Ensayo(int destino, int coste, int[] visitados, ArrayList<Integer> camino, int acumCamino) {
        this.destino = destino;
        this.coste = coste;
        this.visitados = visitados;
        this.camino = camino;
        this.acumCamino = acumCamino;
    }

    @Override
    public int compareTo(Ensayo o) {
        return this.coste - o.coste;
    }

    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
