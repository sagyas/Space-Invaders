package game.levels;
import collision.parts.Block;
import shapes.Rectangle;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Image;
import java.io.LineNumberReader;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * The type Block definition reader.
 */
public class BlockDefinitionReader {
    private Map<String, BlockCreator> blockCreators = new HashMap<>();
    private Map<String, Integer> spacers = new HashMap<>();

    /**
     * From reader blocks from symbols factory.
     *
     * @param reader the reader
     * @return the blocks from symbols factory
     */
    public static BlocksFromSymbolsFactory fromReader(java.io.Reader reader) {
        Map<String, Object> defaults = new HashMap<>();
        LineNumberReader read;
        BlockDefinitionReader result = new BlockDefinitionReader();
        String line;
        Map<String, Integer> propsI = null;
        Map<String, String> propsS = null;
        Map<Integer, Object> fills = null;
        boolean isNewBlock;

        try {
            read = new LineNumberReader(reader);
            while ((line = read.readLine()) != null) {
                isNewBlock = false;
                String[] splitted;
                if (line.startsWith("#") || line.matches("")) {
                    line = read.readLine();
                }
                if (line.startsWith("bdef ")) {
                    fills = new HashMap<>();
                    propsI = new HashMap<>();
                    propsS = new HashMap<>();
                    isNewBlock = true;
                    getInfo(line, propsS, propsI, fills, result);
                }
                if (line.startsWith("sdef ")) {
                    splitted = line.split(" ");
                    String symbol = null;
                    int width = -1;
                    for (int i = 1; i < splitted.length; i++) {
                        String[] split = splitted[i].split(":");
                        if (split[0].matches("symbol")) {
                            symbol = split[1];
                        }
                        if (split[0].matches("width")) {
                            width = Integer.parseInt(split[1]);
                        }
                    }
                    result.spacers.put(symbol, width);
                }
                if (line.startsWith("default ")) {
                    splitted = line.split(" ");
                    for (int i = 1; i < splitted.length; i++) {
                        String[] split = splitted[i].split(":");
                        if (split[0].matches("symbol")) {
                            defaults.put(split[0], split[1]);
                        }
                        if (split[0].matches("width")) {
                            defaults.put(split[0], split[1]);
                        }
                        if (split[0].matches("height")) {
                            defaults.put(split[0], split[1]);
                        }
                        if (split[0].matches("hit_points")) {
                            defaults.put(split[0], split[1]);
                        }
                        if (split[0].matches("fill") || split[0].matches("fill-1")) {
                            if (split[1].startsWith("color(RGB")) {
                                Color color = result.fromRGB(split[1]);
                                defaults.put("1", color.toString());
                            }
                        }
                        Pattern patt = Pattern.compile("fill-[2-9]+");
                        Matcher match = patt.matcher(split[0]);
                        if (match.find()) {
                            int place = Integer.parseInt(split[0].substring("fill-".length()));
                            if (split[1].startsWith("color(RGB")) {
                                Color color = result.fromRGB(split[1]);
                                defaults.put(Integer.toString(place), color.toString());
                            }
                        }
                        if (split[0].matches("stroke")) {
                            Pattern patt1 = Pattern.compile("color\\([a-zA-Z]+\\)");
                            Matcher match1 = patt1.matcher(split[1]);
                            ColorParser parser = new ColorParser();
                            if (match1.find()) {
                                String s = split[1].substring("color(".length(), split[1].length() - 1);
                                Color color = parser.colorFromString(s);
                                defaults.put("stroke", color);
                            }
                        }
                    }
                }
                if (isNewBlock) {
                    if (!propsS.containsKey("symbol")
                            || (!propsI.containsKey("hit_points") && !defaults.containsKey("hit_points"))
                            || (!propsI.containsKey("height") && !defaults.containsKey("height"))
                            || (!propsI.containsKey("width") && !defaults.containsKey("width"))
                            || (fills.isEmpty() && !defaults.containsKey("fill")
                            && !defaults.containsKey("fill-1"))) {
                        throw new RuntimeException();
                    }
                    Map<Integer, Object> finalFills = fills;
                    Map<String, Integer> finalPropsI = propsI;
                    Map<String, String> finalPropsS = propsS;
                    BlockCreator bc = new BlockCreator() {
                        @Override
                        public Block create(int xpos, int ypos) {
                            int width;
                            int height;
                            if (finalPropsI.containsKey("width")) {
                                width = finalPropsI.get("width");
                            } else {
                                String s = defaults.get("width").toString();
                                width = Integer.parseInt(s);
                            }
                            if (finalPropsI.containsKey("height")) {
                                height = finalPropsI.get("height");
                            } else {
                                String s = defaults.get("height").toString();
                                height = Integer.parseInt(s);
                            }
                            Block b = new Block(new Rectangle(new shapes.Point(xpos, ypos), width, height));
                            for (int i = 1; i <= finalFills.size(); i++) {
                                b.setColor(finalFills.get(i));
                            }
                            if (finalPropsI.containsKey("hit_points")) {
                                b.setHits(Integer.toString(finalPropsI.get("hit_points")));
                            } else {
                                b.setHits((String) defaults.get("hit_points"));
                            }
                            if (!finalPropsS.containsKey("stroke")) {
                                b.setStroke((Color) defaults.get("stroke"));
                            }
                            return b;
                        }
                    };
                    result.blockCreators.put(propsS.get("symbol"), bc);
                    isNewBlock = false;
                }
            }
        } finally {
            return new BlocksFromSymbolsFactory(result.spacers, result.blockCreators);
        }
    }

