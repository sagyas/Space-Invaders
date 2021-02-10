package shapes;

import java.util.List;
import java.util.ArrayList;

/**
 * create rectangle object.
 */
public class Rectangle {
    private Point upperLeft;
    private double width;
    private double height;
    public static final int UPPER = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    public static final int LOWER = 4;

    /**
     * Create a new rectangle with location and width/height.
     *
     * @param upperLeft pivot point
     * @param width     width
     * @param height    height
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
    }

    /**
     * Return a (possibly empty) List of intersection points
     * with the specified line.
     *
     * @param line the line
     * @return list of intersection point
     */
    public java.util.List intersectionPoints(Line line) {
        List lines = new ArrayList();
        //gets lines of the rectangle
        lines = this.getRectangleLines();
        List points = new ArrayList();
        /*
         * add to list if the point is intersecting with on of the lines
         */
        for (int i = 0; i < lines.size(); i++) {
            if (((Line) lines.get(i)).intersectionWith(line) != null) {
                Point p = (Point) ((Line) lines.get(i)).intersectionWith(line);
                if (((Line) lines.get(i)).isBetween(p)) {
                    points.add((Point) p);
                } else {
                    points.add((Point) null);
                }
            }
        }
        //return the list
        return points;
    }

    /**
     * Return the width of the rectangle.
     *
     * @return width
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * return the height of the rectangle.
     *
     * @return height
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * Returns the upper-left point of the rectangle.
     *
     * @return point
     */
    public Point getUpperLeft() {
        return this.upperLeft;
    }

    /**
     * set upperleft point.
     *
     * @param p the new point to set
     */
    public void setUpperLeft(Point p) {
        this.upperLeft = p;
    }

    /**
     * gets lines from rectangle.
     *
     * @return list of lines
     */
    public java.util.List getRectangleLines() {
        /*
         * create all the possible lines
         */
        Point upperRight = new Point(this.upperLeft.getX() + width, this.upperLeft.getY());
        Point lowerLeft = new Point(this.upperLeft.getX(), this.upperLeft.getY() + height);
        Point lowerRight = new Point(this.upperLeft.getX() + width, this.upperLeft.getY() + height);
        List lines = new ArrayList();
        /*
         * add them to list
         */
        try {
            lines.add(new Line(this.upperLeft, upperRight));
            lines.add(new Line(this.upperLeft, lowerLeft));
            lines.add(new Line(upperRight, lowerRight));
            lines.add(new Line(lowerLeft, lowerRight));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        //return the list
        return lines;
    }

    /**
     * get upper line.
     *
     * @return line
     */
    public Line getUpper() {
        List l1 = new ArrayList();
        //create list of line
        l1 = this.getRectangleLines();
        //gets the first line
        return (Line) l1.get(UPPER);
    }

    /**
     * get lower line.
     *
     * @return line
     */
    public Line getLower() {
        List l1 = new ArrayList();
        l1 = this.getRectangleLines();
        return (Line) l1.get(LOWER);
    }

    /**
     * get right line.
     *
     * @return line
     */
    public Line getRight() {
        List l1 = new ArrayList();
        l1 = this.getRectangleLines();
        return (Line) l1.get(RIGHT);
    }

    /**
     * get left line.
     *
     * @return line
     */
    public Line getLeft() {
        List l1 = new ArrayList();
        l1 = this.getRectangleLines();
        return (Line) l1.get(LEFT);
    }
}