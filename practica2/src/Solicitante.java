public class Solicitante {

    private int dinero;
    private int peso;
    private int id;

    public Solicitante(int d, int p, int i){
        dinero = d;
        peso = p;
        id = i;
    }

    public int getDinero() {
        return dinero;
    }

    public void setDinero(int dinero) {
        this.dinero = dinero;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public int getId(){
        return this.id;
    }
}
