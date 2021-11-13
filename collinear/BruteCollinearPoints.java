import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdDraw;

public class BruteCollinearPoints {
    private int segmentCount = 0;
    private LineSegment[] lineSegments = null;
    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        for (int pind = 0; pind < points.length - 3; pind++) {
            Point p = points[pind];
            for (int qind = pind + 1; qind < points.length - 2; qind++) {
                Point q = points[qind];
                for (int rind = qind + 1; rind < points.length - 1; rind++) {
                    Point r = points[rind];
                    for (int sind = rind + 1; sind < points.length; sind++) {
                        Point s = points[sind];
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
        StdDraw.setScale(0, 32767);
        StdDraw.setPenRadius(0.01);
        int count = StdIn.readInt();
        Point[] points = new Point[count];
        for (int i = 0; i < count; i++) {
            points[i] = new Point(StdIn.readInt(), StdIn.readInt());
            points[i].draw();
        }
        StdDraw.setPenRadius(0.005);
        StdDraw.setPenColor(StdDraw.BLUE);
        BruteCollinearPoints bc = new BruteCollinearPoints(points);
        LineSegment[] lineSegments = bc.segments();
        for (int i = 0; i < lineSegments.length; i++) {
            lineSegments[i].draw();
        }
    }
}
