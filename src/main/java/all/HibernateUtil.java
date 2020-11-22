/**
 * Osztaly a Hibernate konfiguralasahoz / kezelesehez
 */

package all;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

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
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}
