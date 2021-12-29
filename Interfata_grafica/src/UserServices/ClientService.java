package UserServices;

import Containers.PersonalData;
import Gui.AppService;
import Gui.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientService {
    public static PersonalData personalData = new PersonalData();
    public static void getPersonalData(String username , String password) {
        /// user selection
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
}
