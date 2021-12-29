package UserServices;

import Containers.PersonalData;
import Gui.Main;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientService {
    public static PersonalData personalData = new PersonalData();

    public static void getPersonalData(String username , String password) {
        String statement = "select * from users where `username` = '" + username + "' and `password` = '" + password + "';";
        try {
            System.out.println(username + password);
            PreparedStatement ps = Main.c.prepareStatement(statement);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                personalData.nume = rs.getString("nume");
                personalData.prenume = rs.getString("prenume");
                personalData.adresa = rs.getString("adresa");
                personalData.numarContract = rs.getInt("numar_de_contract");
                personalData.cnp = rs.getString("cnp");
                personalData.numarDeTelefon = rs.getString("numar_de_telefon");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void printData() {
        System.out.println(personalData.toString());
    }

    public static boolean permnisiuneSolicitareCardBancar() {
        /// de determinat cand un client are dreptul de a solicita un card fizic
        return false;
    }

    public static boolean permisiuneDeschidereCont() {
        /// verificare daca clientul are deja 5 conturi
        return true;
    }

    public static DefaultTableModel dataModelConturiBancare() {
        String [][] data = new String[2][2];
        String cols[] = {"ceva" , "altceva"};
        data[0][0] = "ana are mere";
        data[0][1] = "vasile are banane";
        data[1][0] = "sta pe deal";
        data[1][1] = "sta sub deal";
        DefaultTableModel model = new DefaultTableModel(data , cols);
        return model;
    }

    public static void solicitareCardPopUpMenu(JFrame frame) {
        if(permnisiuneSolicitareCardBancar()) {
            JOptionPane.showMessageDialog(frame , "Solicitrea a fost acceptata" , "Solicitare cont bancar" ,
                    JOptionPane.INFORMATION_MESSAGE);
        }
        else {
            JOptionPane.showMessageDialog(frame , "Solicitare a fost refuzata" , "Solicitare cont bancar" ,
                    JOptionPane.ERROR_MESSAGE);
        }
    }

}
