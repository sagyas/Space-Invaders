package game;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The type High scores table.
 */
public class HighScoresTable implements Serializable {
    private List<ScoreInfo> list;
    private int max;

    /**
     * Instantiates a new High scores table.
     *
     * @param size the size
     */
// Create an empty high-scores table with the specified size.
    // The size means that the table holds up to size top scores.
    public HighScoresTable(int size) {
        this.list = new ArrayList<ScoreInfo>(size);
        this.max = size;
    }

    /**
     * Add.
     *
     * @param score the score
     */
// Add a high-score.
    public void add(ScoreInfo score) {
        if (this.list.isEmpty()) {
            this.list.add(score);
            return;
        }
        int rank = this.getRank(score.getScore());

        if (this.size() < this.max) {
            this.list.add(score);
            if (this.size() > 1) {
                this.bubbleSort();
            }
        } else {
            if (rank == 1 || rank == 0) {
                this.list.add(score);
                this.bubbleSort();
                this.list.remove(this.max);
            } else {
                return;
            }
        }
    }

    /**
     * Size int.
     *
     * @return the int
     */
// Return table size.
    public int size() {
        return this.list.size();
    }

    /**
     * Gets high scores.
     *
     * @return the high scores
     */
// Return the current high scores.
    // The list is sorted such that the highest
    // scores come first.
    public List<ScoreInfo> getHighScores() {
        return this.list;
    }

    /**
     * Gets rank.
     *
     * @param score the score
     * @return the rank
     */
// return the rank of the current score: where will it
    // be on the list if added?
    // Rank 1 means the score will be highest on the list.
    // Rank `size` means the score will be lowest.
    // Rank > `size` means the score is too low and will not
    //      be added to the list.
    public int getRank(int score) {
        if (this.list.isEmpty()) {
            return 1;
        }
        if (!this.list.isEmpty()) {
            if (score > this.list.get(0).getScore()) {
                return 1;
            }
            if (score < this.list.get(this.list.size() - 1).getScore()) {
                if (this.size() == this.max) {
                    return this.max + 1;
                } else {
                    return this.size();
                }
            }
        }
        return 0;
    }

    /**
     * Clear.
     */
// Clears the table
    public void clear() {
        this.list.removeAll(this.list);
    }

    /**
     * Load.
     *
     * @param filename the filename
     * @throws IOException the io exception
     */
// Load table data from file.
    // Current table data is cleared.
    public void load(File filename) throws IOException {
        this.clear();

        ObjectInputStream objIn = null;
        HighScoresTable temp;

        try {
            objIn = new ObjectInputStream(new FileInputStream(filename));
            temp = (HighScoresTable) objIn.readObject();
            this.list = temp.getHighScores();
            //this.max = temp.size();
        } catch (IOException e) {
            throw e;
        } catch (ClassNotFoundException e) {
            System.err.println(("cant find class in the file"));
            System.out.println(e.getMessage());
        } finally {
            try {
                if (objIn != null) {
                    objIn.close();
                }
            } catch (IOException e) {
                throw e;
            }
        }
        System.out.println("done loading");
    }

    /**
     * Save.
     *
     * @param filename the filename
     * @throws IOException the io exception
     */
// Save table data to the specified file.
    public void save(File filename) throws IOException {
        ObjectOutputStream objOut = null;
        try {
            objOut = new ObjectOutputStream(new FileOutputStream(filename));
            objOut.writeObject(this);
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                if (objOut != null) {
                    objOut.close();
                }
            } catch (IOException e) {
                throw e;
            }
        }
    }

    /**
     * Load from file high scores table.
     *
     * @param filename the filename
     * @return the high scores table
     */
// Read a table from file and return it.
    // If the file does not exist, or there is a problem with
    // reading it, an empty table is returned.
    public static HighScoresTable loadFromFile(File filename) {
        return null;
    }

    /**
     * Bubble sort.
     */
    public void bubbleSort() {
        for (int j = 0; j < this.size(); j++) {
            for (int i = 0; i < this.size() - 1; i++) {
                if (this.list.get(i).getScore() < this.list.get(i + 1).getScore()) {
                    ScoreInfo temp = this.list.get(i);
                    this.list.set(i, this.list.get(i + 1));
                    this.list.set(i + 1, temp);
                }
            }
        }
        for (int i = this.size() + 1; i < this.list.size(); i++) {
            this.list.remove(i);
        }
    }
}