public class Solicitante {

    private float dinero;
    private float peso;
    private int id;

    public Solicitante(float d, float p){
        dinero = d;
        peso = p;
    }

    public float getDinero() {
        return dinero;
    }

    public void setDinero(float dinero) {
        this.dinero = dinero;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }
}
