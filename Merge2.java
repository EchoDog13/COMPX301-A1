import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;

public class Merge2 {
    public static void main(String[] args) {
        int runCount = 0;
        int cycleCount = 0;

        Scanner reader1 = null, reader2 = null;
        FileWriter writer1 = null, writer2 = null;

        try {
            reader1 = new Scanner(new File("tempFile_1.txt"));
            reader2 = new Scanner(new File("tempFile_2.txt"));
            writer1 = new FileWriter("tempFile_3.txt");
            writer2 = new FileWriter("tempFile_4.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (cycleCount % 2 == 0) {
            String inputFile1 = "tempFile_1.txt";
            String inputFile2 = "tempFile_2.txt";
            String outputFile1 = "tempFile_3.txt";
            String outputFile2 = "tempFile_4.txt";
        } else {
            String inputFile1 = "tempFile_3.txt";
            String inputFile2 = "tempFile_4.txt";
            String outputFile1 = "tempFile_1.txt";
            String outputFile2 = "tempFile_2.txt";

        }

        String R1Previous = null;
        String R2Previous = null;
        String R1Current = null;
        String R2Current = null;

        if (reader1.hasNextLine()) {
            R1Current = reader1.nextLine();
        } else {
            R1Current = null;
        }

        if (reader2.hasNextLine()) {
            R2Current = reader2.nextLine();
        } else {
            R2Current = null;
        }

        FileWriter currentWriter = writer1;
        boolean writerSwitched = false;

        try {
            
      

        while (R1Current != null || R2Current != null) {
            while (R1Current.compareToIgnoreCase(R1Previous) < 0 && (R2Current.compareToIgnoreCase(R2Previous) < 0)) {
                if (R1Current.compareToIgnoreCase(R1Previous) < 0 && R2Current.compareToIgnoreCase(R2Previous) < 0) {
                    if (R1Current.compareToIgnoreCase(R2Current) < 0) {
                        currentWriter.write(R1Current);
                        R1Previous = R1Current;
                        if (reader1.hasNextLine()) {
                            R1Current = reader1.nextLine();
                        } else {
                            R1Current = null;
                        }
                    } else {
                        currentWriter.write(R2Current);
                        R2Previous = R2Current;
                        if (reader2.hasNextLine()) {
                            R2Current = reader2.nextLine();
                        } else {
                            R2Current = null;
                        }
                    }
                }
                while (R2Current.compareToIgnoreCase(R2Previous) < 0 && R1Current.compareToIgnoreCase(R1Previous) < 0) {
                    currentWriter.write(R2Current);
                    R2Previous = R2Current;
                    if (reader2.hasNextLine()) {
                        R2Current = reader2.nextLine();
                    } else {
                        R2Current = null;
                    }

                }
                while (R1Current.compareToIgnoreCase(R1Previous) < 0 && R2Current.compareToIgnoreCase(R2Previous) < 0) {
                    currentWriter.write(R1Current);
                    R1Previous = R1Current;
                    if (reader1.hasNextLine()) {
                        R1Current = reader1.nextLine();
                    } else {
                        R1Current = null;
                    }

                }
                if 
            }

        }

    } catch (Exception e) {
        // TODO: handle exception
    }

    }
}
