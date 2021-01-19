/**
 * Osztaly a bejelentkezes es regisztracio kezelesehez
 */

package usermanagement;

import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import javax.swing.*;
import static usermanagement.SendEmail.send;


public class UserController {
    /**
     * Bejelentkezeskor ellenorzi, hogy az adatbazisban szerepel-e a beirt felhasznalonev es jelszo.
     *
     * @param username a felhasznalo altal megadott felhasznalonev
     * @param password a felhasznalo altal megadott jelszo
     * @return ha jo a felhasznalonev es a jelszo, akkor a felhasznalohoz tartozo szerepkort adja vissza, ez alapjan nyilik meg a megfelelo admin/tanari/felhasznaloi felulet
     */
    public static String verifyLogin(String username, String password) {
        Users user = null;
        Transaction transaction = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            transaction = session.beginTransaction();
            user = (Users) session.createQuery("FROM Users f WHERE f.username = :username").setParameter("username", username).uniqueResult();

            if (user != null && user.getPassword().equals(password)) {
                return user.getRole();
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return "";
    }


    /**
     * uj felhasznalo regisztralasa, minden esetben hallgato szerepkorrel rendelkezik az uj felhasznalo,
     * csak admin adhat neki mas jogosultsagot.
     * @param fullname a felhasznalo teljes neve
     * @param email email cim
     * @param username a felhasznalonev, ami az adatbazisban elsodleges kulcs, igy muszaj egyedinek lennie
     * @param password a felhasznalohoz tartozo jelszo
     */
    public static void registration(String fullname, String email, String username, String password){
        Users tempUser = new Users();
        tempUser.setFull_name(fullname);
        tempUser.setEmail(email);
        tempUser.setUsername(username);
        tempUser.setPassword(password);
        tempUser.setRole("student");

        UserDatabaseManager.create(tempUser);
    }

    public static void forgottenPassword(String email, String username) {
        String newPassword = RandomStringUtils.random(12, true, true);
        String securePassword = UserDatabaseManager.get_SHA512(newPassword);
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Users tempUser = UserDatabaseManager.read(username);
            if (tempUser.getEmail().equals(email)) {
                send("orarendprojekt@gmail.com", "Szakdolgozat00", email, "Jelszo visszaallitas", "Az orarend alkalmazashoz uj jelszo lett generalva: " + newPassword);
                UserDatabaseManager.update(username, securePassword, 4);
                JOptionPane.showMessageDialog(null, "Az uj jelszo elkuldve a megadott email cimre!", "Uj jelszo", JOptionPane.INFORMATION_MESSAGE);
            } else
                JOptionPane.showMessageDialog(null, "A megadott felhasznalonevhez nem ez az email cim tartozik, addj meg egy ujat!", "Rossz username/email", JOptionPane.ERROR_MESSAGE);
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "A megadott felhasznalonevvel nem letezik felhasznalo az adatbazisban!", "Hiba", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void changePassword(String oldPassword, String newPassword, String newPasswordConfirmation) {
        Users currentUser = UserDatabaseManager.read(LoginGUI.loggedInUser);
        System.out.println("Felhasznalo: " + currentUser.getUsername() + ", jelszo: " + currentUser.getPassword());

        if (currentUser.getPassword().equals(oldPassword)) {
            if (newPassword.equals(newPasswordConfirmation)) {
                UserDatabaseManager.update(currentUser.getUsername(), newPasswordConfirmation, 4);
                JOptionPane.showMessageDialog(null, "A jelszo sikeresen megvaltoztatva!", "Sikeres jelszo valtoztatas", JOptionPane.INFORMATION_MESSAGE);
            } else
                JOptionPane.showMessageDialog(null, "A beirt uj jelszavak nem egyeznek!", "Sikertelen jelszo valtoztatas", JOptionPane.ERROR_MESSAGE);
        } else
            JOptionPane.showMessageDialog(null, "A beirt regi jelszo teves", "Sikertelen jelszo valtoztatas", JOptionPane.ERROR_MESSAGE);
    }

}
