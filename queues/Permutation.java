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
            String str = StdIn.readString();
            if (count <= maxCount) {
                queue.enqueue(str);
            } else {
                if (StdRandom.bernoulli((double) maxCount / count)) {
                    queue.dequeue();
                    queue.enqueue(str);
                }
            }
        }
        for (String str : queue) {
            StdOut.println(str);
        }
    }
}
