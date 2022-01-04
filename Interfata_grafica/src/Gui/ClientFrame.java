package Gui;

import UserServices.ClientService;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.sql.CallableStatement;
import java.sql.SQLException;

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
    private JTable firme;
    private JPanel accountsList;
    private JPanel companyList;
    private JButton sendButton;
    private JTextField contViraj;
    private JTextField contPlecare;
    private JTextField sumaTranzactie;
    private JComboBox comboBox2;
    private JTextField selectedAccount;
    private JTextField toSendIban;
    private JTextField toSend;
    private JTextField toSendName;
    private JButton sendButton1;
    private JButton clearButton;
    private JPanel tranzactiiView;
    private JTable tabelConturi;
    private JScrollPane scrollConturi;
    private JScrollPane scrollTransferuri;
    private JScrollPane scrollFirme;
    private JTable accountsTable;

    /// selectia contului bancar
    public int selectedRowInAccounts = 0;
    public int selectedRowInTranzactii = 0;

    public ClientFrame(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(panel1);
        this.pack();

        ClientService.getPersonalData(AppService.getCurrentUsername() , AppService.getCurrentPassword());
        ClientService.printData();
        NumarDeContract.setText(String.valueOf(ClientService.personalData.contractNumber));
        Adresa.setText(ClientService.personalData.adress);
        Nume.setText(ClientService.personalData.lastName);
        Prenume.setText(ClientService.personalData.firstName);
        CNP.setText(ClientService.personalData.cnp);
        NumarDeTelefon.setText(ClientService.personalData.phoneNumber);


        try {
            String SQL_2 = "CALL getAccountData(?)";
            CallableStatement to_call = Main.c.prepareCall(SQL_2);
            to_call.setString(1 , ClientService.personalData.cnp);
            String[] cols = {"suma" , "IBAN" , "economii"};
            var content = AppService.getGenericDataModel(to_call ,  cols);

            accountsTable = new JTable(content);
            accountsTable.setShowGrid(true);
            accountsTable.setShowVerticalLines(true);
            JScrollPane scrollAccounts = new JScrollPane(accountsTable);
            accountsList.setLayout(new BoxLayout(accountsList, BoxLayout.LINE_AXIS));
            accountsList.add(scrollAccounts);

            tabelConturi = new JTable(content);
            tabelConturi.setShowGrid(true);
            tabelConturi.setShowVerticalLines(true);
            scrollConturi = new JScrollPane(tabelConturi);
            vizualizareConturi.setLayout(new BoxLayout(vizualizareConturi , BoxLayout.LINE_AXIS));
            vizualizareConturi.add(scrollConturi);

            String SQL_3 = "CALL getTransfer(?,?)";
            CallableStatement getTransfer = Main.c.prepareCall(SQL_3);
            getTransfer.setString(1 , ClientService.personalData.lastName);
            getTransfer.setString(2 , ClientService.personalData.firstName);
            String[] colsTransfer = {"Data" , "Suma" , "Iban plecare" , "Iban destinatie" , "Nume destinatar"};
            var tableModelTransfer = AppService.getGenericDataModel(getTransfer , colsTransfer);

            tranzactii = new JTable(tableModelTransfer);
            tranzactii.setShowVerticalLines(true);
            tranzactii.setShowGrid(true);
            scrollTransferuri = new JScrollPane(tranzactii);
            tranzactiiView.setLayout(new BoxLayout(tranzactiiView , BoxLayout.LINE_AXIS));
            tranzactiiView.add(scrollTransferuri);

//            String SQL_4 = "CALL getFirme";
//            CallableStatement getFirme = Main.c.prepareCall(SQL_4);
//            String[] colsFirme = {"Iban" , "Furnizor"};
//            var tableModelFirme = AppService.getGenericDataModel(getFirme , colsFirme);
//            firme = new JTable(tableModelFirme);
//            firme.setShowGrid(true);
//            firme.setShowVerticalLines(true);
//            scrollFirme = new JScrollPane(firme);
//            companyList.setLayout(new BoxLayout(companyList , BoxLayout.LINE_AXIS));
//            companyList.add(scrollFirme);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        logutButton.addActionListener( e -> {
            Main.currentFrame.setVisible(false);
            Main.currentFrame.dispose();
            Main.changeCurrentFrame(new MainFrame("Aplicatie Bancara"));
        });

        solicitareCardBancarButton.addActionListener(e -> {
            String currentIban = tabelConturi.getValueAt(selectedRowInAccounts , 1).toString();
            System.out.println(currentIban);
            boolean ok = ClientService.permnisiuneSolicitareCardBancar(currentIban);
            System.out.println(ok);
            ClientService.solicitareCardPopUpMenu(Main.currentFrame , ok);
            if (ok) {
                CallableStatement adaugare = null;
                try {
                    adaugare = Main.c.prepareCall("call adaugareRequestIban('" + currentIban  + "')");
                    adaugare.executeUpdate();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        deschidereContButton.addActionListener(e -> {
            if (ClientService.permisiuneDeschidereCont()) {
                String newIban = ClientService.nextIban();
                System.out.println(newIban);
                String SQL = "call addNewBankAccount(?,?)";
                try {
                    CallableStatement insert = Main.c.prepareCall(SQL);
                    insert.setString(2 , newIban);
                    insert.setString(1 , "Depunere");
                    insert.executeQuery();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                System.out.println(newIban);
                String SQL1 = "call associateCnpIban(? , ?)";
                CallableStatement bound = null;
                try {
                    bound = Main.c.prepareCall(SQL1);
                    bound.setString(1 , ClientService.personalData.cnp);
                    bound.setString(2 , newIban);
                    bound.executeQuery();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                String SQL_2 = "CALL getAccountData(?)";
                try {
                    CallableStatement to_call = Main.c.prepareCall(SQL_2);
                    to_call.setString(1 , ClientService.personalData.cnp);
                    String[] cols = {"suma" , "IBAN" , "economii"};
                    tabelConturi.setModel(AppService.getGenericDataModel(to_call ,  cols));
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                AppService.gengericPopUp(Main.currentFrame , "Se poate deschide un cont nou");
            }
            else {
                System.out.println("are deja 5");
                AppService.gengericPopUp(Main.currentFrame , "NU se poat deschide un alt cont");
            }
        });

        lichidareContButton.addActionListener(e->{
            String currentIban= tabelConturi.getValueAt(selectedRowInAccounts,1).toString();
            String SQL = "CALL deleteIban(?)";
            try {
                CallableStatement delete = Main.c.prepareCall(SQL);
                delete.setString(1 , currentIban);
                delete.executeQuery();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            String SQL_2 = "CALL getAccountData(?)";
            try {
                CallableStatement to_call = Main.c.prepareCall(SQL_2);
                to_call.setString(1 , ClientService.personalData.cnp);
                String[] cols = {"suma" , "IBAN" , "economii"};
                tabelConturi.setModel(AppService.getGenericDataModel(to_call ,  cols));
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        tabelConturi.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                selectedRowInAccounts = tabelConturi.getSelectedRow();
            }
        });

        accountsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                selectedRowInTranzactii = accountsTable.getSelectedRow();
                contPlecare.setText(accountsTable.getValueAt(selectedRowInTranzactii , 1).toString());
            }
        });

        sendButton.addActionListener(e -> {
            System.out.println(selectedRowInTranzactii);
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
