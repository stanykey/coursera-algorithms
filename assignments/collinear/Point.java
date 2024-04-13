import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;


public class Point implements Comparable<Point> {
    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public double slopeTo(Point that) {
        if (that == null) {
            throw new NullPointerException("that is null");
        }

        // Check for degenerate line segment
        if (this.x == that.x && this.y == that.y) {
            return Double.NEGATIVE_INFINITY;
        }

        // Check for horizontal line segment
        if (this.y == that.y) {
            return +0.0;
        }

        // Check for vertical line segment
        if (this.x == that.x) {
            return Double.POSITIVE_INFINITY;
        }

        // Calculate slope
        return (double) (that.y - this.y) / (that.x - this.x);
    }

    public int compareTo(Point that) {
        if (this.x == that.x && this.y == that.y) {
            return 0;
        }

        if (this.y < that.y) {
            return -1;
        }

        return (this.y == that.y && this.x < that.x) ? -1 : 1;
    }

    public Comparator<Point> slopeOrder() {
        return new SlopeOrder();
    }

    public void draw() {
        StdDraw.point(x, y);
    }

    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    private class SlopeOrder implements Comparator<Point> {
        public int compare(Point a, Point b) {
            return Double.compare(slopeTo(a), slopeTo(b));
        }
    }
}
