import javax.swing.*;

public class MainGui {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Aplicatie Bancara");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1080, 720);
        JButton loginButton = new JButton("Login");
        frame.getContentPane().add(loginButton);
        frame.setVisible(true);
    }
}
