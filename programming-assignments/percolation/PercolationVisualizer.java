import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.Font;

public class PercolationVisualizer {
    // delay in milliseconds (controls animation speed)
    private static final int DELAY = 100;

    // draw n-by-n percolation system
    public static void draw(Percolation percolation, int sideSize) {
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setXscale(-0.05 * sideSize, 1.05 * sideSize);
        StdDraw.setYscale(-0.05 * sideSize, 1.05 * sideSize);   // leave a border to write text
        StdDraw.filledSquare(sideSize / 2.0, sideSize / 2.0, sideSize / 2.0);

        // draw n-by-n grid
        int opened = 0;
        for (int row = 1; row <= sideSize; row++) {
            for (int col = 1; col <= sideSize; col++) {
                if (percolation.isFull(row, col)) {
                    StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
                    opened++;
                }
                else if (percolation.isOpen(row, col)) {
                    StdDraw.setPenColor(StdDraw.WHITE);
                    opened++;
                }
                else {
                    StdDraw.setPenColor(StdDraw.BLACK);
                }
                StdDraw.filledSquare(col - 0.5, sideSize - row + 0.5, 0.45);
            }
        }

        // write status text
        StdDraw.setFont(new Font("SansSerif", Font.PLAIN, 12));
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.text(0.25 * sideSize, -0.025 * sideSize, opened + " open sites");
        if (percolation.percolates()) {
            StdDraw.text(0.75 * sideSize, -0.025 * sideSize, "percolates");
        }
        else {
            StdDraw.text(0.75 * sideSize, -0.025 * sideSize, "does not percolate");
        }

    }

    public static void main(String[] args) {
        In in = new In(args[0]);      // input file
        int sideSize = in.readInt();  // n-by-n percolation system

        // turn on animation mode
        StdDraw.enableDoubleBuffering();

        // repeatedly read in sites to open and draw resulting system
        Percolation percolation = new Percolation(sideSize);
        draw(percolation, sideSize);
        StdDraw.show();
        StdDraw.pause(DELAY);
        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();
            percolation.open(i, j);
            draw(percolation, sideSize);
            StdDraw.show();
            StdDraw.pause(DELAY);
        }
    }
}
