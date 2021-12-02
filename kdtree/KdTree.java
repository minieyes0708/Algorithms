import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    private class Node {
        Point2D pt;
        Node left, right;
        public Node(double x, double y) {
            left = null;
            right = null;
            pt = new Point2D(x, y);
        }
    }
    private int N;
    private Node root;
    private static final double ptRadius = 0.02;
    private static final double lineRadius = 0.005;
    public KdTree() {
        N = 0;
        root = null;
    }
    public boolean isEmpty() {
        return N == 0;
    }
    public int size() {
        return N;
    }
    private Node insert_x(Node curnode, Point2D p) {
        if (curnode == null) return new Node(p.x(), p.y());
        if (p.x() < curnode.pt.x()) curnode.left = insert_y(curnode.left, p);
        else curnode.right = insert_y(curnode.right, p);
        return curnode;
    }
    private Node insert_y(Node curnode, Point2D p) {
        if (curnode == null) return new Node(p.x(), p.y());
        if (p.y() < curnode.pt.y()) curnode.left = insert_x(curnode.left, p);
        else curnode.right = insert_x(curnode.right, p);
        return curnode;
    }
    public void insert(Point2D p) {
        N++;
        root = insert_x(root, p);
    }
    private boolean contains_x(Node curnode, Point2D p) {
        if (curnode == null) return false;
        if (p.x() < curnode.pt.x()) return contains_y(curnode.left, p);
        else return contains_y(curnode.right, p);
    }
    private boolean contains_y(Node curnode, Point2D p) {
        if (curnode == null) return false;
        if (p.y() < curnode.pt.y()) return contains_x(curnode.left, p);
        else return contains_x(curnode.right, p);
    }
    public boolean contains(Point2D p) {
        return contains_x(root, p);
    }
    private void draw_x(Node curnode) {
        if (curnode == null) return;

        StdDraw.setPenRadius(ptRadius);
        StdDraw.setPenColor(StdDraw.BLACK);
        curnode.pt.draw();
        StdDraw.setPenRadius(lineRadius);
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.line(curnode.pt.x(), 0, curnode.pt.x(), 1);

        draw_y(curnode.left);
        draw_y(curnode.right);
    }
    private void draw_y(Node curnode) {
        if (curnode == null) return;

        StdDraw.setPenRadius(ptRadius);
        StdDraw.setPenColor(StdDraw.BLACK);
        curnode.pt.draw();
        StdDraw.setPenRadius(lineRadius);
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.line(0, curnode.pt.y(), 1, curnode.pt.y());

        draw_x(curnode.left);
        draw_x(curnode.right);
    }
    public void draw() {
        draw_x(root);
    }
    private void range_x(Node curnode, RectHV rect, Bag<Point2D> bag) {
        if (curnode == null) return;
        if (rect.contains(curnode.pt)) bag.add(curnode.pt);

        RectHV left = new RectHV(0, 0, curnode.pt.x(), 1);
        RectHV right = new RectHV(curnode.pt.x(), 0, 1, 1);
        if (rect.intersects(left)) range_y(curnode.left, rect, bag);
        if (rect.intersects(right)) range_y(curnode.right, rect, bag);
    }
    private void range_y(Node curnode, RectHV rect, Bag<Point2D> bag) {
        if (curnode == null) return;
        if (rect.contains(curnode.pt)) bag.add(curnode.pt);

        RectHV top = new RectHV(0, curnode.pt.y(), 1, 1);
        RectHV bottom = new RectHV(0, 0, 1, curnode.pt.y());
        if (rect.intersects(top)) range_y(curnode.right, rect, bag);
        if (rect.intersects(bottom)) range_y(curnode.left, rect, bag);
    }
    public Iterable<Point2D> range(RectHV rect) {
        Bag<Point2D> bag = new Bag<Point2D>();
        range_x(root, rect, bag);
        return bag;
    }
    private Point2D nearest_x(Node curnode, Point2D p, Point2D best) {
        if (curnode == null) return best;

        if (best != null &&
                curnode.pt.distanceSquaredTo(p) < best.distanceSquaredTo(p))
            best = curnode.pt;

        RectHV left = new RectHV(0, 0, curnode.pt.x(), 1);
        RectHV right = new RectHV(curnode.pt.x(), 0, 1, 1);

        if (p.x() < curnode.pt.x()) {
            best = nearest_y(curnode.left, p, best);
            if (right.distanceSquaredTo(p) < best.distanceSquaredTo(p)) {
                best = nearest_y(curnode.right, p, best);
            }
        }
        else {
            best = nearest_y(curnode.right, p, best);
            if (left.distanceSquaredTo(p) < best.distanceSquaredTo(p)) {
                best = nearest_y(curnode.left, p, best);
            }
        }
        return best;
    }
    private Point2D nearest_y(Node curnode, Point2D p, Point2D best) {
        if (curnode == null) return best;

        if (curnode.pt.distanceSquaredTo(p) < best.distanceSquaredTo(p))
            best = curnode.pt;

        RectHV top = new RectHV(0, curnode.pt.y(), 1, 1);
        RectHV bottom = new RectHV(0, 0, 1, curnode.pt.y());

        if (p.y() < curnode.pt.y()) {
            best = nearest_x(curnode.left, p, best);
            if (top.distanceSquaredTo(p) < best.distanceSquaredTo(p)) {
                best = nearest_x(curnode.right, p, best);
            }
        }
        else {
            best = nearest_x(curnode.right, p, best);
            if (bottom.distanceSquaredTo(p) < best.distanceSquaredTo(p)) {
                best = nearest_x(curnode.left, p, best);
            }
        }
        return best;
    }
    public Point2D nearest(Point2D p) {
        return nearest_x(root, p, root.pt);
    }
    public static void main(String[] args) {
        StdDraw.enableDoubleBuffering();
        KdTree kdtree = new KdTree();
        In in = new In(args[0]);
        while (!in.isEmpty()) {
            Point2D p = new Point2D(in.readDouble(), in.readDouble());
            kdtree.insert(p);
        }
        kdtree.draw();

        Point2D pt = new Point2D(0.8, 0.8);
        StdDraw.setPenRadius(ptRadius *2);
        StdDraw.setPenColor(StdDraw.ORANGE);
        pt.draw();
        Point2D pt2 = kdtree.nearest(pt);
        StdDraw.setPenRadius(ptRadius *2);
        StdDraw.setPenColor(StdDraw.YELLOW);
        pt2.draw();

        // RectHV rect = new RectHV(0.2, 0.2, 0.8, 0.8);
        // StdDraw.setPenRadius(ptRadius);
        // StdDraw.setPenColor(StdDraw.YELLOW);
        // for (Point2D pt : kdtree.range(rect)) {
            // pt.draw();
        // }
        // rect.draw();

        StdDraw.show();
    }
}
