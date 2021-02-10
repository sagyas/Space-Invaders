package sprite.parts;

import java.util.ArrayList;
import java.util.List;

import biuoop.DrawSurface;

/**
 * collection of sprites.
 */
public class SpriteCollection {
    private List l1 = new ArrayList();

    /**
     * adds sprite to list.
     *
     * @param s the object
     */
    public void addSprite(Sprite s) {
        this.l1.add((Sprite) s);
    }

    /**
     * call timePassed() on all sprites.
     *
     * @param dt the dt
     */
    public void notifyAllTimePassed(double dt) {
        int i = 0;
        for (i = 0; i < this.l1.size(); i++) {
            ((Sprite) this.l1.get(i)).timePassed(dt);
        }
    }

    /**
     * return the list of sprites.
     *
     * @return the list
     */
    public java.util.List getList() {
        return this.l1;
    }

    /**
     * call drawOn(d) on all sprites.
     *
     * @param d the surface
     */
    public void drawAllOn(DrawSurface d) {
        for (Object entity : l1) {
            ((Sprite) entity).drawOn(d);
        }
    }
}