package game.animation;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * stop animation by key.
 */
public class KeyPressStoppableAnimation implements Animation {
    private KeyboardSensor keyboard;
    private String key;
    private Animation anim;
    private boolean stop;
    private boolean isAllreadyPressed;

    /**
     * constructor.
     * @param sensor keyboard
     * @param press key
     * @param animation animation
     */
    public KeyPressStoppableAnimation(KeyboardSensor sensor, String press, Animation animation) {
        this.keyboard = sensor;
        this.key = press;
        this.anim = animation;
        this.stop = false;
        this.isAllreadyPressed = true;
    }

    /**
     * stops animation.
     * @return stop
     */
    public boolean shouldStop() {
        return this.stop;
    }

    /**
     * do one frame.
     * @param d the d
     * @param dt speed
     */
    public void doOneFrame(DrawSurface d, double dt) {
        if (keyboard.isPressed(key)) { //&& this.isAllreadyPressed == true){
            this.stop = true;
        } else {
            //this.isAllreadyPressed = false;
            this.anim.doOneFrame(d, dt);
        }
    }


}
