import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        if (StdIn.isEmpty()) {
            StdOut.println("heads tails");
            if (StdRandom.bernoulli(0.5))
                StdOut.println("heads");
            else
                StdOut.println("tails");
            return;
        } else {
            int count = 1;
            String result = StdIn.readString();
            while (!StdIn.isEmpty()) {
                count++;
                String next = StdIn.readString();
                if (StdRandom.bernoulli(1.0 / count)) {
                    result = next;
                }
            }
            StdOut.println(result);
        }
    }
}
