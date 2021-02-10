package game;

import biuoop.DrawSurface;
import collision.parts.Block;
import shapes.Point;
import shapes.Rectangle;
import sprite.parts.Ball;
import sprite.parts.Sprite;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The type Moving blocks.
 */
public class MovingBlocks implements Sprite {
    private GameLevel game;
    private List<Block> blocks;
    private List<Point> originalPositions;
    private int speed;
    private boolean changeY;
    private boolean movingRight;
    private boolean returnToStart;
    /**
     * The constant RIGHT_BORDER.
     */
    public static final int RIGHT_BORDER = 800;
    /**
     * The constant LEFT_BORDER.
     */
    public static final int LEFT_BORDER = 0;
    /**
     * The constant LOW_BORDER.
     */
    public static final int LOW_BORDER = 490;
    /**
     * The constant xStep.
     */
    private int xStep;
    /**
     * The constant Y_STEP.
     */
    public static final int Y_STEP = 1500;
    private long lastTime;

    /**
     * Instantiates a new Moving blocks.
     *
     * @param g    the g
     * @param step the step
     */
    public MovingBlocks(GameLevel g, int step) {
        this.blocks = new ArrayList<>();
        this.originalPositions = new ArrayList<>();
        this.changeY = false;
        this.movingRight = true;
        this.returnToStart = false;
        this.game = g;
        this.xStep = step;
        this.speed = step;
    }


    /**
     * draw the sprite to the screen.
     *
     * @param d the surface
     */
    @Override
    public void drawOn(DrawSurface d) {
        for (int i = 0; i < this.blocks.size(); i++) {
            this.blocks.get(i).drawOn(d);
        }
    }

    /**
     * notify the sprite that time has passed.
     *
     * @param dt the dt
     */
    @Override
    public void timePassed(double dt) {
        this.moveOneStep(dt);
    }

    /**
     * Move one step.
     *
     * @param dt the dt
     */
    public void moveOneStep(double dt) {
        this.checkOutOfBounds(dt);
        this.returnToStart();

        for (int i = 0; i < this.blocks.size(); i++) {
            Block currBlock = this.blocks.get(i);
            if (currBlock.getHitPoints() != "X") {
                Rectangle currRectangle = currBlock.getRectangle();
                double currX = currRectangle.getUpperLeft().getX();
                double currY = currRectangle.getUpperLeft().getY();

                if (this.changeY) {
                    if (movingRight) {
                        currRectangle.setUpperLeft(new Point(currX - this.speed * dt, currY + Y_STEP * dt));
                    } else {
                        currRectangle.setUpperLeft(new Point(currX + this.speed * dt, currY + Y_STEP * dt));
                    }

                } else {
                    if (movingRight) {
                        currRectangle.setUpperLeft(new Point(currX + this.speed * dt, currY));
                    } else {
                        currRectangle.setUpperLeft(new Point(currX - this.speed * dt, currY));
                    }
                }
            }
        }
        if (this.changeY) {
            this.movingRight = !this.movingRight;
        }
        this.changeY = false;

        long time = System.currentTimeMillis();
        if (time - this.lastTime >= 0.5 * 1000) {
            this.lastTime = time;
            this.randomShot(dt);
        }
    }

    /**
     * Add block.
     *
     * @param b the b
     */
    public void addBlock(Block b) {
        this.blocks.add(b);
        this.originalPositions.add(b.getRectangle().getUpperLeft());
    }

    /**
     * Check out of bounds.
     *
     * @param dt the dt
     */
    public void checkOutOfBounds(double dt) {
        for (int i = 0; i < this.blocks.size(); i++) {
            Block currBlock = this.blocks.get(i);
            if (currBlock.getHitPoints() != "X") {
                //get block information
                Rectangle currRectangle = currBlock.getRectangle();
                double currX = currRectangle.getUpperLeft().getX();
                double currY = currRectangle.getUpperLeft().getY();
                //if a block gets to right or left border
                if (currX + currRectangle.getWidth() + this.speed * dt >= RIGHT_BORDER
                        || currX - this.speed * dt <= LEFT_BORDER) {
                    this.changeY = true;
                    this.speed *= 1.1;
                    //this.speed = (int)( (double) this.speed * 1.1D);
                }
                //if a block is too low, return to original positions
                if (currY + currRectangle.getHeight() + Y_STEP * dt >= LOW_BORDER) {
                    this.returnToStart = true;
                }
            }
        }
    }

    /**
     * Return to start.
     */
    public void returnToStart() {
        if (this.returnToStart) {
            for (int j = 0; j < this.blocks.size(); j++) {
                this.blocks.get(j).getRectangle().setUpperLeft(this.originalPositions.get(j));
            }
            this.movingRight = true;
            this.speed = xStep;
            //this.returnToStart = false;
        }
    }

    /**
     * Gets return to start.
     *
     * @return the return to start
     */
    public boolean getReturnToStart() {
        return this.returnToStart;
    }

    /**
     * Sets return to start to false.
     */
    public void setReturnToStartToFalse() {
        this.returnToStart = false;
    }

    /**
     * Sets return to start to false.
     */
    public void setReturnToStartToTrue() {
        this.returnToStart = true;
    }

    /**
     * Random shot.
     *
     * @param dt the dt
     */
    public void randomShot(double dt) {
        List<Block> shooters = new ArrayList<Block>();
        List<Double> columns = new ArrayList<Double>();
        //get columns x's
        for (int i = 0; i < this.blocks.size(); i++) {
            Block currBlock = this.blocks.get(i);
            if (currBlock.getHitPoints() != "X") {
                if (columns.isEmpty()) {
                    columns.add(currBlock.getRectangle().getUpperLeft().getX());
                } else {
                    if (!columns.contains(currBlock.getRectangle().getUpperLeft().getX())) {
                        columns.add(currBlock.getRectangle().getUpperLeft().getX());
                    }
                }
            }
        }

        double lowestY = 0;
        //take lowest y in a column
        for (int j = 0; j < columns.size(); j++) {
            lowestY = 0;
            double column = columns.get(j);
            for (int i = 0; i < this.blocks.size(); i++) {
                Block currBlock = this.blocks.get(i);
                if (currBlock.getHitPoints() != "X" && currBlock.getRectangle().getUpperLeft().getX() == column) {
                    if (currBlock.getRectangle().getUpperLeft().getY() > lowestY) {
                        lowestY = currBlock.getRectangle().getUpperLeft().getY();
                    }
                }
            }

            //find lowest alien and add to list
            for (int i = 0; i < this.blocks.size(); i++) {
                Block currBlock = this.blocks.get(i);
                if (currBlock.getRectangle().getUpperLeft().getY() == lowestY && currBlock.getHitPoints() != "X"
                        && currBlock.getRectangle().getUpperLeft().getX() == column) {
                    shooters.add(currBlock);
                }
            }
        }


        Random rand = new Random();
        //get the random shooter from the lowests
        int row = rand.nextInt((shooters.size()));
        //get the shooter
        Block shooter = shooters.get(row);

        //shoot
        try {
            double width = shooter.getRectangle().getWidth();
            double height = shooter.getRectangle().getHeight();
            Point point = shooter.getRectangle().getUpperLeft();
            Ball b = new Ball(new Point(point.getX() + width / 2, point.getY() + height + 6), 5, Color.RED);
            b.setVelocity(0, 30000 * dt);
            b.setIsFromEnemyTrue();
            b.addGameLevel(this.game);
            b.addToGame(this.game);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}