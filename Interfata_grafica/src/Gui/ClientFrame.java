package Gui;

import UserServices.ClientService;
import com.mysql.cj.xdevapi.Client;

import javax.swing.*;
import java.awt.*;

public class ClientFrame extends JFrame{
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JButton logutButton;

    /// vizualizare date personale
    private JPanel personalData;
    private JLabel NumarDeContract;
    private JLabel Adresa;
    private JLabel Nume;
    private JLabel Prenume;
    private JLabel CNP;
    private JLabel NumarDeTelefon;

    public ClientFrame(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(panel1);
        this.pack();

        ClientService.getPersonalData(AppService.getCurrentUsername() , AppService.getCurrentPassword());
        ClientService.printData();
        NumarDeContract.setText(String.valueOf(ClientService.personalData.numarContract));
        Adresa.setText(ClientService.personalData.adresa);
        Nume.setText(ClientService.personalData.nume);
        Prenume.setText(ClientService.personalData.prenume);
        CNP.setText(ClientService.personalData.cnp);
        NumarDeTelefon.setText(ClientService.personalData.numarDeTelefon);


        logutButton.addActionListener( e -> {
            Main.currentFrame.setVisible(false);
            Main.currentFrame.dispose();
            Main.changeCurrentFrame(new MainFrame("Aplicatie Bancara"));
        });
    }
}
