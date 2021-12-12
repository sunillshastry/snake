import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.Timer;

public class WindowPanel extends JPanel implements ActionListener {
    private final static int FRAME_WIDTH = 600;

    private final static int FRAME_HEIGHT = 600;

    public static final int UNIT_SIZE = 25;

    public static final int GAME_UNITS = (WindowPanel.FRAME_WIDTH * WindowPanel.FRAME_HEIGHT) / WindowPanel.UNIT_SIZE;

    public static final int DELAY = 75;

    private final int[] X_COORD = new int[WindowPanel.GAME_UNITS];

    private final int[] Y_COORD = new int[WindowPanel.GAME_UNITS];

    public int bodyParts = 6;

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

    public void startGame() {}

    public void paintComponent(Graphics g) {}

    public void draw(Graphics g) {}

    public void newEnergy() {}

    public void move() {}

    public void checkEnergy() {}

    public void checkCollision() {}

    public void gameOver(Graphics g) {}

    @Override
    public void actionPerformed(ActionEvent event) {}

    public class CustomKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent event) {}
    }
}
