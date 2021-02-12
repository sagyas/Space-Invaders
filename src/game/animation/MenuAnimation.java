package game.animation;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import game.Menu;
import game.Selection;

import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

/**
 * The type Menu animation.
 *
 * @param <T> the type parameter
 */
public class MenuAnimation<T> implements Menu<T> {
    private boolean stop;
    private String title;
    private List<Selection> list;
    private KeyboardSensor keyboard;
    private T status;
    private GUI gui;
    private Menu sub;

    /**
     * Instantiates a new Menu animation.
     *
     * @param t the t
     * @param k the k
     * @param g the g
     */
    public MenuAnimation(String t, KeyboardSensor k, GUI g) {
        this.title = t;
        this.list = new ArrayList();
        this.keyboard = k;
        this.stop = false;
        this.gui = g;
    }

    /**
     * {@inheritDoc}
     * @param key
     * @param message
     * @param returnVal
     */
    @Override
    public void addSelection(String key, String message, T returnVal) {
        this.list.add(new Selection<>(key, message, returnVal));
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public T getStatus() {
        if (this.status instanceof MenuAnimation) {
            AnimationRunner runner = new AnimationRunner(gui);
            runner.run(this.sub);
            return (T) sub.getStatus();
        }
        return this.status;
    }

    @Override
    public void addSubMenu(String key, String message, Menu<T> subMenu) {
        this.list.add(new Selection<>(key, message, subMenu));
        this.sub = subMenu;
    }


    /**
     * Do one frame.
     * @param dt the speed
     * @param d the d
     */
    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        d.setColor(Color.CYAN);
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
        ImageIcon icon = new ImageIcon("./resources/background_images/menu.png");
        Image img = icon.getImage();
        d.drawImage(0, 0, img);
        int start = 180;
        int x = 50;
        d.setColor(Color.WHITE);
        d.drawText(x, start, this.title, 80);
        start += 130;
        x += 130;
        d.setColor(Color.WHITE);
        for (int i = 0; i < this.list.size(); i++) {
            d.drawText(x, start,
                    "(" + this.list.get(i).getKey() + ")" + " " + this.list.get(i).getMessage(), 55);
            start += 80;
        }

        for (int i = 0; i < this.list.size(); i++) {
            if (this.keyboard.isPressed(this.list.get(i).getKey())) {
                this.status = (T) this.list.get(i).getValue();
                this.stop = true;
            }
        }
    }

    /**
     * Should stop boolean.
     *
     * @return the boolean
     */
    @Override
    public boolean shouldStop() {
        return this.stop;
    }

    /**
     * Sets false.
     */
    public void setFalse() {
        this.stop = false;
    }


}
