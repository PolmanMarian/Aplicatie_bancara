import javax.swing.*;

public class Main {
    public static JFrame currentFrame;
    public static void changeCurrentFrame(JFrame newFrame){
        currentFrame = newFrame;
        currentFrame.setVisible(true);
    }

    public static void main(String ... argc) {
        changeCurrentFrame(new MainFrame("Aplicatie Bancara"));
    }
}
