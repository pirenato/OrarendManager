/**
 * Egyszeru bejelentkezo felulet, JTextField a felhasznalonevhez es jelszohoz, JButton a bejelentkezeshez/regisztraciohoz
 * Ha helyes a felhasznalonev/jelszo megnyitja a szerepkorhoz tartozo feluletet
 */
package usermanagement;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import coursemanagement.*;
import maingui.AdminGUI;
import maingui.StudentGUI;
import maingui.TeacherGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static usermanagement.UserController.*;

public class LoginGUI extends JFrame {
    private JPanel panel;
    private JTextField usernameTextField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registrationButton;
    private JButton forgottenPaswordButton;
    public static String loggedInUser;


    public LoginGUI() {
        CourseDatabaseManager courseDatabaseManager = new CourseDatabaseManager();
        HibernateUtil.setup();
        UserDatabaseManager.initializeIfTableNotExists();

        /**
         * A bejelentkezes gombra kattintva az ActionListener a verifyLogin metodust hivja meg.
         * Ez a metodus megvizsgalja, hogy az adatbazisban szerepel-e a beirt felhasznalonev es a hozza tartozo jelszo.
         * Ha letezik akkor a verifyLogin a felhasznalo szerepkoret adja vissza, ez alapjan nyilik meg a
         * megfelelo szerepkorhoz tartozo felulet
         */
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String password = UserDatabaseManager.get_SHA512(String.valueOf(passwordField.getPassword()));

                switch (verifyLogin(usernameTextField.getText(), password)) {
                    case "admin":
                        JOptionPane.showMessageDialog(null, "Sikeres bejelentkezes!\n", "Admin", JOptionPane.PLAIN_MESSAGE);
                        loggedInUser = usernameTextField.getText();
                        AdminGUI adminGUI = new AdminGUI();
                        adminGUI.AdminPage();
                        break;
                    case "teacher":
                        JOptionPane.showMessageDialog(null, "Sikeres bejelentkezes!\n", "Tanar", JOptionPane.PLAIN_MESSAGE);
                        loggedInUser = usernameTextField.getText();
                        TeacherGUI teacherGUI = new TeacherGUI();
                        teacherGUI.TanarPage();
                        break;
                    case "student":
                        JOptionPane.showMessageDialog(null, "Sikeres bejelentkezes!\n", "Hallgato", JOptionPane.PLAIN_MESSAGE);
                        loggedInUser = usernameTextField.getText();
                        StudentGUI studentGUI = new StudentGUI();
                        studentGUI.HallgatoPage();
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Sikertelen bejelentkezes!\n", "Login", JOptionPane.ERROR_MESSAGE);
                        break;
                }
            }
        });

        /**
         * A regisztracio gombra kattintva egy uj felulet ugrik fel,
         * a szukseges adatok megadasa utan uj felhasznalot lehet letrehozni az adatbazisban
         */
        registrationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegistrationGUI newRegistration = new RegistrationGUI();
                newRegistration.setVisible(true);
            }
        });
        forgottenPaswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewPasswordGUI newPasswordGUI = new NewPasswordGUI();
                newPasswordGUI.setVisible(true);
            }
        });


    }

    public static void main(String[] args) {
        System.out.println(System.getProperty("java.class.path"));
        JFrame frame = new JFrame("LoginPage");
        frame.setContentPane(new LoginGUI().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel = new JPanel();
        panel.setLayout(new GridLayoutManager(7, 2, new Insets(0, 0, 0, 0), -1, -1));
        final JLabel label1 = new JLabel();
        label1.setText("Felhasználónév:");
        panel.add(label1, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel.add(spacer1, new GridConstraints(1, 1, 5, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        usernameTextField = new JTextField();
        usernameTextField.setText("");
        panel.add(usernameTextField, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Jelszó:");
        panel.add(label2, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        passwordField = new JPasswordField();
        panel.add(passwordField, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        loginButton = new JButton();
        loginButton.setText("Bejelentkezés");
        panel.add(loginButton, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        registrationButton = new JButton();
        registrationButton.setText("Regisztráció");
        panel.add(registrationButton, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        forgottenPaswordButton = new JButton();
        forgottenPaswordButton.setText("Elfelejtett jelszó");
        panel.add(forgottenPaswordButton, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel;
    }
}