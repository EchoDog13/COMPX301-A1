import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Merge2 {

    public static void main(String[] args) {
        int cycleCount = 0;

        while (true) {
            boolean isRunComplete = mergePass(cycleCount);
            // System.out.println("Cycle " + cycleCount + " completed. isRunComplete: " +
            // isRunComplete);

            if (isRunComplete) {
                // System.out.println("Run completed. All elements processed.");
                Scanner outputScanner;
                if (cycleCount % 2 == 1) {
                    try {
                        outputScanner = new Scanner(new File("tempFile_1.txt"));
                        while (outputScanner.hasNextLine()) {
                            System.out.println(outputScanner.nextLine());
                        }
                        outputScanner.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        outputScanner = new Scanner(new File("tempFile_3.txt"));
                        while (outputScanner.hasNextLine()) {
                            System.out.println(outputScanner.nextLine());
                        }
                        outputScanner.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            }

            cycleCount++;
        }
    }

    private static boolean mergePass(int cycleCount) {
        Scanner reader1 = null, reader2 = null;
        FileWriter writer1 = null, writer2 = null;

        String inputFile1, inputFile2, outputFile1, outputFile2;

        if (cycleCount % 2 == 0) {
            inputFile1 = "tempFile_1.txt";
            inputFile2 = "tempFile_2.txt";
            outputFile1 = "tempFile_3.txt";
            outputFile2 = "tempFile_4.txt";
        } else {
            inputFile1 = "tempFile_3.txt";
            inputFile2 = "tempFile_4.txt";
            outputFile1 = "tempFile_1.txt";
            outputFile2 = "tempFile_2.txt";
        }

        try {
            reader1 = new Scanner(new File(inputFile1));
            reader2 = new Scanner(new File(inputFile2));
            writer1 = new FileWriter(outputFile1);
            writer2 = new FileWriter(outputFile2);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        boolean isRunComplete = true;
        FileWriter currentWriter = writer1;

        try {
            String value1 = null, value2 = null;
            String prev1 = null, prev2 = null;
            boolean foundBreak = false;

            // Get initial values
            if (reader1.hasNextLine()) {
                value1 = reader1.nextLine();
            }

            if (reader2.hasNextLine()) {
                value2 = reader2.nextLine();
            }

            // Keep track of which output file we're using
            boolean useFirstWriter = true;

            while (value1 != null || value2 != null) {
                // Check for out-of-order sequences and switch writers if needed
                boolean switched = false;

                if (value1 != null && prev1 != null && value1.compareToIgnoreCase(prev1) < 0) {
                    // Found a break in first file
                    foundBreak = true;
                    useFirstWriter = !useFirstWriter;
                    currentWriter = useFirstWriter ? writer1 : writer2;
                    prev1 = null; // Reset for new run
                    prev2 = null; // Reset for new run
                    switched = true;
                }

                if (value2 != null && prev2 != null && value2.compareToIgnoreCase(prev2) < 0) {
                    // Found a break in second file
                    if (!switched) { // Only switch if we haven't already
                        foundBreak = true;
                        useFirstWriter = !useFirstWriter;
                        currentWriter = useFirstWriter ? writer1 : writer2;
                        prev1 = null; // Reset for new run
                        prev2 = null; // Reset for new run
                    }
                }

                // Merge the values
                if (value1 != null && value2 != null) {
                    if (value1.compareToIgnoreCase(value2) <= 0) {
                        currentWriter.write(value1 + "\n");
                        prev1 = value1;
                        value1 = reader1.hasNextLine() ? reader1.nextLine() : null;
                    } else {
                        currentWriter.write(value2 + "\n");
                        prev2 = value2;
                        value2 = reader2.hasNextLine() ? reader2.nextLine() : null;
                    }
                } else if (value1 != null) {
                    currentWriter.write(value1 + "\n");
                    prev1 = value1;
                    value1 = reader1.hasNextLine() ? reader1.nextLine() : null;
                } else {
                    currentWriter.write(value2 + "\n");
                    prev2 = value2;
                    value2 = reader2.hasNextLine() ? reader2.nextLine() : null;
                }
            }

            // If we found any breaks, we're not done yet
            isRunComplete = !foundBreak;

            reader1.close();
            reader2.close();
            writer1.close();
            writer2.close();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return isRunComplete;
    }
}