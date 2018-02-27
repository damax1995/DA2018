import java.io.FileNotFoundException;

public class GestorPpal {

    private static GestorPpal myGestorPpal;
    private static GrafoDirig gD;
    private static GrafoNoDirig gND;
    private static GrafoDirigPes gDP;
    private static GrafoNoDirigPes gNDP;

    private GestorPpal(){
        gD = new GrafoDirig();
        gND = new GrafoNoDirig();
        gDP = new GrafoDirigPes();
        gNDP = new GrafoNoDirigPes();
    }

    public static GestorPpal getMyGestorPpal(){
        if(myGestorPpal == null){
            myGestorPpal = new GestorPpal();
        }
        return myGestorPpal;
    }

    public static GrafoNoDirig getgND() {
        return gND;
    }

    public static void main(String[] args) throws FileNotFoundException {
        GestorFichero.getMyGestorFichero().readFile();
        GestorPpal.getMyGestorPpal().getgND().crearGrafo();
        GestorPpal.getMyGestorPpal().getgND().printGrafo();
    }

}
