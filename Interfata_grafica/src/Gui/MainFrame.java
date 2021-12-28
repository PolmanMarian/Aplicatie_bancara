package Gui;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MainFrame extends JFrame {
    private JPasswordField userName;
    private JPasswordField password;
    private JButton loginButton;
    private JButton resetButton;
    private JPanel mainPanel;

    public MainFrame(String title) {
        super(title);
        JFrame.setDefaultLookAndFeelDecorated(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1080 , 720);
        this.setContentPane(mainPanel);
        this.userName.setEchoChar((char)0);
        this.pack();

        resetButton.addActionListener(e -> {
            userName.setText("");
            password.setText("");
            userName.requestFocus();
        });

        loginButton.addActionListener(e -> {
            String usern = String.valueOf(userName.getPassword());
            String passw = String.valueOf(password.getPassword());
            int validate = AppService.checkAccount(usern , passw);
            switch (validate) {
                case 1: {
                    Main.currentFrame.setVisible(false);
                    Main.currentFrame.dispose();
                    Main.changeCurrentFrame(new ClientFrame("Client"));
                    break;
                }
                case 2: {
                    Main.currentFrame.setVisible(false);
                    Main.currentFrame.dispose();
                    Main.changeCurrentFrame(new AngajatFrame("Angajat"));
                    break;
                }
                case 3: {
                    Main.currentFrame.setVisible(false);
                    Main.currentFrame.dispose();
                    Main.changeCurrentFrame(new AdminFrame("Admin"));
                    break;
                }
                default: {
                    AppService.failedLogin(Main.currentFrame);
                }
            }
        });
        password.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                var pressedKey = e.getKeyChar();
                if (pressedKey == KeyEvent.VK_ENTER) {
                    loginButton.doClick(12);
                }
            }
        });
        userName.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    password.requestFocus();
                }
            }
        });
    }
}
