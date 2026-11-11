package nhom3.Dao;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
 
import nhom3.Model.Danhmuc;
import nhom3.Model.Sanpham;
import nhom3.Utils.HibernateUtils;
public class DanhmucDao {
    public List<Danhmuc> getAll() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("FROM Danhmuc", Danhmuc.class).list();
        } catch (Exception e) {
            System.out.println("Loi DanhmucDAO.getAll: " + e.getMessage());
            return null;
        }
    }
 
    public Danhmuc getById(int maDM) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.find(Danhmuc.class, maDM);
        } catch (Exception e) {
            System.out.println("Loi DanhMucDAO.getById: " + e.getMessage());
            return null;
        }
    }
 
    public Danhmuc getByTen(String tenDanhMuc) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Query<Danhmuc> q = session.createQuery(
                    "FROM Danhmuc WHERE tenDanhMuc = :ten", Danhmuc.class);
            q.setParameter("ten", tenDanhMuc);
            return q.uniqueResult();
        } catch (Exception e) {
            System.out.println("Loi DanhMucDAO.getByTen: " + e.getMessage());
            return null;
        }
    }
 
    public boolean insert(Danhmuc dm) {
        Transaction tx = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(dm);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println("Loi DanhMucDAO.insert: " + e.getMessage());
            return false;
        }
    }
 
    public boolean update(Danhmuc dm) {
        Transaction tx = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(dm);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println("Loi DanhMucDAO.update: " + e.getMessage());
            return false;
        }
    }
 
    public boolean delete(int maDM) {
        Transaction tx = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Danhmuc dm = session.find(Danhmuc.class, maDM);
            if (dm != null) {
                session.remove(dm);
                tx.commit();
                return true;
            }
            tx.rollback();
            return false;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println("Loi DanhMucDAO.delete: " + e.getMessage());
            return false;
        }
    }
    public List<Sanpham> getProducts(int maDM){
        try(Session session=HibernateUtils.getSessionFactory().openSession()){

            Query<Sanpham> q=session.createQuery(
                "FROM Sanpham s WHERE maDM=:id",
                Sanpham.class);

            q.setParameter("id",maDM);

            return q.list();
        }
    }
}
