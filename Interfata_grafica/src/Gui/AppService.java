package Gui;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AppService {
    private static String currentUsername;
    private static String currentPassword;

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
                currentPassword = password;
                currentUsername = username;
                ret = rs.getInt("rank");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(ret);
        return ret;
    }

    public static String getCurrentUsername() {
        return currentUsername;
    }

    public static String getCurrentPassword() {
        return currentPassword;
    }

    public static void failedLogin(JFrame frame) {
        JOptionPane.showMessageDialog(frame , "Eroare la logare" , "Login error" ,
                JOptionPane.INFORMATION_MESSAGE);
    }

}















