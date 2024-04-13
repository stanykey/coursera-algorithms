import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FastCollinearPoints {
    private final List<LineSegment> segmentsList;

    public FastCollinearPoints(Point[] points) {
        points = preparePoints(points);

        this.segmentsList = new ArrayList<>();
        Point[] auxPoint = Arrays.copyOf(points, points.length);
        for (Point origin : points) {
            Arrays.sort(auxPoint);
            Arrays.sort(auxPoint, origin.slopeOrder());

            int collinearCount = 1;
            Point max = origin;
            double previousSlope = auxPoint[0].slopeTo(origin);
            for (int i = 1; i < auxPoint.length; i++) {
                final double currentSlope = auxPoint[i].slopeTo(origin);
                if (currentSlope == previousSlope) {
                    collinearCount++;
                    if (auxPoint[i].compareTo(max) > 0) {
                        max = auxPoint[i];
                    }
                } else {
                    if (collinearCount >= 3 && origin.compareTo(auxPoint[i - collinearCount]) < 0) {
                        segmentsList.add(new LineSegment(origin, max));
                    }
                    collinearCount = 1;
                    max = origin;
                }
                previousSlope = currentSlope;
            }

            if (collinearCount >= 3 && origin.compareTo(auxPoint[auxPoint.length - collinearCount]) < 0) {
                segmentsList.add(new LineSegment(origin, max));
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
        final FastCollinearPoints collinear = new FastCollinearPoints(points);
        StdOut.println("segments = " + collinear.numberOfSegments());
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

    private static Point[] preparePoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("points cannot be null");
        }

        for (Point point : points) {
            if (point == null) {
                throw new IllegalArgumentException("One of the points is null");
            }
        }

        Point[] prepared = Arrays.copyOf(points, points.length);
        Arrays.sort(prepared);

        // Check for duplicate points after sorting
        for (int i = 0, bound = points.length - 1; i < bound; i++) {
            if (prepared[i].compareTo(prepared[i + 1]) == 0) {
                throw new IllegalArgumentException("Duplicate points found");
            }
        }

        return prepared;
    }

    public int numberOfSegments() {
        return segmentsList.size();
    }

    public LineSegment[] segments() {
        return segmentsList.toArray(new LineSegment[0]);
    }
}
