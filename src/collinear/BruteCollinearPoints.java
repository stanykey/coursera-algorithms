import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class BruteCollinearPoints {
    private final List<LineSegment> segmentsList;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        points = preparePoints(points);

        this.segmentsList = new ArrayList<>();

        // Brute force approach
        final int pointsCount = points.length;
        for (int p = 0; p < pointsCount - 3; p++) {
            for (int q = p + 1; q < pointsCount - 2; q++) {
                for (int r = q + 1; r < pointsCount - 1; r++) {
                    for (int s = r + 1; s < pointsCount; s++) {
                        final double slope1 = points[p].slopeTo(points[q]);
                        final double slope2 = points[p].slopeTo(points[r]);
                        final double slope3 = points[p].slopeTo(points[s]);
                        if (Double.compare(slope1, slope2) == 0 && Double.compare(slope1, slope3) == 0) {
                            this.segmentsList.add(new LineSegment(points[p], points[s]));
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        In in = new In(args[0]);

        final int pointsCount = in.readInt();
        Point[] points = new Point[pointsCount];
        for (int i = 0; i < pointsCount; i++) {
            final int x = in.readInt();
            final int y = in.readInt();
            points[i] = new Point(x, y);
        }

        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.setPenColor(StdDraw.BLACK);
        for (Point point : points) {
            point.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        final BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        StdOut.println("segments = " + collinear.numberOfSegments());
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

    private static Point[] preparePoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("The array of points is null");
        }

        // Check that all points are valid and make shallow copy
        final int pointsCount = points.length;
        final Point[] auxPoints = new Point[points.length];
        for (int i = 0; i < auxPoints.length; i++) {
            Point point = points[i];
            if (point == null) {
                throw new IllegalArgumentException("One of the points is null");
            }
            auxPoints[i] = point;
        }

        // Check for duplicate points
        Arrays.sort(auxPoints);
        for (int i = 0; i < pointsCount - 1; i++) {
            if (auxPoints[i].compareTo(auxPoints[i + 1]) == 0) {
                throw new IllegalArgumentException("Duplicate points found");
            }
        }

        return auxPoints;
    }

    public int numberOfSegments() {
        return segmentsList.size();
    }

    public LineSegment[] segments() {
        return segmentsList.toArray(new LineSegment[0]);
    }
}
