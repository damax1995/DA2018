public class Nodo {

    private int valor;
    private float peso;

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
}
