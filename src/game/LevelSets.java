package game;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Level sets.
 */
public class LevelSets {
    private List<LevelSet> list = new ArrayList();

    /**
     * From reader level sets.
     *
     * @param reader the reader
     * @return the level sets
     * @throws IOException the io exception
     */
    public static LevelSets fromReader(Reader reader) throws IOException {
        LevelSets result = new LevelSets();
        LevelSets.LevelSet set = null;
        LineNumberReader lineReader = null;


        try {
            lineReader = new LineNumberReader(reader);
            String line = null;

            while ((line = lineReader.readLine()) != null) {
                if (lineReader.getLineNumber() % 2 == 0) {
                    set.setLevelDefinitionPath(line.trim());
                    result.addLevelSet(set);
                    set = null;
                } else {
                    set = new LevelSets.LevelSet();
                    String[] splitted = line.split(":");
                    set.setKey(splitted[0]);
                    set.setMessage(splitted[1]);
                }
            }
        } finally {
            if (lineReader != null) {
                lineReader.close();
            }

        }

        return result;
    }

    /**
     * Instantiates a new Level sets.
     */
    public LevelSets() {
    }

    /**
     * Add level set.
     *
     * @param levelSet the level set
     */
    public void addLevelSet(LevelSets.LevelSet levelSet) {
        this.list.add(levelSet);
    }

    /**
     * Gets level set list.
     *
     * @return the level set list
     */
    public List<LevelSets.LevelSet> getLevelSetList() {
        return this.list;
    }

    /**
     * The type Level set.
     */
    public static class LevelSet {
        private String message;
        private String key;
        private String levelDefinitionPath;

        /**
         * Instantiates a new Level set.
         */
        public LevelSet() {
        }

        /**
         * Sets message.
         *
         * @param m the message
         */
        public void setMessage(String m) {
            this.message = m;
        }

        /**
         * Sets key.
         *
         * @param k the key
         */
        public void setKey(String k) {
            this.key = k;
        }

        /**
         * Sets level definition path.
         *
         * @param ld the level definition path
         */
        public void setLevelDefinitionPath(String ld) {
            this.levelDefinitionPath = ld;
        }

        /**
         * Gets message.
         *
         * @return the message
         */
        public String getMessage() {
            return this.message;
        }

        /**
         * Gets key.
         *
         * @return the key
         */
        public String getKey() {
            return this.key;
        }

        /**
         * Gets level definition path.
         *
         * @return the level definition path
         */
        public String getLevelDefinitionPath() {
            return this.levelDefinitionPath;
        }
    }
}