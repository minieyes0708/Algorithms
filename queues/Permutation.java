import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
    public static void main(String[] args) {
        int maxCount = Integer.parseInt(args[0]);
        RandomizedQueue<String> queue = new RandomizedQueue<String>();

        if (maxCount == 0) return;

        int count = 0;
        while (!StdIn.isEmpty()) {
            count++;
            if (count <= maxCount) {
                queue.enqueue(StdIn.readString());
            } else {
                if (StdRandom.bernoulli((double) maxCount / count)) {
                    queue.dequeue();
                    queue.enqueue(StdIn.readString());
                }
            }
        }
        for (String str : queue) {
            StdOut.println(str);
        }
    }
}
