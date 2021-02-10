package collision.parts;

import shapes.Point;

/**
 * keeps collision info.
 */
public class CollisionInfo {
    private Point collPoint;
    private Collidable collObj;

    /**
     * constructor.
     *
     * @param point the collision point
     * @param coll  the collision object
     */
    public CollisionInfo(Point point, Collidable coll) {
        this.collObj = coll;
        this.collPoint = point;
    }

    /**
     * the point where collision occured.
     *
     * @return the point
     */
    public Point collisionPoint() {
        return this.collPoint;
    }

    /**
     * the collidable object involved in the collision.
     *
     * @return the object
     */
    public Collidable collisionObject() {
        return this.collObj;
    }
}