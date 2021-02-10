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
 * The type Wide easy.
 */
public class WideEasy implements LevelInformation {

    @Override
    public int numberOfBalls() {
        return 10;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        List<Velocity> list = new ArrayList<Velocity>();
        int start = 95;
        for (int i = 0; i < this.numberOfBalls(); i++) {
            list.add(new Velocity().fromAngleAndSpeed(start, 4));
            start += 20;
        }
        return list;
    }

    @Override
    public int paddleSpeed() {
        return 4;
    }

    @Override
    public int paddleWidth() {
        return 500;
    }

    @Override
    public String levelName() {
        return "Wide Easy";
    }

    @Override
    public Sprite getBackground() {
        Sprite screen = new Sprite() {
            @Override
            public void drawOn(DrawSurface d) {
                d.setColor(Color.LIGHT_GRAY);
                d.fillRectangle(0, 0, d.getWidth(), d.getHeight());

                int s = 20;
                for (int i = 0; i < 50; i++) {
                    d.setColor(new Color(240, 198, 73));
                    d.drawLine(150, 150, s, 260);
                    s += 10;
                }

                d.setColor(new Color(240, 198, 73));
                d.fillCircle(150, 150, 65);
                d.setColor(Color.YELLOW);
                d.fillCircle(150, 150, 50);
            }

            @Override
            public void timePassed(double dt) {

            }
        };
        return screen;
    }

    @Override
    public List<Block> blocks() {
        List<Block> list = new ArrayList<>();
        int start = 20;
        int width = 54;
        for (int i = 0; i < 14; i++) {
            shapes.Rectangle rec = new shapes.Rectangle(new Point(start, 260),
                    width, 30);
            Block block = new Block(rec);
            if (i == 0 || i == 1) {
                block.setColor(Color.RED);
            }
            if (i == 2 || i == 3) {
                block.setColor(Color.orange);
            }
            if (i == 4 || i == 5) {
                block.setColor(Color.YELLOW);
            }
            if (i == 6 || i == 7) {
                block.setColor(Color.GREEN);
            }
            if (i == 8 || i == 9) {
                block.setColor(Color.PINK);
            }
            if (i == 10 || i == 11) {
                block.setColor(Color.LIGHT_GRAY);
            }
            if (i == 12 || i == 13) {
                block.setColor(Color.WHITE);
            }
            block.setHits("1");
            list.add(block);
            start += width;
        }
        return list;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return this.blocks().size();
    }
}
