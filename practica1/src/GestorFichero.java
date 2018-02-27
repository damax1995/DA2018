import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
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
        int k = 0;
        File f = new File("txtGrafo.txt");
        List<String> lines = new BufferedReader(new FileReader(f)).lines().collect(Collectors.toList()); //Leemos el txt
        contenidoTxt = new String[lines.size()];

        for(String s : lines){
            contenidoTxt[k] = s;
            k++;
        }
    }

}
