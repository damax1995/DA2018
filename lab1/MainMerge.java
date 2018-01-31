import com.sun.tools.javac.Main;

import java.util.ArrayList;

public class MainMerge {

    ArrayList<Integer> lista = new ArrayList<Integer>();
    private static MainMerge myMainMerge = new MainMerge();

    private MainMerge(){
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
        lista.add(23);
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
        int izq = inicio;
        int der = medio+1;
        int k = 0;

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

    public static void main(String[] args){
        MainMerge.getMyMainMerge().ordenarLista();
        MainMerge.getMyMainMerge().printLista();
    }

}
