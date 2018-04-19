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
            File logFile = new File("lista.txt");

            // This will output the full path where the file will be written to...
            System.out.println("TU FICHERO 'lista.txt' ESTARÁ EN: "+logFile.getCanonicalPath());

            writer = new BufferedWriter(new FileWriter(logFile));
            writer.write("15 50 30\n");
            for (int i = 0; i < 30; i++) {
                int peso = 0;
                int dinero = (int) (Math.random() * 15) + 1;
                if (dinero <= 7) {
                    peso = (int) (Math.random() * 7) + 1;
                } else {
                    peso = (int) (Math.random() * 8) + 8;
                }
                writer.write(dinero + " " + peso+"\n");
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

    public static void main(String[] args){
        TxtGenerator.getMyTxtGen().generarTxt();
    }

}
