package collision.parts;

import biuoop.DrawSurface;
import game.GameLevel;
import hit.HitListener;
import hit.HitNotifier;
import shapes.Line;
import shapes.Point;
import shapes.Rectangle;
import sprite.parts.Ball;
import sprite.parts.Sprite;
import sprite.parts.Velocity;

import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

/**
 * create Block object.
 */
public class Block implements Collidable, Sprite, Comparable, HitNotifier {
    private Rectangle block;
    private String hits = "X";
    private Color color = Color.DARK_GRAY;
    private List<HitListener> hitListeners = new ArrayList<>();
    private List colors = new ArrayList();
    private Color stroke = null;
    private String fill = null;
    private boolean isEnemy = false;
    /**
     * The constant UPPER.
     */
    public static final int UPPER = 0;
    /**
     * The constant LOWER.
     */
    public static final int LOWER = 3;
    /**
     * The constant RIGHT.
     */
    public static final int RIGHT = 2;
    /**
     * The constant LEFT.
     */
    public static final int LEFT = 1;

    /**
     * constructor.
     *
     * @param shape the shape
     */
    public Block(Rectangle shape) {
        this.block = shape;
    }

    /**
     * set color to block.
     *
     * @param choiceColor the color
     */
    public void setColor(String choiceColor) {
        if (choiceColor.matches("GREEN")) {
            this.colors.add(Color.GREEN);
        }
        if (choiceColor.matches("PINK")) {
            this.colors.add(Color.PINK);
        }
        if (choiceColor.matches("YELLOW")) {
            this.colors.add(Color.YELLOW);
        }
        if (choiceColor.matches("BLUE")) {
            this.colors.add(Color.BLUE);
        }
        if (choiceColor.matches("RED")) {
            this.colors.add(Color.RED);
        }
        if (choiceColor.matches("GRAY")) {
            this.colors.add(Color.GRAY);
        }
        if (choiceColor.matches("CYAN")) {
            this.colors.add(Color.cyan);
        }
    }


    /**
     * Sets color.
     *
     * @param choice the choice
     */
    public void setColor(Object choice) {
        this.colors.add(choice);
    }

    /**
     * gets location of block.
     *
     * @return shapes.Point of upper left
     */
    public Point getLocation() {
        return this.block.getUpperLeft();
    }

    /**
     * gets size of block.
     *
     * @return size size
     */
    public int getSize() {
        return (int) this.block.getWidth() * (int) this.block.getHeight();
    }

    /**
     * Return the "collision shape" of the object.
     *
     * @return shapes.Rectangle
     */
    public Rectangle getCollisionRectangle() {
        return new Rectangle(this.block.getUpperLeft(), this.block.getWidth(), this.block.getHeight());
    }

