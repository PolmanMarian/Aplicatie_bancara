package UserServices;

import Containers.PersonalData;
import Gui.Main;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ClientService {
    public static PersonalData personalData = new PersonalData();

    public static void getPersonalData(String username , String password) {
        String statement = "select * from users where `username` = '" + username + "' and `password` = '" + password + "';";
        try {
            System.out.println(username + password);
            PreparedStatement ps = Main.c.prepareStatement(statement);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                personalData.lastName = rs.getString("nume");
                personalData.firstName = rs.getString("prenume");
                personalData.adress = rs.getString("adresa");
                personalData.contractNumber = rs.getInt("numar_de_contract");
                personalData.cnp = rs.getString("cnp");
                personalData.phoneNumber = rs.getString("numar_de_telefon");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void printData() {
        System.out.println(personalData.toString());
    }

    public static boolean permnisiuneSolicitareCardBancar(String Iban) {
        /// de determinat cand un client are dreptul de a solicita un card fizic
        /// de realizat selectia contului a carui card fizic trebuie emis
        /// de facut inserarea in tabel care atesta ca persoana a solicitat deja cardul bancar

        String statement = "select * from `solicitari_card` where `iban` = " + "'" + Iban + "'";
        try {
            PreparedStatement check = Main.c.prepareStatement(statement);
            ResultSet rs = check.executeQuery();
            return !rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean permisiuneDeschidereCont() {
        /// verificare daca clientul are deja 5 conturi
        String SQL = "CALL getAccountCount(?)";
        int ret = -1;
        ResultSet rs;
        try {
            CallableStatement cnt = Main.c.prepareCall(SQL);
            cnt.setString(1 , personalData.cnp);
            rs = cnt.executeQuery();
            if(rs.next()) {
                ret = rs.getInt(1);
            }
            if(ret < 5) return true;
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void deschidereCont() {
        /// 1) verificare solicitare cont
        /// 2) generarea unui iban nou
        /// 3) inregistrarea ibanului in tabela
    }

    public static void inchidereDepozit() {
        /// 1) Menu selectie depozit pentru inchidere
        /// 2) Executarea stergerii
    }

    public static void solicitareCardPopUpMenu(JFrame frame , boolean ok) {
        if(ok) {
            JOptionPane.showMessageDialog(frame , "Solicitrea a fost acceptata" , "Solicitare cont bancar" ,
                    JOptionPane.INFORMATION_MESSAGE);
        }
        else {
            JOptionPane.showMessageDialog(frame , "Solicitare a fost refuzata" , "Solicitare cont bancar" ,
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static DefaultTableModel dataModelConturiBancare() {
        String SQL = "CALL getAccountData(?)";
        ResultSet rs;
        List<String> ret = new ArrayList<>();

        try {
            CallableStatement collect = Main.c.prepareCall(SQL);
            collect.setString(1 , personalData.cnp);
            rs = collect.executeQuery();
            while(rs.next()) {
                ret.add(rs.getString(1));
                ret.add(rs.getString(2));
                String ceva = rs.getString(3);
                if(ceva.equals("1")) {
                    ret.add("Cont de economii");
                } else {
                    ret.add("Cont curent");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        final int size = ret.size();
        System.out.println(size);
        String [][]data = new String[size][3];
        String cols[] = {"suma" , "IBAN" , "economii"};
        for (int i = 0 ; i < size ; i += 3) {
            data[i / 3][0] = ret.get(i);
            data[i / 3][1] = ret.get(i + 1);
            data[i / 3][2] = ret.get(i + 2);
        }

        DefaultTableModel model = new DefaultTableModel(data , cols);
        return model;
    }

    public static String nextIban() {
        String sequence = "";
        for (int i = 0 ; i < 2 ; i++) {
            Random r = new Random();
            sequence += (char)(r.nextInt(26) + 'A');
        }
        for (int i = 0 ; i < 2 ; i++) {
            Random r = new Random();
            sequence += (char)(r.nextInt(9) + '0');
        }
        sequence += " ";
        for (int step = 0 ; step < 4 ; step++) {
            for (int i = 0 ; i < 4 ; i++) {
                Random r = new Random();
                sequence += (char)(r.nextInt(9) + '0');
            }
            sequence += " ";
        }
        return sequence;
    }

    public static boolean ibanExists(String iban) {
        String SQL = "call existsIban(?)";
        try {
            CallableStatement exists = Main.c.prepareCall(SQL);
            exists.setString(1  , iban);
            ResultSet rs = exists.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static String generateIban() {
        String new_iban = null;
        do {
            new_iban = nextIban();
        }while (ibanExists(new_iban));
        return new_iban;
    }

}