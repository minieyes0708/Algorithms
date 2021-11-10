import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        int maxCount = Integer.parseInt(args[0]);
        RandomizedQueue<String> queue = new RandomizedQueue<String>();

        while (!StdIn.isEmpty()) {
            queue.enqueue(StdIn.readString());
        }
        int count = 0;
        if (maxCount == 0) return;
        for (String str : queue) {
            StdOut.println(str);
            count++;
            if (count == maxCount)
                break;
        }
    }
}
