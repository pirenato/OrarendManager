package usermanagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChangePasswordGUI extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField oldPasswordTextFIeld;
    private JTextField newPasswordTextField;
    private JTextField confirmNewPasswordTextField;

    public ChangePasswordGUI() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setTitle("Jelszó megváltoztatása");

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
        String oldPassword = UserController.get_SHA512(oldPasswordTextFIeld.getText());
        String newPassword = UserController.get_SHA512(newPasswordTextField.getText());
        String newPasswordConfirmation = UserController.get_SHA512(confirmNewPasswordTextField.getText());
        UserController.changePassword(oldPassword, newPassword, newPasswordConfirmation);
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
