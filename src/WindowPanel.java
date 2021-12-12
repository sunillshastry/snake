// Import all the required modules/packages
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import javax.swing.Timer;

/**
 * The Java JPanel consisting of the game objects in a formatted manner
 * a panel that imitates the JPanel class while implementing ActionListener.
 */
public class WindowPanel extends JPanel implements ActionListener{
    /** The width of the window screen */
    private static final int FRAME_WIDTH = 1200;

    /** The height of the window screen */
    private static final int FRAME_HEIGHT = 700;

    /** The size of each unit inside a grid-layout structure */
    private static final int UNIT_SIZE = 50;

    /** Total units inside the grid-layout structure of the game */
    private static final int GAME_UNITS = (FRAME_WIDTH * FRAME_HEIGHT) / (UNIT_SIZE * UNIT_SIZE);

    /** The delay for the Timer class, in milliseconds */
    private static final int TIMER_DELAY = 175;

    /** An integer array for recording the X-coordinate values */
    private final int[] X_CORDS = new int[WindowPanel.GAME_UNITS];

    /** An integer array for recording the Y-coordinate values */
    private final int[] Y_CORDS = new int[WindowPanel.GAME_UNITS];

    /** Initial number of body parts for the snake */
    private int bodyParts = 6;

    /** The number of energy bars eaten by the snake */
    private int energyEaten;

    /** The X-axis coordinate value of the energy bar on the grid-system */
    private int energyX;

    /** The Y-axis coordinate value of the energy bar on the grid-system */
    private int energyY;

    /** The direction in which the snake is moving */
    private char direction = 'R';

    /** A boolean value indicating if the snake is moving or not */
    private boolean isMoving = false;

    /** A timer object instantiated using the Timer class from Swing */
    private Timer timer;

    /** A random class object to determine a random position for the energy bar */
    private final Random random;

    /**
     * A public constructor for WindowPanel, sets the basic defaults and starts the game
     * using the beginGame method
     */
    public WindowPanel(){
        this.random = new Random();

        // Size of the panel
        this.setPreferredSize(new Dimension(WindowPanel.FRAME_WIDTH, WindowPanel.FRAME_HEIGHT));

        // Background color
        this.setBackground(new Color(220, 224, 204));
        this.setFocusable(true);
        this.addKeyListener(new CustomKeyAdapter());

        // Start the game
        this.beginGame();
    }

    /**
     * Starts the game. Changes the private attribute 'isMoving' value to true making it run the game
     */
    public void beginGame() {
        this.newEnergyBlock();
        this.isMoving = true;

        // Calculate the total time spent on the game
        this.timer = new Timer(WindowPanel.TIMER_DELAY,this);
        this.timer.start();
    }

