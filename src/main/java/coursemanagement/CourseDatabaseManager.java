/**
 * Osztaly a Course osztaly adatbazismuveleteinek kezelesehez
 */

package coursemanagement;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import usermanagement.HibernateUtil;

import javax.swing.*;
import java.util.List;

@SuppressWarnings("ALL")
public class CourseDatabaseManager {

    /**
     * Az adatbazis feltoltese az elozetes orarendbol beolvasott orakkal
     * @param courseList Course objektumokbol allo lista
     */
    public static void initializeCourseTable(List<Course> courseList) {
        try {
            CourseDatabaseManager courseDatabaseManager = new CourseDatabaseManager();
            for (Course c : courseList) {
                courseDatabaseManager.create(c);
            }
        } catch (NullPointerException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * Uj ora irasa az adatbazisba
     * @param c
     */
    public void create(Course c) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        session.save(c);

        session.getTransaction().commit();
        session.close();
    }

    /**
     * ora adatainak modositasa az adatbazisba
     * A JTable-ben modosithatok a cellak, modositaskor eleg a tablazatban atirni az erintett mezot.
     * @param id annak a targynak az azonositoja, amelyet modositani szeretnenk
     * @param newData az uj ertek, amire leakarjuk cserelni az adatbazisban szereplo erteket
     * @param column a JTable-ben kivalasztott oszlop, aminek az adatat modositani szeretnenk
     */
    public static void update(long id, String newData, int column) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Course course = (Course) session.get(Course.class, id);

            switch (column) {
                case 0:
                    JOptionPane.showMessageDialog(null, "Az ID nem szerkesztheto", "Szerkesztesi hiba", JOptionPane.ERROR_MESSAGE);
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
     * Adatbazisbol egy rekord beolvasasa az id alapjan
     * @param id a targy elsodleges kulcsa
     * @return ha letezik az id visszaadja a Course objektumot
    */
    protected Course read(long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Course course = session.get(Course.class, id);
        session.close();
        return course;
    }

    /**
     * Targy torlese adatbazisbol
     * @param id a torlendo targy elsodleges kulcsa
     */
    public static void delete(long id) {
        Course course = new Course();
        course.setId(id);

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        session.delete(course);

        session.getTransaction().commit();
        session.close();
    }

    /**
     * Az osszes adatbazisba levo rekord beolvasasa
     * @return Course objektumokbol allo lista
     */
    public List<Course> loadAllData() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Course course = new Course();

        return session.createCriteria(Course.class).list();
    }


    /**
     * Az orarend generalasakor keres az adatbazisban, a kivalasztott oszlopban a felhasznalo altal beirt erteket
     * @param selectedColumn oszlop az adatbazisban, amiben keresi a megadott erteket
     * @param userEntry a keresett ertek
     * @return a keresett Course objektumokat tartalmazo listat adja vissza
     */
    public List<Course> searchDatabaseColumn(String selectedColumn, String userEntry) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        String hql = "FROM Course c WHERE c." + selectedColumn + "= :" + selectedColumn;
        Query query = session.createQuery(hql);
        query.setParameter(selectedColumn, userEntry);
        List<Course> result = query.list();

        return result;
    }


}

