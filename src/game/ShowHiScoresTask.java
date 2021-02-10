package game;

import game.animation.Animation;
import game.animation.AnimationRunner;

/**
 * The type Show hi scores task.
 */
public class ShowHiScoresTask implements Task<Void> {
    private AnimationRunner runner;
    private Animation score;

    /**
     * Instantiates a new Show hi scores task.
     *
     * @param runner              the runner
     * @param highScoresAnimation the high scores animation
     */
    public ShowHiScoresTask(AnimationRunner runner, Animation highScoresAnimation) {
        this.runner = runner;
        this.score = highScoresAnimation;
    }

    /**
     * runs the animation.
     * @return nothing
     */
    public Void run() {
        this.runner.run(this.score);
        return null;
    }
}
