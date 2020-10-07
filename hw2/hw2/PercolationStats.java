package hw2;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
public class PercolationStats {
    private double[] sampleTestArray;

    // Runs T experiments for each percolated grid
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0)  {
            throw new IllegalArgumentException(
                    "T and/or N cannot be 0 or less than 0 itself"
            );
        }
        sampleTestArray = new double[T];
        for (int index = 0; index < T; index++) {
            Percolation testGrid = pf.make(N);
            while (!testGrid.percolates()) {
                testGrid.open(StdRandom.uniform(N), StdRandom.uniform(N));
            }
            sampleTestArray[index] = testGrid.numberOfOpenSites() / Math.pow(N, 2);
        }
    }

    // Returns mean of the sampled tests
    public double mean() {
        return StdStats.mean(sampleTestArray);
    }

    // Returns std dev of sampled tests
    public double stddev() {
        return StdStats.stddev(sampleTestArray);
    }

    // Returns 95% confidence interval from sampled tests
    public double confidenceLow() {
        return mean() - 1.96 * stddev() / Math.sqrt(sampleTestArray.length);
    }

    // Returns 105% confidence interval from sampled tests
    public double confidenceHigh() {
        return mean() + 1.96 * stddev() / Math.sqrt(sampleTestArray.length);
    }

}
