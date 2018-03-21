import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class GestorFichero {

    private static GestorFichero myGestorFichero;
    private String[] contenidoTxt;

    private GestorFichero(){

    }

    public static GestorFichero getMyGestorFichero(){
        if(myGestorFichero == null){
            myGestorFichero = new GestorFichero();
        }
        return myGestorFichero;
    }

    public String[] getContenidoTxt() {
        return contenidoTxt;
    }

    public void readFile() throws FileNotFoundException {
        File f = null;
        int k = 0;
        Scanner scan = new Scanner(System.in);
        System.out.println("Introduce opción (1, 2, 3 o 4):\n" +
                "\t1) Cargar fichero grafo sin nodo 5 ni nodo 7.\n" +
                "\t2) Cargar fichero Componenetes Conexas.\n" +
                "\t3) Cargar fichero Kruskal.\n" +
                "\t4) Cargar fichero Kruskal grafo 1000 nodos y 8433 aristas.");


        int opc = scan.nextInt();
        if(opc == 1){
            f = new File("grafoDSC.txt");
        }else if(opc == 2){
            f = new File("grafoCC.txt");
        }
        else if(opc == 3) {
            f = new File("grafo8-16.txt");
        }
        else if(opc == 4){
            f = new File("grafo1000-8433.txt");
        }

        List<String> lines = new BufferedReader(new FileReader(f)).lines().collect(Collectors.toList()); //Leemos el txt
        contenidoTxt = new String[lines.size()];

        for(String s : lines){
            contenidoTxt[k] = s;
            k++;
        }
    }

}
