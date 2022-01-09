package Gui;

import Containers.RegisterContainer;

import javax.swing.*;

public class RegisterFrame extends JFrame {
    private JPanel panel1;
    private JTextField nume;
    private JTextField prenume;
    private JTextField username;
    private JTextField password;
    private JTextField adresa;
    private JTextField telefon;
    private JButton backButton;
    private JCheckBox terms;
    private JButton submitButton;
    private JButton resetButton;
    private JTextField cenepe;

    RegisterFrame (String title) {
        super.setTitle(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(panel1);
        this.pack();

        submitButton.addActionListener(e -> {
            if (nume.getText().equals("") ||
                prenume.getText().equals("") ||
                username.getText().equals("") ||
                password.getText().equals("") ||
                adresa.getText().equals("") ||
                telefon.getText().equals("") ||
                cenepe.getText().equals("")) {
                AppService.gengericPopUp(Main.currentFrame , "Toate campurile sunt obligatorii");
            }
            else
            if (terms.isSelected()) {
                RegisterContainer recordToAdd = new RegisterContainer(
                        nume.getText() , prenume.getText() ,
                        username.getText() , password.getText(),
                        adresa.getText(), telefon.getText() , cenepe.getText()
                );
                AppService.addNewUser(recordToAdd);
                AppService.gengericPopUp(Main.currentFrame , "Inregistrare executat cu succes");
                resetButton.doClick(12);
                Main.currentFrame.setVisible(false);
                Main.currentFrame.dispose();
                Main.changeCurrentFrame(new MainFrame("Aplicatie Bancara"));
                recordToAdd.print();
            }
            else {
                AppService.mustAcceptTerms(Main.currentFrame);
            }
        });

        resetButton.addActionListener(e -> {
            nume.setText("");
            prenume.setText("");
            username.setText("");
            password.setText("");
            adresa.setText("");
            telefon.setText("");
            terms.setSelected(false);
        });


        backButton.addActionListener(e -> {
            Main.currentFrame.setVisible(false);
            Main.currentFrame.dispose();
            Main.changeCurrentFrame(new MainFrame("Aplicatie Bancara"));
        });
    }
}
