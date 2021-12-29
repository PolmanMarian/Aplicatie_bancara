package Gui;

import UserServices.AdminService;

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
    private JTable operatiuni;
    private JScrollPane scrollPane;
    private JTextField searchText;

    public AdminFrame(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(panel1);
        this.pack();

        operatiuni = new JTable(AdminService.dataModelTransferuriBancare(""));
        operatiuni.setShowGrid(true);
        operatiuni.setShowVerticalLines(true);
        String [] cols = {"iban_cont_plecare" , "iban_cont_viraj" , "numele_titularului" , "id" , "status"};
        operatiuni.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                final int col = e.getColumn();
                final int row = e.getFirstRow();
                String ceva = (String) operatiuni.getValueAt(row , col);
                System.out.println(ceva);
                String SQL = "update transferuri_bancare set " + cols[col] + "= '"+ ceva +"' where id = " +(String) operatiuni.getValueAt(row , 3)+";";
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
//            System.out.println(criteriu);
            String statement = "select * from transferuri_bancare " +
                    "where concat(iban_cont_plecare , iban_cont_viraj , numele_titularului , id , `status`) like '%"+ criteriu +"%';";
//            System.out.println(statement);
            operatiuni.setModel(AdminService.dataModelTransferuriBancare(statement));

        });

        logoutButton.addActionListener( e -> {
            Main.currentFrame.setVisible(false);
            Main.currentFrame.dispose();
            Main.changeCurrentFrame(new MainFrame("Aplicatie Bancara"));
        });
    }
}
