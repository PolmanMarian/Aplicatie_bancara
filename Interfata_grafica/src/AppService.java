import javax.swing.*;

public class AppService {
    public static boolean checkAccount(String username , String password) {
        return true;
    }

    public static void failedLogin(JFrame frame) {
        JDialog d = new JDialog(frame , "Failed Login");
        JLabel l = new JLabel("Error");
        d.add(l);
        d.setSize(100 , 100);
        d.setVisible(true);
    }
}
