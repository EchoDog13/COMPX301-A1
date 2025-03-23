public class XSort {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Usage: java XSort <runLength> <k-way merge>");
            return;
        }
        if (Integer.parseInt(args[1]) != 2) {
            System.err.println("Only 2-way merge is supported.");
            return;
        }

        int runLength = Integer.parseInt(args[0]);
        Heap.main(runLength);
        Merge.main(args);
    }

}
