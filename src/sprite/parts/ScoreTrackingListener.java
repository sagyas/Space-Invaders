package sprite.parts;

import collision.parts.Block;
import hit.Counter;
import hit.HitListener;

/**
 * score tracking listerner.
 */
public class ScoreTrackingListener implements HitListener {
    private Counter currentScore;

    /**
     * Instantiates a new Score tracking listener.
     *
     * @param scoreCounter the score counter
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    /**
     * hit event.
     *
     * @param beingHit the block
     * @param hitter   the ball
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        if (beingHit.getHitPoints().matches("X") && beingHit.isEnemy() && !hitter.isFromEnemy()) {
            currentScore.increase(100);
        }
        //currentScore.increase(5);
    }
}
