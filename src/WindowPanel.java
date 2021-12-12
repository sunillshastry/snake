import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import javax.swing.Timer;

public class WindowPanel extends JPanel implements ActionListener {
    private final static int FRAME_WIDTH = 600;

    private final static int FRAME_HEIGHT = 600;

    public static final int UNIT_SIZE = 25;

    public static final int GAME_UNITS = (WindowPanel.FRAME_WIDTH * WindowPanel.FRAME_HEIGHT) / WindowPanel.UNIT_SIZE;

    public static final int TIMER_DELAY = 75;

    private final int[] X_COORD = new int[WindowPanel.GAME_UNITS];

    private final int[] Y_COORD = new int[WindowPanel.GAME_UNITS];

    public static int bodyParts = 6;

    public int energyConsumed;

    public int energyX;

    public int energyY;

    public char direction = 'R';

    public boolean running = false;

    public Timer gameTimer;

    public Random random;

    public WindowPanel() {
        this.random = new Random();
        this.setPreferredSize(new Dimension(WindowPanel.FRAME_WIDTH, WindowPanel.FRAME_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new CustomKeyAdapter());
    }

    public void startGame() {
        this.newEnergy();
        this.running = true;
        this.gameTimer = new Timer(WindowPanel.TIMER_DELAY, this);
        this.gameTimer.start();
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        this.draw(graphics);
    }

    public void draw(Graphics graphics) {
        final int MAX = WindowPanel.FRAME_HEIGHT / WindowPanel.UNIT_SIZE;
        for(int i = 0; i < MAX; i++) {
            int cord = i * WindowPanel.UNIT_SIZE;
            graphics.drawLine(cord, 0, cord, WindowPanel.FRAME_HEIGHT);
            graphics.drawLine(0, cord, WindowPanel.FRAME_WIDTH, cord);
        }
        graphics.setColor(Color.CYAN);
        graphics.fillOval(this.energyX, this.energyY, WindowPanel.UNIT_SIZE, WindowPanel.UNIT_SIZE);

        for(int i = 0; i < WindowPanel.bodyParts; i++) {
            if(i == 0) {
                graphics.setColor(Color.GREEN);
                graphics.fillRect(this.X_COORD[i], this.Y_COORD[i], WindowPanel.UNIT_SIZE, WindowPanel.UNIT_SIZE);
            } else {
                graphics.setColor(Color.orange);
                graphics.fillRect(this.X_COORD[i], this.Y_COORD[i], WindowPanel.UNIT_SIZE, WindowPanel.UNIT_SIZE);
            }
        }
    }

    public void newEnergy() {
        int energyRangeX = ((int) (WindowPanel.FRAME_WIDTH / WindowPanel.UNIT_SIZE)) * WindowPanel.UNIT_SIZE ;
        int energyRangeY = ((int) (WindowPanel.FRAME_HEIGHT / WindowPanel.UNIT_SIZE)) * WindowPanel.UNIT_SIZE;
        this.energyX = this.random.nextInt(energyRangeX);
        this.energyY = this.random.nextInt(energyRangeY);
    }

    public void move() {
        for(int i = WindowPanel.bodyParts; i > 0; i++) {
            this.X_COORD[i] = this.X_COORD[i - 1];
            this.Y_COORD[i] = this.Y_COORD[i - 1];
        }
        switch(this.direction) {
            case 'U':
                this.Y_COORD[0] = this.Y_COORD[0] - WindowPanel.UNIT_SIZE;
                break;
            case 'D':
                this.Y_COORD[0] = this.Y_COORD[0] + WindowPanel.UNIT_SIZE;
                break;
            case 'L':
                this.X_COORD[0] = this.X_COORD[0] - WindowPanel.UNIT_SIZE;
                break;
            case 'R':
                this.X_COORD[0] = this.X_COORD[0] + WindowPanel.UNIT_SIZE;
                break;
        }
    }

    public void checkEnergy() {}

    public void checkCollision() {}

    public void gameOver(Graphics graphics) {}

    @Override
    public void actionPerformed(ActionEvent event) {}

    public class CustomKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent event) {}
    }
}
