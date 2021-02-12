package game.levels;

import biuoop.DrawSurface;
import collision.parts.Block;
import hit.Counter;
import sprite.parts.Sprite;
import sprite.parts.Velocity;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Image;
import java.io.Reader;
import java.io.LineNumberReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The type Level specification reader.
 */
public class LevelSpecificationReader implements LevelInformation {
    private String name = null;
    private List vList = new ArrayList();
    private Sprite background;
    private int paddleSpeed = -1;
    private int paddleWidth = -1;
    private Counter blocksToRemove = new Counter();
    private Counter balls = new Counter();
    /**
     * The List.
     */
    private List<Block> list = new ArrayList<>();

    /**
     * From reader list.
     *
     * @param reader the reader
     * @return the list
     * @throws IOException the io exception
     */
    public static List<LevelInformation> fromReader(Reader reader) throws IOException {
        List<LevelInformation> levels = new ArrayList<>();
        String line = null;
        String name = null;
        String background = null;
        LineNumberReader read = null;
        LevelSpecificationReader level = null;
        BlocksFromSymbolsFactory blkFactory = null;
        List vList = new ArrayList();
        String blocksDef = null;
        int blocksX = -1;
        int blocksY = -1;
        int rowHeight = -1;
        int numBlocks = -1;
        try {
            read = new LineNumberReader(reader);
            while ((line = read.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("#") || line.matches("")) {
                    line = read.readLine();
                }
                if (line.startsWith("START_LEVEL")) {
                    level = new LevelSpecificationReader();
                    while (!line.startsWith("END_LEVEL")) {
                        line = read.readLine();
                        if (line.startsWith("level_name:")) {
                            name = line.substring("level_name:".length());
                            level.setName(name);
                        }
                        if (line.startsWith("ball_velocities:")) {
                            vList = getVelocities(line);
                            level.setVelos(getVelocities(line));
                        }
                        if (line.startsWith("background:image(")) {
                            Pattern patt = Pattern.compile("\\(.*\\)");
                            Matcher match = patt.matcher(line);
                            while (match.find()) {
                                try {
                                    InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(
                                            line.substring(match.start() + 1, match.end() - 1));
                                    reader = new InputStreamReader(is);
                                    Image img = ImageIO.read(is);
                                    background = line.substring(match.start() + 1, match.end() - 1);
                                    Sprite imge = new Sprite() {
                                        @Override
                                        public void drawOn(DrawSurface d) {
                                            d.drawImage(0, 0, img);
                                            d.setColor(Color.DARK_GRAY);
                                            d.fillRectangle(0, 20, 800, 25);
                                            d.setColor(Color.DARK_GRAY);
                                            d.fillRectangle(0, 45, 25, 600 - 25);
                                            d.setColor(Color.DARK_GRAY);
                                            d.fillRectangle(800 - 25, 45, 25, 600 - 25);
                                        }

                                        @Override
                                        public void timePassed(double dt) {
                                        }
                                    };
                                    level.setBackground(imge);
                                } catch (IOException e) {
                                    throw new RuntimeException("cant find image");
                                }
                            }
                        }
                        if (line.startsWith("background:color(")) {

                            Color finalColor = colorFromRGB(line);
                            Sprite back = coloredSprite(finalColor);
                            background = finalColor.toString();
                            level.setBackground(back);
                        }
                        if (line.startsWith("paddle_speed:")) {
                            Pattern patt = Pattern.compile("[0-9]+");
                            level.paddleSpeed = getIntOfField(line, patt);
                            level.setPaddleSpeed(getIntOfField(line, patt));
                        }
                        if (line.startsWith("paddle_width:")) {
                            Pattern patt = Pattern.compile("[0-9]+");
                            level.paddleWidth = getIntOfField(line, patt);
                            level.setPaddleWidth(getIntOfField(line, patt));
                        }
                        if (line.startsWith("block_definitions:")) {
                            String[] split = line.split(":");
                            blocksDef = split[1];
                        }
                        if (line.startsWith("blocks_start_x:")) {
                            Pattern patt = Pattern.compile("[0-9]+");
                            blocksX = getIntOfField(line, patt);
                        }
                        if (line.startsWith("blocks_start_y:")) {
                            Pattern patt = Pattern.compile("[0-9]+");
                            blocksY = getIntOfField(line, patt);
                        }
                        if (line.startsWith("row_height:")) {
                            Pattern patt = Pattern.compile("[0-9]+");
                            rowHeight = getIntOfField(line, patt);
                        }
                        if (line.startsWith("num_blocks:")) {
                            Pattern patt = Pattern.compile("[0-9]+");
                            numBlocks = getIntOfField(line, patt);
                        }
                        if (line.startsWith("START_BLOCKS")) {
                            line = read.readLine();
                            Reader reader2 = null;
                            try {
                                //reader2 = new FileReader(new File(blocksDef));
                                InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(blocksDef);
                                reader2 = new InputStreamReader(is);
                                BlockDefinitionReader bRead = new BlockDefinitionReader();
                                blkFactory = bRead.fromReader(reader2);
                            } catch (RuntimeException e) {
                                e.printStackTrace();
                            }
                            while (!line.startsWith("END_BLOCKS")) {
                                Pattern patt = Pattern.compile("(.)");
                                Matcher match = patt.matcher(line);
                                int x = blocksX;
                                double newX;
                                while (match.find()) {
                                    if (blkFactory.isBlockSymbol((line.substring(match.start(), match.end())))) {
                                        level.setBlocks(blkFactory.getBlock(line.substring(match.start(), match.end()
                                        ), x, blocksY));
                                        newX = blkFactory.getBlock(line.substring(match.start(), match.end()), x,
                                                blocksY).getCollisionRectangle().getWidth();
                                        x += newX;
                                    }
                                    if (blkFactory.isSpaceSymbol(line.substring(match.start(), match.end()))) {
                                        x += blkFactory.getSpaceWidth(line.substring(match.start(), match.end()));
                                    }
                                }
                                blocksY += rowHeight;
                                line = read.readLine();
                            }
                        }
                    }
                    if (numBlocks == -1 || rowHeight == -1 || blocksX == -1 || blocksY == -1 || name == null
                            || background == null || vList.isEmpty() || level.paddleSpeed() == -1
                            || level.paddleWidth() == -1 || blocksDef == null) {
                        throw new RuntimeException();
                    }
                    level.blocksToRemove.increase(numBlocks);
                    levels.add(level);
                }
            }
            return levels;
        } finally {
            return levels;
        }
    }

