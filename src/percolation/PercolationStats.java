import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;

    private final int trialCount;
    private final double[] fractions;

    public PercolationStats(int sideSize, int trialsCount) {
        if (sideSize <= 0) {
            throw new IllegalArgumentException("grid side size has to be greater zero!");
        }

        if (trialsCount <= 0) {
            throw new IllegalArgumentException("trials count has to be greater zero!");
        }

        this.trialCount = trialsCount;
        this.fractions = new double[trialsCount];

        final double numberOfSites = sideSize * sideSize;
        for (int i = 0; i < trialsCount; i++) {
            Percolation percolation = new Percolation(sideSize);
            while (!percolation.percolates()) {
                openRandomClosed(percolation, sideSize);
            }
            this.fractions[i] = percolation.numberOfOpenSites() / numberOfSites;
        }
    }

    public static void main(String[] args) {
        final int sideSize = Integer.parseInt(args[0]);
        final int trialsCount = Integer.parseInt(args[1]);
        final PercolationStats stats = new PercolationStats(sideSize, trialsCount);

        stats.print();
    }

    public double mean() {
        return StdStats.mean(fractions);
    }

    public double stddev() {
        return StdStats.stddev(fractions);
    }

    public double confidenceLo() {
        return mean() - ((CONFIDENCE_95 * stddev()) / Math.sqrt(trialCount));
    }

    public double confidenceHi() {
        return mean() + ((CONFIDENCE_95 * stddev()) / Math.sqrt(trialCount));
    }

    private void openRandomClosed(Percolation percolation, int sideSize) {
        boolean found = false;
        while (!found) {
            final int row = StdRandom.uniformInt(1, sideSize + 1);
            final int col = StdRandom.uniformInt(1, sideSize + 1);
            found = !percolation.isOpen(row, col);
            if (found) {
                percolation.open(row, col);
            }
        }
    }

    private void print() {
        StdOut.printf("mean                    = %.16f\n", mean());
        StdOut.printf("stddev                  = %.16f\n", mean());
        StdOut.printf("95%% confidence interval = [%.16f, %.16f]\n", confidenceLo(), confidenceHi());
    }
}
