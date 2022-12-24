public class Paddle {
    // Constants for the paddle's size and movement range
    public static final int PADDLE_WIDTH = 10;
    public static final int PADDLE_HEIGHT = 50;
    public static final int PADDLE_MOVEMENT_RANGE = 100;
    private static final int PADDLE_SPEED = 5;

    // Variables to store the paddle's current position and movement range
    private int x, y;
    private int movementRange;

    public Paddle(int x, int y, int movementRange) {
        // Set the paddle's initial position and movement range
        this.x = x;
        this.y = y;
        this.movementRange = movementRange;
    }

    public void moveUp() {
        // Move the paddle up within its movement range
        y = Math.max(y - PADDLE_SPEED, 0);
    }

    public void moveDown() {
        // Move the paddle down within its movement range
        y = Math.min(y + PADDLE_SPEED, movementRange - PADDLE_HEIGHT);
    }
}
