package Assignment_1;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Heap {

    private static final int runLength = 64 + 1;
    private static final String inputFile = "input_story.txt";
    private static final String outputFile = "output_story.txt";
    private String[] heap = new String[runLength];

    public static void main(String[] args) {
        Heap heapObj = new Heap();
        heapObj.readDataStream();
        // heapObj.printArray();
        heapObj.popAndPrint();
    }

    private void readDataStream() {
        try (Scanner scanner = new Scanner(new FileInputStream(inputFile))) {
            int i = 1;
            while (scanner.hasNextLine() && i < runLength) {
                String line = scanner.nextLine().trim();
                // Skip empty lines
                while (line.isEmpty() && scanner.hasNextLine()) {
                    line = scanner.nextLine().trim();
                }
                if (!line.isEmpty()) {
                    heap[i] = line;
                    i++;
                }
            }
            buildMinHeap();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void buildMinHeap() {
        int n = heap.length - 1; // Ignore index 0
        for (int i = n / 2; i >= 1; i--) {
            heapify(i, n);
        }
    }

    // i = current position to run heapify
    private void heapify(int i, int n) {
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

    private void swap(int i, int smallest) {
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

    private void popAndPrint() {

        System.out.println("\n\n\n" + "ORDERED" + "\n\n\n");

        int n = heap.length - 1; // size of heap (ignoring index 0)

        try (FileWriter writer = new FileWriter(outputFile)) {
            while (n > 0) {
                // Print the root (smallest element)
                System.out.println(heap[1]);
                writer.write(heap[1] + "\n");

                // Swap the root with the last element
                swap(1, n);

                // Decrease the heap size
                n--;

                // Heapify the root to restore the min-heap property
                heapify(1, n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}