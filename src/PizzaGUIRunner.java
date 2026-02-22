import java.awt.*;

public class PizzaGUIRunner {
    public static void main(String[] args) {
        PizzaGUIFrame frame = new PizzaGUIFrame();
        frame.setVisible(true);
        frame.setSize(400, 400);

        Toolkit tk = Toolkit.getDefaultToolkit();

        Dimension screenSize = tk.getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        int frameWidth = (int) (screenWidth * 0.6);
        int frameHeight = (int) (screenHeight * 0.67);

        frame.setSize(frameWidth, frameHeight);
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);

    }
}