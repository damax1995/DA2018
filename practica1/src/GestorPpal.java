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

    public GrafoDirigPes getgDP() {
        return gDP;
    }

    public GrafoNoDirigPes getgNDP() {
        return gNDP;
    }

    public GrafoDirig getgD() {
        return gD;
    }

    public static GrafoNoDirig getgND() {
        return gND;
    }

    public static void main(String[] args) throws FileNotFoundException {
        GestorFichero.getMyGestorFichero().readFile();
        System.out.println("\t\t** CREACION DE GRAFO NO DIRIGIDO **");
        GestorPpal.getMyGestorPpal().getgND().crearGrafo();
        GestorPpal.getMyGestorPpal().getgND().printGrafo();

        System.out.println("\n\t\t** CREACION DE GRAFO DIRIGIDO **");
        GestorPpal.getMyGestorPpal().getgD().crearGrafo();
        GestorPpal.getMyGestorPpal().getgD().printGrafo();

        System.out.println("\n\t\t** CREACION DE GRAFO NO DIRIGIDO CON PESOS **");
        GestorPpal.getMyGestorPpal().getgNDP().crearGrafo();
        GestorPpal.getMyGestorPpal().getgNDP().printGrafo();

        System.out.println("\n\t\t** CREACION DE GRAFO DIRIGIDO CON PESOS **");
        GestorPpal.getMyGestorPpal().getgDP().crearGrafo();
        GestorPpal.getMyGestorPpal().getgDP().printGrafo();

        System.out.println("\n\t\t** COMPROBAMOS SI TIENE CICLOS EL GD.... **");
        if(GestorPpal.getMyGestorPpal().getgD().tieneCicloD()){
            System.out.println("\tSi tiene cliclos.\n");
        }
        else{
            System.out.println("\tNo tiene ciclos.\n");
        }

        System.out.println("\n\t\t** COMPROBAMOS SI TIENE CICLOS EL GND.... **");
        if(GestorPpal.getMyGestorPpal().getgND().tieneCicloND()){
            System.out.println("\tSi tiene cliclos.");
        }
        else{
            System.out.println("\tNo tiene ciclos.");
        }
    }
}
