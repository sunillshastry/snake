import javax.swing.JFrame;

public class WindowFrame extends JFrame {
    private static final String FRAME_TITLE = "Snake";

    public WindowFrame() {
        WindowPanel panel = new WindowPanel();
        add(panel);
        setTitle(WindowFrame.FRAME_TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
    }
}
