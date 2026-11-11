	package nhom3.Utils;
	
		import org.hibernate.SessionFactory;
		import org.hibernate.cfg.Configuration;
	
		public class HibernateUtils {
		    private static final SessionFactory sessionFactory;
	
		    static {
		        try {
		            sessionFactory = new Configuration()
		                    .configure("hibernate.cfg.xml")
		                    .buildSessionFactory();
		        } catch (Throwable ex) {
		            System.err.println("Lỗi tạo SessionFactory: " + ex);
		            throw new ExceptionInInitializerError(ex);
		        }
		    }
	
		    public static SessionFactory getSessionFactory() {
		        return sessionFactory;
		    }
		}