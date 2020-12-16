import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {
    private final double[] fractions;

    public PercolationStats(int sideSize, int trialsCount) {
        if (sideSize <= 0) {
            throw new IllegalArgumentException("grid side size has to be greater zero!");
        }

        if (trialsCount <= 0) {
            throw new IllegalArgumentException("trials count has to be greater zero!");
        }

        double numberOfSites = sideSize * sideSize;
        this.fractions = new double[trialsCount];
        for (int i = 0; i < trialsCount; i++) {
            Percolation percolation = new Percolation(sideSize);
            while (!percolation.percolates()) {
                openRandomClosed(percolation, sideSize);
            }
            this.fractions[i] = percolation.numberOfOpenSites() / numberOfSites;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(this.fractions);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(this.fractions);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return StdStats.min(this.fractions);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return StdStats.max(this.fractions);
    }

    private void openRandomClosed(Percolation percolation, int sideSize) {
        boolean found = false;
        while (!found) {
            int row = StdRandom.uniform(1, sideSize + 1);
            int col = StdRandom.uniform(1, sideSize + 1);
            found = !percolation.isOpen(row, col);
            if (found) {
                percolation.open(row, col);
            }
        }
    }

    private void print() {
        StdOut.printf("mean                    = %.16f\n", mean());
        StdOut.printf("stddev                  = %.16f\n", mean());
        StdOut.printf("95%% confidence interval = [%.16f, %.16f]\n", confidenceLo(),
                      confidenceHi());
    }

    // perform independent trials on an n-by-n grid
    public static void main(String[] args) {
        int sideSize = Integer.parseInt(args[0]);
        int trialsCount = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(sideSize, trialsCount);

        stats.print();
    }


}
