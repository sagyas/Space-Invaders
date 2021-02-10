package sprite.parts;

import game.GameLevel;
import hit.Counter;
import biuoop.DrawSurface;

import java.awt.Color;

/**
 * The type Score indicator.
 */
public class ScoreIndicator implements Sprite {
    private Counter score;

    /**
     * Instantiates a new Score indicator.
     */
    public ScoreIndicator() {
        this.score = new Counter();
    }

    /**
     * draw the sprite to the screen.
     *
     * @param d the surface
     */
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.LIGHT_GRAY);
        d.fillRectangle(100, 0, 800, 20);
        d.setColor(Color.BLACK);
        d.drawText(350, 18, "Score: " + this.score.getValue(), 20);
    }

    /**
     * notify the sprite that time has passed.
     * @param dt the speed
     */
    @Override
    public void timePassed(double dt) {
    }

    /**
     * Add to game.
     *
     * @param g the g
     */
    public void addToGame(GameLevel g) {
        g.addSprite((Sprite) this);
    }

    /**
     * Get counter counter.
     *
     * @return the counter
     */
    public Counter getCounter() {
        return this.score;
    }
}
