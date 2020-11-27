/**
 * GUI uj felhasznalo regisztralasara
 */

package usermanagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import static usermanagement.UserController.*;

public class RegistrationGUI extends JDialog {
    private JPanel contentPane;
    private JButton registrationButton;
    private JButton backButton;
    private JTextField fullnameTextField;
    private JTextField emailTextField;
    private JTextField usernameTextField;
    private JTextField passwordTextField;

    public RegistrationGUI() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(registrationButton);
        setTitle("Regisztráció");

        /**
         * A gombra kattintva a felhasznalo altal beirt adatok alapjan letrehoz egy uj felhasznalot
         * az adatbazisban
         */
        registrationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registration(fullnameTextField.getText(), emailTextField.getText(), usernameTextField.getText(), get_SHA512(passwordTextField.getText()));
                JOptionPane.showMessageDialog(null, "Sikeres regisztracio", "Regisztralas", JOptionPane.PLAIN_MESSAGE);
            }
        });

        backButton.addActionListener(new ActionListener() {
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

        setMinimumSize(new Dimension(280,170));
        pack();
    }


    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
