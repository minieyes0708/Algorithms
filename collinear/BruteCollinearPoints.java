import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;

public class BruteCollinearPoints {
    private int segmentCount = 0;
    private LineSegment[] lineSegments = null;
    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        for (Point pt : points) if (pt == null) throw new IllegalArgumentException();

        for (int pind = 0; pind < points.length - 3; pind++) {
            Point p = points[pind];
            for (int qind = pind + 1; qind < points.length - 2; qind++) {
                Point q = points[qind];
                if (p.slopeTo(q) == Double.NEGATIVE_INFINITY) throw new IllegalArgumentException();
                for (int rind = qind + 1; rind < points.length - 1; rind++) {
                    Point r = points[rind];
                    if (p.slopeTo(r) == Double.NEGATIVE_INFINITY) throw new IllegalArgumentException();
                    if (q.slopeTo(r) == Double.NEGATIVE_INFINITY) throw new IllegalArgumentException();
                    for (int sind = rind + 1; sind < points.length; sind++) {
                        Point s = points[sind];
                        if (p.slopeTo(s) == Double.NEGATIVE_INFINITY) throw new IllegalArgumentException();
                        if (q.slopeTo(s) == Double.NEGATIVE_INFINITY) throw new IllegalArgumentException();
                        if (r.slopeTo(s) == Double.NEGATIVE_INFINITY) throw new IllegalArgumentException();
                        if (p.slopeTo(q) == q.slopeTo(r) && q.slopeTo(r) == r.slopeTo(s)) {
                            Point pt1 = p, pt2 = p;
                            for (Point pt : new Point[] {p, q, r, s}) {
                                if (pt.compareTo(pt1) < 0) pt1 = pt;
                                if (pt.compareTo(pt2) > 0) pt2 = pt;
                            }
                            addSegment(pt1, pt2);
                        }
                    }
                }
            }
        }
    }
    public int numberOfSegments() {
        return segmentCount;
    }
    public LineSegment[] segments() {
        LineSegment[] result = new LineSegment[segmentCount];
        for (int i = 0; i < segmentCount; i++)
            result[i] = lineSegments[i];
        return result;
    }
    private void resize(int newsize) {
        LineSegment[] newLineSegments = new LineSegment[newsize];
        for (int i = 0; i < segmentCount; i++)
            newLineSegments[i] = lineSegments[i];
        lineSegments = newLineSegments;
    }
    private void addSegment(Point pt1, Point pt2) {
        if (lineSegments == null)
            lineSegments = new LineSegment[1];
        if (segmentCount == lineSegments.length - 1)
            resize(lineSegments.length * 2);
        lineSegments[segmentCount++] = new LineSegment(pt1, pt2);
    }
    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
