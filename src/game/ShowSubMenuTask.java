package game;

import game.animation.Animation;
import game.animation.AnimationRunner;

/**
 * The type Show sub menu task.
 */
public class ShowSubMenuTask implements Task<Void> {
    private AnimationRunner runner;
    private Animation subMenu;

    /**
     * Instantiates a new Show sub menu task.
     *
     * @param runner           the runner
     * @param subMenuAnimation the sub menu animation
     */
    public ShowSubMenuTask(AnimationRunner runner, Animation subMenuAnimation) {
        this.runner = runner;
        this.subMenu = subMenuAnimation;
    }

    /**
     * runs the animation.
     * @return nothing
     */
    public Void run() {
        this.runner.run(this.subMenu);
        return null;
    }
}
