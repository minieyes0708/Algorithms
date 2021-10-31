import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        if (StdIn.isEmpty()) {
            System.out.println("heads tails");
            if (StdRandom.bernoulli(0.5))
                System.out.println("heads");
            else
                System.out.println("tails");
            return;
        }
    }
}
