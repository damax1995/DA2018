public class Nodo {

    //EN REALIDAD MAS QUE 'NODO', CREO QUE ESTA CLASE DEBERIA LLAMARSE 'ARISTA' o algo del estilo.

    private int origen;
    private int destino;
    private int peso;
    private Nodo next;
    private boolean visitado;

    public Nodo(int o, int d, int p){
        origen = o;
        destino = d;
        peso = p;
        visitado = false;
    }

    public int getOrigen() {
        return origen;
    }

    public void setOrigen(int origen) {
        this.origen = origen;
    }

    public int getDestino(){
        return destino;
    }

    public int getPeso() {
        return peso;
    }

    public void setDestino(int v){
        destino = v;
    }

    public void setPeso(int p){
        peso = p;
    }

    public boolean visitado(){
        return visitado;
    }

    public void setVisitado(){
        visitado = true;
    }

    public void setNoVisitado(){
        visitado = false;
    }

    public void setNext(Nodo n){
        next = n;
    }
}
