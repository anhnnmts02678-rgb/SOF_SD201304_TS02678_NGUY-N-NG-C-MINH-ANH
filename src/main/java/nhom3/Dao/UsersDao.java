package nhom3.Dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import nhom3.Model.Users;
import nhom3.Utils.HibernateUtils;

public class UsersDao {

    // Lấy tất cả khách hàng
    public List<Users> getAll() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery(
                    "FROM Users ORDER BY maKH DESC",
                    Users.class)
                    .list();
        } catch (Exception e) {
            System.out.println("Lỗi getAll(): " + e.getMessage());
            return null;
        }
    }

    // Tìm theo mã khách hàng
    public Users getById(int maKH) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.find(Users.class, maKH);
        } catch (Exception e) {
            System.out.println("Lỗi getById(): " + e.getMessage());
            return null;
        }
    }

    // Tìm theo Email
    public Users getByEmail(String email) {

        Session session = HibernateUtils.getSessionFactory().openSession();

        try {

            System.out.println("Tìm email = " + email);

            String hql = "FROM Users WHERE email = :email";

            Users user = session.createQuery(hql, Users.class)
                    .setParameter("email", email.trim())
                    .uniqueResult();

            System.out.println("Kết quả = " + user);

            return user;

        } finally {
            session.close();
        }
    }


    // Tìm theo số điện thoại
    public Users getBySdt(String sdt) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {

            Query<Users> query = session.createQuery(
                    "FROM Users WHERE sdt = :sdt",
                    Users.class);

            query.setParameter("sdt", sdt);

            return query.uniqueResult();

        } catch (Exception e) {
            System.out.println("Lỗi getBySdt(): " + e.getMessage());
            return null;
        }
    }

    // Đăng nhập
    public Users login(String email, String password) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {

            Query<Users> query = session.createQuery(
                    "FROM Users WHERE email = :email AND password = :password AND trangThai = true",
                    Users.class);

            query.setParameter("email", email);
            query.setParameter("password", password);

            return query.uniqueResult();

        } catch (Exception e) {
            System.out.println("Lỗi login(): " + e.getMessage());
            return null;
        }
    }

    // Tìm theo tên
    public List<Users> searchByName(String keyword) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {

            Query<Users> query = session.createQuery(
                    "FROM Users WHERE fullName LIKE :name",
                    Users.class);

            query.setParameter("name", "%" + keyword + "%");

            return query.list();

        } catch (Exception e) {
            System.out.println("Lỗi searchByName(): " + e.getMessage());
            return null;
        }
    }

    // Thêm khách hàng
    public boolean insert(Users user) {

        Transaction tx = null;
        Session session = null;

        try {

            session = HibernateUtils.getSessionFactory().openSession();

            tx = session.beginTransaction();

            session.persist(user);

            tx.commit();

            return true;

        } catch (Exception e) {

            if (tx != null && tx.isActive()) {
                tx.rollback();
            }

            System.out.println("Lỗi insert(): ");
            e.printStackTrace();

            return false;

        } finally {

            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    // Cập nhật khách hàng
    public boolean update(Users user) {

        Transaction tx = null;

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {

            tx = session.beginTransaction();

            session.merge(user);

            tx.commit();

            return true;

        } catch (Exception e) {

            if (tx != null)
                tx.rollback();

            System.out.println("Lỗi update(): " + e.getMessage());

            return false;
        }
    }

    // Xóa khách hàng
    public boolean delete(int maKH) {

        Transaction tx = null;

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {

            tx = session.beginTransaction();

            Users user = session.find(Users.class, maKH);

            if (user == null) {
                tx.rollback();
                return false;
            }

            session.remove(user);

            tx.commit();

            return true;

        } catch (Exception e) {

            if (tx != null)
                tx.rollback();

            System.out.println("Lỗi delete(): " + e.getMessage());

            return false;
        }
    }

}