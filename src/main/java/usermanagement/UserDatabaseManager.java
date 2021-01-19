/**
 * Ez az osztaly felelos a felhasznaloi fiokok adatbazis muveleteiert
 */

package usermanagement;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@SuppressWarnings("ALL")
public class UserDatabaseManager {

    private static final String SALT = "SZAKDOLGOZAT";

    /**
     * Uj felhasznalo letrehozasa az adatbazisban
     * @param user
     */
    protected static void create(Users user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        session.save(user);

        session.getTransaction().commit();
        session.close();
    }

    /**
     * Adatbazis feltoltese teszt felhasznalokkal, ha meg nem leteznek az adatbazisban
     */
    public static void initializeIfTableNotExists() {
        Users user = new Users();

        try {
            if (read("admin") == null) {

                user.setFull_name("Admin Admin");
                user.setEmail("admin@timetable.com");
                user.setUsername("admin");
                user.setPassword(get_SHA512("adminpw"));
                user.setRole("admin");
                create(user);
            }

            if (read("teacher") == null) {
                user.setFull_name("Test Teacher");
                user.setEmail("email@timetable.com");
                user.setUsername("teacher");
                user.setPassword(get_SHA512("teacherpw"));
                user.setRole("teacher");
                create(user);
            }

            if (read("student") == null) {
                user.setFull_name("Test Student");
                user.setEmail("student@timetable.com");
                user.setUsername("student");
                user.setPassword(get_SHA512("studentpw"));
                user.setRole("student");
                create(user);
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    public static Users read(String username) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Users tempUser = (Users) session.get(Users.class, username);
        session.close();
        return tempUser;
    }

    /**
     * Felhasznalo adatainak modositasa az adatbazisban
     * @param username a felhasznalonev mindenkepp egyedi, ezert ez az elsodleges kulcs, ez alapjan talalja meg az adatbazisban a felhasznalot
     * @param newData az uj adat amire modositani szeretnenk a regi adatot
     * @param column a tablazatban kivalasztott oszlop, amiben a modositando adat szerepel
     */
    public static void update(String username, String newData, int column) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Users user = (Users) session.get(Users.class, username);

            switch (column) {
                case 0:
                    user.setFull_name(newData);
                case 1:
                    //felhasznalonevet nem lehet megvaltoztatni
                    break;
                case 2:
                    user.setEmail(newData);
                    break;
                case 3:
                    user.setRole(newData);
                    break;
                case 4:
                    user.setPassword(newData);
                    break;
            }

            session.update(user);
            tx.commit();
            session.close();
        } catch (HibernateException ex) {
            if (tx != null) tx.rollback();
            ex.printStackTrace();
        }
    }

    /**
     * Jelszo hash + salt SHA-512-vel
     * @param passwordToHash A plaintext jelszo
     * @return a hashelt jelszo
     */
    public static String get_SHA512(String passwordToHash) {
        String hashedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(SALT.getBytes());
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for(int i=0; i < bytes.length; i++)  {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            hashedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hashedPassword;
    }

    /**
     * Az osszes adatbazisba levo rekord beolvasasa
     * @return Course objektumokból álló lista
     */
    public List<Users> loadAllUserData() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Users user = new Users();

        return session.createCriteria(Users.class).list();
    }
}
