/**
 * Ez az osztaly felelos a felhasznaloi fiokok adatbazis muveleteiert
 */

package usermanagement;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

@SuppressWarnings("ALL")
public class UserDatabaseManager {

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
     * Ha a
     */
    public static void initializeIfTableNotExists() {
        Users user = new Users();

        if (read("admin") == null) {

            user.setFull_name("Admin Admin");
            user.setEmail("admin@timetable.com");
            user.setUsername("admin");
            user.setPassword("adminpw");
            user.setRole("admin");
            create(user);
        }

        if (read("teacher") == null) {
            user.setFull_name("Test Teacher");
            user.setEmail("email@timetable.com");
            user.setUsername("teacher");
            user.setPassword("teacherpw");
            user.setRole("teacher");
            create(user);
        }

        if (read("student") == null) {
            user.setFull_name("Test Student");
            user.setEmail("student@timetable.com");
            user.setUsername("student");
            user.setPassword("studentpw");
            user.setRole("student");
            create(user);
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
     * Az osszes adatbazisba levo rekord beolvasasa
     * @return Course objektumokból álló lista
     */
    public List<Users> loadAllUserData() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Users user = new Users();

        return session.createCriteria(Users.class).list();
    }
}
