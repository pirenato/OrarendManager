/**
 * Egyszerű bejelentkező felület, egy-egy JTextField a felhasználónévhez és jelszóhoz, egy-egy JButton a bejelentkezéshez/regisztrációhoz
 * Ha helyes a felhasználónév/jelszó megnyitja a szerepkörhöz tartozó felületet
 */
package usermanagement;

import all.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static usermanagement.UserController.*;

public class LoginGUI extends JFrame {
    private JPanel panel;
    private JTextField usernameTextField;
    private JPasswordField passwordField1;
    private JButton bejelentkezesButton;
    private JButton registrationButton;

    public LoginGUI() {
        CourseDatabaseManager courseDatabaseManager = new CourseDatabaseManager();
        HibernateUtil.setup();
        /**
         * A bejelentkezés gombra kattintva az ActionListener a verifyLogin metódust hívja meg.
         * Ez a metódus megvizsgálja, hogy az adatbázisban szerepel-e a beírt felhasználónév és jelszó.
         * Ha létezik, akkor a felhasználó szerepköre alapján az admin, tanári vagy hallgatói felületet nyitja meg.
         */
        bejelentkezesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String password = String.valueOf(passwordField1.getPassword());
                switch (verifyLogin(usernameTextField.getText(), password)) {
                    case "admin":
                        JOptionPane.showMessageDialog(null, "Sikeres bejelentkezés!\n", "Admin", JOptionPane.PLAIN_MESSAGE);
                        AdminGUI adminGUI = new AdminGUI();
                        adminGUI.AdminPage();
                        break;
                    case "tanar":
                        JOptionPane.showMessageDialog(null, "Sikeres bejelentkezés!\n", "Tanár", JOptionPane.PLAIN_MESSAGE);
                        TanarGUI tanarGUI = new TanarGUI();
                        tanarGUI.TanarPage();
                        break;
                    case "hallgato":
                        JOptionPane.showMessageDialog(null, "Sikeres bejelentkezés!\n", "Hallgató", JOptionPane.PLAIN_MESSAGE);
                        HallgatoGUI hallgatoGUI = new HallgatoGUI();
                        hallgatoGUI.HallgatoPage();
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Sikertelen bejelentkezés!\n", "Login", JOptionPane.ERROR_MESSAGE);
                        break;
                }
            }
        });

        /**
         * A regisztráció gombra kattintva egy új felület ugrik fel, ahol a szükséges adatok megadása után új felhasználót lehet létrehozni.
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
