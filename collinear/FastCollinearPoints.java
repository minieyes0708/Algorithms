import java.util.Arrays;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;

public class FastCollinearPoints {
    private int startind, endind;
    private int segmentCount = 0;
    private LineSegment[] lineSegments = null;
    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        for (Point pt : points) if (pt == null) throw new IllegalArgumentException();

        int curind = 0;
        while (curind < points.length) {
            Arrays.sort(points, curind + 1, points.length, points[curind].slopeOrder());
            findLinePoints(points, curind);
            curind = moveLinePoints(points, curind);
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
        startind = endind = -1;
        for (int i = curind + 1; i < points.length - 1; i++)
        {
            if (points[curind].slopeTo(points[i]) == Double.NEGATIVE_INFINITY) throw new IllegalArgumentException();
            if (points[curind].slopeTo(points[i + 1]) == Double.NEGATIVE_INFINITY) throw new IllegalArgumentException();
            if (points[curind].slopeTo(points[i]) == points[curind].slopeTo(points[i + 1]))
            {
                if (startind == -1)
                    startind = i;
                endind = i + 2; // go beyond last point
            }
        }
        if (startind != -1) {
            Point pt1 = points[curind], pt2 = points[curind];
            for (int i = startind; i < endind; i++) {
                if (points[i].compareTo(pt1) < 0) pt1 = points[i];
                if (points[i].compareTo(pt2) > 0) pt2 = points[i];
            }
            addSegment(pt1, pt2);
        }
    }
    private int moveLinePoints(Point[] points, int curind) {
        if (startind != -1) {
            for (int i = 0; i < endind - startind; i++) {
                swap(points, curind + i + 1, i + startind);
            }
            return endind;
        }
        else {
            return curind + 1;
        }
    }
    private void swap(Point[] points, int ind1, int ind2) {
        Point tmp = points[ind1];
        points[ind1] = points[ind2];
        points[ind2] = tmp;
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
        int ind = 0;
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
