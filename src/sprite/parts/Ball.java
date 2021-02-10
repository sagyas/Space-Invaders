package sprite.parts;

import biuoop.DrawSurface;
import collision.parts.Block;
import collision.parts.CollisionInfo;
import collision.parts.Paddle;
import game.GameEnvironment;
import game.GameLevel;
import hit.HitListener;
import shapes.Line;
import shapes.Point;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * create ball.
 */
public class Ball implements Sprite {
    private int radius;
    // default
    private Point location = new Point(0, 0);
    // default
    private Velocity velocity = new Velocity(0, 0);
    private Color color;
    private GameEnvironment game;
    private GameLevel level;
    private List<HitListener> hitListeners = new ArrayList<>();
    private boolean isFromEnemy = false;
    /**
     * The constant DISTANCE.
     */
    public static final double DISTANCE = 1;
    /**
     * The constant HEIGHT.
     */
    public static final int HEIGHT = 600;

    /**
     * constructor to a ball with no boundaries.
     *
     * @param center - location
     * @param radius - radius
     * @param color  - ball color
     * @throws Exception if radius is not good
     */
    public Ball(Point center, int radius, java.awt.Color color) throws Exception {
        this(center.getX(), center.getY(), radius, color);
    }

    /**
     * constructor.
     *
     * @param x       - x axis
     * @param y       - y axis
     * @param radius1 - radius
     * @param color   - ball color
     * @throws Exception if radius is not good
     */
    public Ball(double x, double y, int radius1, java.awt.Color color) throws Exception {
        if (radius1 <= 0) {
            throw new Exception("radius must be positive");
        }
        Point center = new Point(x, y);
        this.location = center;
        this.radius = radius1;
        this.color = color;
    }

    /**
     * get x-axis location of ball.
     *
     * @return integer x
     */
    public int getX() {
        return (int) this.location.getX();
    }

    /**
     * get y-axis location of ball.
     *
     * @return integer y
     */
    public int getY() {
        return (int) this.location.getY();
    }

    /**
     * get radius of ball.
     *
     * @return integer size
     */
    public int getSize() {
        return this.radius;
    }

    /**
     * get color of ball.
     *
     * @return Color color
     */
    public java.awt.Color getColor() {
        return this.color;
    }

    /**
     * draw ball on surface.
     *
     * @param surface - the given surface
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.getColor());
        surface.fillCircle(this.getX(), this.getY(), this.getSize());
        surface.setColor(Color.BLACK);
        surface.drawCircle(this.getX(), this.getY(), this.getSize());
    }

    /**
     * sets velocity for ball.
     *
     * @param vel - the given velocity
     */
    public void setVelocity(Velocity vel) {
        this.velocity = vel;
    }

    /**
     * sets velocity.
     *
     * @param dx - delta x
     * @param dy - delta y
     */
    public void setVelocity(double dx, double dy) {
        // creates new velocity out of dx and dy
        Velocity v = new Velocity(dx, dy);
        this.velocity = v;
    }

    /**
     * gets the velocity.
     *
     * @return Velocity velocity
     */
    public Velocity getVelocity() {
        return this.velocity;
    }

    /**
     * moves the ball around.
     *
     * @param dt the dt
     * @throws Exception if there's no bounds entered
     */
    public void moveOneStep(double dt) throws Exception {
        /*
         * creates line movement on x axis
         */
        double dx = this.velocity.getDX();
        double nextX = this.location.getX() + dx;
        Line lineX = new Line(this.location, new Point(nextX, this.location.getY()));
        /*
         * creates line movement on y axis
         */
        double dy = this.velocity.getDY();
        double nextY = this.location.getY() + dy;
        Line lineY = new Line(this.location, new Point(this.location.getX(), nextY));
        //general trajectory
        Line line = new Line(this.location, this.getVelocity().applyToPoint(this.location, dt));
        double check = line.length();
        //booleans to know if we changed the DX/DY speed
        boolean collX = false;
        boolean collY = false;
        collX = this.axisCollision(lineX);
        collY = this.axisCollision(lineY);
        //if one axis speed didn't change use to general trajectory
        if (!(collX || collY)) {
            this.axisCollision(line);
        }
        // change location of ball
        this.location = this.getVelocity().applyToPoint(this.location, dt);
    }

    /**
     * change velocity on specific line (for x-axis line or y-axis line or general trajectory).
     *
     * @param line the line of movement
     * @return true if we changed velocity
     */
    public boolean axisCollision(Line line) {
        if (this.game.getClosestCollision(line) != null) {
            double dt = 1 / 60;
            // gets info about the closest collision
            CollisionInfo info = this.game.getClosestCollision(line);
            Velocity newVelocity = info.collisionObject().hit(this, info.collisionPoint(), this.velocity);
            // change velocity when its DISTANCE * radius away from collision
            Point p = this.getVelocity().applyToPoint(this.location, dt);
            if (p.distance(info.collisionPoint()) <= DISTANCE * this.radius) {
                this.velocity = newVelocity;
                // after changed direction means after the hit, count the hit
                if (info.collisionObject() instanceof Block) {
                    ((Block) info.collisionObject()).gotHit();
                }
                //to know we changed speed
                if (info.collisionObject().getCollisionRectangle().getUpperLeft().getY() >= HEIGHT) {
                    this.notifyHit((Block) info.collisionObject());
                }
                if (info.collisionObject() instanceof Block) {
                    ((Block) info.collisionObject()).notifyHit(this);
                }
                if (info.collisionObject() instanceof Paddle) {
                    this.removeFromGame(this.level);
                    this.level.setHitPaddleTrue();
                }
                return true;
            }
        }
        //if we didn't change speed
        return false;
    }

    /**
     * set environment to ball.
     *
     * @param g the environment
     */
    public void setEnvironment(GameEnvironment g) {
        this.game = g;
    }

    /**
     * moves ball one step.
     *
     * @param dt speed
     */
    public void timePassed(double dt) {

        try {
            this.moveOneStep(dt);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * set environment and adds to sprite collection.
     *
     * @param g the GameLevel.GameLevel
     */
    public void addToGame(GameLevel g) {
        this.setEnvironment(g.getEnvironment());
        g.addSprite((Sprite) this);
    }

    /**
     * Remove from game.
     *
     * @param gameLevel the game level
     */
    public void removeFromGame(GameLevel gameLevel) {
        gameLevel.getSprites().getList().remove(this);
    }

    /**
     * Notify hit.
     *
     * @param block the block
     */
    public void notifyHit(Block block) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(block, this);
        }
    }

    /**
     * Add hit listener.
     *
     * @param hl the hl
     */
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    /**
     * Remove hit listener.
     *
     * @param hl the hl
     */
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    /**
     * Add game level.
     *
     * @param l the l
     */
    public void addGameLevel(GameLevel l) {
        this.level = l;
    }

    /**
     * Sets is from enemy true.
     */
    public void setIsFromEnemyTrue() {
        this.isFromEnemy = true;
    }

    /**
     * Is from enemy boolean.
     *
     * @return the boolean
     */
    public boolean isFromEnemy() {
        return this.isFromEnemy;
    }
}

