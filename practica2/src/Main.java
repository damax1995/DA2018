import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws FileNotFoundException {

        Scanner scan = new Scanner(System.in);
        int opc = -1;

        while(opc != 0) {
            System.out.println("Que algoritmo quieres ejecutar?\n" +
                    "\t1) Dinamica\n" +
                    "\t2) Backtrack01\n" +
                    "\t3) Backtrack1N\n" +
                    "\t0) Salir");

            opc = Integer.parseInt(scan.next());

            if (opc == 1) {
                System.out.println("Maximo beneficio = " + Dinamica.getMyDinamica().mBenef());
            } else if (opc == 2) {
                System.out.println("Maximo beneficio = " + Backtrack01.getMyBacktrack().backtrack());
                System.out.println("Ensayos totales de BACKTRACK01 = " + Backtrack01.getMyBacktrack().getNumEnsayos());
            } else if (opc == 3) {
                System.out.println("Maximo beneficio = " + BacktrackN.getMyBacktrack().backtrack());
                System.out.println("Ensayos totales de BACKTRACK N-ARIO = " + BacktrackN.getMyBacktrack().getNumEnsayos());
            }
        }
    }
}