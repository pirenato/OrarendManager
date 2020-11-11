/**
 * Osztály a bejelentkezés és regisztráció kezeléséhez
 */

package usermanagement;

import all.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;


public class UserController {

    /**
     * Bejelentkezéskor ellenőrzi, hogy az adatbázisban szerepel-e a beírt felhasználónév és jelszó.
     * @param username a felhasználó által megadott felhasználónév
     * @param password a felhasználó által megadott jelszó
     * @return ha jó a felhasználónév és a jelszó, akkor a felhasználóhoz tartozó szerepkört adja vissza, ez alapján nyílik meg a megfelelő admin/tanári/felhasználói felület
     */
    public static String verifyLogin(String username, String password) {
        boolean found = false;

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
     * Új felhasználó regisztrálása. Ezt követően hallgató szerepkörrel rendelkezik az új felhasználó, az admin adhat neki más jogosultságot.
     * @param fullname a felhasználó teljes neve
     * @param email email cím
     * @param username a felhasználónév, ami az adatbázisban elsődleges kulcs, így muszáj egyedinek lennie
     * @param password a felhasználóhoz tartozó jelszó
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
