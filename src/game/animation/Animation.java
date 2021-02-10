package game.animation;

import biuoop.DrawSurface;

/**
 * The interface animation.
 */
public interface Animation {
    /**
     * Do one frame.
     *
     * @param d the d
     * @param dt speed
     */
    void doOneFrame(DrawSurface d, double dt);

    /**
     * Should stop boolean.
     *
     * @return the boolean
     */
    boolean shouldStop();
}
