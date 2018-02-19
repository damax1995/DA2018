import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class TxtGenerator {

    private static TxtGenerator myTxtGen = new TxtGenerator();

    private TxtGenerator(){

    }

    public static TxtGenerator getMyTxtGen(){
        if(myTxtGen == null){
            myTxtGen = new TxtGenerator();
        }
        return myTxtGen;
    }

    public void generarTxt(int opc){
        BufferedWriter writer = null;
        try {
            //create a temporary file
            File logFile = new File("numbersMonticulo.txt");

            // This will output the full path where the file will be written to...
            System.out.println("TU FICHERO 'numbersMonticulo.txt' ESTARÁ EN: "+logFile.getCanonicalPath());

            writer = new BufferedWriter(new FileWriter(logFile));
            if(opc == 0) {
                for (int i = 300000; i > 1; i--) {
                    writer.write(i + "\n");
                }
            }else{
                for (int i = 15; i > 1; i--) {
                    writer.write(i + "\n");
                }
            }
            writer.write("1");
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

}
