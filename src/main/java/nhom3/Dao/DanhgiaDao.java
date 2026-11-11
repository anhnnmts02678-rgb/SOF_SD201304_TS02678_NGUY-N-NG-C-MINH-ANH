package nhom3.Dao;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
 
import nhom3.Model.Danhgia;
import nhom3.Utils.HibernateUtils;
public class DanhgiaDao {
    /** Use case: Danh gia - lay tat ca review cua 1 san pham */
    public List<Danhgia> getBySanPham(int maSP) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Query<Danhgia> q = session.createQuery(
                    "FROM Danhgia WHERE maSP = :maSP ORDER BY thoiGian DESC", Danhgia.class);
            q.setParameter("maSP", maSP);
            return q.list();
        } catch (Exception e) {
            System.out.println("Loi DanhGiaDAO.getBySanPham: " + e.getMessage());
            return null;
        }
    }
 
    /** Lay danh gia cua 1 khach hang voi 1 san pham cu the */
    public Danhgia getByKhachHangVaSanPham(int maKH, int maSP) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Query<Danhgia> q = session.createQuery(
                    "FROM Danhgia WHERE maKH = :maKH AND maSP = :maSP", Danhgia.class);
            q.setParameter("maKH", maKH);
            q.setParameter("maSP", maSP);
            return q.uniqueResult();
        } catch (Exception e) {
            System.out.println("Loi DanhGiaDAO.getByKhachHangVaSanPham: " + e.getMessage());
            return null;
        }
    }
 
    public List<Danhgia> getByKhachHang(int maKH) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Query<Danhgia> q = session.createQuery(
                    "FROM Danhgia WHERE maKH = :maKH ORDER BY thoiGian DESC", Danhgia.class);
            q.setParameter("maKH", maKH);
            return q.list();
        } catch (Exception e) {
            System.out.println("Loi DanhGiaDAO.getByKhachHang: " + e.getMessage());
            return null;
        }
    }
 
    public List<Danhgia> getAll() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery(
                    "FROM Danhgia ORDER BY thoiGian DESC", Danhgia.class).list();
        } catch (Exception e) {
            System.out.println("Loi DanhGiaDAO.getAll: " + e.getMessage());
            return null;
        }
    }
 
    public Danhgia getById(int maDG) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.find(Danhgia.class, maDG);
        } catch (Exception e) {
            System.out.println("Loi DanhGiaDAO.getById: " + e.getMessage());
            return null;
        }
    }
 
    /** Use case: Danh gia - them review san pham */
    public boolean insert(Danhgia dg) {
        Transaction tx = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(dg);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println("Loi DanhGiaDAO.insert: " + e.getMessage());
            return false;
        }
    }
 
    public boolean update(Danhgia dg) {
        Transaction tx = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(dg);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println("Loi DanhGiaDAO.update: " + e.getMessage());
            return false;
        }
    }
 
    /** Use case: Quan ly danh gia - Admin xoa review vi pham */
    public boolean delete(int maDG) {
        Transaction tx = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Danhgia dg = session.find(Danhgia.class, maDG);
            if (dg != null) {
                session.remove(dg);
                tx.commit();
                return true;
            }
            tx.rollback();
            return false;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println("Loi DanhGiaDAO.delete: " + e.getMessage());
            return false;
        }
    }
 
    /** Tinh diem trung binh cua san pham - thong ke */
    public double getAverageSoSao(int maSP) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Query<Double> q = session.createQuery(
                    "SELECT AVG(CAST(soSao AS double)) FROM Danhgia WHERE maSP = :maSP",
                    Double.class);
            q.setParameter("maSP", maSP);
            Double result = q.uniqueResult();
            return result != null ? result : 0.0;
        } catch (Exception e) {
            System.out.println("Loi DanhGiaDAO.getAverageSoSao: " + e.getMessage());
            return 0.0;
        }
    }

}
