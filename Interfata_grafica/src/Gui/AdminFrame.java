package Gui;

import com.mysql.cj.xdevapi.JsonArray;

import javax.sound.sampled.BooleanControl;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.Callable;

public class AdminFrame extends JFrame {
    private JTabbedPane administrare; /// aici se vor executa diferite actiuni
    private JPanel panel1;
    private JButton logoutButton;
    private JPanel transferuri;
    private JPanel displayPanel;
    private JPanel searchBar;
    private JPanel tabelulAngajati;
    private JPanel searchBar2;
    private JTextField searchText2;
    private JButton plataPlebeuButton;
    private JButton shimbatSucursalaButton;
    private JButton button3;
    private JPanel vizualizareUtlizarori;
    private JPanel cereriCarduri;
    private JPanel vizualizareConturi;
    private JPanel taxePane;
    private JPanel depozitePane;
    private JPanel userPane;
    private JTable operatiuni;
    private final JScrollPane scrollPane;
    private final JTextField searchText;

    private JTable angajatiTabel;
    private JScrollPane angajatiScroll;

    private JTable usersTable;
    private JScrollPane userScroll;

    private JTable cereriTable;
    private JScrollPane cereriScroll;

    private JTable conturiTable;
    private JScrollPane conturiSroll;

    private JTable taxeTable;
    private JScrollPane taxeScroll;

    private JTable depoziteTable;
    private JScrollPane depoziteScroll;

