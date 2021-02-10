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
 * the level Green 3.
 */
public class Green3 implements LevelInformation {

    @Override
    public int numberOfBalls() {
        return 2;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        List<Velocity> list = new ArrayList<Velocity>();
        int s = 150;
        for (int i = 0; i < this.numberOfBalls(); i++) {
            list.add(new Velocity().fromAngleAndSpeed(s, 4));
            s += 60;
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
        return "Green 3";
    }

    @Override
    public Sprite getBackground() {
        Sprite screen = new Sprite() {
            @Override
            public void drawOn(DrawSurface d) {
                d.setColor(new Color(2, 133, 0));
                d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
                d.setColor(Color.DARK_GRAY);
                d.fillRectangle(70, 400, 130, 600);
                d.fillRectangle(120, 350, 35, 50);
                d.fillRectangle(130, 250, 15, 100);

                int s = 80;
                int y = 410;
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 6; j++) {
                        d.setColor(Color.WHITE);
                        d.fillRectangle(s, y, 10, 30);
                        s += 20;
                    }
                    s = 80;
                    y += 50;
                }

                d.setColor(Color.ORANGE);
                d.fillCircle(137, 240, 13);
                d.setColor(Color.RED);
                d.fillCircle(137, 240, 9);
                d.setColor(Color.WHITE);
                d.fillCircle(137, 240, 4);
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
        int num = 10;
        int width = 50;
        int height = 30;
        int y = 200;
        int x = 275;
        int start = x;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < num; j++) {
                shapes.Rectangle rec = new shapes.Rectangle(new Point(x, y),
                        width, height);
                Block block = new Block(rec);
                block.setColor(colors.get(i));
                if (i == 0) {
                    block.setHits("2");
                } else {
                    block.setHits("1");
                }
                list.add(block);
                x += width;
            }
            y += height;
            x = start + (i + 1) * width;
            num--;
        }
        return list;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return this.blocks().size();
    }
}

