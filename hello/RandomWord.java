import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        int count = 0;
        String result = "";
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
