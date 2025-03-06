package Assignment_1;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Heap {

    private static final int runLength = 64 + 1;
    private static final String inputFile = "MobyDick.txt";
    private static final String outputFile = "output_story.txt";
    private static String[] heap = new String[runLength];

    public static void main(String[] args) {
        // Heap heapObj = new Heap();
        readDataStream();
        // heapObj.printArray();
    }

    private static void readDataStream() {
        try (Scanner scanner = new Scanner(new FileInputStream(inputFile))) {

            while (scanner.hasNextLine()) {
                Arrays.fill(heap, null);
                int i = 1;

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

                // Pass the actual count of elements (i - 1) instead of heap.length
                buildMinHeap(i - 1);
                popAndPrint(i - 1);

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

    private static void popAndPrint(int size) {
        System.out.println("\n\n\nORDERED\n\n\n");

        if (size < 1)
            return;

        try (FileWriter writer = new FileWriter(outputFile)) {
            while (size > 0 && heap[1] != null) {
                System.out.println(heap[1]);
                writer.write(heap[1] + "\n");

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