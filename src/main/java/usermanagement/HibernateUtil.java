/**
 * Osztaly a Hibernate konfiguralasahoz / kezelesehez
 */

package usermanagement;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;


@SuppressWarnings("all")
public class HibernateUtil {

    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;

    /**
     * Hibernate konfiguralasa a hibernate.cfg.xml alapjan
     */
    public static void setup() {
        try {
            //create registry
            registry = new StandardServiceRegistryBuilder().configure().build();

            //create MetadataSources
            MetadataSources sources = new MetadataSources(registry);

            //create Metadata
            Metadata metadata = sources.getMetadataBuilder().build();

            //create sessionFactory
            sessionFactory = metadata.getSessionFactoryBuilder().build();
        } catch (Exception e) {
            e.printStackTrace();
            if (registry != null) {
                StandardServiceRegistryBuilder.destroy(registry);
            }
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}
