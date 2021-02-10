package game;

import game.animation.Animation;

/**
 * menu interface.
 * @param <T>
 */
public interface Menu<T> extends Animation {
    /**
     * adds selection.
     * @param key the key
     * @param message the message
     * @param returnVal the return value
     */
    void addSelection(String key, String message, T returnVal);

    /**
     * get status.
     * @return status
     */
    T getStatus();

    /**
     * add sub menu.
     * @param key the key
     * @param message the message
     * @param subMenu the menu
     */
    void addSubMenu(String key, String message, Menu<T> subMenu);
}
