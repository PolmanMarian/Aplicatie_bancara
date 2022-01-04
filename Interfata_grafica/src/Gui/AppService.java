package Gui;

import Containers.RegisterContainer;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public static void mustAcceptTerms(JFrame frame) {
        JOptionPane.showMessageDialog(frame , "Trebuie sa accepti termenii si conditiile" , "" ,
                JOptionPane.INFORMATION_MESSAGE);
    }

    public static void gengericPopUp(JFrame frame , String message) {
        JOptionPane.showMessageDialog(frame , message , "" , JOptionPane.INFORMATION_MESSAGE);
    }

    public static void addNewUser(RegisterContainer reg) {
        /// de terminat de facut inregistrarea cu o procedura
        String statement = "insert ignore into users (username, password, cnp, nume, prenume, adresa, numar_de_telefon) values ('cerqwerq' , 'c' , '6211217044662' , 'George' , 'Dumitru' , 'In deal' , '4673676674');";
        try {
            PreparedStatement add = Main.c.prepareStatement(statement);
            add.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static DefaultTableModel getGenericDataModel(
            CallableStatement SQL_statement,
            String[] cols
    ) {
        ResultSet rs;
        List<String> ret = new ArrayList<>();
        int index = 1;
        final int len = cols.length;
        try {
            rs = SQL_statement.executeQuery();
            while (rs.next()) {
                for (int i = 0 ; i < len ; i++) {
                    ret.add(rs.getString(index + i));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        final int size = ret.size();
        System.out.println("Asta e de unde trebuie" + size);
        String[][] data = new String[size/len][len];
        for (int i = 0 ; i < size ; i += len) {
            for (int j = 0 ; j < len ; j++) {
                data[i/3][j] = ret.get(i + j);
            }
        }

        DefaultTableModel model = new DefaultTableModel(data , cols);
        return model;
    }
}















