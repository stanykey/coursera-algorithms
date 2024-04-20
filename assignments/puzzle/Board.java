import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {
    private final int dimension;
    private final int[] tiles;
    private final int hamming;
    private final int manhattan;


    public Board(int[][] tiles) {
        this(tiles.length, convertToFlatten(tiles));
    }

    private Board(int dimension, int[] tiles) {
        this.dimension = dimension;
        this.tiles = tiles;

        final int[] weights = computeWeights(this.dimension, this.tiles);
        this.hamming = weights[0];
        this.manhattan = weights[1];
    }

    public static void main(String[] args) {
        final int[][] tiles = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
        final Board board = new Board(tiles);
        StdOut.println("board = \n" + board);

        StdOut.println("dimension = " + board.dimension());
        StdOut.println("hamming = " + board.hamming());
        StdOut.println("manhattan = " + board.manhattan());
        StdOut.println("isGoal = " + board.isGoal());
        StdOut.println("equals to twin = " + board.equals(board.twin()));
        StdOut.println("twi twins equal = " + board.twin().equals(board.twin()));

        StdOut.println();
        for (Board neighbor : board.neighbors()) {
            StdOut.println(neighbor);
            StdOut.println();
        }
    }

    private static int[] convertToFlatten(int[][] tiles) {
        if (tiles == null) {
            throw new NullPointerException("tiles is null");
        }

        final int dimension = tiles.length;
        if (dimension != tiles[0].length) {
            throw new IllegalArgumentException("Tiles must be squared matrix.");
        }

        final int[] flattenTiles = new int[dimension * dimension];
        for (int i = 0; i < dimension; i++) {
            System.arraycopy(tiles[i], 0, flattenTiles, i * dimension, dimension);
        }
        return flattenTiles;
    }

    private static int[] computeWeights(int dimension, int[] tiles) {
        int[] weights = new int[2];
        for (int i = 0; i < tiles.length; i++) {
            final int tile = tiles[i];
            if (tile == 0) {
                // assuming 0 is the blank space which does not need to be moved
                continue;
            }

            final int targetIndex = tile - 1;
            if (targetIndex != i) {
                weights[0]++;
            }

            final int targetRow = targetIndex / dimension;
            final int targetCol = targetIndex % dimension;
            final int actualRow = i / dimension;
            final int actualCol = i % dimension;
            weights[1] += Math.abs(actualRow - targetRow) + Math.abs(actualCol - targetCol);
        }
        return weights;
    }

    public Iterable<Board> neighbors() {
        int blankPos = 0;
        while (blankPos != tiles.length) {
            if (tiles[blankPos] == 0) {
                break;
            }
            blankPos++;
        }

        final List<Board> neighbors = new ArrayList<>();

        final int blankRow = blankPos / dimension;
        final int blankCol = blankPos % dimension;
        final int[] rowShift = {-1, 1, 0, 0}; // up, down
        final int[] colShift = {0, 0, -1, 1}; // left, right
        for (int i = 0; i < 4; i++) {
            final int newRow = blankRow + rowShift[i];
            final int newCol = blankCol + colShift[i];

            // Check if the new row and column are within the bounds of the board
            if (newRow >= 0 && newRow < dimension && newCol >= 0 && newCol < dimension) {
                final int newBlankIndex = newRow * dimension + newCol;
                final int[] newTiles = tiles.clone();

                // Swap the blank tile with the target tile
                newTiles[blankPos] = newTiles[newBlankIndex];
                newTiles[newBlankIndex] = 0;

                neighbors.add(new Board(dimension, newTiles));
            }
        }

        return neighbors;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(dimension).append("\n");

        // Calculate the maximum number of digits any element can have
        final int cellSize = Integer.toString(tiles.length).length();
        for (int row = 0; row < dimension; row++) {
            for (int col = 0; col < dimension; col++) {
                // Format each number to be right-aligned within the cellSize
                builder.append(String.format("%" + cellSize + "d ", tiles[row * dimension + col]));
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    public int dimension() {
        return dimension;
    }

    public int hamming() {
        return hamming;
    }

    public int manhattan() {
        return manhattan;
    }

    public boolean isGoal() {
        return (hamming() == 0);
    }

    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        if (getClass() != other.getClass()) {
            return false;
        }

        final Board that = (Board) other;
        if (this == that) {
            return true;
        }
        return (this.dimension == that.dimension) && Arrays.equals(this.tiles, that.tiles);
    }

    public Board twin() {
        final int[] newTiles = tiles.clone();

        // Find two non-zero tiles to swap
        int first = -1;
        int second = -1;
        for (int i = 0; i < tiles.length - 1; i++) {
            // Avoid the last column boundary for horizontal twin swaps
            if ((i + 1) % dimension != 0 && tiles[i] != 0 && tiles[i + 1] != 0) {
                first = i;
                second = i + 1;
                break;
            }
        }

        // Swap the two chosen tiles
        final int temp = newTiles[first];
        newTiles[first] = newTiles[second];
        newTiles[second] = temp;

        return new Board(dimension, newTiles);
    }
}
