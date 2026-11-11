package nhom3.Utils;

import org.hibernate.SessionFactory;

import org.hibernate.cfg.Configuration;



public class JPAUtils {

    private static final SessionFactory factory;



    static {

        try {

            factory = new Configuration()

                    .configure()

                    .buildSessionFactory();

        } catch (Exception e) {

            throw new ExceptionInInitializerError(e);

        }

    }



    public static SessionFactory getFactory() {

        return factory;

    }

}