package hit;

import collision.parts.Block;
import game.GameLevel;
import sprite.parts.Ball;

/**
 * The type Block remover.
 */
// a BlockRemover is in charge of removing blocks from the gameLevel, as well as keeping count
// of the number of blocks that remain.
public class BlockRemover implements HitListener {
    private GameLevel gameLevel;
    private Counter remainingBlocks;

    /**
     * Instantiates a new Block remover.
     *
     * @param gameLevel     the game level
     * @param removedBlocks the removed blocks
     */
    public BlockRemover(GameLevel gameLevel, Counter removedBlocks) {
        this.gameLevel = gameLevel;
        this.remainingBlocks = removedBlocks;
    }

    /**
     *      Blocks that are hit and reach 0 hit-points should be removed
     *      from the gameLevel. Remember to remove this listener from the block
     *      that is being removed from the gameLevel.
     * @param beingHit the block
     * @param hitter the ball
     */

    public void hitEvent(Block beingHit, Ball hitter) {
        if (beingHit.isEnemy() && hitter.isFromEnemy()) {
            beingHit.setHits("1");
            return;
        }
        if (beingHit.getHitPoints().equals("X")) {
            beingHit.removeFromGame(gameLevel);
            if (beingHit.isEnemy()) {
                this.remainingBlocks.decrease(1);
            }
        }
    }

    /**
     * Gets counter.
     *
     * @return the counter
     */
    public Counter getCounter() {
        return this.remainingBlocks;
    }
}
