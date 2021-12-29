package Gui;
import com.mysql.cj.x.protobuf.MysqlxPrepare;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AngajatFrame extends JFrame{
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JButton logoutButton;
    private JTable tranzactiiInAsteptare;
    private JTable DepoziteInAsteptare;
    private JTable vizualizareClienti;
    private JButton saveChangesButton;

    public AngajatFrame(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(panel1);
        this.pack();

        logoutButton.addActionListener( e -> {
            Main.currentFrame.setVisible(false);
            Main.currentFrame.dispose();
            Main.changeCurrentFrame(new MainFrame("Aplicatie Bancara"));
        });

        saveChangesButton.addActionListener(e -> {

        });
    }
}
