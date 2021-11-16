import java.util.Arrays;
import java.util.Comparator;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;

public class FastCollinearPoints {
    private class PointInfo {
        private class PointInfoComparator implements Comparator<PointInfo> {
            private PointInfo parent;
            public PointInfoComparator(PointInfo info) {
                parent = info;
            }
            public int compare(PointInfo info1, PointInfo info2) {
                return Double.compare(parent.point.slopeTo(info1.point), parent.point.slopeTo(info2.point));
            }
        }
        public Point point;
        public boolean visited;
        public PointInfo(Point pt) {
            point = pt;
            visited = false;
        }
        public Comparator<PointInfo> slopeOrder() {
            return new PointInfoComparator(this);
        }
    }
    private int segmentCount = 0;
    private int startind, endind;
    private PointInfo[] pointInfos = null;
    private LineSegment[] lineSegments = null;
    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        for (Point pt : points) if (pt == null) throw new IllegalArgumentException();

        pointInfos = new PointInfo[points.length];
        for (int i = 0; i < points.length; i++)
            pointInfos[i] = new PointInfo(points[i]);

        for (int curind = 0; curind < points.length; curind++) {
            Arrays.sort(pointInfos, 0, pointInfos.length, new PointInfo(points[curind]).slopeOrder());

            endind = -1;
            startind = -1;
            findLinePoints();
            pointInfos[0].visited = true;
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
    private void findLinePoints() {
        for (int i = 1; i < pointInfos.length; i++)
        {
            if (pointInfos[0].point.slopeTo(pointInfos[i].point) == Double.NEGATIVE_INFINITY) throw new IllegalArgumentException();
            if (i == pointInfos.length - 1) continue;

            if (pointInfos[0].point.slopeTo(pointInfos[i + 1].point) == Double.NEGATIVE_INFINITY) throw new IllegalArgumentException();
            if (pointInfos[0].point.slopeTo(pointInfos[i].point) == pointInfos[0].point.slopeTo(pointInfos[i + 1].point))
            {
                if (startind == -1)
                    startind = i;
                endind = i + 2; // go beyond last point
            }
            else
            {
                if (startind != -1) {
                    if (endind - startind > 2) {
                        boolean visited = false;
                        Point pt1 = pointInfos[0].point, pt2 = pointInfos[0].point;
                        for (int j = startind; j < endind; j++) {
                            if (pointInfos[j].visited) {
                                visited = true;
                                break;
                            }
                            if (pointInfos[j].point.compareTo(pt1) < 0) pt1 = pointInfos[j].point;
                            if (pointInfos[j].point.compareTo(pt2) > 0) pt2 = pointInfos[j].point;
                        }
                        if (!visited)
                            addSegment(pt1, pt2);
                    }
                    endind = -1;
                    startind = -1;
                }
            }
        }
        if (startind != -1 && endind - startind > 2) {
            boolean visited = false;
            Point pt1 = pointInfos[0].point, pt2 = pointInfos[0].point;
            for (int i = startind; i < endind; i++) {
                if (pointInfos[i].visited) {
                    visited = true;
                    break;
                }
                if (pointInfos[i].point.compareTo(pt1) < 0) pt1 = pointInfos[i].point;
                if (pointInfos[i].point.compareTo(pt2) > 0) pt2 = pointInfos[i].point;
            }
            if (!visited)
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
        if (lineSegments == null) {
            lineSegments = new LineSegment[1];
        }
        if (segmentCount == lineSegments.length - 1) {
            resize(lineSegments.length * 2);
        }
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
