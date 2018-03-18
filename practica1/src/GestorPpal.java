import sun.awt.image.ImageWatched;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;

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
        GestorFichero.getMyGestorFichero().readFile();
        System.out.println("\t\t** CREACION DE GRAFO NO DIRIGIDO **");
        long startTime = System.currentTimeMillis();
        GestorPpal.getMyGestorPpal().getgND().crearGrafo();
        GestorPpal.getMyGestorPpal().rellenarListaNodos();
        GestorPpal.getMyGestorPpal().getgND().printGrafo();
        long timeTotal = System.currentTimeMillis() - startTime;
        System.out.println("\n\tTiempo empleado en las operaciones: "+(int) timeTotal / 1000 + "sec, " + timeTotal * 1000+"ms.");

        System.out.println("\n\t\t** CREACION DE GRAFO DIRIGIDO **");
        startTime = System.currentTimeMillis();
        GestorPpal.getMyGestorPpal().getgD().crearGrafo();
        GestorPpal.getMyGestorPpal().getgD().printGrafo();
        timeTotal = System.currentTimeMillis() - startTime;
        System.out.println("\n\tTiempo empleado en las operaciones: "+(int) timeTotal / 1000 + "sec, " + timeTotal * 1000+"ms.");

        System.out.println("\n\t\t** CREACION DE GRAFO NO DIRIGIDO CON PESOS **");
        startTime = System.currentTimeMillis();
        GestorPpal.getMyGestorPpal().getgNDP().crearGrafo();
        GestorPpal.getMyGestorPpal().getgNDP().printGrafo();
        timeTotal = System.currentTimeMillis() - startTime;
        System.out.println("\n\tTiempo empleado en las operaciones: "+(int) timeTotal / 1000 + "sec, " + timeTotal * 1000+"ms.");

        System.out.println("\n\t\t** CREACION DE GRAFO DIRIGIDO CON PESOS **");
        startTime = System.currentTimeMillis();
        GestorPpal.getMyGestorPpal().getgDP().crearGrafo();
        GestorPpal.getMyGestorPpal().getgDP().printGrafo();
        timeTotal = System.currentTimeMillis() - startTime;
        System.out.println("\n\tTiempo empleado en las operaciones: "+(int) timeTotal / 1000 + "sec, " + timeTotal * 1000+"ms.");

        System.out.println("\n\t\t** COMPROBAMOS SI TIENE CICLOS EL GD.... **");
        startTime = System.currentTimeMillis();
        if(GestorPpal.getMyGestorPpal().getgD().tieneCicloD()){
            System.out.println("\tSi tiene cliclos.\n");
        }
        else{
            System.out.println("\tNo tiene ciclos.\n");
        }
        timeTotal = System.currentTimeMillis() - startTime;
        System.out.println("\n\tTiempo empleado en comprobar ciclo: "+(int) timeTotal / 1000 + "sec, " + timeTotal * 1000+"ms.");

        System.out.println("\n\t\t** COMPROBAMOS SI TIENE CICLOS EL GND.... **");
        startTime = System.currentTimeMillis();
        if(GestorPpal.getMyGestorPpal().getgND().tieneCicloND()){
            System.out.println("\tSi tiene cliclos.");
        }
        else{
            System.out.println("\tNo tiene ciclos.");
        }
        timeTotal = System.currentTimeMillis() - startTime;
        System.out.println("\n\tTiempo empleado en comprobar ciclo: "+(int) timeTotal / 1000 + "sec, " + timeTotal * 1000+"ms.");

        System.out.println("\n\t\t** REALIZAREMOS EL ORDEN TOPOLOGICO DEL GRAFO DIRIGIDO **");
        startTime = System.currentTimeMillis();
        for(int i : GestorPpal.getMyGestorPpal().getgD().ordenTopologico()){
            System.out.println("\t"+i);
        }
        timeTotal = System.currentTimeMillis() - startTime;
        System.out.println("\n\tTiempo empleado en el orden topológico: "+(int) timeTotal / 1000 + "sec, " + timeTotal * 1000+"ms.");

        System.out.println("\n\t\t** OBTENDREMOS LAS COORD. CONEXAS PARA EL GND **");
        startTime = System.currentTimeMillis();
        LinkedList<Integer>[] l = GestorPpal.getMyGestorPpal().getgND().componentesConexas();
        timeTotal = System.currentTimeMillis() - startTime;
        System.out.println("\n\tTiempo empleado en obtener la lista de coordenadas conexas: "+(int) timeTotal / 1000 + "sec, " + timeTotal * 1000+"ms.");

        System.out.println("\n\t\t** EJECUTAREMOS EL ALGORITMO DE KRUSKAL PARA EL GNDP **");
        startTime = System.currentTimeMillis();
        GestorPpal.getMyGestorPpal().getgNDP().MSTKruskal();
        timeTotal = System.currentTimeMillis() - startTime;
        System.out.println("\n\tTiempo empleado en el algoritmo MSTKruskal: "+(int) timeTotal / 1000 + "sec, " + timeTotal * 1000+"ms.");


    }
}
