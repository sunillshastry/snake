import javax.swing.JFrame;

/**
 * The Java JFrame consisting of the game, a window that imitates the JFrame class.
 */
public class WindowFrame extends JFrame{
    /** The title of the window, i.e the main title for the game */
    private static final String FRAME_TITLE = "Snake";

    /**
     * public constructor for WindowFrame, initiates the JFrame by setting the basic, default values
     */
    public WindowFrame() {
        // Create a new WindowPanel instance inside the constructor
        WindowPanel panel = new WindowPanel();

        // Add the panel and set the defaults
        this.add(panel);
        this.setTitle(WindowFrame.FRAME_TITLE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
