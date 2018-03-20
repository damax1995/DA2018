public class Nodo {

    private int valor;
    private float peso;
    private boolean visitado;
    private int padre; //Lo guardo como integer, ya que me refiero al valor del nodoPadre, ya que con nodos fallaban las referencias.
    private int rank;

    public Nodo(int v){
        valor = v;
        visitado = false;
        peso = 0;
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

    public void setPadre(Integer n){
        this.padre = n;
    }

    public void setRank(int pRank){
        rank = pRank;
    }

    public void aumRank(){
        rank++;
    }

    public int getRank(){
        return rank;
    }

    public int getPadre(){
        return padre;
    }

}
