package nhom3.Dao;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
 
import nhom3.Model.Employee;
import nhom3.Utils.HibernateUtils;
public class EmployeeDao {
    public Employee login(String username, String password) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Query<Employee> q = session.createQuery(
                    "FROM Employee WHERE username = :u AND password = :p AND trangThai = true",
                    Employee.class);
            q.setParameter("u", username);
            q.setParameter("p", password);
            return q.uniqueResult();
        } catch (Exception e) {
            System.out.println("Loi EmployeeDAO.login: " + e.getMessage());
            return null;
        }
    }
 
    // ===================== CRUD =====================
 
    public List<Employee> getAll() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("FROM Employee", Employee.class).list();
        } catch (Exception e) {
            System.out.println("Loi EmployeeDAO.getAll: " + e.getMessage());
            return null;
        }
    }
 
    public Employee getById(int maNV) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.find(Employee.class, maNV);
        } catch (Exception e) {
            System.out.println("Loi EmployeeDAO.getById: " + e.getMessage());
            return null;
        }
    }
 
    public Employee getByUsername(String username) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Query<Employee> q = session.createQuery(
                    "FROM Employee WHERE username = :u", Employee.class);
            q.setParameter("u", username);
            return q.uniqueResult();
        } catch (Exception e) {
            System.out.println("Loi EmployeeDAO.getByUsername: " + e.getMessage());
            return null;
        }
    }
 
    public boolean insert(Employee emp) {
        Transaction tx = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(emp);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println("Loi EmployeeDAO.insert: " + e.getMessage());
            return false;
        }
    }
 
    public boolean update(Employee emp) {
        Transaction tx = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(emp);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println("Loi EmployeeDAO.update: " + e.getMessage());
            return false;
        }
    }
 
    public boolean updatePassword(int maNV, String newPassword) {
        Transaction tx = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            int rows = session.createMutationQuery(
                    "UPDATE Employee SET password = :p WHERE maNV = :id")
                    .setParameter("p", newPassword)
                    .setParameter("id", maNV)
                    .executeUpdate();            tx.commit();
            return rows > 0;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println("Loi EmployeeDAO.updatePassword: " + e.getMessage());
            return false;
        }
    }
 
    public boolean updateTrangThai(int maNV, boolean trangThai) {

        Transaction tx = null;

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {

            tx = session.beginTransaction();

            int rows = session.createMutationQuery(
                    "UPDATE Employee SET trangThai = :tt WHERE maNV = :id")
                    .setParameter("tt", trangThai)
                    .setParameter("id", maNV)
                    .executeUpdate();

            tx.commit();

            return rows > 0;

        } catch (Exception e) {

            if (tx != null)
                tx.rollback();

            e.printStackTrace();

            return false;
        }
    }
 
    public boolean delete(int maNV) {

        Transaction tx = null;

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {

            tx = session.beginTransaction();

            Employee emp = session.find(Employee.class, maNV);

            if (emp == null) {
                tx.rollback();
                return false;
            }

            session.remove(emp);

            tx.commit();

            return true;

        } catch (Exception e) {

            if (tx != null)
                tx.rollback();

            e.printStackTrace();

            return false;
        }
    }
 // ===================== SEARCH =====================

    public List<Employee> searchByKeyword(String keyword) {

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {

            Query<Employee> query = session.createQuery(
                    "FROM Employee WHERE " +
                    "lower(hoTen) LIKE :kw OR " +
                    "lower(username) LIKE :kw OR " +
                    "lower(email) LIKE :kw",
                    Employee.class);

            query.setParameter("kw", "%" + keyword.toLowerCase() + "%");

            return query.list();

        } catch (Exception e) {

            e.printStackTrace();

            return null;
        }
    }
}
