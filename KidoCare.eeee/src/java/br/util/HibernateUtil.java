
package br.util;

import org.hibernate.MappingException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class HibernateUtil {
 


    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() 
            throws MappingException {
        if (sessionFactory == null) {
            sessionFactory = new AnnotationConfiguration()
                    .configure().buildSessionFactory();
        }
        return sessionFactory;
    }
    public static Session getSession() {
        return getSessionFactory().openSession();
    }
}
    