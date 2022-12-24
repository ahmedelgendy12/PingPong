import java.util.Timer;
import java.util.TimerTask;

public class Powerups {
    // Constants for the power-up duration and effect
    public static final int POWER_UP_DURATION = 10000; // in milliseconds
    public static final int POWER_UP_SPEED_BONUS = 2;

    // Variables to store the power-up effect and duration
    private int effect;
    private int duration;

    public Powerups(int effect, int duration) {
        // Set the power-up effect and duration
        this.effect = effect;
        this.duration = duration;
    }

    public void apply() {
        // Apply the power-up effect to the game
        switch (effect) {
            case POWER_UP_SPEED_BONUS:
                // Increase the ball's speed
                Ball.BALL_SPEED += POWER_UP_SPEED_BONUS;
                break;
            // Add other power-up effects here
        }

        // Start a timer to track the duration of the power-up
        Timer powerUpTimer = new Timer();
        powerUpTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Remove the power-up effect after the duration expires
                switch (effect) {
                    case POWER_UP_SPEED_BONUS:
                        // Decrease the ball's speed
                        Ball.BALL_SPEED -= POWER_UP_SPEED_BONUS;
                        break;
                    // Add other power-up effects here
                }
            }
        }, duration);
    }
}
