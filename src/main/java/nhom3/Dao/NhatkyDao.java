package nhom3.Dao;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import nhom3.Model.Nhatky;
import nhom3.Utils.HibernateUtils;

public class NhatkyDao {

    public List<Nhatky> getAll() {

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {

            return session.createQuery(
                    "FROM Nhatky ORDER BY thoiGian DESC",
                    Nhatky.class)
                    .list();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Nhatky getById(int maNK) {

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {

            return session.find(Nhatky.class, maNK);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Nhatky> getByNhanVien(Integer maNV) {

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {

            Query<Nhatky> q = session.createQuery(
                    "FROM Nhatky WHERE maNV = :maNV ORDER BY thoiGian DESC",
                    Nhatky.class);

            q.setParameter("maNV", maNV);

            return q.list();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Nhatky> getByAdmin(Integer maAdmin) {

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {

            Query<Nhatky> q = session.createQuery(
                    "FROM Nhatky WHERE maAdmin = :maAdmin ORDER BY thoiGian DESC",
                    Nhatky.class);

            q.setParameter("maAdmin", maAdmin);

            return q.list();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Nhatky> getByHanhDong(String hanhDong) {

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {

            Query<Nhatky> q = session.createQuery(
                    "FROM Nhatky WHERE hanhDong LIKE :hd ORDER BY thoiGian DESC",
                    Nhatky.class);

            q.setParameter("hd", "%" + hanhDong + "%");

            return q.list();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Nhatky> getByThoiGian(Timestamp tuNgay, Timestamp denNgay) {

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {

            Query<Nhatky> q = session.createQuery(
                    "FROM Nhatky WHERE thoiGian BETWEEN :tuNgay AND :denNgay ORDER BY thoiGian DESC",
                    Nhatky.class);

            q.setParameter("tuNgay", tuNgay);
            q.setParameter("denNgay", denNgay);

            return q.list();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean insert(Nhatky nk) {

        Transaction tx = null;

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {

            tx = session.beginTransaction();

            session.persist(nk);

            tx.commit();

            return true;

        } catch (Exception e) {

            if (tx != null)
                tx.rollback();

            e.printStackTrace();

            return false;
        }
    }

    public boolean ghiLogNhanVien(Integer maNV,
            String hanhDong,
            String moTa) {

        Nhatky nk = new Nhatky();

        nk.setMaNV(maNV);
        nk.setMaAdmin(null);
        nk.setHanhDong(hanhDong);
        nk.setMoTa(moTa);
        nk.setThoiGian(new Timestamp(System.currentTimeMillis()));

        return insert(nk);
    }

    public boolean ghiLogAdmin(int maAdmin, String hanhDong, String moTa) {

        Nhatky nk = new Nhatky();

        nk.setMaNV(null);
        nk.setMaAdmin(maAdmin);
        nk.setHanhDong(hanhDong);
        nk.setMoTa(moTa);
        nk.setThoiGian(new Timestamp(System.currentTimeMillis()));

        return insert(nk);
    }

    public boolean delete(int maNK) {

        Transaction tx = null;

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {

            tx = session.beginTransaction();

            Nhatky nk = session.find(Nhatky.class, maNK);

            if (nk == null) {
                tx.rollback();
                return false;
            }

            session.remove(nk);

            tx.commit();

            return true;

        } catch (Exception e) {

            if (tx != null)
                tx.rollback();

            e.printStackTrace();

            return false;
        }
    }
}
