import edu.princeton.cs.algs4.StdOut;
public class TestVisualAccumulator
{
    public static void main(String[] args)
    {
        int T = Integer.parseInt(args[0]);
        VisualAccumulator a = new VisualAccumulator(T, 1.0);
        for (int t = 0; t < T; t++)
            a.addDateValue(StdRandom.random());
        StdOut.println(a);
    }
}
