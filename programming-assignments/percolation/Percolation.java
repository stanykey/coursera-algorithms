import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    private final int sideSize;

    private int openedSitesCount;
    private final int[][] ids;
    private final boolean[][] statuses;

    private final int invisibleTop;
    private final int invisibleBottom;
    private final WeightedQuickUnionUF unionFinder;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int sideSize) {
        if (sideSize <= 0) {
            throw new IllegalArgumentException("grid side size has to be greater zero!");
        }

        this.sideSize = sideSize;
        this.ids = new int[sideSize][sideSize];
        this.statuses = new boolean[sideSize][sideSize];
        this.openedSitesCount = 0;

        int id = 1;
        for (int row = 0; row < sideSize; row++) {
            for (int col = 0; col < sideSize; col++) {
                this.ids[row][col] = id;
                this.statuses[row][col] = false;
                id++;
            }
        }

        this.invisibleTop = 0;
        this.invisibleBottom = (sideSize * sideSize) + 1;
        this.unionFinder = new WeightedQuickUnionUF(invisibleBottom + 1);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (isOpen(row, col)) {
            return;
        }

        int id = getSiteId(row, col);
        this.statuses[row - 1][col - 1] = true;
        this.openedSitesCount++;

        if (isFirstRow(row)) {
            this.unionFinder.union(id, this.invisibleTop);
        }

        if (isLastRow(row)) {
            this.unionFinder.union(id, this.invisibleBottom);
        }

        // try to connect with the site above
        if (!isFirstRow(row) && isSiteOpen(row - 1, col)) {
            this.unionFinder.union(id, getSiteId(row - 1, col));
        }

        // try to connect with the site below
        if (!isLastRow(row) && isSiteOpen(row + 1, col)) {
            this.unionFinder.union(id, getSiteId(row + 1, col));
        }

        // try to connect with the site on the left
        if (!isFirstCol(col) && isSiteOpen(row, col - 1)) {
            this.unionFinder.union(id, getSiteId(row, col - 1));
        }

        // try to connect with the site on the right
        if (!isLastCol(col) && isSiteOpen(row, col + 1)) {
            this.unionFinder.union(id, getSiteId(row, col + 1));
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkCoordinates(row, col);
        return isSiteOpen(row, col);
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        checkCoordinates(row, col);
        return areSitesConnected(getSiteId(row, col), this.invisibleTop);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.openedSitesCount;
    }

    // does the system percolate?
    public boolean percolates() {
        return areSitesConnected(this.invisibleTop, this.invisibleBottom);
    }

    private boolean isFirstRow(int row) {
        return (row == 1);
    }

    private boolean isLastRow(int row) {
        return (row == this.sideSize);
    }

    private boolean isFirstCol(int col) {
        return (col == 1);
    }

    private boolean isLastCol(int col) {
        return (col == this.sideSize);
    }

    private void checkCoordinates(int row, int col) {
        if (row < 1 || row > this.sideSize) {
            throw new IllegalArgumentException("row index is out of range");
        }

        if (col < 1 || col > this.sideSize) {
            throw new IllegalArgumentException("col index is out of range");
        }
    }

    private boolean areSitesConnected(int lid, int rid) {
        return (this.unionFinder.find(lid) == this.unionFinder.find(rid));
    }

    private int getSiteId(int row, int col) {
        return this.ids[row - 1][col - 1];
    }

    private boolean isSiteOpen(int row, int col) {
        return this.statuses[row - 1][col - 1];
    }

}
