package nhom3.Dao;

import java.sql.Date;
import java.util.List;
 
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
 
import nhom3.Model.Khuyenmai;
import nhom3.Utils.HibernateUtils;
 
public class KhuyenmaiDao {
 
    public List<Khuyenmai> getAll() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("FROM Khuyenmai", Khuyenmai.class).list();
        } catch (Exception e) {
            System.out.println("Loi KhuyenMaiDAO.getAll: " + e.getMessage());
            return null;
        }
    }
 
    /** Lay cac khuyen mai dang hoat dong (con hieu luc va trang thai = true) */
    public List<Khuyenmai> getAllActive() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Date today = new Date(System.currentTimeMillis());
            Query<Khuyenmai> q = session.createQuery(
                    "FROM Khuyenmai WHERE trangThai = true "
                    + "AND ngayBatDau <= :today AND ngayKetThuc >= :today",
                    Khuyenmai.class);
            q.setParameter("today", today);
            return q.list();
        } catch (Exception e) {
            System.out.println("Loi KhuyenMaiDAO.getAllActive: " + e.getMessage());
            return null;
        }
    }
 
    public Khuyenmai getById(int maKM) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.find(Khuyenmai.class, maKM);
        } catch (Exception e) {
            System.out.println("Loi KhuyenMaiDAO.getById: " + e.getMessage());
            return null;
        }
    }
 
    public List<Khuyenmai> getByLoai(String loaiKM) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Query<Khuyenmai> q = session.createQuery(
                    "FROM Khuyenmai WHERE loaiKM = :loai", Khuyenmai.class);
            q.setParameter("loai", loaiKM);
            return q.list();
        } catch (Exception e) {
            System.out.println("Loi KhuyenMaiDAO.getByLoai: " + e.getMessage());
            return null;
        }
    }
 
    /** Use case: Quan ly khuyen mai - Admin them khuyen mai moi */
    public boolean insert(Khuyenmai km) {
        Transaction tx = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(km);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println("Loi KhuyenMaiDAO.insert: " + e.getMessage());
            return false;
        }
    }
 
    /** Use case: Quan ly khuyen mai - Admin chinh sua khuyen mai */
    public boolean update(Khuyenmai km) {
        Transaction tx = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(km);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println("Loi KhuyenMaiDAO.update: " + e.getMessage());
            return false;
        }
    }
 
    /** Bat / tat khuyen mai */
    public boolean updateTrangThai(int maKM, boolean trangThai) {
        Transaction tx = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            int rows = session.createMutationQuery(
                    "UPDATE Khuyenmai SET trangThai = :tt WHERE maKM = :id")
                    .setParameter("tt", trangThai)
                    .setParameter("id", maKM)
                    .executeUpdate();
            tx.commit();
            return rows > 0;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println("Loi KhuyenMaiDAO.updateTrangThai: " + e.getMessage());
            return false;
        }
    }
 
    public boolean delete(int maKM) {
        Transaction tx = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Khuyenmai km = session.find(Khuyenmai.class, maKM);
            if (km != null) {
                session.remove(km);
                tx.commit();
                return true;
            }
            tx.rollback();
            return false;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println("Loi KhuyenMaiDAO.delete: " + e.getMessage());
            return false;
        }
    }
}