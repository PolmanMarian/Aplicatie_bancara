import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Handler;

public class Login {
    public static JFrame loginFrame;
    JLabel loginLabel;
    JButton loginButton;
    static Handler handler;

    Login() {
        loginFrame = new JFrame();
        loginFrame.setSize(1080 , 720);
        loginFrame.setResizable(false);
        loginFrame.setTitle("George Login page");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setLayout(null);
        loginFrame.setVisible(true);

        handler = new Handler();
        loginLabel = new JLabel("Bun venit la BCR");
        loginLabel.setBounds(100 , 100 , 100 , 20);
        loginFrame.add(loginLabel);

        loginButton = new JButton("Login");
        loginButton.addActionListener(handler);
        loginFrame.add(loginButton);
    }

    public class Handler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
        //    if(e.getSource() == )
        }
    }
}
