import javax.swing.*;

public class MainFrame extends JFrame {
    private JPasswordField userName;
    private JPasswordField password;
    private JButton loginButton;
    private JButton resetButton;
    private JPanel mainPanel;
    private JTextField userNameTextField;
    private JTextField passwordTextField;

    public MainFrame(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1080 , 720);
        this.setContentPane(mainPanel);
        this.userNameTextField.setText("Username");
        this.passwordTextField.setText("Password");
        this.userName.setEchoChar((char)0);
        this.pack();

        resetButton.addActionListener(e -> {
            userName.setText("");
            password.setText("");
        });

        loginButton.addActionListener(e -> {
            String usern = String.valueOf(userName.getPassword());
            String passw = String.valueOf(password.getPassword());
            boolean validate = AppService.checkAccount(usern , passw);
            if (!validate) {
                AppService.failedLogin(Main.currentFrame);
            } else {
                Main.currentFrame.setVisible(false);
                Main.currentFrame.dispose();
                Main.changeCurrentFrame(new AngajatFrame("Angajat"));
            }
        });
    }
}
