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

    public void generarTxt(){
        BufferedWriter writer = null;
        try {
            //create a temporary file
            File logFile = new File("numbers.txt");

            // This will output the full path where the file will be written to...
            System.out.println("TU FICHERO NUMBERS.TXT ESTARÁ EN: "+logFile.getCanonicalPath());

            writer = new BufferedWriter(new FileWriter(logFile));
            for(int i = 37829; i>=1; i--){
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

}
