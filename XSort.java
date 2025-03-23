public class XSort {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: java XSort <runLength>");
            return;
        }

        int runLength = Integer.parseInt(args[0]);
        Heap.main(runLength);
        Merge2.main(args);
    }

}
