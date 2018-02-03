
//import com.sun.tools.javac.Main;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MainMerge {

    ArrayList<Integer> lista = new ArrayList<Integer>();
    private static MainMerge myMainMerge = new MainMerge();

    private MainMerge(){
        /*lista.add(34);
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
        lista.add(23);*/
    }

    public static MainMerge getMyMainMerge(){
        if(myMainMerge == null){
            myMainMerge = new MainMerge();
        }
        return myMainMerge;
    }

    public void ordenarLista(){
        if(lista.size()>1){
            this.mergeSort(this.lista, 0, lista.size()-1);
        }
    }

    private void mergeSort(ArrayList<Integer> l, int inicio, int fin){
        if(inicio<fin){
            mergeSort(l, inicio, (inicio+fin)/2);
            mergeSort(l, ((inicio+fin)/2)+1, fin);
            mezcla(l, inicio, (inicio+fin)/2, fin);
        }
    }

    private void mezcla(ArrayList<Integer> l, int inicio, int medio, int fin){
        int[] auxL = new int[l.size()];
        int izq = inicio; //izq no podra pasar de medio
        int der = medio+1; //der no podra pasar del limite
        int k = 0;
                            //Tenemos el array dividido en 2, con un indicador en el principio de cada mitad.
        while(izq<=medio && der<=fin){      //Aquí comparamos cual de los dos numeros es menor, y lo colocamos en la lista auxiliar.
            if(l.get(izq) < l.get(der)){
                auxL[k] = l.get(izq);
                izq++;
            }
            else{
                auxL[k] = l.get(der);
                der++;
            }
            k++;
        }
                                            //Llegados a este punto, izq o der estaran en el limite,
        if(izq > medio){                    //asi que quedaria volcar el contenido restante a la lista auxiliar
            while(der<=fin){
                auxL[k] = l.get(der);
                der++;
                k++;
            }
        }
        else{
            while(izq<=medio){
                auxL[k] = l.get(izq);
                izq++;
                k++;
            }
        }

        k=0;
        for(int j=inicio; j<=fin; j++){     //Sustituimos el array desordenado que se recibe, por el auxiliar ordenado que hemos obtenido.
            l.set(j, auxL[k]);
            k++;
        }
    }

    public void printLista(){
        for(int i : lista){
            System.out.println(i);
        }
    }

    public void readFile() throws FileNotFoundException {
        //String[] auxS = null;
        ArrayList<String> auxS = new ArrayList<>();
        File f = new File("numbers.txt");
        List<String> lines = new BufferedReader(new FileReader(f)).lines().collect(Collectors.toList());

        for(String line : lines){
            auxS.add(line);
        }

        for(String s : auxS){
            lista.add(Integer.parseInt(s));
        }

    }

    public void writeFile(){
        BufferedWriter writer = null;
        try {
            //create a temporary file
            File logFile = new File("output.txt");

            // This will output the full path where the file will be written to...
            System.out.println("TU FICHERO OUTPUT.TXT ESTARÁ EN: "+logFile.getCanonicalPath());

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

    public static void main(String[] args){
        try {
            TxtGenerator.getMyTxtGen().generarTxt();
            MainMerge.getMyMainMerge().readFile();
            MainMerge.getMyMainMerge().ordenarLista();
            //MainMerge.getMyMainMerge().printLista();
            MainMerge.getMyMainMerge().writeFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
