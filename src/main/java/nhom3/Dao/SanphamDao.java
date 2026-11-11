package nhom3.Dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import nhom3.Model.Sanpham;
import nhom3.Utils.HibernateUtils;

public class SanphamDao {

    // ===========================
    // Lấy tất cả sản phẩm
    // ===========================
    public List<Sanpham> getAll() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {

            return session.createQuery(
                    "FROM Sanpham ORDER BY maSP DESC",
                    Sanpham.class)
                    .list();

        } catch (Exception e) {
            System.out.println("Lỗi getAll(): " + e.getMessage());
            return null;
        }
    }

    // ===========================
    // Lấy theo ID
    // ===========================
    public Sanpham getById(int maSP) {

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {

            return session.find(Sanpham.class, maSP);

        } catch (Exception e) {

            System.out.println("Lỗi getById(): " + e.getMessage());
            return null;
        }
    }

    // ===========================
    // Tìm theo tên
    // ===========================
    public List<Sanpham> getByTen(String tenSP) {

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {

            Query<Sanpham> query = session.createQuery(
                    "FROM Sanpham WHERE tenSP LIKE :ten ORDER BY maSP DESC",
                    Sanpham.class);

            query.setParameter("ten", "%" + tenSP + "%");

            return query.list();

        } catch (Exception e) {

            System.out.println("Lỗi getByTen(): " + e.getMessage());
            return null;
        }
    }

    // ===========================
    // Theo danh mục
    // ===========================
    public List<Sanpham> getByDanhMuc(int maDM) {

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {

            Query<Sanpham> query = session.createQuery(
                    "FROM Sanpham\r\n"
                    + "WHERE maDM=:maDM\r\n"
                    + "AND trangThai=true ORDER BY maSP DESC",
                    Sanpham.class);

            query.setParameter("maDM", maDM);

            return query.list();

        } catch (Exception e) {

            System.out.println("Lỗi getByDanhMuc(): " + e.getMessage());
            return null;
        }
    }

    // ===========================
    // Theo nhân viên
    // ===========================
    public List<Sanpham> getByNhanVien(int maNV) {

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {

            Query<Sanpham> query = session.createQuery(
                    "FROM Sanpham WHERE maNV = :maNV ORDER BY maSP DESC",
                    Sanpham.class);

            query.setParameter("maNV", maNV);

            return query.list();

        } catch (Exception e) {

            System.out.println("Lỗi getByNhanVien(): " + e.getMessage());
            return null;
        }
    }

    // ===========================
    // Theo khuyến mãi
    // ===========================
    public List<Sanpham> getByKhuyenMai(int maKM) {

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {

            Query<Sanpham> query = session.createQuery(
                    "FROM Sanpham WHERE maKM = :maKM ORDER BY maSP DESC",
                    Sanpham.class);

            query.setParameter("maKM", maKM);

            return query.list();

        } catch (Exception e) {

            System.out.println("Lỗi getByKhuyenMai(): " + e.getMessage());
            return null;
        }
    }

    // ===========================
    // Theo trạng thái
    // ===========================
    public List<Sanpham> getByTrangThai(boolean trangThai) {

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {

            Query<Sanpham> query = session.createQuery(
                    "FROM Sanpham WHERE trangThai = :tt ORDER BY maSP DESC",
                    Sanpham.class);

            query.setParameter("tt", trangThai);

            return query.list();

        } catch (Exception e) {

            System.out.println("Lỗi getByTrangThai(): " + e.getMessage());
            return null;
        }
    }

    // ===========================
    // Thêm sản phẩm
    // ===========================
    public boolean insert(Sanpham sp) {

        Transaction tx = null;

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {

            tx = session.beginTransaction();

            session.persist(sp);

            session.flush();

            tx.commit();

            return true;

        } catch (Exception e) {

            if (tx != null && tx.isActive()) {
                tx.rollback();
            }

            e.printStackTrace();

            return false;
        }
    }

    // ===========================
    // Cập nhật sản phẩm
    // ===========================
    public boolean update(Sanpham sp) {

        Transaction tx = null;

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {

            tx = session.beginTransaction();

            session.merge(sp);

            session.flush();

            tx.commit();

            return true;

        } catch (Exception e) {

            if (tx != null && tx.isActive()) {
                tx.rollback();
            }

            e.printStackTrace();

            return false;
        }
    }

    // ===========================
    // Xóa sản phẩm
    // ===========================
    public boolean delete(int maSP) {

        Transaction tx = null;

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {

            tx = session.beginTransaction();

            Sanpham sp = session.find(Sanpham.class, maSP);

            if (sp == null) {

                tx.rollback();

                return false;
            }

            session.remove(sp);

            tx.commit();

            return true;

        } catch (Exception e) {

            if (tx != null && tx.isActive()) {
                tx.rollback();
            }

            e.printStackTrace();

            return false;
        }
    }
    
    public boolean truTonKho(int maSP, int soLuong) {

        Transaction tx = null;

        try(Session session = HibernateUtils.getSessionFactory().openSession()){

            tx = session.beginTransaction();

            int row = session.createMutationQuery(
                    "UPDATE Sanpham " +
                    "SET soLuong = soLuong - :sl " +
                    "WHERE maSP = :id")
                    .setParameter("sl", soLuong)
                    .setParameter("id", maSP)
                    .executeUpdate();

            tx.commit();

            return row > 0;

        }catch(Exception e){

            if(tx != null) tx.rollback();

            e.printStackTrace();

            return false;
        }
    }
    
    public boolean congTonKho(int maSP, int soLuong) {

        Transaction tx = null;

        try(Session session = HibernateUtils.getSessionFactory().openSession()){

            tx = session.beginTransaction();

            int row = session.createMutationQuery(
                    "UPDATE Sanpham " +
                    "SET soLuong = soLuong + :sl " +
                    "WHERE maSP = :id")
                    .setParameter("sl", soLuong)
                    .setParameter("id", maSP)
                    .executeUpdate();

            tx.commit();

            return row > 0;

        }catch(Exception e){

            if(tx != null) tx.rollback();

            e.printStackTrace();

            return false;
        }
    }

}