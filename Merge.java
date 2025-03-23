import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Merge {

    /**
     * Main method to merge the two input files and write the output to the console
     * when fully merged
     * 
     * @param args
     */
    public static void main() {
        // Cycle Count stores the number of times the merge pass has been called to
        // determine which files are input and output
        int cycleCount = 0;

        /**
         * The merge pass is called until the run is complete. The run is complete when
         * there are no out-of-order sequences in the output file.
         */
        while (true) {
            boolean isRunComplete = mergePass(cycleCount);
            // If the run is complete, the output file is printed to the console and the
            // program exits.
            if (isRunComplete) {
                // Print the output file to the console
                Scanner outputScanner;

                /**
                 * Determine which file contains the final merge output based on cycle count
                 * If cycle count is odd, the final output is in tempFile_1.txt
                 * If cycle count is even, the final output is in tempFile_3.txt
                 */
                if (cycleCount % 2 == 1) {
                    outputFinalRun("tempFile_1.txt");
                } else {
                    outputFinalRun("tempFile_3.txt");
                }
                // Exit the program now that the final sorted run has been printed
                break;
            }
            // Increment the cycle count
            cycleCount++;
        }
    }

    /**
     * Method to merge the two input files and write the output to two output files
     * 
     * @param cycleCount used to determine which files are input and output
     * @return true if the merge is complete, false otherwise
     */
    private static boolean mergePass(int cycleCount) {

        // Initialize the readers and writers
        Scanner reader1 = null, reader2 = null;
        FileWriter writer1 = null, writer2 = null;

        // Initialize the input and output files
        String inputFile1, inputFile2, outputFile1, outputFile2;

        // Determine which files are input and output based on the cycle count
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
        // Initialize the readers and writers for the specified files and catch any
        // exceptions
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
        // Initialize the current writer that is used to alternate between the two
        // output files
        FileWriter currentWriter = writer1;

        try {
            String curr1 = null, curr2 = null;
            String prev1 = null, prev2 = null;
            // Initialize the boolean foundBreak to determine if there are any out-of-order
            // sequences
            boolean foundBreak = false;

            // Get initial values
            if (reader1.hasNextLine()) {
                curr1 = reader1.nextLine();
            }

            if (reader2.hasNextLine()) {
                curr2 = reader2.nextLine();
            }

            // Keep track of which output file we're using
            boolean useFirstWriter = true;
            // Merge the two input files until the end of both files is reached
            while (curr1 != null || curr2 != null) {
                // Check for out-of-order sequences and switch writers if needed
                boolean switched = false;

                // Detect breaks in the sequence for each file
                // If the previous value is lexicographically greater than the current value,
                // then there is a break in the sequence
                if (curr1 != null && prev1 != null && curr1.compareToIgnoreCase(prev1) < 0) {
                    // Found a break in first file
                    foundBreak = true;
                    // Switch writers
                    useFirstWriter = !useFirstWriter;
                    currentWriter = useFirstWriter ? writer1 : writer2;
                    prev1 = null; // Reset for new run
                    prev2 = null; // Reset for new run

                    switched = true;
                }

                if (curr2 != null && prev2 != null && curr2.compareToIgnoreCase(prev2) < 0) {
                    // Found a break in second file
                    if (!switched) { // Only switch if we haven't already
                        foundBreak = true;
                        // Switch writers
                        useFirstWriter = !useFirstWriter;
                        currentWriter = useFirstWriter ? writer1 : writer2;
                        prev1 = null; // Reset for new run
                        prev2 = null; // Reset for new run
                    }
                }

                // Merge the values, checking for null values
                if (curr1 != null && curr2 != null) {
                    // Compare elements as strings for lexicographical sorting
                    if (curr1.compareToIgnoreCase(curr2) <= 0) {
                        // Write the current value (curr1) to the current output file
                        currentWriter.write(curr1 + "\n");
                        // Update the previous and current values
                        prev1 = curr1;
                        curr1 = reader1.hasNextLine() ? reader1.nextLine() : null;
                    } else {
                        // curr2 comes before curr1, so write curr2 to the current output file
                        currentWriter.write(curr2 + "\n");
                        prev2 = curr2;
                        curr2 = reader2.hasNextLine() ? reader2.nextLine() : null;
                    }
                    // handle end of file, keep reading and writing until the end of the other file
                    // is reached
                } else if (curr1 != null) {
                    currentWriter.write(curr1 + "\n");
                    prev1 = curr1;
                    // Read the next line from the first file if it exists
                    curr1 = reader1.hasNextLine() ? reader1.nextLine() : null;
                } else {
                    currentWriter.write(curr2 + "\n");
                    prev2 = curr2;
                    // Read the next line from the second file if it exists
                    curr2 = reader2.hasNextLine() ? reader2.nextLine() : null;
                }
            }

            // If any breaks, we're not done yet, so keep merging
            isRunComplete = !foundBreak;

            // Close the readers and writers
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

    /**
     * Method to print the final run to the console/standard output
     * 
     * @param outputFile name of the output file to print to the console
     */
    private static void outputFinalRun(String outputFile) {
        Scanner outputScanner;
        try {
            outputScanner = new Scanner(new File(outputFile));
            while (outputScanner.hasNextLine()) {
                System.out.println(outputScanner.nextLine());
            }
            outputScanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}