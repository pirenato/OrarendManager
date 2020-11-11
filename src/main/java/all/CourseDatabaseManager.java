/**
 * Osztály a Course osztály adatbázisműveleteinek kezeléséhez
 */

package all;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.swing.*;
import java.util.List;

@SuppressWarnings("ALL")
public class CourseDatabaseManager {

    /**
     * Új óra írása az adatbázisba
     * @param c
     */
    protected void create(Course c) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        session.save(c);

        session.getTransaction().commit();
        session.close();
    }

    /**
     * Óra adatainak módosítása az adatbázisba
     * A JTable-ben módosíthatók a cellák, módosításkor elég a kilistázott tárgyak közül átírni a kívánt mezőt.
     * @param id annak a tárgynak az azonosítója, amelyet módosítani szeretnénk
     * @param newData az új érték, amire leakarjuk cserélni az adatbázisban szereplő értéket
     * @param column a JTable-ben kiválasztott oszlop, aminek az adatát módosítani szeretnénk
     */
    protected static void update(long id, String newData, int column) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Course course = (Course) session.get(Course.class, id);

            switch (column) {
                case 0:
                    JOptionPane.showMessageDialog(null, "Az ID nem szerkeszthető", "Szerkesztési hiba", JOptionPane.ERROR_MESSAGE);
                    break;
                case 1:
                    course.setFelev(Integer.valueOf(newData));
                    break;
                case 2:
                    course.setKar(newData);
                    break;
                case 3:
                    course.setSzki(newData);
                    break;
                case 4:
                    course.setTi(newData);
                    break;
                case 5:
                    course.setTantargy(newData);
                    break;
                case 6:
                    course.setTanszek(newData);
                    break;
                case 7:
                    course.setEloado(newData);
                    break;
                case 8:
                    course.setCsoport(newData);
                    break;
                case 9:
                    course.setFo(Integer.parseInt(newData));
                    break;
                case 10:
                    course.setKezdes(Integer.parseInt(newData));
                    break;
                case 11:
                    course.setHossz(Integer.parseInt(newData));
                    break;
                case 12:
                    course.setTerem(newData);
                    break;
                case 13:
                    course.setNap(newData);
                    break;
                case 14:
                    course.setTipus(newData);
                    break;
            }

            session.update(course);
            tx.commit();
            session.close();
        } catch (HibernateException ex) {
            if (tx != null) tx.rollback();
            ex.printStackTrace();
        }
    }


    /**
     * Adatbázisból egy rekord beolvasása az id alapján
     * @param id a tárgy elsődleges kulcsa
     * @return ha létezik az id visszaadja a CourseDB objektumot
    */
    protected Course read(long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Course course = session.get(Course.class, id);
        session.close();
        return course;
    }


    /**
     * Tárgy törlése
     * @param id a törlendő tárgy elsődleges kulcsa
     */
    protected static void delete(long id) {
        Course course = new Course();
        course.setId(id);

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        session.delete(course);

        session.getTransaction().commit();
        session.close();
    }

    /**
     * Az összes adatbázisba lévő rekord beolvasása
     * @return Course objektumokból álló lista
     */
    protected List<Course> loadAllData() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Course course = new Course();

        return session.createCriteria(Course.class).list();
    }


}

