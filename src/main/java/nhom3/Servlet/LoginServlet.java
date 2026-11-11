package nhom3.Servlet;

import java.io.IOException;

import org.hibernate.Session;
import org.hibernate.query.Query;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import nhom3.Dao.NhatkyDao;
import nhom3.Model.Admin;
import nhom3.Model.Employee;
import nhom3.Model.Users;
import nhom3.Utils.HibernateUtils;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private NhatkyDao nhatkyDao = new NhatkyDao();

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Session session = HibernateUtils.getSessionFactory().openSession();

        try {

            //==================== ADMIN ====================

            String hqlAdmin =
                    "FROM Admin WHERE " +
                    "(username=:u OR email=:u OR sdt=:u) " +
                    "AND password=:p " +
                    "AND trangThai=true";

            Query<Admin> adminQuery =
                    session.createQuery(hqlAdmin, Admin.class);

            adminQuery.setParameter("u", username);
            adminQuery.setParameter("p", password);

            Admin admin = adminQuery.uniqueResult();

            if (admin != null) {

                HttpSession ss = request.getSession();

                ss.setAttribute("admin", admin);
                ss.setAttribute("ten", admin.getHoTen());

                //========== GHI NHẬT KÝ ==========
                nhatkyDao.ghiLogAdmin(
                        admin.getMaAdmin(),
                        "Đăng nhập",
                        "Admin \"" + admin.getHoTen() + "\" đã đăng nhập hệ thống."
                );

                response.sendRedirect("admin/index.jsp");
                return;
            }

            //==================== NHÂN VIÊN ====================

            String hqlEmployee =
                    "FROM Employee WHERE " +
                    "(username=:u OR email=:u OR sdt=:u) " +
                    "AND password=:p " +
                    "AND trangThai=true";

            Query<Employee> empQuery =
                    session.createQuery(hqlEmployee, Employee.class);

            empQuery.setParameter("u", username);
            empQuery.setParameter("p", password);

            Employee emp = empQuery.uniqueResult();

            if (emp != null) {

                HttpSession ss = request.getSession();

                ss.setAttribute("employee", emp);
                ss.setAttribute("ten", emp.getHoTen());

                //========== GHI NHẬT KÝ ==========
                nhatkyDao.ghiLogNhanVien(
                        emp.getMaNV(),
                        "Đăng nhập",
                        "Nhân viên \"" + emp.getHoTen() + "\" đã đăng nhập hệ thống."
                );

                response.sendRedirect("employee/index.jsp");
                return;
            }

            //==================== KHÁCH HÀNG ====================

            String hqlUser =
                    "FROM Users WHERE " +
                    "(email=:u OR sdt=:u) " +
                    "AND password=:p " +
                    "AND trangThai=true";

            Query<Users> userQuery =
                    session.createQuery(hqlUser, Users.class);

            userQuery.setParameter("u", username);
            userQuery.setParameter("p", password);

            Users user = userQuery.uniqueResult();

            if (user != null) {

                HttpSession ss = request.getSession();

                ss.setAttribute("user", user);
                ss.setAttribute("ten", user.getFullName());

                // User cũng ghi vào nhật ký
                nhatkyDao.ghiLogNhanVien(
                        null,
                        "Khách hàng đăng nhập",
                        "Khách hàng \"" + user.getFullName() + "\" đã đăng nhập."
                );

                response.sendRedirect("index.jsp");
                return;
            }

            request.setAttribute("message",
                    "Sai tài khoản hoặc mật khẩu!");

            request.getRequestDispatcher("login.jsp")
                    .forward(request, response);

        } finally {

            session.close();

        }

    }

}