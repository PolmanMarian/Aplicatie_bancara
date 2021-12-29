package Gui;

import UserServices.ClientService;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.util.EventObject;

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


    ///conturi bancare
    private JPanel conturi;
    private JPanel vizualizareConturi;
    private JPanel butoaneCont;
    private JButton solicitareCardBancarButton;
    private JButton deschidereContButton;
    private JButton lichidareContButton;
    private JTable tranzactii;
    private JTable tabelConturi;
    private JScrollPane scrollConturi;

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

        tabelConturi = new JTable(ClientService.dataModelConturiBancare());
        tabelConturi.setShowGrid(true);
        tabelConturi.setShowVerticalLines(true);
        scrollConturi = new JScrollPane(tabelConturi);
        vizualizareConturi.setLayout(new BoxLayout(vizualizareConturi , BoxLayout.LINE_AXIS));
        vizualizareConturi.add(scrollConturi);

        logutButton.addActionListener( e -> {
            Main.currentFrame.setVisible(false);
            Main.currentFrame.dispose();
            Main.changeCurrentFrame(new MainFrame("Aplicatie Bancara"));
        });

        solicitareCardBancarButton.addActionListener(e -> {
            ClientService.solicitareCardPopUpMenu(Main.currentFrame);
        });

        deschidereContButton.addActionListener(e -> {
            if (ClientService.permisiuneDeschidereCont()) {
                AppService.gengericPopUp(Main.currentFrame , "Se poate deschide un cont nou");
            }
            else {
                AppService.gengericPopUp(Main.currentFrame , "NU se poat deschide un alt cont");
            }
        });
    }
}
