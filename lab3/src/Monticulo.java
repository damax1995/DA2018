import java.io.*;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Monticulo {

    public int[] lista;
    public int maxLista;
    private static Monticulo myMonticulo;

    private Monticulo(){
        //lista = new int[300001];
    }

    public static Monticulo getMyMonticulo(){
        if(myMonticulo == null){
            myMonticulo = new Monticulo();
        }
        return myMonticulo;
    }

    public void readFile(int opc) throws FileNotFoundException {
        TxtGenerator.getMyTxtGen().generarTxt(opc);
        File f = new File("numbersMonticulo.txt");
        List<String> lines = new BufferedReader(new FileReader(f)).lines().collect(Collectors.toList());
        lista = new int[lines.size()+1];
        maxLista = lista.length-2;
        int[] auxL = new int[lines.size()+1];
        int k = 1;
        for(String s : lines){
            auxL[k] = Integer.parseInt(s);
            k++;
        }

        crearMonticulo(auxL);
    }

    public void writeFile(){
        BufferedWriter writer = null;
        try {
            //create a temporary file
            File logFile = new File("outputMonticulo.txt");

            // This will output the full path where the file will be written to...
            System.out.println("TU FICHERO 'outputMonticulo.txt' ESTARÁ EN: "+logFile.getCanonicalPath());

            writer = new BufferedWriter(new FileWriter(logFile));
            for(int i : lista){
                writer.write(i+"\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Close the writer regardless of what happens...
                writer.close();
            } catch (Exception e) {
            }
        }
    }

    public void crearMonticulo(int[] l){
        for(int i=l.length-1; i>=1; i--){
            hundir(l[i], i);
        }
    }

    public int sacarRaiz(){
        if(lista.length<=1){
            return -1;
        }
        else{
            int elem = lista[1];
            int ultimoElem = lista[maxLista+1];
            //quitarUltimoElem();
            //System.out.println("Tamaño tras reducir: ["+lista.length+"]");
            hundirAux(ultimoElem, 1, maxLista);
            lista[maxLista+1] = elem;
            maxLista--;
            return elem;
        }
    }

    public void hundirAux(int elem, int pos, int maxLista){
        int min = minchild(pos);
        while(min != 0 && lista[min]<elem && min<=maxLista){
            lista[pos] = lista[min];
            pos = min;
            min = minchild(pos);
        }
        lista[pos] = elem;
    }

    public void printLista(int[] auxL){
        for(int i : auxL){
            System.out.println(i);
        }
    }

    public void quitarUltimoElem(){
        int[] auxL = new int[lista.length-1];
        int k = 0;
        for(int i = 0; i<lista.length-1; i++){
            auxL[k] = lista[i];
            k++;
        }

        lista = new int[auxL.length];
        lista = auxL;
    }

    public void insert(int elem){
        flotar(elem, lista.length+1);
    }

    public void elevarNodo(int elem){
        int pos = getIndice(elem);
        if(pos != -1){
            flotar(elem, pos);
        }
    }

    public void flotar(int elem, int pos) {
        // place element x in position i of lista, and let it bubble up
        int p = pos/2;
        while (pos != 1 && (lista[p] > elem)){
            lista[pos] = lista[p];
            pos = p;
            p = pos/2;
        }
        lista[pos] = elem;
    }

    public int getIndice(int elem){
        int k = 0;
        for(int i : lista) {
            if(i == elem){
                return k;
            }
            k++;
        }
        k = -1;
        return k;
    }

    public void hundir(int elem, int pos){
        int min = minchild(pos);
        while(min != 0 && lista[min]<elem){
            lista[pos] = lista[min];
            pos = min;
            min = minchild(pos);
        }
        lista[pos] = elem;
    }

    public int minchild(int pos) {
        //return the index of the smallest child of h(elem))
        if (2*pos>lista.length-1) {
            return 0; //No tiene hijos
        }
        else{
            if((2 * pos) + 1 >lista.length-1){  //El ultimo nodo de la anteultima fila, no tiene hijo derecho,
                return 2*pos;                   //así que devolvemos directamente el izquierdo
            }
            else {
                if (lista[2 * pos] >= lista[2 * pos + 1]) {
                    return 2 * pos + 1;
                } else {
                    return 2 * pos;
                }
            }
        }

    }

    public void ordenarArray(){
        int i = 1;
        while(i<=15){
            sacarRaiz();
            i++;
        }
        printLista(lista);
    }

    public static void main(String[] args){
        try {
            int opc = 0;

            Scanner scanner = new Scanner(System.in);

            System.out.println("Introduce '0' si quieres probar con fichero de 1 a 300.000 elementos, o '1' para la prueba trivial de 1 a 15.");
            opc = Integer.parseInt(scanner.nextLine());
            Monticulo.getMyMonticulo().readFile(opc);
            Monticulo.getMyMonticulo().writeFile();

            if(opc == 0) {
                System.out.println("\nEl indice del elemento con valor 1 deberia ser 1, ya que es un monticuloMin y tenemos: " + Monticulo.getMyMonticulo().getIndice(1));
            }
            else{
                System.out.println("\nEn la posicion 0 deberiamos tener un 0 y tenemos: "+Monticulo.getMyMonticulo().lista[0]);
                System.out.println("El indice del elemento con valor 1 deberia ser 1, ya que es un monticuloMin y obtenemos: "+Monticulo.getMyMonticulo().lista[1]);
                System.out.println("En la posicion 3 deberiamos tener un 2 y tenemos: "+Monticulo.getMyMonticulo().lista[3]);
                System.out.println("En la posicion 5 deberiamos tener un 6 y tenemos: "+Monticulo.getMyMonticulo().lista[5]);
                System.out.println("En la posicion 15 deberiamos tener un 15 y tenemos: "+Monticulo.getMyMonticulo().lista[15]);

            }

            System.out.println("\n[*]Ahora procederemos a ordenar el array de mayor a menor usando monticulos.");
            Monticulo.getMyMonticulo().ordenarArray();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
