package Gui;

import UserServices.AdminService;
import UserServices.ClientService;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.sql.CallableStatement;
import java.sql.SQLException;

public class AdminFrame extends JFrame {
    private JTabbedPane administrare; /// aici se vor executa diferite actiuni
    private JPanel panel1;
    private JButton logoutButton;
    private JPanel transferuri;
    private JPanel displayPanel;
    private JPanel searchBar;
    private JPanel tabelulAngajati;
    private JPanel searchBar2;
    private JTextField textField1;
    private JTable operatiuni;
    private final JScrollPane scrollPane;
    private final JTextField searchText;

    private JTable angajatiTabel;
    private JScrollPane angajatiScroll;

    public AdminFrame(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(panel1);
        this.pack();

        String SQL_3 = "CALL getAllTransfer";
        CallableStatement getTransfer = null;
        try {
            getTransfer = Main.c.prepareCall(SQL_3);
//            getTransfer.setString(1 , ClientService.personalData.lastName);
//            getTransfer.setString(2 , ClientService.personalData.firstName);
            String[] colsTransfer = {"Data" , "Suma" , "Iban plecare" , "Iban destinatie" , "Nume destinatar"};
            var tableModelTransfer = AppService.getGenericDataModel(getTransfer , colsTransfer);
            operatiuni = new JTable(tableModelTransfer);
            operatiuni.setShowGrid(true);
            operatiuni.setShowVerticalLines(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        operatiuni = new JTable();
//        operatiuni.setShowGrid(true);
//        operatiuni.setShowVerticalLines(true);
        String [] cols = {"iban_cont_plecare" , "iban_cont_viraj" , "numele_titularului" , "id" , "status"};


        /// gestionarea angajatilor
        String SQL_4 = "call getAllAngajati";
        try {
            CallableStatement sA = Main.c.prepareCall(SQL_4);
            String[] atributeAngajati = {"norma" , "salariu" , "sucursala"};
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
                System.out.println(ceva);
                String SQL = "update transferuri_bancare set " + cols[col] + "= '"+ ceva +"' where id = " + operatiuni.getValueAt(row , 3) +";";
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
            String statement = "select data, suma, iban_cont_plecare, iban_cont_viraj, numele_virant, status from transferuri_bancare " +
                    "where concat(data , suma , iban_cont_plecare , iban_cont_viraj , numele_titularului , id , status) like '%"+ criteriu +"%';";
            String[] colsTransfer = {"Data" , "Suma" , "Iban plecare" , "Iban destinatie" , "Nume destinatar"};
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
    }
}
