package game.animation;

import sprite.parts.SpriteCollection;
import biuoop.DrawSurface;
import biuoop.Sleeper;

import java.awt.Color;

/**
 * The type Countdown animation.
 */
// The CountdownAnimation will display the given gameScreen,
// for numOfSeconds seconds, and on top of them it will show
// a countdown from countFrom back to 1, where each number will
// appear on the screen for (numOfSeconds / countFrom) secods, before
// it is replaced with the next one.
public class CountdownAnimation implements Animation {
    private double seconds;
    private int from;
    private SpriteCollection screen;
    private boolean stop;
    private int passed;

    /**
     * Instantiates a new Countdown animation.
     *
     * @param numOfSeconds the num of seconds
     * @param countFrom    the count from
     * @param gameScreen   the game screen
     */
    public CountdownAnimation(double numOfSeconds,
                              int countFrom,
                              SpriteCollection gameScreen) {
        this.seconds = numOfSeconds;
        this.from = countFrom;
        this.screen = gameScreen;
        this.stop = false;
        this.passed = 0;
    }

    /**
     * does one frame of the counting.
     *
     * @param d the surface to draw on
     * @param dt the speed
     */
    public void doOneFrame(DrawSurface d, double dt) {
        if (this.passed == this.from) {
            this.stop = true;
        }
        //draws the screen
        Sleeper sleep = new Sleeper();
        this.screen.drawAllOn(d);

        //hold the count for second
        d.setColor(Color.WHITE);
        //draw the number of seconds left
        d.drawText(d.getWidth() / 2, d.getHeight() / 2 + 100, Integer.toString(this.from - this.passed), 100);
        sleep.sleepFor((long) ((this.seconds / this.from) * 1000));
        this.passed++;

    }

    /**
     * stops the animation.
     *
     * @return true if needs to stop
     */
    public boolean shouldStop() {
        return this.stop;
    }
}
