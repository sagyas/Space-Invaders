package game.levels;

import collision.parts.Block;
import shapes.Point;
import sprite.parts.Sprite;
import sprite.parts.Velocity;
import biuoop.DrawSurface;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * level called Direct hit.
 */
public class DirectHit implements LevelInformation {

    @Override
    public int numberOfBalls() {
        return 1;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        List<Velocity> list = new ArrayList<Velocity>();
        list.add(new Velocity().fromAngleAndSpeed(180, 5));
        return list;
    }

    @Override
    public int paddleSpeed() {
        return 8;
    }

    @Override
    public int paddleWidth() {
        return 100;
    }

    @Override
    public String levelName() {
        return "Direct hit";
    }

    @Override
    public Sprite getBackground() {
        Sprite screen = new Sprite() {
            @Override
            public void drawOn(DrawSurface d) {
                d.setColor(Color.BLACK);
                d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
                d.setColor(Color.BLUE);
                int r = 40;
                for (int i = 0; i < 3; i++) {
                    d.drawCircle(d.getWidth() / 2 - 10, 160, r);
                    r += 20;
                }
                d.drawLine(d.getWidth() / 2, 160, d.getWidth() / 2 + 80, 160);
                d.drawLine(d.getWidth() / 2 - 10, 160, d.getWidth() / 2 - 10, 60);
                d.drawLine(d.getWidth() / 2 - 10, 160, d.getWidth() / 2 - 100, 160);
                d.drawLine(d.getWidth() / 2 - 10, 160, d.getWidth() / 2 - 10, 260);

            }

            @Override
            public void timePassed(double dt) {

            }
        };
        return screen;
    }

    @Override
    public List<Block> blocks() {
        shapes.Rectangle rec = new shapes.Rectangle(new Point(380, 150), 20, 20);
        Block block = new Block(rec);
        block.setColor(Color.RED);
        block.setHits("1");
        List<Block> list = new ArrayList<>();
        list.add(block);
        return list;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return this.blocks().size();
    }
}
