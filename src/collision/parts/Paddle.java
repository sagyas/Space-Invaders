package collision.parts;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import game.GameLevel;
import hit.HitListener;
import shapes.Line;
import shapes.Point;
import shapes.Rectangle;
import sprite.parts.Ball;
import sprite.parts.Sprite;
import sprite.parts.Velocity;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * controlled paddle.
 */
public class Paddle implements Sprite, Collidable {
    private Rectangle shape;
    private GUI gui;
    private double speed = STEP;
    private double width = 100;
    private List<HitListener> hitListeners = new ArrayList<>();
    /**
     * The constant DEFAULT_RECT.
     */
    public static final Rectangle DEFAULT_RECT = new Rectangle(new Point(280, 50), 100, 50);
    /**
     * The constant MAX_LEFT.
     */
    public static final int MAX_LEFT = 0;
    /**
     * The constant MAX_RIGHT.
     */
    public static final int MAX_RIGHT = 800;
    /**
     * The constant STEP.
     */
    public static final int STEP = 5;

    /**
     * default constructor.
     */
    public Paddle() {
        // default rectangle
        this(DEFAULT_RECT);
    }

    /**
     * Instantiates a new Paddle.
     *
     * @param g      the gui
     * @param speed1 the speed
     * @param size   the size
     */
    public Paddle(GUI g, double speed1, double size) {
        this.gui = g;
        this.shape = new Rectangle(new Point(this.gui.getDrawSurface().getWidth() / 2 - size / 2, 570), size, 20);
        this.speed = speed1;
    }

    /**
     * constructor.
     *
     * @param rect the shape of the paddle
     */
    public Paddle(Rectangle rect) {
        this.shape = rect;
    }

    /**
     * set gui.
     *
     * @param g the gui
     */
    public void setGUI(GUI g) {
        this.gui = g;
    }

    /**
     * change location of paddle by number of steps on x axis.
     *
     * @param dt the dt
     */
    public void moveLeft(double dt) {
        // can't move out of screen
        if (this.shape.getUpperLeft().getX() - this.speed * dt < MAX_LEFT) {
            return;
        }
        // make new point and change the block's shape upper left point
        Point newP = new Point(this.shape.getUpperLeft().getX() - this.speed * dt, this.shape.getUpperLeft().getY());
        this.shape = new Rectangle(newP, this.shape.getWidth(), this.shape.getHeight());
    }

    /**
     * change location of paddle by number of steps on x axis.
     *
     * @param dt the dt
     */
    public void moveRight(double dt) {
        // can't move out of screen
        if (this.shape.getUpperLeft().getX() + this.getCollisionRectangle().getWidth() + this.speed * dt > MAX_RIGHT) {
            return;
        }
        // change shape's upper left point to new point
        Point newP = new Point(this.shape.getUpperLeft().getX() + this.speed * dt, this.shape.getUpperLeft().getY());
        this.shape = new Rectangle(newP, this.shape.getWidth(), this.shape.getHeight());
    }

    /**
     * gets the keyboard button and tells if move right or left.
     *
     * @param dt speed
     */
    public void timePassed(double dt) {
        biuoop.KeyboardSensor keyb = this.gui.getKeyboardSensor();
        /*
         * if left key is pressed call moveLeft if right key is pressed call moveRight
         */
        if (keyb.isPressed(KeyboardSensor.LEFT_KEY)) {
            this.moveLeft(dt);
        }
        if (keyb.isPressed(KeyboardSensor.RIGHT_KEY)) {
            this.moveRight(dt);
        }
    }

    /**
     * draws paddle on surface.
     *
     * @param d the surface
     */
    public void drawOn(DrawSurface d) {
        d.setColor(Color.ORANGE);
        d.fillRectangle((int) this.shape.getUpperLeft().getX(), (int) this.shape.getUpperLeft().getY(),
                (int) this.shape.getWidth(), (int) this.shape.getHeight());
        d.setColor(Color.BLACK);
        d.drawRectangle((int) this.shape.getUpperLeft().getX(), (int) this.shape.getUpperLeft().getY(),
                (int) this.shape.getWidth(), (int) this.shape.getHeight());
    }

    /**
     * return the shape of paddle.
     *
     * @return rectangle of paddle
     */
    public Rectangle getCollisionRectangle() {
        return new Rectangle(this.shape.getUpperLeft(), this.shape.getWidth(), this.shape.getHeight());
    }

    /**
     * return velocity after hit for the ball.
     *
     * @param hitter          the ball
     * @param collisionPoint  the collision point
     * @param currentVelocity the current velocity
     * @return new velocity
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        Velocity newVel = currentVelocity;
        List lines = new ArrayList();
        // get rectangle lines
        lines = this.shape.getRectangleLines();
        /*
         * check what line gets the ball hit
         */
        for (int i = 0; i < lines.size(); i++) {
            // check if the point is on the specific line out of 5
            if (((Line) lines.get(i)).isBetween(collisionPoint)) {
                //if its the top line of the paddle gets hit from above
                if (i == 0 && currentVelocity.getDY() > 0) {
                    List divide = new ArrayList();
                    //divide the line to 5 parts
                    divide = ((Line) lines.get(i)).divideTo5();
                    for (int j = 1; j < 6; j++) {
                        if (((Line) divide.get(j - 1)).isBetween(collisionPoint)) {
                            //if its most left part of paddle return velocity hard to the left
                            if (j == 1) {
                                newVel = new Velocity().fromAngleAndSpeed(300, currentVelocity.getSpeed());
                                //if its second-most left part of paddle return velocity less hard to the left
                                // than before
                            } else if (j == 2) {
                                newVel = new Velocity().fromAngleAndSpeed(330, currentVelocity.getSpeed());
                                //on the middle return as usual (-dy)
                            } else if (j == 3) {
                                newVel = new Velocity(currentVelocity.getDX(), -currentVelocity.getDY());
                                //if its second-most right part of paddle return velocity less hard to the right as next
                            } else if (j == 4) {
                                newVel = new Velocity().fromAngleAndSpeed(30, currentVelocity.getSpeed());
                                //if its most right part of paddle return velocity hard to the right
                            } else if (j == 5) {
                                newVel = new Velocity().fromAngleAndSpeed(60, currentVelocity.getSpeed());
                            }
                        }
                    }
                }
            }
        }
        return newVel;
    }

    /**
     * adds paddle to game.
     *
     * @param g the game
     */
    public void addToGame(GameLevel g) {
        g.addCollidable((Collidable) this);
        g.addSprite((Sprite) this);
    }

    /**
     * Sets speed.
     *
     * @param speed1 the speed 1
     */
    public void setSpeed(double speed1) {
        this.speed = speed1;
    }

    /**
     * Sets width.
     *
     * @param size the size
     */
    public void setWidth(double size) {
        this.width = size;
    }

}