    /**
     * Number of balls int.
     *
     * @return the int
     */
    @Override
    public int numberOfBalls() {
        return this.balls.getValue();
    }

    /**
     * Sets velos.
     *
     * @param l the list
     */
    public void setVelos(List l) {
        for (int i = 0; i < l.size(); i += 2) {
            Velocity v = new Velocity().fromAngleAndSpeed((int) l.get(i), (int) l.get(i + 1));
            this.vList.add(v);
            this.balls.increase(1);
        }
    }

    /**
     * Initial ball velocities list.
     *
     * @return the list
     */
    @Override
    public List<Velocity> initialBallVelocities() {
        return this.vList;
    }

    /**
     * Paddle speed int.
     *
     * @return the int
     */
    @Override
    public int paddleSpeed() {
        return this.paddleSpeed;
    }

    /**
     * Sets paddle speed.
     *
     * @param num the num
     */
    public void setPaddleSpeed(int num) {
        this.paddleSpeed = num;
    }

    /**
     * Paddle width int.
     *
     * @return the int
     */
    @Override
    public int paddleWidth() {
        return this.paddleWidth;
    }

    /**
     * Sets paddle width.
     *
     * @param num the num
     */
    public void setPaddleWidth(int num) {
        this.paddleWidth = num;
    }

    /**
     * Level name string.
     *
     * @return the string
     */
    @Override
    public String levelName() {
        return this.name;
    }

    /**
     * Sets name.
     *
     * @param n the n
     */
    public void setName(String n) {
        this.name = n;
    }

    /**
     * Gets background.
     *
     * @return the background
     */
    @Override
    public Sprite getBackground() {
        return this.background;
    }

    /**
     * Sets background.
     *
     * @param back the back
     */
    public void setBackground(Sprite back) {
        this.background = back;
    }

    /**
     * Blocks list.
     *
     * @return the list
     */
    @Override
    public List<Block> blocks() {
        return this.list;
    }

