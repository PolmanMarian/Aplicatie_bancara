import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AppService {
    public static int checkAccount(String username , String password) {
        String statement = "select users.`rank` from users where `username` = '" + username + "' and `password` = '" + password + "';";
        int ret = 0; /// user rank
        try {
            PreparedStatement ps = Main.c.prepareStatement(statement);
            ResultSet rs = ps.executeQuery();
            if(!rs.next()) {
                ret = -1;
            }
            else {
                ret = rs.getInt("rank");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(ret);
        return ret;
    }


    public static void failedLogin(JFrame frame) {
        JDialog d = new JDialog(frame , "Failed Login");
        JLabel l = new JLabel("Error");
        d.add(l);
        d.setSize(100 , 100);
        d.setVisible(true);
    }
}
