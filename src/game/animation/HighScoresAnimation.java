package game.animation;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import game.HighScoresTable;

import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;

/**
 * high score animation.
 */
public class HighScoresAnimation implements Animation {
    /**
     * The constant LOCATION.
     */
    public static final int LOCATION = 200;
    /**
     * The constant FONT.
     */
    public static final int FONT = 32;
    private HighScoresTable table;
    private KeyboardSensor keyboard;
    private boolean stop;
    private String key;

    /**
     * Instantiates a new High scores animation.
     *
     * @param scores the scores
     * @param keyb   the keyb
     * @param press  the press
     */
    public HighScoresAnimation(HighScoresTable scores, KeyboardSensor keyb, String press) {
        this.table = scores;
        this.stop = false;
        this.keyboard = keyb;
        this.key = press;
    }

    /**
     * do one frame.
     * @param d the d
     * @param dt speed
     */
    public void doOneFrame(DrawSurface d, double dt) {
        d.setColor(Color.LIGHT_GRAY);
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
        d.setColor(Color.WHITE);

        ImageIcon icon = new ImageIcon("./resources/background_images/menu.png");
        Image img = icon.getImage();
        d.drawImage(0, 0, img);

        int startY = d.getHeight() / 2 - LOCATION;
        d.drawText(LOCATION + 70, d.getHeight() / 2 - LOCATION, "Name", FONT);
        d.drawText(LOCATION + 220, d.getHeight() / 2 - LOCATION, "Score", FONT);
        d.drawLine(LOCATION + 70, d.getHeight() / 2 - LOCATION, LOCATION + 300, d.getHeight() / 2 - LOCATION);
        d.drawLine(LOCATION + 190, d.getHeight() / 2 - LOCATION - FONT, LOCATION + 190, d.getHeight() / 2 + 50);
        d.setColor(Color.WHITE);
        startY += FONT;
        for (int i = 0; i < this.table.size(); ++i) {
            d.drawText(LOCATION + 70, startY, this.table.getHighScores().get(i).getName(), FONT);
            d.drawText(LOCATION + 220, startY, Integer.toString(this.table.getHighScores().get(i).getScore()), FONT);
            startY += FONT;
        }

        d.setColor(Color.WHITE);
        d.drawText(199, 500, "Press SPACE to continue", FONT);
        if (this.keyboard.isPressed(key)) {
            this.stop = true;
        }
    }

    /**
     * stops animation.
     * @return stop
     */
    public boolean shouldStop() {
        return this.stop;
    }

}
