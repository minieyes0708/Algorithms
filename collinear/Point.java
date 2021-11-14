/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import java.util.Comparator;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdDraw;

class SlopeOrderComparator implements Comparator<Point> {
    private final Point parent;
    public SlopeOrderComparator(Point p) {
        parent = p;
    }
    public int compare(Point p1, Point p2) {
        double diff = parent.slopeTo(p1) - parent.slopeTo(p2);
        if (diff == 0) return 0;
        return diff < 0 ? -1 : 1;
    }
}

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param  x the <em>x</em>-coordinate of the point
     * @param  y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param  that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        if (x == that.x) {
            if (y == that.y) return Double.NEGATIVE_INFINITY;
            else return Double.POSITIVE_INFINITY;
        }
        else if (y == that.y) {
            return 0.0;
        }
        return ((double) y - that.y) / (x - that.x);
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param  that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     *         point (x0 = x1 and y0 = y1);
     *         a negative integer if this point is less than the argument
     *         point; and a positive integer if this point is greater than the
     *         argument point
     */
    public int compareTo(Point that) {
        if (y == that.y) {
            return x - that.x;
        }
        return y - that.y;
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        return new SlopeOrderComparator(this);
    }


    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        // Point[] points = new Point[9];
        // points[0] = new Point(3, 2);
        // points[1] = new Point(3, 3);
        // points[2] = new Point(2, 3);
        // points[3] = new Point(1, 3);
        // points[4] = new Point(1, 2);
        // points[5] = new Point(1, 1);
        // points[6] = new Point(2, 1);
        // points[7] = new Point(3, 1);
        // points[8] = new Point(2, 2);

        // Point pt1 = new Point(2, 2);
        // Comparator<Point> comp = pt1.slopeOrder();

        // for (Point pt2 : points) {
            // System.out.println(pt1 + " slope to " + pt2 + " = " + pt1.slopeTo(pt2));
        // }
        // for (Point pt2 : points) {
            // System.out.println(pt1 + " compare to " + pt2 + " = " + pt1.compareTo(pt2));
        // }
        // for (Point pt2 : points) {
            // for (Point pt3 : points) {
                // System.out.println(pt1 + " slopeOrder to " + pt2 + ", " + pt3 + " = " + comp.compare(pt2, pt3));
            // }
        // }
        StdDraw.setScale(0, 32767);
        StdDraw.setPenRadius(0.01);
        int count = StdIn.readInt();
        Point[] points = new Point[count];
        for (int i = 0; i < count; i++) {
            points[i] = new Point(StdIn.readInt(), StdIn.readInt());
            points[i].draw();
        }
        StdDraw.setPenRadius(0.001);
        StdDraw.setPenColor(StdDraw.BLUE);
        for (int i = 1; i < count; i++) {
            (new LineSegment(points[0], points[i])).draw();
            StdDraw.text(points[i].x, points[i].y, String.format("%f", points[0].slopeTo(points[i])));
        }
    }
}
