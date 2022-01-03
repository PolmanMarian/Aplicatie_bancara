package Gui;

import UserServices.ClientService;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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

    /// selectia contului bancar
    public int selectedRowInAccounts = 0;

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
            String currentIban = tabelConturi.getValueAt(selectedRowInAccounts , 1).toString();
            System.out.println(currentIban);
            ClientService.solicitareCardPopUpMenu(Main.currentFrame , currentIban);
            if (ClientService.permnisiuneSolicitareCardBancar(currentIban)) {

            }
        });

        deschidereContButton.addActionListener(e -> {
            if (ClientService.permisiuneDeschidereCont()) {
                AppService.gengericPopUp(Main.currentFrame , "Se poate deschide un cont nou");
            }
            else {
                AppService.gengericPopUp(Main.currentFrame , "NU se poat deschide un alt cont");
            }
        });

        tabelConturi.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                selectedRowInAccounts = tabelConturi.getSelectedRow();
               // System.out.println(selectedRowInAccounts);
//                System.out.println(tabelConturi.getValueAt(tabelConturi.getSelectedRow() , 1).toString());
            }
        });
    }

    public JTabbedPane getTabbedPane1() {
        return tabbedPane1;
    }

    public void setTabbedPane1(JTabbedPane tabbedPane1) {
        this.tabbedPane1 = tabbedPane1;
    }

    public JPanel getPanel1() {
        return panel1;
    }

    public void setPanel1(JPanel panel1) {
        this.panel1 = panel1;
    }

    public JButton getLogutButton() {
        return logutButton;
    }

    public void setLogutButton(JButton logutButton) {
        this.logutButton = logutButton;
    }

    public JPanel getPersonalData() {
        return personalData;
    }

    public void setPersonalData(JPanel personalData) {
        this.personalData = personalData;
    }

    public JLabel getNumarDeContract() {
        return NumarDeContract;
    }

    public void setNumarDeContract(JLabel numarDeContract) {
        NumarDeContract = numarDeContract;
    }

    public JLabel getAdresa() {
        return Adresa;
    }

    public void setAdresa(JLabel adresa) {
        Adresa = adresa;
    }

    public JLabel getNume() {
        return Nume;
    }

    public void setNume(JLabel nume) {
        Nume = nume;
    }

    public JLabel getPrenume() {
        return Prenume;
    }

    public void setPrenume(JLabel prenume) {
        Prenume = prenume;
    }

    public JLabel getCNP() {
        return CNP;
    }

    public void setCNP(JLabel CNP) {
        this.CNP = CNP;
    }

    public JLabel getNumarDeTelefon() {
        return NumarDeTelefon;
    }

    public void setNumarDeTelefon(JLabel numarDeTelefon) {
        NumarDeTelefon = numarDeTelefon;
    }

    public JPanel getConturi() {
        return conturi;
    }

    public void setConturi(JPanel conturi) {
        this.conturi = conturi;
    }

    public JPanel getVizualizareConturi() {
        return vizualizareConturi;
    }

    public void setVizualizareConturi(JPanel vizualizareConturi) {
        this.vizualizareConturi = vizualizareConturi;
    }

    public JPanel getButoaneCont() {
        return butoaneCont;
    }

    public void setButoaneCont(JPanel butoaneCont) {
        this.butoaneCont = butoaneCont;
    }

    public JButton getSolicitareCardBancarButton() {
        return solicitareCardBancarButton;
    }

    public void setSolicitareCardBancarButton(JButton solicitareCardBancarButton) {
        this.solicitareCardBancarButton = solicitareCardBancarButton;
    }

    public JButton getDeschidereContButton() {
        return deschidereContButton;
    }

    public void setDeschidereContButton(JButton deschidereContButton) {
        this.deschidereContButton = deschidereContButton;
    }

    public JButton getLichidareContButton() {
        return lichidareContButton;
    }

    public void setLichidareContButton(JButton lichidareContButton) {
        this.lichidareContButton = lichidareContButton;
    }

    public JTable getTranzactii() {
        return tranzactii;
    }

    public void setTranzactii(JTable tranzactii) {
        this.tranzactii = tranzactii;
    }

    public JTable getTabelConturi() {
        return tabelConturi;
    }

    public void setTabelConturi(JTable tabelConturi) {
        this.tabelConturi = tabelConturi;
    }

    public JScrollPane getScrollConturi() {
        return scrollConturi;
    }

    public void setScrollConturi(JScrollPane scrollConturi) {
        this.scrollConturi = scrollConturi;
    }
}
