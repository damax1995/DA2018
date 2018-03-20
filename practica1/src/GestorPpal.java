
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class GestorPpal {

    private static GestorPpal myGestorPpal;
    private static GrafoDirig gD;
    private static GrafoNoDirig gND;
    private static GrafoDirigPes gDP;
    private static GrafoNoDirigPes gNDP;
    private ArrayList<Nodo> listaNodos; //este ArrayList contiene todos los nodos del txt

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

    public void rellenarListaNodos(){
        listaNodos = getgND().getListaNodos();
    }

    public ArrayList<Nodo> getListaNodos(){
        return listaNodos;
    }

    public static void main(String[] args) throws FileNotFoundException {

        int opc = 0;
        GestorFichero.getMyGestorFichero().readFile();
        while(opc != -1){
            Scanner scanner = new Scanner(System.in);
            System.out.println("ELIGE UNA DE LAS OPCIONES: \n" +
                    "0) Salir\n"+
                    "1) Crear GD\n" +
                    "2) Crear GND\n" +
                    "3) Crear GNDP\n" +
                    "4) Crear GDP\n" +
                    "5) Ciclo GD\n" +
                    "6) Ciclo GND\n" +
                    "7) Orden topologico\n" +
                    "8) Coordenadas Conexas\n" +
                    "9) Kruskal\n");

            opc = Integer.parseInt(scanner.next());
            long startTime = System.currentTimeMillis();
            if(opc ==1){
                GestorPpal.getMyGestorPpal().getgND().crearGrafo();
                GestorPpal.getMyGestorPpal().rellenarListaNodos();
                GestorPpal.getMyGestorPpal().getgND().printGrafo();
            }
            else if(opc == 2){
                GestorPpal.getMyGestorPpal().getgD().crearGrafo();
                GestorPpal.getMyGestorPpal().getgD().printGrafo();
            }
            else if(opc == 3){
                GestorPpal.getMyGestorPpal().getgNDP().crearGrafo();
                GestorPpal.getMyGestorPpal().getgNDP().printGrafo();
            }
            else if(opc == 4){
                GestorPpal.getMyGestorPpal().getgDP().crearGrafo();
                GestorPpal.getMyGestorPpal().getgDP().printGrafo();
            }
            else if(opc == 5){
                if(GestorPpal.getMyGestorPpal().getgD().tieneCicloD()){
                    System.out.println("\tSi tiene cliclos.\n");
                }
                else{
                    System.out.println("\tNo tiene ciclos.\n");
                }
            }
            else if(opc == 6){
                if(GestorPpal.getMyGestorPpal().getgND().tieneCicloND()){
                    System.out.println("\tSi tiene cliclos.");
                }
                else{
                    System.out.println("\tNo tiene ciclos.");
                }
            }
            else if(opc == 7){
                for(int i : GestorPpal.getMyGestorPpal().getgD().ordenTopologico()){
                    System.out.println("\t"+i);
                }
            }
            else if(opc == 8){
                LinkedList<Integer>[] l = GestorPpal.getMyGestorPpal().getgND().componentesConexas();
            }
            else if(opc == 9){
                GestorPpal.getMyGestorPpal().getgNDP().MSTKruskal();
            }
            else if(opc == 0){
                System.out.println("Agur xd.");
                opc = -1;
            }
            long timeTotal = System.currentTimeMillis() - startTime;
        }


    }
}
