package nhom3.Servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import nhom3.Dao.NhatkyDao;
import nhom3.Model.Admin;
import nhom3.Model.Employee;
import nhom3.Model.Users;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private NhatkyDao nhatkyDao = new NhatkyDao();

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session != null) {

            Admin admin = (Admin) session.getAttribute("admin");
            Employee employee = (Employee) session.getAttribute("employee");
            Users user = (Users) session.getAttribute("user");

            //========== ADMIN ==========
            if (admin != null) {

                nhatkyDao.ghiLogAdmin(
                        admin.getMaAdmin(),
                        "Đăng xuất",
                        "Admin \"" + admin.getHoTen() + "\" đã đăng xuất."
                );
            }

            //========== NHÂN VIÊN ==========
            if (employee != null) {

                nhatkyDao.ghiLogNhanVien(
                        employee.getMaNV(),
                        "Đăng xuất",
                        "Nhân viên \"" + employee.getHoTen() + "\" đã đăng xuất."
                );
            }

            //========== KHÁCH HÀNG ==========
            if (user != null) {

                nhatkyDao.ghiLogNhanVien(
                        null,
                        "Khách hàng đăng xuất",
                        "Khách hàng \"" + user.getFullName() + "\" đã đăng xuất."
                );
            }

            session.invalidate();
        }

        response.sendRedirect("login.jsp");
    }
}