    /**
     * Notify the object that we collided with it at collisionPoint with a given
     * velocity. The return is the new velocity expected after the hit (based on the
     * force the object inflicted on us).
     *
     * @param hitter          the ball
     * @param collisionPoint  the point
     * @param currentVelocity the current velocity
     * @return new velocity
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        Velocity newVel = currentVelocity;
        List lines = new ArrayList();
        // get lines of rectangle
        lines = this.block.getRectangleLines();
        /*
         * check if collision is on this line
         */
        for (int i = 0; i < lines.size(); i++) {
            if (((Line) lines.get(i)).isBetween(collisionPoint)) {
                // 0 or 3 means horizontal lines
                if (i == UPPER || i == LOWER) {
                    // changes y axis speed
                    newVel = new Velocity(newVel.getDX(), -newVel.getDY());
                }
                // 1 or 2 means vertical lines
                if (i == RIGHT || i == LEFT) {
                    // changes x axis speed
                    newVel = new Velocity(-newVel.getDX(), newVel.getDY());
                }
            }
        }
        return newVel;
    }

    /**
     * draws the block with number of hits.
     *
     * @param surface to draw on
     */
    public void drawOn(DrawSurface surface) {
        if (this.hits != "X") {
            if (this.colors.get(Integer.parseInt(this.hits) - 1) instanceof Color) {
                surface.setColor((Color) this.colors.get(Integer.parseInt(this.hits) - 1));
                surface.fillRectangle((int) this.block.getUpperLeft().getX(), (int) this.block.getUpperLeft().getY(),
                        (int) this.block.getWidth(), (int) this.block.getHeight());
            }

            if (this.colors.get(Integer.parseInt(this.hits) - 1) instanceof Image) {
                surface.drawImage((int) this.block.getUpperLeft().getX(), (int) this.block.getUpperLeft().getY(),
                        (Image) this.colors.get(Integer.parseInt(this.hits) - 1));
            }
        }

        surface.setColor(Color.BLACK);
        if (this.stroke != null) {
            surface.setColor(this.stroke);
            surface.drawRectangle((int) this.block.getUpperLeft().getX(), (int) this.block.getUpperLeft().getY(),
                    (int) this.block.getWidth(), (int) this.block.getHeight());
        }
    }

    /**
     * set number of hits to hit the block until getting X.
     *
     * @param num max number of hits the enter
     */
    public void setHits(String num) {
        this.hits = num;
    }

    /**
     * updated the hits counter.
     */
    public void gotHit() {
        /*
         * done if its already X
         */
        if (this.hits.matches("X")) {
            return;
        }
        /*
         * convert the number
         */
        int num = Integer.parseInt(this.hits);
        if (num == 1) {
            this.hits = "X";
        } else {
            this.hits = Integer.toString(num - 1);
        }
    }

    /**
     * @param dt time for speed
     */
    public void timePassed(double dt) {
    }

    /**
     * add block to game.
     *
     * @param theGameLevel the game
     */
    public void addToGame(GameLevel theGameLevel) {
        theGameLevel.addCollidable((Collidable) this);
        theGameLevel.addSprite((Sprite) this);
    }

    /**
     * compare block to another to know if its the same on.
     *
     * @param other the other block
     * @return 1 if same , -1 otherwise
     */
    public int compareTo(Object other) {
        if (other instanceof Block) {
            Block blk2 = (Block) other;
            /*
             * comparing all member: upper left point, width and height
             */
            if ((this.getCollisionRectangle().getUpperLeft().getX() == blk2.getCollisionRectangle().getUpperLeft()
                    .getX())
                    && (this.getCollisionRectangle().getUpperLeft().getY() == blk2.getCollisionRectangle()
                    .getUpperLeft().getY())
                    && (this.getCollisionRectangle().getWidth() == blk2.getCollisionRectangle().getWidth())
                    && (this.getCollisionRectangle().getHeight() == blk2.getCollisionRectangle().getHeight())) {
                return 1;
            }
        }
        // if its not the same block
        return -1;
    }

    /**
     * Remove from game.
     *
     * @param gameLevel the game level
     */
    public void removeFromGame(GameLevel gameLevel) {
        gameLevel.getSprites().getList().remove(this);
        gameLevel.getEnvironment().getList().remove(this);
    }

    /**
     * Notify hit.
     *
     * @param hitter the hitter
     */
    public void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

    /**
     * add listener to list.
     *
     * @param hl the listener
     */
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    /**
     * remove listener from list.
     *
     * @param hl the listener
     */
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    /**
     * Gets hit points.
     *
     * @return the hit points
     */
    public String getHitPoints() {
        return this.hits;
    }

    /**
     * Sets stroke.
     *
     * @param c the color
     */
    public void setStroke(Color c) {
        this.stroke = c;
    }

    /**
     * Sets image.
     *
     * @param img the img
     */
    public void setImage(Image img) {
        this.colors.add(img);
    }

    /**
     * Get rectangle rectangle.
     *
     * @return the rectangle
     */
    public Rectangle getRectangle() {
        return this.block;
    }

    /**
     * Is friend boolean.
     *
     * @return the boolean
     */
    public boolean isEnemy() {
        return this.isEnemy;
    }

    /**
     * Set enemy.
     */
    public void setEnemy() {
        this.isEnemy = true;
    }

    /**
     * Get listeners list.
     *
     * @return the list
     */
    public List getListeners() {
        return this.hitListeners;
    }
}