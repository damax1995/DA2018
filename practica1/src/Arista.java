public class Arista {

    private Nodo nodoX;
    private Nodo nodoY;
    private float peso;


    public Arista(Nodo nx, Nodo ny, float pvalor){
        nodoX = nx;
        nodoY = ny;
        peso = pvalor;
    }

    public float getPeso(){
        return peso;
    }

    public Nodo getNodoX(){
        return nodoX;
    }

    public Nodo getNodoY(){
        return nodoY;
    }

}
