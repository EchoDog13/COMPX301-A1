
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;

public class Heap {
    private static int runLength = 64 + 1; // Default heap size (1-based indexing)

    /**
     * Set the run length for the heap
     * 
     * @param length the run length
     */
    public static void setRunLength(int length) {
        // Set the run length for the heap adding 1 for 1-based indexing of the heap
        runLength = length + 1;
        heap = new String[runLength];
    }

    // Temporary files for heap sort output
    private static final String tempFile_1 = "tempFile_1.txt";
    private static final String tempFile_2 = "tempFile_2.txt";

    static String[] heap = new String[runLength + 1];

    public static void main(int runLength) {
        int runCount = 0;
        setRunLength(runLength);

        // Clear the output files
        try (FileWriter writer1 = new FileWriter(tempFile_1, false);
                FileWriter writer2 = new FileWriter(tempFile_2, false)) {
            // Files are cleared by opening in overwrite mode
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Read input from the console/standard input
        try (Scanner scanner = new Scanner(System.in)) {
            while (scanner.hasNextLine()) {
                Arrays.fill(heap, null); // Clear the heap for each run
                int i = 1;

                // Read elements into the heap
                while (scanner.hasNextLine() && i < runLength + 1) {
                    // Read the next line and trim leading/trailing whitespace
                    String line = scanner.nextLine().trim();
                    // Skip empty lines
                    while (line.isEmpty() && scanner.hasNextLine()) {
                        line = scanner.nextLine().trim();
                    }
                    // If the line is not empty, add to the head
                    if (!line.isEmpty()) {
                        heap[i] = line;
                        i++;
                    }
                }

                // Build the min-heap with the actual number of elements (i - 1)
                buildMinHeap(i - 1);

                // Pop elements from the heap and write to the appropriate file
                popAndPrint(i - 1, runCount);
                runCount++;

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Build a min-heap from the input array
     * 
     * @param size the number of elements in the heap - normally runLength but
     *             sometimes less at the end of the input
     */
    private static void buildMinHeap(int size) {
        if (size < 2)
            return; // No need to heapify if there's only one element
        // Start from the last non-leaf node and heapify each node in reverse order
        for (int i = size / 2; i >= 1; i--) {
            heapify(i, size);
        }
    }

    // Heapify the subtree rooted at index i
    private static void heapify(int i, int n) {
        int smallest = i;
        int left = 2 * i;
        int right = (2 * i) + 1;

        // Compare elements as strings for lexicographical sorting
        if (left <= n && heap[left].compareToIgnoreCase(heap[smallest]) < 0) {
            smallest = left;
        }

        if (right <= n && heap[right].compareToIgnoreCase(heap[smallest]) < 0) {
            smallest = right;
        }

        // If the smallest element is not the root, swap the root with the smallest
        // element
        if (smallest != i) {
            swap(i, smallest);
            heapify(smallest, n); // Recursively heapify the affected subtree
        }
    }

    /**
     * Swap two elements in the heap
     * 
     * @param i first item index
     * @param j second item index
     */
    private static void swap(int i, int j) {
        String temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    /**
     * Pop elements from the heap and write to the appropriate file/standard output
     * 
     * @param size     the number of elements in the heap
     * @param runCount the current run count used for determining the output file
     */
    private static void popAndPrint(int size, int runCount) {
        // No need to pop if there are no elements
        if (size < 1)
            return;

        // Write the smallest element to the appropriate file
        try (FileWriter writer = new FileWriter(runCount % 2 == 0 ? tempFile_1 : tempFile_2, true)) {

            while (size > 0 && heap[1] != null) {

                // Write the smallest element to the appropriate file
                writer.write(heap[1] + "\n");
                // System.out.println(heap[1]); // Print the smallest element

                // Swap the root with the last element and reduce the heap size
                swap(1, size);
                size--;

                // Heapify the root to maintain the heap property
                if (size > 0) {
                    heapify(1, size);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}