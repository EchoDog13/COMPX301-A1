
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;

public class Heap {
    private static int runLength = 10 + 1; // Default heap size (1-based indexing)

    public static void setRunLength(int length) {
        runLength = length + 1;
        heap = new String[runLength];
    }

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

        try (Scanner scanner = new Scanner(System.in)) {
            while (scanner.hasNextLine()) {
                Arrays.fill(heap, null); // Clear the heap for each run
                int i = 1;

                // Read elements into the heap
                while (scanner.hasNextLine() && i < runLength + 1) {
                    String line = scanner.nextLine().trim();
                    while (line.isEmpty() && scanner.hasNextLine()) {
                        line = scanner.nextLine().trim();
                    }
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

    private static void buildMinHeap(int size) {
        if (size < 2)
            return; // No need to heapify if there's only one element

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

        if (smallest != i) {
            swap(i, smallest);
            heapify(smallest, n); // Recursively heapify the affected subtree
        }
    }

    private static void swap(int i, int j) {
        String temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    private static void popAndPrint(int size, int runCount) {
        if (size < 1)
            return;

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