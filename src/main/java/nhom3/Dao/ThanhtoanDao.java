package nhom3.Dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import nhom3.Model.Thanhtoan;
import nhom3.Utils.HibernateUtils;

public class ThanhtoanDao {

    // Lấy tất cả thanh toán
    public List<Thanhtoan> getAll() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery(
                    "FROM Thanhtoan ORDER BY maThanhToan DESC",
                    Thanhtoan.class)
                    .list();
        } catch (Exception e) {
            System.out.println("Lỗi ThanhtoanDao.getAll: " + e.getMessage());
            return null;
        }
    }

    // Lấy theo mã thanh toán
    public Thanhtoan getById(int maThanhToan) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.find(Thanhtoan.class, maThanhToan);
        } catch (Exception e) {
            System.out.println("Lỗi ThanhtoanDao.getById: " + e.getMessage());
            return null;
        }
    }

    // Tìm theo phương thức thanh toán
    public List<Thanhtoan> getByPhuongThuc(String phuongThuc) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {

            Query<Thanhtoan> query = session.createQuery(
                    "FROM Thanhtoan WHERE phuongThuc = :pt",
                    Thanhtoan.class);

            query.setParameter("pt", phuongThuc);

            return query.list();

        } catch (Exception e) {
            System.out.println("Lỗi ThanhtoanDao.getByPhuongThuc: " + e.getMessage());
            return null;
        }
    }

    // Tìm theo trạng thái
    public List<Thanhtoan> getByTrangThai(String trangThai) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {

            Query<Thanhtoan> query = session.createQuery(
                    "FROM Thanhtoan WHERE trangThai = :tt",
                    Thanhtoan.class);

            query.setParameter("tt", trangThai);

            return query.list();

        } catch (Exception e) {
            System.out.println("Lỗi ThanhtoanDao.getByTrangThai: " + e.getMessage());
            return null;
        }
    }

    // Tìm theo mã giao dịch
    public Thanhtoan getByMaGiaoDich(String maGiaoDich) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {

            Query<Thanhtoan> query = session.createQuery(
                    "FROM Thanhtoan WHERE maGiaoDich = :maGD",
                    Thanhtoan.class);

            query.setParameter("maGD", maGiaoDich);

            return query.uniqueResult();

        } catch (Exception e) {
            System.out.println("Lỗi ThanhtoanDao.getByMaGiaoDich: " + e.getMessage());
            return null;
        }
    }

    // Thêm thanh toán
    public boolean insert(Thanhtoan tt) {

        Transaction tx = null;

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {

            tx = session.beginTransaction();

            session.persist(tt);

            tx.commit();

            return true;

        } catch (Exception e) {

            if (tx != null)
                tx.rollback();

            System.out.println("Lỗi ThanhtoanDao.insert: " + e.getMessage());

            return false;
        }
    }

    // Cập nhật thanh toán
    public boolean update(Thanhtoan tt) {

        Transaction tx = null;

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {

            tx = session.beginTransaction();

            session.merge(tt);

            tx.commit();

            return true;

        } catch (Exception e) {

            if (tx != null)
                tx.rollback();

            System.out.println("Lỗi ThanhtoanDao.update: " + e.getMessage());

            return false;
        }
    }

    // Cập nhật trạng thái
    public boolean updateTrangThai(int maThanhToan, String trangThai) {

        Transaction tx = null;

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {

            tx = session.beginTransaction();

            int rows = session.createMutationQuery(
                    "UPDATE Thanhtoan SET trangThai = :tt WHERE maThanhToan = :id")
                    .setParameter("tt", trangThai)
                    .setParameter("id", maThanhToan)
                    .executeUpdate();

            tx.commit();

            return rows > 0;

        } catch (Exception e) {

            if (tx != null)
                tx.rollback();

            System.out.println("Lỗi ThanhtoanDao.updateTrangThai: " + e.getMessage());

            return false;
        }
    }

    // Xóa thanh toán
    public boolean delete(int maThanhToan) {

        Transaction tx = null;

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {

            tx = session.beginTransaction();

            Thanhtoan tt = session.find(Thanhtoan.class, maThanhToan);

            if (tt == null) {
                tx.rollback();
                return false;
            }

            session.remove(tt);

            tx.commit();

            return true;

        } catch (Exception e) {

            if (tx != null)
                tx.rollback();

            System.out.println("Lỗi ThanhtoanDao.delete: " + e.getMessage());

            return false;
        }
    }
    
    public Thanhtoan getLast() {

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {

            return session.createQuery(
                    "FROM Thanhtoan ORDER BY maThanhToan DESC",
                    Thanhtoan.class)
                    .setMaxResults(1)
                    .uniqueResult();

        } catch (Exception e) {

            System.out.println("Lỗi getLast(): " + e.getMessage());
            return null;
        }
    }
}