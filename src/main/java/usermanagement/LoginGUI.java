/**
 * Egyszeru bejelentkezo felulet, JTextField a felhasznalonevhez es jelszohoz, JButton a bejelentkezeshez/regisztraciohoz
 * Ha helyes a felhasznalonev/jelszo megnyitja a szerepkorhoz tartozo feluletet
 */
package usermanagement;

import all.*;
import gui.AdminGUI;
import gui.HallgatoGUI;
import gui.TanarGUI;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static usermanagement.UserController.*;

public class LoginGUI extends JFrame {
    private JPanel panel;
    private JTextField usernameTextField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registrationButton;

    public LoginGUI() {
        CourseDatabaseManager courseDatabaseManager = new CourseDatabaseManager();
        HibernateUtil.setup();

        /**
         * A bejelentkezes gombra kattintva az ActionListener a verifyLogin metodust hivja meg.
         * Ez a metodus megvizsgalja, hogy az adatbazisban szerepel-e a beirt felhasznalonev es a hozza tartozo jelszo.
         * Ha letezik akkor a verifyLogin a felhasznalo szerepkoret adja vissza, ez alapjan nyilik meg a
         * megfelelo szerepkorhoz tartozo felulet
         */
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String password = String.valueOf(passwordField.getPassword());
                switch (verifyLogin(usernameTextField.getText(), password)) {
                    case "admin":
                        JOptionPane.showMessageDialog(null, "Sikeres bejelentkezes!\n", "Admin", JOptionPane.PLAIN_MESSAGE);
                        AdminGUI adminGUI = new AdminGUI();
                        adminGUI.AdminPage();
                        break;
                    case "tanar":
                        JOptionPane.showMessageDialog(null, "Sikeres bejelentkezes!\n", "Tanar", JOptionPane.PLAIN_MESSAGE);
                        TanarGUI tanarGUI = new TanarGUI();
                        tanarGUI.TanarPage();
                        break;
                    case "hallgato":
                        JOptionPane.showMessageDialog(null, "Sikeres bejelentkezes!\n", "Hallgato", JOptionPane.PLAIN_MESSAGE);
                        HallgatoGUI hallgatoGUI = new HallgatoGUI();
                        hallgatoGUI.HallgatoPage();
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
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("LoginPage");
        frame.setContentPane(new LoginGUI().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
