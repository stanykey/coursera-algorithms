import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    private static final byte CONNECTED_WITH_SOURCE = 1 << 1;
    private static final byte CONNECTED_WITH_RECEIVER = 1;
    private static final byte OPEN = 1 << 2;
    private static final byte PIPE = CONNECTED_WITH_SOURCE | CONNECTED_WITH_RECEIVER | OPEN;

    private final int dimension;
    private final byte[] state;
    private final WeightedQuickUnionUF finder;

    private int openedSites = 0;
    private boolean isPercolated = false;

    public Percolation(int dimension) {
        if (dimension <= 0) {
            throw new IllegalArgumentException("grid side size has to be greater zero!");
        }

        this.dimension = dimension;
        this.state = new byte[dimension * dimension];
        this.finder = new WeightedQuickUnionUF(this.state.length);
    }

    public void open(int row, int col) {
        if (isOpen(row, col)) {
            return;
        }

        final int index = index(row, col);
        state[index] |= OPEN;
        openedSites++;

        if (isFirstRow(row)) {
            state[index] |= CONNECTED_WITH_SOURCE;
        }

        if (isLastRow(row)) {
            state[index] |= CONNECTED_WITH_RECEIVER;
        }

        if (!isFirstRow(row) && isOpen(above(index))) {
            connect(index, above(index));
        }

        if (!isLastRow(row) && isOpen(below(index))) {
            connect(index, below(index));
        }

        if (!isFirstCol(col) && isOpen(left(index))) {
            connect(index, left(index));
        }

        if (!isLastCol(col) && isOpen(right(index))) {
            connect(index, right(index));
        }

        if (!isPercolated) {
            isPercolated = (state[finder.find(index)] == PIPE);
        }
    }

    public boolean isOpen(int row, int col) {
        checkCoordinates(row, col);
        final int site = finder.find(index(row, col));
        return ((state[site] & OPEN) != 0);
    }

    public boolean isFull(int row, int col) {
        checkCoordinates(row, col);
        final byte site = state[finder.find(index(row, col))];
        return ((site & CONNECTED_WITH_SOURCE) != 0);
    }

    public int numberOfOpenSites() {
        return openedSites;
    }

    public boolean percolates() {
        return isPercolated;
    }

    private void checkCoordinates(int row, int col) {
        if (row < 1 || row > dimension) {
            throw new IllegalArgumentException("row index is out of range");
        }

        if (col < 1 || col > dimension) {
            throw new IllegalArgumentException("col index is out of range");
        }
    }

    private void connect(int first, int second) {
        final int firstRoot = finder.find(first);
        final int secondRoot = finder.find(second);
        if (firstRoot == secondRoot) {
            return;
        }

        finder.union(firstRoot, secondRoot);

        final int newRoot = finder.find(first);
        state[newRoot] |= state[firstRoot];
        state[newRoot] |= state[secondRoot];
    }

    private int index(int row, int col) {
        return ((row - 1) * dimension) + (col - 1);
    }

    private boolean isOpen(int index) {
        return ((state[index] & OPEN) != 0);
    }

    private boolean isFirstRow(int row) {
        return (row == 1);
    }

    private boolean isLastRow(int row) {
        return (row == dimension);
    }

    private boolean isFirstCol(int col) {
        return (col == 1);
    }

    private boolean isLastCol(int col) {
        return (col == dimension);
    }

    private int above(int siteIndex) {
        return siteIndex - dimension;
    }

    private int below(int siteIndex) {
        return siteIndex + dimension;
    }

    private int left(int siteIndex) {
        return siteIndex - 1;
    }

    private int right(int siteIndex) {
        return siteIndex + 1;
    }
}
