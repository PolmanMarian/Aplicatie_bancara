package Gui;

import UserServices.ClientService;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

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
    private JComboBox<String> selectiaContuluiTransferGeneric;
    private JTextField selectedAccount;
    private JTextField toSendIban;
    private JTextField toSend;
    private JTextField toSendName;
    private JButton sendButton1;
    private JButton clearButton;
    private JPanel tranzactiiView;
    private JPanel depozite;
    private JButton deschidereDepozitButton;
    private JButton lichidareDepozitButton;
    private JTable tabelConturi;
    private JScrollPane scrollConturi;
    private JScrollPane scrollTransferuri;
    private JScrollPane scrollFirme;
    private JTable accountsTable;
    private JScrollPane scrollAccounts;
    private JTable depoziteTable;
    private JScrollPane depoziteList;

    /// selectia contului bancar
    public int selectedRowInAccounts = 0;
    public int selectedRowInTranzactii = 0;
    public int getSelectedRowInFirme = 0;
    public int selectedDep = 0;

    public void update_conturi(JTable to_update) {
        String SQL_2 = "CALL getAccountData(?)";
        CallableStatement to_call = null;
        try {
            to_call = Main.c.prepareCall(SQL_2);
            to_call.setString(1 , ClientService.personalData.cnp);
            String[] cols = {"suma" , "IBAN" , "economii"};
            var content = AppService.getGenericDataModel(to_call ,  cols);
            to_update.setModel(content);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void update_conturi_combo(JComboBox ceva) {
        ceva.removeAllItems();
        int n = accountsTable.getRowCount();
        for(int i = 0 ; i < n ; ++i) {
            ceva.addItem(accountsTable.getValueAt(i , 1).toString());
        }
    }

    public ClientFrame(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(panel1);
        this.pack();

        /// Selectia datelor personale
        ClientService.getPersonalData(AppService.getCurrentUsername() , AppService.getCurrentPassword());
        ClientService.printData();
        NumarDeContract.setText(String.valueOf(ClientService.personalData.contractNumber));
        Adresa.setText(ClientService.personalData.adress);
        Nume.setText(ClientService.personalData.lastName);
        Prenume.setText(ClientService.personalData.firstName);
        CNP.setText(ClientService.personalData.cnp);
        NumarDeTelefon.setText(ClientService.personalData.phoneNumber);


        try {
            /// Selectia conturilor bancare
            String SQL_2 = "CALL getAccountData(?)";
            CallableStatement to_call = Main.c.prepareCall(SQL_2);
            to_call.setString(1 , ClientService.personalData.cnp);
            String[] cols = {"suma" , "IBAN" , "economii"};
            var content = AppService.getGenericDataModel(to_call ,  cols);

            /// Meniul de plata a factirulor (lista cu conturile bancare curente)
            accountsTable = new JTable(content);
            accountsTable.setShowGrid(true);
            accountsTable.setShowVerticalLines(true);
            scrollAccounts = new JScrollPane(accountsTable);
            accountsList.setLayout(new BoxLayout(accountsList, BoxLayout.LINE_AXIS));
            accountsList.add(scrollAccounts);

            /// Meniul de vizualizare conturi (lista cu conturile bancare)
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

            String SQL_4 = "CALL getFurnizori";
            CallableStatement getFirme = Main.c.prepareCall(SQL_4);
            String[] colsFirme = {"Iban" , "Furnizor"};
            var tableModelFirme = AppService.getGenericDataModel(getFirme , colsFirme);
            firme = new JTable(tableModelFirme);
            firme.setShowGrid(true);
            firme.setShowVerticalLines(true);
            scrollFirme = new JScrollPane(firme);
            companyList.setLayout(new BoxLayout(companyList , BoxLayout.LINE_AXIS));
            companyList.add(scrollFirme);

            ///depozitele clientului
            String SQL_5 = "call getDepozite(?)";
            CallableStatement getdep = Main.c.prepareCall(SQL_5);
            getdep.setString(1 , ClientService.personalData.cnp);
            String[] colsDepozite = {"Id" , "Suma" , "Data" , "Dobanda"};
            var tableModel = AppService.getGenericDataModel(getdep , colsDepozite);
            depoziteTable = new JTable(tableModel);
            depoziteTable.setShowGrid(true);
            depoziteTable.setShowVerticalLines(true);
            depoziteList = new JScrollPane(depoziteTable);
            depozite.setLayout(new BoxLayout(depozite , BoxLayout.LINE_AXIS));
            depozite.add(depoziteList);

            ///Ulitmul menu transfer bancar generic
            String[] conturiCurente;
            ArrayList<String> helper = new ArrayList<String>();
            final int n = accountsTable.getRowCount();
            for(int i = 0 ; i < n ; ++i) {
                    helper.add(accountsTable.getValueAt(i , 1).toString());
            }
            conturiCurente = new String[helper.size()];
            for (int i = 0 ; i < helper.size() ; ++i) {
                conturiCurente[i] = helper.get(i);
                selectiaContuluiTransferGeneric.addItem(helper.get(i));
            }


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
//            System.out.println(currentIban);
            boolean ok = ClientService.permnisiuneSolicitareCardBancar(currentIban);
//            System.out.println(ok);
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
                //Alegerea tipului de cont
                String[] selections = { "De economii" , "Curent" };
                Object val = JOptionPane.showInputDialog(Main.currentFrame , "Alege tipul de cont" , "Input" , JOptionPane.INFORMATION_MESSAGE , null , selections , selections[0]);
//                System.out.println(val);

                String newIban = ClientService.nextIban();
//                System.out.println(newIban);
                String SQL = "call addNewBankAccount(?,?)";
                try {
                    CallableStatement insert = Main.c.prepareCall(SQL);
                    insert.setString(1 , newIban);
                    insert.setString(2 , (String)val);
                    insert.executeQuery();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

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
            this.update_conturi(accountsTable);
            this.update_conturi_combo(selectiaContuluiTransferGeneric);
        });

        lichidareContButton.addActionListener(e->{
            String currentIban = tabelConturi.getValueAt(selectedRowInAccounts,1).toString();
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
            this.update_conturi(accountsTable);
            this.update_conturi_combo(selectiaContuluiTransferGeneric);
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

        firme.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                getSelectedRowInFirme = firme.getSelectedRow();
                contViraj.setText(firme.getValueAt(getSelectedRowInFirme , 0).toString());
            }
        });

        sendButton.addActionListener(e -> {
            String SQL = "Call insertTransfer(?,?,?,?,?)";
            try {
                CallableStatement insert = Main.c.prepareCall(SQL);
                insert.setInt(1 , Integer.parseInt(sumaTranzactie.getText()));
                insert.setString(2 , contPlecare.getText());
                insert.setString(3 , contViraj.getText());
                insert.setString(4 , ClientService.personalData.lastName + " " + ClientService.personalData.firstName);
                insert.setString(5 , firme.getValueAt(getSelectedRowInFirme , 1).toString());
                insert.executeQuery();
                contPlecare.setText("");
                contViraj.setText("");
                sumaTranzactie.setText("");
                String SQL_3 = "CALL getTransfer(?,?)";
                CallableStatement getTransfer = Main.c.prepareCall(SQL_3);
                getTransfer.setString(1 , ClientService.personalData.lastName);
                getTransfer.setString(2 , ClientService.personalData.firstName);
                String[] colsTransfer = {"Data" , "Suma" , "Iban plecare" , "Iban destinatie" , "Nume destinatar"};
                var tableModelTransfer = AppService.getGenericDataModel(getTransfer , colsTransfer);
                tranzactii.setModel(tableModelTransfer);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        deschidereDepozitButton.addActionListener(e -> {
            String SQL = "call insertDepozit(?,?,?)";
            try {
                String[] options = {"30 zile" , "90 zile" , "180 zile"};
                Object input = JOptionPane.showInputDialog(Main.currentFrame, "Alege durata depozitului" , "Input " , JOptionPane.PLAIN_MESSAGE , null , options , options[0]);
                Integer d = 3;
                if (input.equals("30 zile")) {
                    d = 1;
                }
                if (input.equals("90 zile")) {
                    d = 2;
                }
                String ret = JOptionPane.showInputDialog(
                        Main.currentFrame , "BAGA SUMA" , "BAGA SUMA" , 1);
                CallableStatement insert = Main.c.prepareCall(SQL);
                int suma = Integer.parseInt(ret);
                insert.setString(1 , Integer.toString(suma));
                insert.setString(2 , ClientService.personalData.cnp);
                insert.setInt(3 , d);
                insert.executeQuery();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            String SQL_5 = "call getDepozite(?)";
            CallableStatement getdep = null;
            try {
                getdep = Main.c.prepareCall(SQL_5);
                getdep.setString(1 , ClientService.personalData.cnp);
                String[] colsDepozite = {"Id" , "Suma" , "Data" , "Dobanda"};
                var tableModel = AppService.getGenericDataModel(getdep , colsDepozite);
                depoziteTable.setModel(tableModel);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        depoziteTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                selectedDep = depoziteTable.getSelectedRow();
            }
        });

        lichidareDepozitButton.addActionListener(e -> {
            String ibanDestinatie = "";
            Integer idDepozit = 0;
            idDepozit = Integer.parseInt(depoziteTable.getValueAt(selectedDep , 0).toString());
            String[] conturiCurente;
            ArrayList<String> helper = new ArrayList<String>();
            final int n = accountsTable.getRowCount();
            for(int i = 0 ; i < n ; ++i) {
                if (accountsTable.getValueAt(i , 2).toString().equals("Curent")) {
                    helper.add(accountsTable.getValueAt(i , 1).toString());
                }
            }
            conturiCurente = new String[helper.size()];
            for (int i = 0 ; i < helper.size() ; ++i) {
                conturiCurente[i] = helper.get(i);
            }
            Object input = JOptionPane.showInputDialog(Main.currentFrame,
                    "Alege contul curent in care vrei sa-ti fie restituiti banii" ,
                    "Input " , JOptionPane.PLAIN_MESSAGE , null , conturiCurente , conturiCurente[0]);
            ibanDestinatie = (String)input;
            System.out.println(ibanDestinatie + idDepozit);
            String SQL = "call lichidareDepozit(? , ?)";
            try {
                CallableStatement li = Main.c.prepareCall(SQL);
                li.setInt(String.valueOf(1 , idDepozit));
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        });

        clearButton.addActionListener(e -> {
            selectedAccount.setText("");
            toSendIban.setText("");
            toSendName.setText("");
            toSend.setText("");
        });

        selectiaContuluiTransferGeneric.addActionListener(e -> {
            selectedAccount.setText(Objects.requireNonNull(selectiaContuluiTransferGeneric.getSelectedItem()).toString());
        });

        sendButton1.addActionListener(e -> {
            String SQL = "insert pentru tranzactii + update accounts";
            clearButton.doClick();
        });
    }
}