    /**
     * From rgb color.
     *
     * @param s the s
     * @return the color
     */
    public Color fromRGB(String s) {
        Pattern patt = Pattern.compile("[0-9]+");
        Matcher match = patt.matcher(s);
        List fills = new ArrayList();
        while (match.find()) {
            int n = Integer.parseInt(s.substring(match.start(), match.end()));
            fills.add(n);
        }
        return new Color((int) fills.get(0), (int) fills.get(1), (int) fills.get(2));
    }

    /**
     * Gets info.
     *
     * @param line   the line
     * @param propsS the props s
     * @param propsI the props i
     * @param fills  the fills
     * @param result the result
     */
    public static void getInfo(String line, Map propsS, Map propsI, Map fills, BlockDefinitionReader result) {
        String[] splitted = line.split(" ");
        for (int i = 1; i < splitted.length; i++) {
            String[] split = splitted[i].split(":");
            if (split[0].matches("symbol")) {
                propsS.put(split[0], split[1]);
            }
            if (split[0].matches("width")) {
                propsI.put(split[0], Integer.parseInt(split[1]));
            }
            if (split[0].matches("height")) {
                propsI.put(split[0], Integer.parseInt(split[1]));
            }
            if (split[0].matches("hit_points")) {
                propsI.put(split[0], Integer.parseInt(split[1]));
            }
            if (split[0].matches("fill") || split[0].matches("fill-1")) {
                ColorParser parser = new ColorParser();
                if (split[1].startsWith("color(RGB")) {
                    Color color = result.fromRGB(split[1]);
                    fills.put(1, color);
                } else if (split[1].startsWith("color(")) {
                    String s = split[1].substring("color(".length(), split[1].length() - 1);
                    Color color = parser.colorFromString(s);
                    fills.put(1, color);
                }
                if (split[1].startsWith("image(")) {
                    try {
                        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(split[1].
                                substring("image(".length(), split[1].length() - 1));
                        Image img = ImageIO.read(is);
                        fills.put(1, img);

                    } catch (IOException e) {
                        throw new RuntimeException("cant find image");
                    }
                }
            }
            Pattern patt = Pattern.compile("fill-[2-9]+");
            Matcher match = patt.matcher(split[0]);
            if (match.find()) {
                ColorParser parser = new ColorParser();
                int place = Integer.parseInt(split[0].substring("fill-".length()));
                if (split[1].startsWith("color(RGB")) {
                    Color color = result.fromRGB(split[1]);
                    fills.put(place, color);
                } else if (split[1].startsWith("color(")) {
                    String s = split[1].substring("color(".length(), split[1].length() - 1);
                    Color color = parser.colorFromString(s);
                    fills.put(place, color);
                }
                if (split[1].startsWith("image(")) {
                    try {
                        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(split[1].
                                substring("image(".length(), split[1].length() - 1));
                        Image img = ImageIO.read(is);
                        fills.put(place, img);

                    } catch (IOException e) {
                        throw new RuntimeException("cant find image");
                    }
                }
            }
        }
    }
}