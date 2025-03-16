package Assignment_1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Merge {

    public static void main(String[] args) {
        // Call the static merge method in a loop until only one run is left
        int cycleCount = 0;
        while (new File("tempFile_1.txt").length() > 0 && new File("tempFile_2.txt").length() > 0) {
            cycleCount = merge(cycleCount);
        }
    }

    public static int merge(int cycleCount) {
        int runCount = 0; // Tracks the number of merged runs

        Scanner reader1 = null;
        Scanner reader2 = null;
        PrintWriter writer1 = null;
        PrintWriter writer2 = null;

        try {
            // Initialize the readers and writers based on cycleCount
            if (cycleCount % 2 == 0) {
                reader1 = new Scanner(new File("tempFile_1.txt"));
                reader2 = new Scanner(new File("tempFile_2.txt"));
                writer1 = new PrintWriter("tempFile_3.txt");
                writer2 = new PrintWriter("tempFile_4.txt");
            } else {
                reader1 = new Scanner(new File("tempFile_3.txt"));
                reader2 = new Scanner(new File("tempFile_4.txt"));
                writer1 = new PrintWriter("tempFile_1.txt");
                writer2 = new PrintWriter("tempFile_2.txt");
            }

            String prevR1 = null;
            String prevR2 = null;

            String currR1 = reader1.hasNextLine() ? reader1.nextLine() : null;
            String currR2 = reader2.hasNextLine() ? reader2.nextLine() : null;

            // Start with Writer1
            PrintWriter currentWriter = writer1;

            // Continue merging until both files are exhausted
            while (currR1 != null || currR2 != null) {
                // Merge based on the lexicographical order
                if (currR1 != null && (currR2 == null || currR1.compareTo(currR2) < 0)) {
                    currentWriter.println(currR1); // Write to current writer
                    prevR1 = currR1;
                    System.out.println(currR1);
                    currR1 = reader1.hasNextLine() ? reader1.nextLine() : null; // Read next line from file 1
                } else if (currR2 != null) {
                    currentWriter.println(currR2); // Write to current writer
                    prevR2 = currR2;
                    System.err.println(currR2);
                    currR2 = reader2.hasNextLine() ? reader2.nextLine() : null; // Read next line from file 2
                }

                // Check if the order condition breaks
                if ((currR1 != null && prevR1 != null && prevR1.compareTo(currR1) > 0) ||
                        (currR2 != null && prevR2 != null && prevR2.compareTo(currR2) > 0)) {
                    // Order condition breaks, switch to a new run
                    runCount++;
                    if (currentWriter == writer1) {
                        currentWriter = writer2;
                    } else {
                        currentWriter = writer1;
                    }
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found.");
            e.printStackTrace();
        } finally {
            // Close the readers and writers
            if (reader1 != null)
                reader1.close();
            if (reader2 != null)
                reader2.close();
            if (writer1 != null)
                writer1.close();
            if (writer2 != null)
                writer2.close();
        }
        return cycleCount + 1;
    }
}