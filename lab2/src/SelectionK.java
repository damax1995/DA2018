import java.io.IOException;
import java.util.ArrayList;

public class SelectionK {

    ArrayList<Integer> lista;
    private static SelectionK mySelectionK = new SelectionK();

    private SelectionK() {
        lista = new ArrayList<>();
        lista.add(34);
        lista.add(27);
        lista.add(53);
        lista.add(18);
        lista.add(41);
        lista.add(15);
        lista.add(7);
        lista.add(3);
        lista.add(21);
        lista.add(45);
        lista.add(1);
        lista.add(132);
        lista.add(96);
        lista.add(81);
        lista.add(41);
        lista.add(59);
        lista.add(71);
        lista.add(33);
        lista.add(62);
        lista.add(97);
        lista.add(48);
        lista.add(102);
        lista.add(31);
        lista.add(64);
    }

    public static SelectionK getMySelectionK() {
        if (mySelectionK == null) {
            mySelectionK = new SelectionK();
        }
        return mySelectionK;
    }

    public Integer particion(int i, int j) {
        Integer pivote = lista.get(i);
        int izq = i;
        int der = j;
        while (izq < der) {
            while (lista.get(izq) <= pivote) {
                izq++;
            }
            while (lista.get(der) > pivote) {
                der--;
            }
            if (izq < der) {
                swap(izq, der);
            }
        }
        swap(i, der);
        return der;
    }

    public void swap(int i, int j){
        int temp = lista.get(i);
        lista.set(i, lista.get(j));
        lista.set(j, temp);
    }

    public Integer seleccionar(int i, int j, int k) {
        int s = (i+k) - 1;
        if(i == j) {
            return lista.get(i);
        }else{
            int p = particion(i, j);
            if(s == p){
                return lista.get(p);
            }
            else if(s<p){
                return seleccionar(i, p-1, k);
            }
            else{
                return seleccionar(p+1, j, s-p);
            }
        }
    }

    public static void main(String[] args){
        System.out.println("El elemento en esa posicion es: "+SelectionK.getMySelectionK().seleccionar(0, 23, 6));
    }

}