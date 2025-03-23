import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Merge {

    public static void main(String[] args) {
        // Call the static merge method in a loop until only one run is left
        int cycleCount = 0;
        boolean progressMade = true;

        while (progressMade) {
            File file1 = new File("tempFile_1.txt");
            File file2 = new File("tempFile_2.txt");
            File file3 = new File("tempFile_3.txt");
            File file4 = new File("tempFile_4.txt");

            // Check if both input files are empty and stop if they are
            if ((cycleCount % 2 == 0 && file1.length() == 0 && file2.length() == 0) ||
                    (cycleCount % 2 != 0 && file3.length() == 0 && file4.length() == 0)) {
                progressMade = false;
                break;
            }

            cycleCount = merge(cycleCount);
        }
    }

    public static int merge(int cycleCount) {
        int runCount = 0;
        boolean merged = false;

        Scanner reader1 = null;
        Scanner reader2 = null;
        PrintWriter writer1 = null;
        PrintWriter writer2 = null;

        try {
            String inputFile1 = (cycleCount % 2 == 0) ? "tempFile_1.txt" : "tempFile_3.txt";
            String inputFile2 = (cycleCount % 2 == 0) ? "tempFile_2.txt" : "tempFile_4.txt";
            String outputFile1 = (cycleCount % 2 == 0) ? "tempFile_3.txt" : "tempFile_1.txt";
            String outputFile2 = (cycleCount % 2 == 0) ? "tempFile_4.txt" : "tempFile_2.txt";

            reader1 = new Scanner(new File(inputFile1));
            reader2 = new Scanner(new File(inputFile2));
            writer1 = new PrintWriter(outputFile1);
            writer2 = new PrintWriter(outputFile2);

            String currR1 = reader1.hasNextLine() ? reader1.nextLine() : null;
            String currR2 = reader2.hasNextLine() ? reader2.nextLine() : null;

            PrintWriter currentWriter = writer1;
            boolean switchWriter = false;

            while (currR1 != null || currR2 != null) {
                if (currR1 != null && (currR2 == null || currR1.compareToIgnoreCase(currR2) < 0)) {
                    currentWriter.println(currR1);
                    currR1 = reader1.hasNextLine() ? reader1.nextLine() : null;
                } else {
                    currentWriter.println(currR2);
                    currR2 = reader2.hasNextLine() ? reader2.nextLine() : null;
                }

                if (switchWriter) {
                    currentWriter = (currentWriter == writer1) ? writer2 : writer1;
                    switchWriter = false;
                    merged = true;
                }

                if (currR1 != null && currR2 != null && currR1.compareToIgnoreCase(currR2) > 0) {
                    switchWriter = true;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found.");
            e.printStackTrace();
        } finally {
            if (reader1 != null)
                reader1.close();
            if (reader2 != null)
                reader2.close();
            if (writer1 != null)
                writer1.close();
            if (writer2 != null)
                writer2.close();
        }
        return merged ? cycleCount + 1 : cycleCount;
    }
}