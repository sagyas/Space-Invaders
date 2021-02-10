package shapes;

/**
 * create point.
 */
public class Point implements Comparable {
    private double x;
    private double y;

    /**
     * constructor.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * distance -- return the distance of this point to the other point.
     *
     * @param other the other point to distance from
     * @return the distance
     */
    public double distance(Point other) {
        // distance formula
        return Math.sqrt((this.x - other.x) * (this.x - other.x) + (this.y - other.y) * (this.y - other.y));
    }

    /**
     * equals -- return true is the points are equal, false otherwise.
     *
     * @param other the other point to distance from
     * @return true\false
     */
    public boolean equals(Point other) {
        // check both x and y
        return ((this.x == other.x) && (this.y == other.y));
    }

    /**
     * Return the x value of this point.
     *
     * @return the x value
     */
    public double getX() {
        return this.x;
    }

    /**
     * Return the y value of this point.
     *
     * @return the y value
     */
    public double getY() {
        return this.y;
    }

    /**
     * compare which point is closer to this.
     *
     * @param other1 point 1
     * @param other2 point 2
     * @return 1 if p1 is closer, -1 is p2 is closer, 0 otherwise
     */
    public int compareTo(Object other1, Object other2) {
        if ((other1 instanceof Point) && (other2 instanceof Point)) {
            Point p1 = (Point) other1;
            Point p2 = (Point) other2;
            //return 1 if p1 is closer than p2
            if ((this.distance(p1) < this.distance(p2)) || (p1 == null || p2 == null)) {
                return 1;
                //return -1 if p2 is closer than p1
            } else if (this.distance(p1) > this.distance(p2)) {
                return -1;
            } else {
                return 0;
            }
        } else {
            // some default
            return 0;
        }
    }

    @Override
    public int compareTo(Object arg0) {
        // TODO Auto-generated method stub
        return 0;
    }
}