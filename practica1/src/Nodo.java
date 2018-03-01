public class Nodo {

    private int valor;
    private float peso;
    private Nodo next;
    private boolean visitadoCiclo;

    public Nodo(int v){
        valor = v;
        visitadoCiclo = false;
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
        return visitadoCiclo;
    }

    public void setVisitado(){
        visitadoCiclo = true;
    }

    public void setNoVisitado(){
        visitadoCiclo = false;
    }

    public void setNext(Nodo n){
        next = n;
    }
}
