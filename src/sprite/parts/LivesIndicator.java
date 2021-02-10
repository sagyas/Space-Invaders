package sprite.parts;

import game.GameLevel;
import hit.Counter;
import biuoop.DrawSurface;

import java.awt.Color;

/**
 * The type Lives indicator.
 */
public class LivesIndicator implements Sprite {
    private Counter lives;

    /**
     * Instantiates a new Lives indicator.
     */
    public LivesIndicator() {
        this.lives = new Counter();
    }

    /**
     * draw the sprite to the screen.
     *
     * @param d the surface
     */
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.LIGHT_GRAY);
        d.fillRectangle(0, 0, 100, 20);
        d.setColor(Color.BLACK);
        d.drawText(10, 18, "Lives: " + this.lives.getValue(), 20);
    }

    /**
     * notify the sprite that time has passed.
     * @param dt the speed
     */
    @Override
    public void timePassed(double dt) {
    }

    /**
     * Gets counter.
     *
     * @return the counter
     */
    public Counter getCounter() {
        return this.lives;
    }

    /**
     * Add to game.
     *
     * @param g the g
     */
    public void addToGame(GameLevel g) {
        g.addSprite((Sprite) this);
    }
}
