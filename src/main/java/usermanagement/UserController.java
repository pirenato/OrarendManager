/**
 * Osztaly a bejelentkezes es regisztracio kezelesehez
 */

package usermanagement;

import all.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;


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
        UserDatabaseManager userDatabaseManager = new UserDatabaseManager();

        Users tempUser = new Users();
        tempUser.setFullName(fullname);
        tempUser.setEmail(email);
        tempUser.setUsername(username);
        tempUser.setPassword(password);
        tempUser.setRole("hallgato");

        userDatabaseManager.create(tempUser);
    }
}
