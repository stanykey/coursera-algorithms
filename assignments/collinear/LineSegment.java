public class LineSegment {
    private final Point start;
    private final Point destination;

    public LineSegment(Point start, Point destination) {
        if (start == null || destination == null) {
            throw new IllegalArgumentException("argument to LineSegment constructor is null");
        }
        if (start.equals(destination)) {
            throw new IllegalArgumentException("both arguments to LineSegment constructor are the same point: " + start);
        }
        this.start = start;
        this.destination = destination;
    }


    public String toString() {
        return start + " -> " + destination;
    }

    public void draw() {
        start.drawTo(destination);
    }

    public int hashCode() {
        throw new UnsupportedOperationException("hashCode() is not supported");
    }
}
