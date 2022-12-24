public class Ball {
    // Constants for the ball's size and speed
    public static final int BALL_SIZE = 10;
    public static int BALL_SPEED = 5;

    // Variables to store the ball's current position and velocity
    private int x, y;
    private int velocityX, velocityY;

    public Ball() {
        // Set the initial position and velocity of the ball
        x = 0;
        y = 0;
        velocityX = BALL_SPEED;
        velocityY = BALL_SPEED;
    }

    public void move() {
        // Update the ball's position based on its velocity
        x += velocityX;
        y += velocityY;
    }
}