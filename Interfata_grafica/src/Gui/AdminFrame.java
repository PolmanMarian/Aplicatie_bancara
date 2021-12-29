package Gui;

import UserServices.AdminService;

import javax.swing.*;

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
        operatiuni.setDefaultEditor(Object.class , null); // nu se pot edita
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
