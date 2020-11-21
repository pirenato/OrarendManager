package usermanagement;

import all.Course;
import all.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

@SuppressWarnings("ALL")
public class UserDatabaseManager {

    protected void create(Users user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        session.save(user);

        session.getTransaction().commit();
        session.close();
    }

    public static void update(String username, String newData, int column) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Users user = (Users) session.get(Users.class, username);

            switch (column) {
                case 0:
                    user.setFullName(newData);
                case 1:
                    //felhasznalonevet nem lehet valtoztatni
                    break;
                case 2:
                    user.setEmail(newData);
                    break;
                case 3:
                    user.setRole(newData);
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
     * Az összes adatbázisba lévő rekord beolvasása
     * @return Course objektumokból álló lista
     */
    public List<Users> loadAllUserData() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Users user = new Users();

        return session.createCriteria(Users.class).list();
    }
}
