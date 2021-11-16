import java.util.Arrays;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;

public class FastCollinearPoints {
    private int segmentCount = 0;
    private int startind, endind, curind;
    private Point[] originalPoints = null;
    private LineSegment[] lineSegments = null;
    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        for (Point pt : points) if (pt == null) throw new IllegalArgumentException();

        originalPoints = new Point[points.length];
        for (int i = 0; i < points.length; i++)
            originalPoints[i] = points[i];

        for (curind = 0; curind < points.length; curind++) {
            Arrays.sort(points, 0, points.length, originalPoints[curind].slopeOrder());

            endind = -1;
            startind = -1;
            findLinePoints(points, curind);
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
    private void findLinePoints(Point[] points, int curind) {
        for (int i = 1; i < points.length - 1; i++)
        {
            if (points[0].slopeTo(points[i]) == Double.NEGATIVE_INFINITY) throw new IllegalArgumentException();
            if (points[0].slopeTo(points[i + 1]) == Double.NEGATIVE_INFINITY) throw new IllegalArgumentException();
            if (points[0].slopeTo(points[i]) == points[0].slopeTo(points[i + 1]))
            {
                if (startind == -1)
                    startind = i;
                endind = i + 2; // go beyond last point
            }
            else
            {
                if (startind != -1) {
                    if (endind - startind > 2) {
                        Point pt1 = points[0], pt2 = points[0];
                        for (int j = startind; j < endind; j++) {
                            if (points[j].compareTo(pt1) < 0) pt1 = points[j];
                            if (points[j].compareTo(pt2) > 0) pt2 = points[j];
                        }
                        addSegment(pt1, pt2);
                    }
                    endind = -1;
                    startind = -1;
                }
            }
        }
        if (startind != -1 && endind - startind > 2) {
            Point pt1 = points[0], pt2 = points[0];
            for (int i = startind; i < endind; i++) {
                if (points[i].compareTo(pt1) < 0) pt1 = points[i];
                if (points[i].compareTo(pt2) > 0) pt2 = points[i];
            }
            addSegment(pt1, pt2);
        }
    }
    private void resize(int newsize) {
        LineSegment[] newLineSegments = new LineSegment[newsize];
        for (int i = 0; i < segmentCount; i++)
            newLineSegments[i] = lineSegments[i];
        lineSegments = newLineSegments;
    }
    private void addSegment(Point pt1, Point pt2) {
        LineSegment newLineSegment = new LineSegment(pt1, pt2);

        boolean findSlope = false;
        for (LineSegment seg : lineSegments) {
            if (seg.toString() == newLineSegment.toString()) {
                findSlope = true;
                break;
            }
        }
        if (findSlope) {
            return;
        }

        if (lineSegments == null) {
            lineSegments = new LineSegment[1];
        }
        if (segmentCount == lineSegments.length - 1) {
            resize(lineSegments.length * 2);
        }
        lineSegments[segmentCount++] = newLineSegment;
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
        StdDraw.setPenRadius(0.005);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        StdDraw.setPenRadius(0.001);
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
