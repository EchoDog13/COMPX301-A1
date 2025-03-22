package Assignment_1;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Heap {

    private static final int runLength = 10000 + 1; // Heap size (1-based indexing)
    private static final String inputFile = "BrownCorpus.txt";
    private static final String tempFile_1 = "tempFile_1.txt";
    private static final String tempFile_2 = "tempFile_2.txt";

    private static String[] heap = new String[runLength];

    public static void main(String[] args) {
        readDataStream();
    }

    public static void readDataStream() {
        int runCount = 0;

        // Clear the output files
        try (FileWriter writer1 = new FileWriter(tempFile_1, false);
                FileWriter writer2 = new FileWriter(tempFile_2, false)) {
            // Files are cleared by opening in overwrite mode
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (Scanner scanner = new Scanner(new FileInputStream(inputFile))) {
            while (scanner.hasNextLine()) {
                Arrays.fill(heap, null); // Clear the heap for each run
                int i = 1;
                runCount++;

                // Read elements into the heap
                while (scanner.hasNextLine() && i < runLength) {
                    String line = scanner.nextLine().trim();
                    System.out.println(line);
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
            }

            scanner.close();
        } catch (Exception e) {
            // TODO: handle exception
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
        if (left <= n && heap[left].compareTo(heap[smallest]) < 0) {
            smallest = left;
        }

        if (right <= n && heap[right].compareTo(heap[smallest]) < 0) {
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
        System.out.println("\n\n\nORDERED\n\n\n");

        if (size < 1)
            return;

        try (FileWriter writer1 = new FileWriter(tempFile_1, true);
                FileWriter writer2 = new FileWriter(tempFile_2, true)) {
            while (size > 0 && heap[1] != null) {
                System.out.println(heap[1]); // Print the smallest element

                // Write to the appropriate file based on runCount
                if (runCount % 2 == 0) {
                    writer1.append(heap[1] + "\n");
                    writer1.flush();
                } else {
                    writer2.append(heap[1] + "\n");
                    writer2.flush();
                }

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