    /**
     * Appends/Paints graphical objects onto the panel using the paintComponent method provided by
     * the parent class, using the super function.
     * @param graphics An object of type Graphics. To draw things onto the Panel
     */
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        this.draw(graphics);
    }

    /**
     * Draws and adds objects of type graphic to the window screen. Including the snake's head, body and
     * the energy bar.
     * @param graphics An object of type Graphics. To draw things onto the Panel
     */
    public void draw(Graphics graphics) {
        if(this.isMoving) {
            // Head of the snake
            graphics.setColor(new Color(120, 29, 66));
            graphics.fillOval(this.energyX, this.energyY, WindowPanel.UNIT_SIZE, WindowPanel.UNIT_SIZE);

            // Rest of the body of the snake, in a slightly different colour.
            for(int i = 0; i < this.bodyParts; i++) {
                if(i == 0) {
                    graphics.setColor(new Color(236, 179, 101));
                    graphics.fillRect(this.X_CORDS[i], this.Y_CORDS[i], WindowPanel.UNIT_SIZE, WindowPanel.UNIT_SIZE);
                }
                else {
                    graphics.setColor(new Color(240,210,144));
                    graphics.fillRect(this.X_CORDS[i], this.Y_CORDS[i], WindowPanel.UNIT_SIZE, WindowPanel.UNIT_SIZE);
                }
            }

            // Display the score to the player
            graphics.setColor(new Color(163, 66, 60));
            graphics.setFont( new Font("Roboto Mono", Font.BOLD, 30));
            FontMetrics metrics = getFontMetrics(graphics.getFont());
            graphics.drawString("SCORE: "
                    + this.energyEaten,
                    (WindowPanel.FRAME_WIDTH - metrics.stringWidth("Score: " + this.energyEaten)) / 2,
                    graphics.getFont().getSize());
        }
        else {
            this.gameOver(graphics);
        }
    }

    /**
     * Creates a new energy block at a random position on the window
     */
    public void newEnergyBlock(){
        // New X-axis coordinate value for the energy block
        this.energyX = random.nextInt((int)(WindowPanel.FRAME_WIDTH / UNIT_SIZE)) * UNIT_SIZE;

        // New Y-axis coordinate value for the energy block
        this.energyY = random.nextInt((int)(WindowPanel.FRAME_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
    }

    /**
     * Provides a functionality for the user to move around the window. Adds a form of interaction
     * between the game and the user.
     */
    public void move(){
        for(int i = this.bodyParts; i > 0; i--) {
            this.X_CORDS[i] = this.X_CORDS[i - 1];
            this.Y_CORDS[i] = this.Y_CORDS[i - 1];
        }

        switch(this.direction) {
            case 'U':
                this.Y_CORDS[0] = this.Y_CORDS[0] - WindowPanel.UNIT_SIZE;
                break;
            case 'D':
                this.Y_CORDS[0] = this.Y_CORDS[0] + WindowPanel.UNIT_SIZE;
                break;
            case 'L':
                this.X_CORDS[0] = this.X_CORDS[0] - WindowPanel.UNIT_SIZE;
                break;
            case 'R':
                this.X_CORDS[0] = this.X_CORDS[0] + WindowPanel.UNIT_SIZE;
                break;
        }
    }

    /**
     * Checks if the snake has consumed the energy bar, at which a new bar must be created
     */
    public void checkEnergy() {
        if((this.X_CORDS[0] == this.energyX) && (this.Y_CORDS[0] == this.energyY)) {
            this.bodyParts++;
            this.energyEaten++;
            this.newEnergyBlock();
        }
    }

    /**
     * Checks if the snake has collided with an obstacle
     */
    public void checkCollisions() {
        for(int i = this.bodyParts; i > 0; i--) {
            if((this.X_CORDS[0] == this.X_CORDS[i]) && (this.Y_CORDS[0] == this.Y_CORDS[i])) {
                this.isMoving = false;
            }
        }
        // See if the snake's head touches left border
        if(this.X_CORDS[0] < 0) {
            this.isMoving = false;
        }
        // See if the snake's head touches right border
        if(this.X_CORDS[0] > WindowPanel.FRAME_WIDTH) {
            this.isMoving = false;
        }
        // See if the snake's head touches top border
        if(this.Y_CORDS[0] < 0) {
            this.isMoving = false;
        }
        // See if the snake's head touches bottom border
        if(this.Y_CORDS[0] > WindowPanel.FRAME_HEIGHT) {
            this.isMoving = false;
        }

        // Stop the timer when the game stops
        if(!this.isMoving) {
            this.timer.stop();
        }
    }

    /**
     * Game ends if the snake has collided with itself, or bashed into the walls around.
     * @param graphics An object of type Graphics. To draw things onto the Panel
     */
    public void gameOver(Graphics graphics) {
        // Display the score to the player
        graphics.setColor(new Color(163, 66, 60));
        graphics.setFont(new Font("Roboto Mono", Font.BOLD, 30));
        FontMetrics metrics1 = getFontMetrics(graphics.getFont());
        graphics.drawString("Score: "
                + this.energyEaten,
                (WindowPanel.FRAME_WIDTH - metrics1.stringWidth("Score: "+ this.energyEaten)) / 2,
                graphics.getFont().getSize());

        // Display a "Game Over" text indicating the current status of the game.
        graphics.setColor(new Color(163, 66, 60));
        graphics.setFont( new Font("Roboto Mono",Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(graphics.getFont());
        graphics.drawString("Game Over",
                (WindowPanel.FRAME_WIDTH - metrics2.stringWidth("Game Over")) / 2,
                WindowPanel.FRAME_HEIGHT / 2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(this.isMoving) {
            this.move();
            this.checkEnergy();
            this.checkCollisions();
        }
        repaint();
    }

    public class CustomKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent event) {
            switch(event.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_A:
                    if(direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_D:
                    if(direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                case KeyEvent.VK_W:
                    if(direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_S:
                    if(direction != 'U') {
                        direction = 'D';
                    }
                    break;
            }
        }
    }
}
