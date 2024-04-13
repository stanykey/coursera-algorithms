import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;


public class InteractivePercolationVisualizer {
    public static void main(String[] args) {
        // n-by-n percolation system (read from command-line, default = 10)
        int sideSize = (args.length == 1) ? Integer.parseInt(args[0]) : 10;

        // repeatedly open site specified my mouse click and draw resulting system
        StdOut.println(sideSize);

        StdDraw.enableDoubleBuffering();
        Percolation percolation = new Percolation(sideSize);
        PercolationVisualizer.draw(percolation, sideSize);
        StdDraw.show();

        while (true) {
            // detected mouse click
            if (StdDraw.isMousePressed()) {
                // screen coordinates
                double x = StdDraw.mouseX();
                double y = StdDraw.mouseY();

                // convert to row i, column j
                int i = (int) (sideSize - Math.floor(y));
                int j = (int) (1 + Math.floor(x));

                // open site (i, j) provided it's in bounds
                if (i >= 1 && i <= sideSize && j >= 1 && j <= sideSize) {
                    if (!percolation.isOpen(i, j)) {
                        StdOut.println(i + " " + j);
                    }
                    percolation.open(i, j);
                }

                // draw n-by-n percolation system
                PercolationVisualizer.draw(percolation, sideSize);
                StdDraw.show();
            }

            StdDraw.pause(20);
        }
    }
}