    public AdminFrame(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(panel1);
        this.pack();

        try {
            CallableStatement getUsers = Main.c.prepareCall("call getAllUsers");
            String[] cols = {"Nume" , "adresa" , "numar de telefon" };
            DefaultTableModel ceva = AppService.getGenericDataModel(
                    getUsers , cols);
            usersTable = new JTable(ceva);
            usersTable.setShowGrid(true);
            usersTable.setShowVerticalLines(true);
            userScroll = new JScrollPane(usersTable);
            vizualizareUtlizarori.setLayout(new BoxLayout(vizualizareUtlizarori , BoxLayout.LINE_AXIS));
            vizualizareUtlizarori.add(userScroll);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            CallableStatement getCereri = Main.c.prepareCall("call getCereri");
            String[] cols = {"Iban" , "Aprobare admin" , "Aprobare angajat"};
            DefaultTableModel ceva = AppService.getGenericDataModel(getCereri , cols);
            cereriTable = new JTable(ceva);
            cereriTable.setShowGrid(true);
            cereriTable.setShowVerticalLines(true);
            cereriScroll = new JScrollPane(cereriTable);
            cereriCarduri.setLayout(new BoxLayout(cereriCarduri , BoxLayout.LINE_AXIS));
            cereriCarduri.add(cereriScroll);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            CallableStatement getConturi = Main.c.prepareCall("call getAllAcounts");
            String[] cols = {"CNP" , "Nume" , "Iban" , "Suma"};
            DefaultTableModel ceva = AppService.getGenericDataModel(getConturi , cols);
            conturiTable = new JTable(ceva);
            conturiTable.setShowGrid(true);
            conturiTable.setShowVerticalLines(true);
            conturiSroll = new JScrollPane(conturiTable);
            vizualizareConturi.setLayout(new BoxLayout(vizualizareConturi , BoxLayout.LINE_AXIS));
            vizualizareConturi.add(conturiSroll);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            CallableStatement getTaxes = Main.c.prepareCall("call getTaxes");
            String[] cols = {"Id" , "Descriere" , "Valoare"};
            DefaultTableModel ceva = AppService.getGenericDataModel(getTaxes , cols);
            taxeTable = new JTable(ceva);
            taxeTable.setShowGrid(true);
            taxeTable.setShowVerticalLines(true);
            taxeScroll = new JScrollPane(taxeTable);
            taxePane.setLayout(new BoxLayout(taxePane , BoxLayout.LINE_AXIS));
            taxePane.add(taxeScroll);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            CallableStatement getUsers = Main.c.prepareCall("call getAllUsersWithPasswords");
            String[] cols = {"Username" , "Password" , "CNP" , "Nume" , "Prenume" , "Adresa" , "Numar de telefon" , "Numar de contract" , "rank"};
            DefaultTableModel ceva = AppService.getGenericDataModel(getUsers , cols);
            usersTable = new JTable(ceva);
            usersTable.setShowGrid(true);
            usersTable.setShowVerticalLines(true);
            userScroll = new JScrollPane(usersTable);
            userPane.setLayout(new BoxLayout(userPane , BoxLayout.LINE_AXIS));
            userPane.add(userScroll);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        try {
            CallableStatement getDep = Main.c.prepareCall("call getAllDepozite");
            String[] cols = {"id" , "suma" , "status" , "data" , "cnp" , "dobanda"};
            DefaultTableModel ceva = AppService.getGenericDataModel(getDep , cols);
            depoziteTable = new JTable(ceva);
            depoziteTable.setShowGrid(true);
            depoziteTable.setShowVerticalLines(true);
            depoziteScroll = new JScrollPane(depoziteTable);
            depozitePane.setLayout(new BoxLayout(depozitePane , BoxLayout.LINE_AXIS));
            depozitePane.add(depoziteScroll);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        String SQL_3 = "CALL getAllTransfer";
        CallableStatement getTransfer = null;
        try {
            getTransfer = Main.c.prepareCall(SQL_3);
//            getTransfer.setString(1 , ClientService.personalData.lastName);
//            getTransfer.setString(2 , ClientService.personalData.firstName);
            String[] colsTransfer = {"Data" , "Suma" , "Iban plecare" , "Iban destinatie" , "Nume destinatar" , "Status" , "Id"};
            var tableModelTransfer = AppService.getGenericDataModel(getTransfer , colsTransfer);
            operatiuni = new JTable(tableModelTransfer);
            operatiuni.setShowGrid(true);
            operatiuni.setShowVerticalLines(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String [] cols = {"data" , "suma" , "iban_cont_plecare" , "iban_cont_viraj" ,
                "numele_virant" , "status"};


        /// gestionarea angajatilor
        String SQL_4 = "call getAllAngajati";
        try {
            CallableStatement sA = Main.c.prepareCall(SQL_4);
            String[] atributeAngajati = {"nume" , "salariu" , "norma" , "sucursala" , "departament" , "iban salariu"};
            var modelAngajati = AppService.getGenericDataModel(sA , atributeAngajati);
            angajatiTabel = new JTable(modelAngajati);
            angajatiTabel.setShowVerticalLines(true);
            angajatiTabel.setShowGrid(true);
            angajatiScroll = new JScrollPane(angajatiTabel);
            tabelulAngajati.setLayout(new BoxLayout(tabelulAngajati
                    , BoxLayout.LINE_AXIS));
            tabelulAngajati.add(angajatiScroll);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        operatiuni.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                final int col = e.getColumn();
                final int row = e.getFirstRow();
                String ceva = (String) operatiuni.getValueAt(row , col);
                System.out.println(cols[col]);
                String SQL = "update transferuri_bancare set " + cols[col] + "= '"+ ceva +"' where id = " + operatiuni.getValueAt(row , 6) +";";
                System.out.println(SQL);
                CallableStatement update = null;
                try {
                    update = Main.c.prepareCall(SQL);
                    update.executeUpdate();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                System.out.println(ceva);
            }
        });

        scrollPane = new JScrollPane(operatiuni);
        displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.LINE_AXIS));
        displayPanel.add(scrollPane);
        searchText = new JTextField();
        searchBar.setLayout(new BoxLayout(searchBar , BoxLayout.LINE_AXIS));
        searchBar.add(searchText);

        searchText.addActionListener(e -> {
            String criteriu = searchText.getText();
            String statement = "select data, suma, iban_cont_plecare, iban_cont_viraj, numele_virant, status , id from transferuri_bancare " +
                    "where concat(data , suma , iban_cont_plecare , iban_cont_viraj , numele_titularului , id , status) like '%"+ criteriu +"%';";
            String[] colsTransfer = {"Data" , "Suma" , "Iban plecare" , "Iban destinatie" , "Nume destinatar" , "Status" , "Id"};
            try {
                CallableStatement ceva = Main.c.prepareCall(statement);
                operatiuni.setModel(AppService.getGenericDataModel(ceva , colsTransfer));
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        logoutButton.addActionListener( e -> {
            Main.currentFrame.setVisible(false);
            Main.currentFrame.dispose();
            Main.changeCurrentFrame(new MainFrame("Aplicatie Bancara"));
        });

        plataPlebeuButton.addActionListener(e -> {
            int angajatIndex = angajatiTabel.getSelectedRow();
            Integer salar = Integer.parseInt(angajatiTabel.getValueAt(angajatIndex , 1).toString());
            String nume = angajatiTabel.getValueAt(angajatIndex , 0).toString();
            String iban = angajatiTabel.getValueAt(angajatIndex , 5).toString();
            System.out.println(iban);
            try {
                CallableStatement plata = Main.c.prepareCall("call plataSalariu(? , ? , ?)");
                plata.setString(1 , iban);
                plata.setInt(2 , salar);
                plata.setString(3 , nume);
                plata.execute();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        shimbatSucursalaButton.addActionListener(e -> {
            /// 0 -> "Beclean"
            /// 1 -> "Medgidia"
            /// 2 -> "Cluj-Napoca"
            String[] sucursaleOprions = {"Beclean" , "Medgidia" , "ClujNapoca"};
            JComboBox<String> combo = new JComboBox<String>(sucursaleOprions);
            Object selection = JOptionPane.showInputDialog(Main.currentFrame, "Alege sucursala" ,
                    "Input " , JOptionPane.PLAIN_MESSAGE , null , sucursaleOprions , sucursaleOprions[0]);
            System.out.println(selection);
            /// de selectat angajatul si de facut update
        });
    }
}
