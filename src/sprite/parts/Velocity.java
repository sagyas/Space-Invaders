package sprite.parts;

import shapes.Point;

/**
 * Velocity specifies the change in position on the `x` and the `y` axes.
 */
public class Velocity {
    private double dx;
    private double dy;

    /**
     * sets default velocity.
     */
    public Velocity() {
        this(0, 0);
    }

    /**
     * sets velocity.
     *
     * @param dx - change on x axis
     * @param dy - change on y axis
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * creates velocity out of angle and speed.
     *
     * @param angle - the angle of the direction
     * @param speed - the speed of ball
     * @return Velocity velocity
     */
    public Velocity fromAngleAndSpeed(double angle, double speed) {
        // calculates using cos and sin functions and converts to radians
        double dx1 = Math.cos(Math.toRadians(angle - 90)) * speed;
        double dy1 = Math.sin(Math.toRadians(angle - 90)) * speed;
        return new Velocity(dx1, dy1);
    }

    /**
     * Take a point with position (x,y) and return a new point with position (x+dx,
     * y+dy).
     *
     * @param p  - the point to be changed
     * @param dt the dt
     * @return shapes.Point point
     */
    public Point applyToPoint(Point p, double dt) {
        // calculates new location after velocity
        Point newpoint = new Point(p.getX() + this.dx * dt, p.getY() + this.dy * dt);
        return newpoint;
    }

    /**
     * gets speed.
     *
     * @return the speed
     */
    public double getSpeed() {
        return Math.sqrt((this.dy * this.dy) + (this.dx * this.dx));
    }

    /**
     * return the change on x axis.
     *
     * @return double dx
     */
    public double getDX() {
        return this.dx;
    }

    /**
     * return the change on the y axis.
     *
     * @return double dy
     */
    public double getDY() {
        return this.dy;
    }
}