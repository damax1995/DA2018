public class Nodo {

    private int valor;
    private float peso;
    private Nodo next;
    private boolean visitado;

    public Nodo(int v){
        valor = v;
        visitado = false;
    }

    public Nodo(int v, float p){
        valor = v;
        peso = p;
    }

    public int getValor(){
        return valor;
    }

    public float getPeso() {
        return peso;
    }

    public void setValor(int v){
        valor = v;
    }

    public void setPeso(float p){
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
