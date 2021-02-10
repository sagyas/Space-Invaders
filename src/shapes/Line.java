package shapes;

import java.util.List;
import java.util.ArrayList;
/**
 * create line.
 */
public class Line {
    private Point start;
    private Point end;
    /**
     * first constructor.
     * @param start the starting point
     * @param end the ending point
     * @throws Exception for points that don't create line
     */
    public Line(Point start, Point end) throws Exception {
        // calls the second constructor
        this(start.getX(), start.getY(), end.getX(), end.getY());
    }
    /**
     * second constructor.
     * @param x1 x of starting point
     * @param y1 y of starting point
     * @param x2 x of ending point
     * @param y2 y of ending point
     */
    public Line(double x1, double y1, double x2, double y2) {
        // define new points from the parameters and build line object out of them
        Point start1 = new Point(x1, y1);
        this.start = start1;
        Point end1 = new Point(x2, y2);
        this.end = end1;
    }
    /**
     * Return the length of the line.
     * @return the length
     */
    public double length() {
        // use distance method to do so
        return this.start.distance(end);
    }
    /**
     * Returns the middle point of the line.
     * @return the middle point
     */
    public Point middle() {
        // calculate averages to know the middle
        double x = (this.end.getX() + this.start.getX()) / 2;
        double y = (this.end.getY() + this.start.getY()) / 2;
        Point middle = new Point(x, y);
        return middle;
    }
    /**
     * Returns the start point of the line.
     * @return starting point
     */
    public Point start() {
        Point start1 = new Point(this.start.getX(), this.start.getY());
        return start1;
    }
    /**
     * Returns the end point of the line.
     * @return the ending point
     */
    public Point end() {
        Point end1 = new Point(this.end.getX(), this.end.getY());
        return end1;
    }
    /**
     * Returns true if the lines intersect, false otherwise.
     * @param other the other line to check with
     * @return true if intersection point isn't null, else false
     */
    public boolean isIntersecting(Line other) {
        if (this.intersectionWith(other) == null) {
            return false;
        }
        return true;
    }
    /**
     * Returns the intersection point if the lines intersect, and null otherwise.
     * @param other the other line to check with
     * @return the intersection point
     */
    public Point intersectionWith(Line other) {
        // false if there's infinite number of intersections
        if (this.equals(other)) {
            return null;
        }
        // define the delta of x and y of the line
        double deltaY1 = this.end.getY() - this.start.getY();
        double deltaX1 = this.end.getX() - this.start.getX();
        // define the delta of x and y of the line
        double deltaY2 = other.end.getY() - other.start.getY();
        double deltaX2 = other.end.getX() - other.start.getX();
        //same slope will never intersect
        if (deltaX1 == deltaX2 && deltaY1 == deltaY2) {
            return null;
        }
        //infinite slope no intersection
        if (deltaY1 == 0 && deltaY2 == 0) {
            return null;
        }
        // define the slope of the line
        double slope1 = deltaY1 / deltaX1;
        // define the slope of the line
        double slope2 = deltaY2 / deltaX2;
        double interX;
        double interY;
        Point inter = null;
        /*
         * if one is horizontal the intersection x is that line's x
         * if the other line is not vertical calculate his y, else its
         * the y of the vertical line
         */
        if (deltaX1 == 0 && deltaX2 != 0) {
            interX = this.start.getX();
            if (deltaY2 == 0 && deltaY1 != 0) {
                interY = other.start().getY();
            } else {
                interY = slope2 * (interX - other.start.getX()) + other.start.getY();
            }
            inter = new Point(interX, interY);
            /*
             * same as before
             */
        } else if (deltaX2 == 0 && deltaX1 != 0) {
            interX = other.start.getX();
            if (deltaY1 == 0 && deltaY2 != 0) {
                interY = this.start.getY();
            } else {
                interY = slope1 * (interX - this.start.getX()) + this.start.getY();
            }
            inter = new Point(interX, interY);
            /*
             * if none is horizontal or vertical calculate as usual
             */
        } else {
            // use the formula to know X intersection
            interX = (slope1 * this.start.getX() - slope2 * other.start.getX() + other.start.getY() - this.start.getY())
                    / (slope1 - slope2);
            // use X in one of the line formulas to know Y
            interY = slope1 * (interX - this.start.getX()) + this.start.getY();
        }
        inter = new Point(interX, interY);
        //check if its on the line and return
        if (this.isBetween(inter)) {
            return inter;
        }
        return null;
    }
    /**
     * equals -- return true is the lines are equal, false otherwise.
     * @param other the other line to check with
     * @return true if the points are equal, false otherwise
     */
    public boolean equals(Line other) {
        // equals
        if (((this.start.equals(other.start)) && (this.end.equals(other.end)))
                || ((this.end.equals(other.start)) && (this.start.equals(other.end)))) {
            return true;
        }
        return false;
    }
    /**
     * return true if the point is on one of the lines, false otherwise.
     * @param p2 the point to check if on the lines
     * @param l2 the second line to check with
     * @return true\false
     */
    public boolean between(Point p2, Line l2) {
        boolean x = false;
        boolean y = false;
        // check if the intersected x is on a the segment
        if ((((p2.getX() >= this.start.getX()) && (p2.getX() <= this.end.getX()))
                || ((p2.getX() <= this.start.getX()) && (p2.getX() >= this.end.getX())))
                && (((p2.getX() >= l2.start.getX()) && (p2.getX() <= l2.end.getX()))
                        || ((p2.getX() <= l2.start.getX()) && (p2.getX() >= l2.end.getX())))) {
            x = true;
        }
        // check if the intersected y is on a the segment
        if ((((p2.getY() >= this.start.getY()) && (p2.getY() <= this.end.getY()))
                || ((p2.getY() <= this.start.getY()) && (p2.getY() >= this.end.getY())))
                && (((p2.getX() >= l2.start.getX()) && (p2.getX() <= l2.end.getX()))
                        || ((p2.getX() <= l2.start.getX()) && (p2.getX() >= l2.end.getX())))) {
            y = true;
        }
        return x && y;
    }
    /**
     * check if point is on this line.
     * @param p1 the point to check
     * @return true if point is between line, false otherwise
     */
    public boolean isBetween(Point p1) {
        boolean x = false;
        boolean y = false;
        /*
         * true if x is on this line
         */
        if ((p1.getX() >= this.start.getX() && p1.getX() <= this.end.getX())
                || (p1.getX() <= this.end.getX() && p1.getX() >= this.start.getX())) {
            x = true;
        }
        /*
         * true if y is on this line
         */
        if ((p1.getY() >= this.start.getY() && p1.getY() <= this.end.getY())
                || (p1.getY() <= this.end.getY() && p1.getY() >= this.start.getY())) {
            y = true;
        }
        return x && y;
    }
    /**
     * If this line does not intersect with the rectangle, return null.
     * Otherwise, return the closest intersection point to the
     * start of the line.
     * @param rect shapes.Rectangle
     * @return the intersection point
     */

    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        Line curLine = null;
        try {
            curLine = new Line(this.start, this.end);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (rect.intersectionPoints(curLine).isEmpty()) {
            return null;
        }
        List l1 = new ArrayList();
        // lists of intersection points
        l1 = rect.intersectionPoints(curLine);
        /*
         * bubble sort of the points
         */
        for (int j = 0; j < l1.size() - 1; j++) {
            for (int i = 0; i < l1.size() - j - 1; i++) {
                Point p1 = (Point) l1.get(i);
                Point p2 = (Point) l1.get(i + 1);
                if (this.start.compareTo(p1, p2) == -1) {
                    l1.set(i + 1, p1);
                    l1.set(i, p2);
                }
            }
        }
        //return closest point
        return (Point) l1.get(0);
    }
    /**
     * calculate slope of line.
     * @return slope
     */
    public double getSlope() {
        return (this.end.getY() - this.start.getY()) / (this.end.getX() - this.start.getX());
    }
    /**
     * divide line to 5 parts for paddle.
     * @return list of the departed lines
     */
    public java.util.List divideTo5() {
        List l1 = new ArrayList();
        Point p1 = this.start();
        double size = (this.end.getX() - this.start.getX()) / 5;
        for (int i = 0; i < 5; i++) {
            Point p2 = new Point(p1.getX() + size, this.start.getY());
            try {
                Line line = new Line(p1, p2);
                l1.add((Line) line);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            p1 = p2;
        }
        //return list of lines
        return l1;
    }
}