package sprite.parts;

import biuoop.DrawSurface;

/**
 * sprite interface.
 */
public interface Sprite {
    /**
     * draw the sprite to the screen.
     *
     * @param d the surface
     */
    void drawOn(DrawSurface d);

    /**
     * notify the sprite that time has passed.
     *
     * @param dt the dt
     */
    void timePassed(double dt);
}