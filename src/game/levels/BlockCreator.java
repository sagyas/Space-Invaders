package game.levels;

import collision.parts.Block;

/**
 * creates block.
 */
public interface BlockCreator {
    /**
     * Create a block at the specified location.
     *
     * @param xpos the xpos
     * @param ypos the ypos
     * @return the block
     */
    Block create(int xpos, int ypos);
}
