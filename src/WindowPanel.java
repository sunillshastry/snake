import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import javax.swing.Timer;

public class WindowPanel extends JPanel implements ActionListener{
    private static final int FRAME_WIDTH = 1200;

    private static final int FRAME_HEIGHT = 700;

    private static final int UNIT_SIZE = 50;

    private static final int GAME_UNITS = (FRAME_WIDTH * FRAME_HEIGHT) / (UNIT_SIZE * UNIT_SIZE);

    private static final int TIMER_DELAY = 175;

    private final int[] X_CORDS = new int[WindowPanel.GAME_UNITS];

    private final int[] Y_CORDS = new int[WindowPanel.GAME_UNITS];

    private int bodyParts = 6;

    private int energyEaten;

    private int energyX;

    private int energyY;

    private char direction = 'R';

    private boolean isMoving = false;

    private Timer timer;

    private final Random random;

    public WindowPanel(){
        this.random = new Random();
        this.setPreferredSize(new Dimension(WindowPanel.FRAME_WIDTH, WindowPanel.FRAME_HEIGHT));
        this.setBackground(new Color(220, 224, 204));
        this.setFocusable(true);
        this.addKeyListener(new CustomKeyAdapter());
        this.beginGame();
    }

    public void beginGame() {
        this.newEnergyBlock();
        this.isMoving = true;

        this.timer = new Timer(WindowPanel.TIMER_DELAY,this);
        this.timer.start();
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        this.draw(graphics);
    }

    public void draw(Graphics graphics) {
        if(this.isMoving) {
            graphics.setColor(new Color(120, 29, 66));
            graphics.fillOval(this.energyX, this.energyY, WindowPanel.UNIT_SIZE, WindowPanel.UNIT_SIZE);

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

    public void newEnergyBlock(){
        this.energyX = random.nextInt((int)(WindowPanel.FRAME_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        this.energyY = random.nextInt((int)(WindowPanel.FRAME_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
    }

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


    public void checkEnergy() {
        if((this.X_CORDS[0] == this.energyX) && (this.Y_CORDS[0] == this.energyY)) {
            this.bodyParts++;
            this.energyEaten++;
            this.newEnergyBlock();
        }
    }

    public void checkCollisions() {
        for(int i = this.bodyParts; i > 0; i--) {
            if((this.X_CORDS[0] == this.X_CORDS[i]) && (this.Y_CORDS[0] == this.Y_CORDS[i])) {
                this.isMoving = false;
            }
        }
        //check if head touches left border
        if(this.X_CORDS[0] < 0) {
            this.isMoving = false;
        }
        //check if head touches right border
        if(this.X_CORDS[0] > WindowPanel.FRAME_WIDTH) {
            this.isMoving = false;
        }
        //check if head touches top border
        if(this.Y_CORDS[0] < 0) {
            this.isMoving = false;
        }
        //check if head touches bottom border
        if(this.Y_CORDS[0] > WindowPanel.FRAME_HEIGHT) {
            this.isMoving = false;
        }

        if(!this.isMoving) {
            this.timer.stop();
        }
    }

    public void gameOver(Graphics graphics) {
        //Score
        graphics.setColor(new Color(163, 66, 60));
        graphics.setFont(new Font("Roboto Mono", Font.BOLD, 30));
        FontMetrics metrics1 = getFontMetrics(graphics.getFont());
        graphics.drawString("Score: "
                + this.energyEaten,
                (WindowPanel.FRAME_WIDTH - metrics1.stringWidth("Score: "+ this.energyEaten)) / 2,
                graphics.getFont().getSize());

        //Game Over text
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
