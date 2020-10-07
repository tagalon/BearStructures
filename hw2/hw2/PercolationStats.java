package hw2;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
public class PercolationStats {
    private double[] sampleTestArray;
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
    public double mean() {
        return StdStats.mean(sampleTestArray);
    }
    public double stddev() {
        return StdStats.stddev(sampleTestArray);
    }
    public double confidenceLow() {
        return mean() - 1.96 * stddev() / Math.sqrt(sampleTestArray.length);
    }
    public double confidenceHigh() {
        return mean() + 1.96 * stddev() / Math.sqrt(sampleTestArray.length);
    }

}
