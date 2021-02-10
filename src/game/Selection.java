package game;

/**
 * The type Selection.
 *
 * @param <T> the type parameter
 */
public class Selection<T> {

    private String key;
    private String message;
    private T returnVal;

    /**
     * Instantiates a new Selection.
     *
     * @param k   the k
     * @param m   the m
     * @param val the val
     */
    public Selection(String k, String m, T val) {
        this.key = k;
        this.message = m;
        this.returnVal = val;
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    public T getValue() {
        return this.returnVal;
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
}
