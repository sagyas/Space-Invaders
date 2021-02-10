package collision.parts;

import shapes.Point;
import shapes.Rectangle;
import sprite.parts.Ball;
import sprite.parts.Velocity;

/**
 * things that collidable.
 */
public interface Collidable {
    /**
     * Return the "collision shape" of the object.
     *
     * @return rectangle
     */
    Rectangle getCollisionRectangle();

    /**
     * Notify the object that we collided with it at collisionPoint with
     * a given velocity.
     * The return is the new velocity expected after the hit (based on
     * the force the object inflicted on us).
     *
     * @param collisionPoint  the collision point
     * @param hitter          the ball
     * @param currentVelocity the current velocity
     * @return new velocity
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}