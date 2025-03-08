package Assignment_1;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Heap {

    private static final int runLength = 64 + 1;
    private static final String inputFile = "MobyDick.txt";
    private static final String tempFile_1 = "tempFile_1.txt";
    private static final String tempFile_2 = "tempFile_2.txt";

    private static String[] heap = new String[runLength];

    public static void main(String[] args) {
        // Heap heapObj = new Heap();
        readDataStream();
        // heapObj.printArray();
    }

    private static void readDataStream() {
        int runCount = 0;
        try (Scanner scanner = new Scanner(new FileInputStream(inputFile))) {

            while (scanner.hasNextLine()) {
                Arrays.fill(heap, null);
                int i = 1;
                runCount++;

                while (scanner.hasNextLine() && i < runLength) {
                    String line = scanner.nextLine().trim();
                    while (line.isEmpty() && scanner.hasNextLine()) {
                        line = scanner.nextLine().trim();
                    }
                    if (!line.isEmpty()) {
                        heap[i] = line;
                        i++;
                    }
                }

                // Pass the actual count of elements (i - 1)
                buildMinHeap(i - 1);
                popAndPrint(i - 1, runCount);

            }
            scanner.close();
        } catch (IOException e) {
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

    // i = current position to run heapify
    private static void heapify(int i, int n) {
        int smallest = i;
        int left = 2 * i;
        int right = (2 * i) + 1;

        if (left <= n && heap[left].compareTo(heap[smallest]) < 0) {
            smallest = left;
        }

        if (right <= n && heap[right].compareTo(heap[smallest]) < 0) {
            smallest = right;
        }

        if (smallest != i) {
            swap(i, smallest);
            heapify(smallest, n);
        }
    }

    private static void swap(int i, int smallest) {
        String temp = heap[i];
        heap[i] = heap[smallest];
        heap[smallest] = temp;
    }

    private void printArray() {
        for (int i = 1; i < heap.length; i++) {
            if (heap[i] != null) {
                System.out.println(heap[i]);
            }
        }
    }

    private static void popAndPrint(int size, int runCount) {
        System.out.println("\n\n\nORDERED\n\n\n");

        if (size < 1)
            return;

        try (FileWriter writer1 = new FileWriter(tempFile_1, true);
                FileWriter writer2 = new FileWriter(tempFile_2, true);) {
            while (size > 0 && heap[1] != null) {
                System.out.println(heap[1]);

                if (runCount % 2 == 0) {
                    writer1.write(heap[1] + "\n");
                    writer1.flush();
                } else {
                    writer2.write(heap[1] + "\n");
                    writer2.flush();
                }

                swap(1, size);
                size--;

                if (size > 0) {
                    heapify(1, size);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}