import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Graphics;

public class WindowPanel extends JPanel implements ActionListener {
    public WindowPanel() {}

    public void startGame() {}

    public void paintComponent(Graphics g) {}

    public void draw(Graphics g) {}

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
