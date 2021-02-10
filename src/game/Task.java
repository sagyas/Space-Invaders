package game;

/**
 * The interface Task.
 *
 * @param <T> the type parameter
 */
public interface Task<T> {
    /**
     * Run t.
     *
     * @return the t
     */
    T run();
}
