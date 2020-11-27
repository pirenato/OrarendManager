/**
 * Osztaly a Hibernate konfiguralasahoz / kezelesehez
 */

package usermanagement;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@SuppressWarnings("all")
public class HibernateUtil {

    private static SessionFactory sessionFactory;

    /**
     * Hibernate konfiguralasa a hibernate.cfg.xml alapjan
     */
    public static void setup() {
        Configuration config = new Configuration();
        config.configure();

        try {
            sessionFactory = config.buildSessionFactory();

        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
