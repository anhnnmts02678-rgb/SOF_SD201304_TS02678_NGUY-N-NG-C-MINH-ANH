package nhom3.Dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
 
import nhom3.Model.Admin;
import nhom3.Utils.HibernateUtils;
 
public class AdminDao {
 
    // ===================== DANG NHAP =====================
 
    public Admin login(String username, String password) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            String hql = "FROM Admin WHERE username = :u AND password = :p AND trangThai = true";
            Query<Admin> q = session.createQuery(hql, Admin.class);
            q.setParameter("u", username);
            q.setParameter("p", password);
            return q.uniqueResult();
        } catch (Exception e) {
            System.out.println("Loi AdminDAO.login: " + e.getMessage());
            return null;
        }
    }
 
    // ===================== CRUD =====================
 
    public List<Admin> getAll() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("FROM Admin", Admin.class).list();
        } catch (Exception e) {
            System.out.println("Loi AdminDAO.getAll: " + e.getMessage());
            return null;
        }
    }
 
    public Admin getById(int maAdmin) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.find(Admin.class, maAdmin);
        } catch (Exception e) {
            System.out.println("Loi AdminDAO.getById: " + e.getMessage());
            return null;
        }
    }
 
    public Admin getByUsername(String username) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Query<Admin> q = session.createQuery("FROM Admin WHERE username = :u", Admin.class);
            q.setParameter("u", username);
            return q.uniqueResult();
        } catch (Exception e) {
            System.out.println("Loi AdminDAO.getByUsername: " + e.getMessage());
            return null;
        }
    }
 
    public boolean insert(Admin admin) {
        Transaction tx = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(admin);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println("Loi AdminDAO.insert: " + e.getMessage());
            return false;
        }
    }
 
    public boolean update(Admin admin) {
        Transaction tx = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(admin);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println("Loi AdminDAO.update: " + e.getMessage());
            return false;
        }
    }
 
    public boolean updatePassword(int maAdmin, String newPassword) {
        Transaction tx = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            int rows = session.createMutationQuery(
                    "UPDATE Admin SET password = :p WHERE maAdmin = :id")
                    .setParameter("p", newPassword)
                    .setParameter("id", maAdmin)
                    .executeUpdate();
            tx.commit();
            return rows > 0;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println("Loi AdminDAO.updatePassword: " + e.getMessage());
            return false;
        }
    }
 
    public boolean updateTrangThai(int maAdmin, boolean trangThai) {
        Transaction tx = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            int rows = session.createMutationQuery(
                    "UPDATE Admin SET trangThai = :tt WHERE maAdmin = :id")
                    .setParameter("tt", trangThai)
                    .setParameter("id", maAdmin)
                    .executeUpdate();
            tx.commit();
            return rows > 0;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println("Loi AdminDAO.updateTrangThai: " + e.getMessage());
            return false;
        }
    }
 
    public boolean delete(int maAdmin) {
        Transaction tx = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Admin admin = session.find(Admin.class, maAdmin);
            if (admin != null) {
                session.remove(admin);
                tx.commit();
                return true;
            }
            tx.rollback();
            return false;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println("Loi AdminDAO.delete: " + e.getMessage());
            return false;
        }
    }
}