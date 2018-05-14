import java.util.ArrayList;

public class Ensayo implements Comparable<Ensayo>, Cloneable{
    //a es 0 siempre, asi que pa que guardar
    int id;//b
    int coste;
    int[] visitados;//S
    ArrayList camino;
    int acumCamino;

    public Ensayo(int id, int coste, int[] visitados, ArrayList camino, int acumCamino) {
        this.id = id;
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
