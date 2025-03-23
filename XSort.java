public class XSort {
    /**
     * Main program to call Heap and Merge classes to sort the input using a 2 way
     * balanced sort merge
     * 
     * @param args 1: runLength 2: k-way merge
     */
    public static void main(String[] args) {
        // Check for correct number of arguments
        if (args.length < 2) {
            System.err.println("Usage: java XSort <runLength> <k-way merge>");
            return;
        }
        // Check for correct k-way merge: only support 2-way merge
        if (Integer.parseInt(args[1]) != 2) {
            System.err.println("Only 2-way merge is supported.");
            return;
        }
        // Check for correct run length range between 64 and 1024
        if (Integer.parseInt(args[0]) < 64 || Integer.parseInt(args[0]) > 1024) {
            System.err.println("Run length must be between 64 and 1024.");
            return;
        }

        // Set run length
        int runLength = Integer.parseInt(args[0]);
        // call heap and parse runLength
        Heap.main(runLength);
        // call merge
        Merge.main();
    }

}
