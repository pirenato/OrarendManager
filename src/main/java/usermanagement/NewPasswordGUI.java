package usermanagement;

import org.apache.commons.lang3.RandomStringUtils;
import usermanagement.UserController;
import usermanagement.UserDatabaseManager;
import usermanagement.Users;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static usermanagement.SendEmail.send;

public class NewPasswordGUI extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField emailTextField;
    private JTextField usernameTextField;

    public NewPasswordGUI() {
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

        setMinimumSize(new Dimension(300,150));
        pack();
    }

    private void onOK() {
        UserController.forgottenPassword(emailTextField.getText(), usernameTextField.getText());
    }

    private void onCancel() {
        dispose();
    }
}
