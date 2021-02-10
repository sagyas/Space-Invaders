package hit;

/**
 * The type Counter.
 */
public class Counter {
    private int count = 0;
    // add number to current count.

    /**
     * Increase.
     *
     * @param number the number
     */
    public void increase(int number) {
        this.count += number;
    }

    /**
     * Decrease.
     *
     * @param number the number
     */
// subtract number from current count.
    public void decrease(int number) {
        this.count -= number;
    }

    /**
     * Gets value.
     *
     * @return the value
     */
// get current count.
    public int getValue() {
        return this.count;
    }
}
