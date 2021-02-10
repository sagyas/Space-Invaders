package game.animation;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * The type End.
 */
public class End implements Animation {
    private int score;
    private KeyboardSensor keyboard;
    private boolean stop;
    private boolean win;
    /**
     * The constant LOCATION.
     */
    public static final int LOCATION = 150;

    /**
     * Instantiates a new End.
     *
     * @param k       the keyboard
     * @param num     the score
     * @param victory the victory
     */
    public End(KeyboardSensor k, int num, boolean victory) {
        this.keyboard = k;
        this.score = num;
        this.stop = false;
        this.win = victory;
    }

    /**
     * draw end screen.
     *
     * @param d the surface
     * @param dt the speed
     */
    public void doOneFrame(DrawSurface d, double dt) {
        if (this.win) {
            d.drawText(LOCATION, d.getHeight() / 2, "YOU WIN! your score is: " + this.score, 32);
        } else {
            d.drawText(LOCATION, d.getHeight() / 2, "game Over. your score is: " + this.score, 32);
        }
        d.drawText(LOCATION, d.getHeight() - 32, "press space to continue", 32);
        //wait for space press
        if (this.keyboard.isPressed(KeyboardSensor.SPACE_KEY)) {
            this.stop = true;
        }
    }

    /**
     * when to stop animation.
     *
     * @return boolean false if no
     */
    public boolean shouldStop() {
        return this.stop;
    }
}
