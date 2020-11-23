package usermanagement;

import usermanagement.LoginGUI;
import usermanagement.UserDatabaseManager;
import usermanagement.Users;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChangePassword extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField oldPasswordTextFIeld;
    private JTextField newPasswordTextField;
    private JTextField confirmNewPasswordTextField;

    public ChangePassword() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        this.setMinimumSize(new Dimension(300, 150));
        setLocationRelativeTo(null);
        this.pack();

    }

    private void onOK() {
        Users currentUser = UserDatabaseManager.read(LoginGUI.loggedInUser);
        System.out.println("Felhasznalo: " + currentUser.getUsername() + ", jelszo: " + currentUser.getPassword());

        if (currentUser.getPassword().equals(oldPasswordTextFIeld.getText())) {
            if (newPasswordTextField.getText().equals(confirmNewPasswordTextField.getText())) {
                UserDatabaseManager.update(currentUser.getUsername(), newPasswordTextField.getText(), 4);
                JOptionPane.showMessageDialog(null, "A jelszo sikeresen megvaltoztatva!", "Sikeres jelszo valtoztatas", JOptionPane.INFORMATION_MESSAGE);
            } else
                JOptionPane.showMessageDialog(null, "A beirt uj jelszavak nem egyeznek!", "Sikertelen jelszo valtoztatas", JOptionPane.ERROR_MESSAGE);
        } else
            JOptionPane.showMessageDialog(null, "A beirt regi jelszo teves", "Sikertelen jelszo valtoztatas", JOptionPane.ERROR_MESSAGE);
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
