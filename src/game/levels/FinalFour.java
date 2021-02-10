package game.levels;

import collision.parts.Block;
import shapes.Point;
import sprite.parts.Sprite;
import sprite.parts.Velocity;
import biuoop.DrawSurface;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * the level called Final Four.
 */
public class FinalFour implements LevelInformation {

    @Override
    public int numberOfBalls() {
        return 3;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        List<Velocity> list = new ArrayList<Velocity>();
        int s = 30;
        for (int i = 0; i < this.numberOfBalls(); i++) {
            list.add(new Velocity().fromAngleAndSpeed(s, 4));
            s -= 30;
        }
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
        return "Final Four";
    }

    @Override
    public Sprite getBackground() {
        Sprite screen = new Sprite() {
            @Override
            public void drawOn(DrawSurface d) {
                d.setColor(new Color(0, 130, 255));
                d.fillRectangle(0, 0, d.getWidth(), d.getHeight());


                int x = 88;
                int y = 450;
                for (int i = 0; i < 10; i++) {
                    d.setColor(Color.WHITE);
                    d.drawLine(x, y, x - 20, 600);
                    x += 8;
                }

                d.setColor(Color.LIGHT_GRAY);
                d.fillCircle(100, 450, 20);
                d.fillCircle(120, 440, 20);
                d.setColor(new Color(211, 207, 198));
                d.fillCircle(140, 460, 25);


                x = 688;
                y = 450;
                for (int i = 0; i < 8; i++) {
                    d.setColor(Color.WHITE);
                    d.drawLine(x, y, x - 20, 600);
                    x += 7;
                }

                d.setColor(Color.LIGHT_GRAY);
                d.fillCircle(688, 450, 20);
                d.fillCircle(700, 440, 20);
                d.setColor(new Color(211, 207, 198));
                d.fillCircle(720, 460, 25);


            }

            @Override
            public void timePassed(double dt) {

            }
        };
        return screen;
    }

    @Override
    public List<Block> blocks() {
        Random rand = new Random();
        List<Block> list = new ArrayList<>();
        List<Color> colors = new ArrayList<>();
        colors.add(Color.GRAY);
        colors.add(Color.RED);
        colors.add(Color.YELLOW);
        colors.add(Color.BLUE);
        colors.add(Color.WHITE);
        colors.add(new Color(255, 180, 255));
        colors.add(new Color(0, 220, 255));
        int num = 10;
        int width = 50;
        int height = 30;
        int y = 100;
        int x = 24;
        int start = x;
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 15; j++) {
                shapes.Rectangle rec = new shapes.Rectangle(new Point(x, y),
                        width, height);
                Block block = new Block(rec);
                block.setColor(colors.get(i));
                block.setHits("1");
                list.add(block);
                x += width;
            }
            x = start;
            y += height;
        }
        return list;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return this.blocks().size();
    }
}