    /**
     * Sets blocks.
     *
     * @param b the b
     */
    public void setBlocks(Block b) {
        this.list.add(b);
    }

    /**
     * Number of blocks to remove int.
     *
     * @return the int
     */
    @Override
    public int numberOfBlocksToRemove() {
        return this.blocksToRemove.getValue();
    }

    /**
     * Gets velocities.
     *
     * @param line the line
     * @return the velocities
     */
    public static List getVelocities(String line) {
        List vList = new ArrayList();
        Pattern patt1 = Pattern.compile("-*\\d*,\\d*");
        Matcher matcher1 = patt1.matcher(line);
        while (matcher1.find()) {
            Pattern patt2 = Pattern.compile("\\d+|-+\\d+");
            Matcher matcher2 = patt2.matcher(line.substring(matcher1.start(), matcher1.end()));
            while (matcher2.find()) {
                vList.add(Integer.parseInt(line.substring(matcher1.start(),
                        matcher1.end()).substring(matcher2.start(), matcher2.end())));
            }
        }
        return vList;
    }

    /**
     * Gets int of field.
     *
     * @param line the line
     * @param patt the patt
     * @return the int of field
     */
    public static int getIntOfField(String line, Pattern patt) {
        Matcher match = patt.matcher(line);
        int speed = 0;
        if (match.find()) {
            speed = Integer.parseInt(line.substring(match.start(), match.end()));
            return speed;
        }
        return -1;
    }

    /**
     * Colored sprite sprite.
     *
     * @param finalColor the final color
     * @return the sprite
     */
    public static Sprite coloredSprite(Color finalColor) {
        return new Sprite() {
            @Override
            public void drawOn(DrawSurface d) {
                d.setColor(finalColor);
                d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
                d.setColor(Color.DARK_GRAY);
                //d.fillRectangle(0, 20, 800, 25);
                d.setColor(Color.DARK_GRAY);
                //d.fillRectangle(0, 45, 25, 600 - 25);
                d.setColor(Color.DARK_GRAY);
                //d.fillRectangle(800 - 25, 45, 25, 600 - 25);
            }

            @Override
            public void timePassed(double dt) {
            }
        };
    }

    /**
     * Color from rgb color.
     *
     * @param line the line
     * @return the color
     */
    public static Color colorFromRGB(String line) {
        Pattern patt = Pattern.compile("\\(.*\\)");
        Matcher match = patt.matcher(line);
        Color color = null;
        ColorParser parser = new ColorParser();
        int n1 = 0;
        int n2 = 0;
        int n3 = 0;
        while (match.find()) {
            Pattern patt1 = Pattern.compile("[a-zA-Z]+");
            Matcher match1 = patt1.matcher(line.substring(match.start() + 1, match.end() - 1));
            if (match1.find()) {
                color = parser.colorFromString(line.substring(match.start() + 1, match.end() - 1));
            }
            Pattern patt2 = Pattern.compile("[0-9]+\\,[0-9]+\\,[0-9]+");
            Matcher match2 = patt2.matcher(line.substring(match.start() + 1, match.end() - 1));
            if (match2.find()) {
                Pattern patt3 = Pattern.compile("[0-9]+");
                Matcher match3 = patt3.matcher(line.substring(match.start() + 1, match.end() - 1).
                        substring(
                                match2.start(), match2.end()));
                int i = 1;
                while (match3.find()) {
                    if (i == 1) {
                        n1 = Integer.parseInt(line.substring(match.start() + 1, match.end() - 1).
                                substring(
                                        match2.start(), match2.end()).substring(match3.start(),
                                match3.end()));
                    }
                    if (i == 2) {
                        n2 = Integer.parseInt(line.substring(match.start() + 1, match.end() - 1).
                                substring(
                                        match2.start(), match2.end()).substring(match3.start(),
                                match3.end()));
                    }
                    if (i == 3) {
                        n3 = Integer.parseInt(line.substring(match.start() + 1, match.end() - 1).
                                substring(
                                        match2.start(), match2.end()).substring(match3.start(),
                                match3.end()));
                        color = new Color(n1, n2, n3);
                    }
                    i++;
                }
            }
        }
        return color;
    }
}
