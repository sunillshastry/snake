import javax.swing.JFrame;

public class WindowFrame extends JFrame{
    private static final String FRAME_TITLE = "Snake";

    public WindowFrame() {
        WindowPanel panel = new WindowPanel();
        this.add(panel);
        this.setTitle(WindowFrame.FRAME_TITLE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
