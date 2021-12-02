import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Point2D;

public class PointSET {
    private final SET<Point2D> set;
    public PointSET() {
        set = new SET<Point2D>();
    }
    public boolean isEmpty() {
        return set.isEmpty();
    }
    public int size() {
        return set.size();
    }
    public void insert(Point2D p) {
        set.add(p);
    }
    public boolean contains(Point2D p) {
        return set.contains(p);
    }
    public void draw() {
        for (Point2D pt : set)
            pt.draw();
    }
    public Iterable<Point2D> range(RectHV rect) {
        Queue<Point2D> result = new Queue<Point2D>();
        for (Point2D pt : set) {
            if (rect.contains(pt)) {
                result.enqueue(pt);
            }
        }
        return result;
    }
    public Point2D nearest(Point2D p) {
        Point2D minpt = null;
        double mindist = 0.0;
        for (Point2D pt : set) {
            double dist = pt.distanceSquaredTo(p);
            if (minpt == null || dist < mindist) {
                minpt = pt;
                mindist = dist;
            }
        }
        return minpt;
    }

    public static void main(String[] args) {
        PointSET pset = new PointSET();
        In in = new In(args[0]);
        while (!in.isEmpty()) {
            Point2D pt = new Point2D(in.readDouble(), in.readDouble());
            pset.insert(pt);
        }
        pset.draw();
    }
}
