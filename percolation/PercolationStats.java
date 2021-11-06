import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_RATIO = 1.96;
    private final int trials;
    private final double[] results;
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials)
    {
        if (n <= 0) throw new IllegalArgumentException();
        if (trials <= 0) throw new IllegalArgumentException();

        this.trials = trials;
        results = new double[trials];
        for (int t = 0; t < trials; t++) {
            Percolation percolation = new Percolation(n);
            int[] perm = StdRandom.permutation(n * n);
            for (int i = 0; i < n * n; i++) {
                int target = perm[i];
                int targetRow = target / n;
                int targetCol = target % n;
                percolation.open(targetRow + 1, targetCol + 1);
                if (percolation.percolates()) break;
            }
            results[t] = (double) percolation.numberOfOpenSites() / n / n;
        }
    }
    // sample mean of percolation threshold
    public double mean()
    {
        return StdStats.mean(results);
    }
    // sample standard deviation of percolation threshold
    public double stddev()
    {
        return StdStats.stddev(results);
    }
    // low endpoint of 95% confidence interval
    public double confidenceLo()
    {
        return mean() - CONFIDENCE_RATIO * stddev() / Math.sqrt((double) trials);
    }
    // high endpoint of 95% confidence interval
    public double confidenceHi()
    {
        return mean() + CONFIDENCE_RATIO * stddev() / Math.sqrt((double) trials);
    }
   // test client (see below)
    public static void main(String[] args)
    {
        PercolationStats stats = new PercolationStats(
                Integer.parseInt(args[0]),
                Integer.parseInt(args[1]));
        System.out.println(String.format("%-23s = %f", "mean", stats.mean()));
        System.out.println(String.format("%-23s = %f", "stddev", stats.stddev()));
        System.out.println(String.format("%-23s = [%f, %f]",
                    "95% confidence interval", stats.confidenceLo(), stats.confidenceHi()));
    }
}
