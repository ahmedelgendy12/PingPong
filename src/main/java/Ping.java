import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Ping extends JPanel implements KeyListener {
    private static final long serialVersionUID = 1L;

    // Constants for the game
    private static final int BALL_SIZE = 30;
    private static final int PADDLE_WIDTH = 15;
    private static final int PADDLE_HEIGHT = 80;
    private static final int PADDLE_SPEED = 20;

    // Variables for the game objects
    private int ballX = 0;
    private int ballY = 0;
    private int ballVelocityX = 3;
    private int ballVelocityY = 3;
    private int leftPaddleY = 0;
    private int rightPaddleY = 0;
    private int leftScore = 0;
    private int rightScore = 0;

    // Variables for paddle movement
    private boolean leftPaddleUpPressed = false;
    private boolean leftPaddleDownPressed = false;
    private boolean rightPaddleUpPressed = false;
    private boolean rightPaddleDownPressed = false;



    public void paint(Graphics g) {
        // Clear the screen
        g.setColor(Color.RED);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Draw the ball
        g.setColor(Color.BLACK);
        g.fillOval(ballX, ballY, BALL_SIZE, BALL_SIZE);

        // Draw the paddles
        g.fillRect(0, leftPaddleY, PADDLE_WIDTH, PADDLE_HEIGHT);
        g.fillRect(getWidth() - PADDLE_WIDTH, rightPaddleY, PADDLE_WIDTH, PADDLE_HEIGHT);

        // Draw the scores
        g.setColor(Color.WHITE);
        g.drawString(String.valueOf(leftScore), 100, 100);
        g.drawString(String.valueOf(rightScore), getWidth() - 100, 100);
    }

    public void moveBall() {
        // Update the ball's position based on its velocity
        ballX += ballVelocityX;
        ballY += ballVelocityY;

        // Check for ball collision with the top or bottom of the screen
        if (ballY < 0 || ballY > getHeight() - BALL_SIZE) {
            ballVelocityY *= -1; // Reverse the ball's vertical velocity
        }

        // Check for ball collision with a paddle
        if (ballX < PADDLE_WIDTH && ballY > leftPaddleY && ballY < leftPaddleY + PADDLE_HEIGHT) {
            ballVelocityX *= -1; // Reverse the ball's horizontal velocity
        }
        if (ballX > getWidth() - PADDLE_WIDTH && ballY > rightPaddleY && ballY < rightPaddleY + PADDLE_HEIGHT) {
            ballVelocityX *= -1; // Reverse the ball's horizontal velocity
        }

        // Check for ball scoring
        if (ballX < 0) {
            // Right player scores
            rightScore++;
            resetBall();
        }
        if (ballX > getWidth()) {
            // Left player scores
            leftScore++;
            resetBall();
        }
    }

    public void resetBall() {
        // Reset the ball's position and velocity
        ballX = getWidth() / 2;
        ballY = getHeight() / 2;
        ballVelocityX *= -1;
    }

    public void movePaddles() {
        // Move the left paddle based on user input
        if (leftPaddleUpPressed) {
            leftPaddleY -= PADDLE_SPEED;
        }
        if (leftPaddleDownPressed) {
            leftPaddleY += PADDLE_SPEED;
        }

        // Move the right paddle based on user input
        if (rightPaddleUpPressed) {
            rightPaddleY -= PADDLE_SPEED;
        }
        if (rightPaddleDownPressed) {
            rightPaddleY += PADDLE_SPEED;
        }

        // Ensure the paddles stay within the screen bounds
        if (leftPaddleY < 0) {
            leftPaddleY = 0;
        }
        if (leftPaddleY > getHeight() - PADDLE_HEIGHT) {
            leftPaddleY = getHeight() - PADDLE_HEIGHT;
        }
        if (rightPaddleY < 0) {
            rightPaddleY = 0;
        }
        if (rightPaddleY > getHeight() - PADDLE_HEIGHT) {
            rightPaddleY = getHeight() - PADDLE_HEIGHT;
        }
    }

    public void run() {
        while (true) {
            moveBall();
            movePaddles();
            repaint(); // Repaint the screen

            // Pause the game loop to slow down the game
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Update the pressed key flags
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            rightPaddleUpPressed = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            rightPaddleDownPressed = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_W) {
            leftPaddleUpPressed = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            leftPaddleDownPressed = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char key = e.getKeyChar();
    }

    public static final int RAGE_MODE_BALL_SPEED_MULTIPLIER = 3; // Ball speed is multiplied by this value in rage mode
    public static final int RAGE_MODE_PADDLE_SPEED_MULTIPLIER = 3; // Paddle speed is multiplied by this value in rage mode

    private boolean rageMode = false; // Flag indicating whether rage mode is active

    public void enterRageMode() {
        rageMode = true;
        ballVelocityX *= RAGE_MODE_BALL_SPEED_MULTIPLIER;
        ballVelocityY *= RAGE_MODE_BALL_SPEED_MULTIPLIER;
        setBackground(Color.RED);
    }

    public void exitRageMode() {
        rageMode = false;
        ballVelocityX /= RAGE_MODE_BALL_SPEED_MULTIPLIER;
        ballVelocityY /= RAGE_MODE_BALL_SPEED_MULTIPLIER;
        setBackground(Color.GREEN);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_UP) {
            // Move the left paddle up
            rightPaddleY -= PADDLE_SPEED * (rageMode ? RAGE_MODE_PADDLE_SPEED_MULTIPLIER : 1);
        } else if (keyCode == KeyEvent.VK_DOWN) {
            // Move the left paddle down
            rightPaddleY += PADDLE_SPEED * (rageMode ? RAGE_MODE_PADDLE_SPEED_MULTIPLIER : 1);
        } else if (keyCode == KeyEvent.VK_W) {
            // Move the right paddle up
            leftPaddleY -= PADDLE_SPEED * (rageMode ? RAGE_MODE_PADDLE_SPEED_MULTIPLIER : 1);

        } else if (keyCode == KeyEvent.VK_S) {
            // Move the right paddle down
            leftPaddleY += PADDLE_SPEED * (rageMode ? RAGE_MODE_PADDLE_SPEED_MULTIPLIER : 1);

        } else if (keyCode == KeyEvent.VK_SPACE) {
            // Toggle rage mode
            if (rageMode) {
                exitRageMode();
            } else {
                enterRageMode();
            }
        }
    }


    public static void main(String[] args) {
        // Set up the game window
        JFrame frame = new JFrame("Ping Pong");
        frame.setSize(1440, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        // Add the game panel to the window
        Ping game = new Ping();
        frame.add(game);
        frame.setBackground(Color.GREEN);

        // Show the window
        frame.setVisible(true);
        frame.addKeyListener(game);

        // Start the game loop
        game.run();
    }
@Override
public void paintComponent(Graphics g) {
    super.paintComponent(g);

    // Draw the paddles
    g.setColor(Color.WHITE);
    g.fillRect(0, leftPaddleY, PADDLE_WIDTH, PADDLE_HEIGHT);
    g.fillRect(WIDTH - PADDLE_WIDTH, rightPaddleY, PADDLE_WIDTH, PADDLE_HEIGHT);

    // Draw the ball
    g.fillOval(ballX, ballY, BALL_SIZE, BALL_SIZE);

    // Draw the scores
    g.setColor(Color.WHITE);
    g.drawString(String.valueOf(leftScore), WIDTH / 2 - 50, 50);
    g.drawString(String.valueOf(rightScore), WIDTH / 2 + 25, 50);
}

    private void updatePaddles() {
        // Update the left paddle's position based on key input
        if (leftPaddleUpPressed && leftPaddleY > 0) {
            leftPaddleY -= PADDLE_SPEED;
        }
        if (leftPaddleDownPressed && leftPaddleY < HEIGHT - PADDLE_HEIGHT) {
            leftPaddleY += PADDLE_SPEED;
        }

        // Update the right paddle's position based on key input
        if (rightPaddleUpPressed && rightPaddleY > 0) {
            rightPaddleY -= PADDLE_SPEED;
        }
        if (rightPaddleDownPressed && rightPaddleY < HEIGHT - PADDLE_HEIGHT) {
            rightPaddleY += PADDLE_SPEED;
        }
    }
        // Booleans to track whether the up and down keys are being pressed
        private boolean upPressed = false;
        private boolean downPressed = false;





}

