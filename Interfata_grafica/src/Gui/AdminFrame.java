package Gui;

import UserServices.AdminService;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public class AdminFrame extends JFrame {
    private JTabbedPane administrare; /// aici se vor executa diferite actiuni
    private JPanel panel1;
    private JButton logoutButton;
    private JPanel transferuri;
    private JTable operatiuni;
    private JScrollPane scrollPane;

    public AdminFrame(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(panel1);
        this.pack();

        operatiuni = new JTable(AdminService.dataModelTransferuriBancare());
        operatiuni.setShowGrid(true);
        operatiuni.setShowVerticalLines(true);
        //operatiuni.setDefaultEditor(Object.class , null); // nu se pot edita
        operatiuni.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                final int col = e.getColumn();
                final int row = e.getFirstRow();
                String ceva = (String) operatiuni.getValueAt(row , col);
                /// aici trebuie sa vin p procedura de update
                System.out.println(ceva);
            }
        });
        scrollPane = new JScrollPane(operatiuni);
        transferuri.setLayout(new BoxLayout(transferuri , BoxLayout.LINE_AXIS));
        transferuri.add(scrollPane);

        logoutButton.addActionListener( e -> {
            Main.currentFrame.setVisible(false);
            Main.currentFrame.dispose();
            Main.changeCurrentFrame(new MainFrame("Aplicatie Bancara"));
        });
    }
}
