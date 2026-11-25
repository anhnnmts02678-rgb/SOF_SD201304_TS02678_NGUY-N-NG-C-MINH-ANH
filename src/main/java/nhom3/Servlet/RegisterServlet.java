package nhom3.Servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import nhom3.Dao.UsersDao;
import nhom3.Utils.MailUtil;


@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {

    private UsersDao usersDao;

public RegisterServlet() {
    this.usersDao = new UsersDao();
}

// Constructor dùng cho Unit Test
RegisterServlet(UsersDao usersDao) {
    this.usersDao = usersDao;
}

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String sdt = request.getParameter("sdt");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String address = request.getParameter("address");

        // kiểm tra mật khẩu
        if (!password.equals(confirmPassword)) {

            request.setAttribute("message",
                    "Mật khẩu nhập lại không khớp!");

            request.getRequestDispatcher("register.jsp")
                    .forward(request, response);
            return;
        }

        // kiểm tra email
        if (usersDao.getByEmail(email) != null) {

            request.setAttribute("message",
                    "Email đã được sử dụng!");

            request.getRequestDispatcher("register.jsp")
                    .forward(request, response);
            return;
        }

        // kiểm tra SĐT
        if (usersDao.getBySdt(sdt) != null) {

            request.setAttribute("message",
                    "Số điện thoại đã được sử dụng!");

            request.getRequestDispatcher("register.jsp")
                    .forward(request, response);
            return;
        }

        // tạo OTP
        String otp = String.valueOf(
                (int) (Math.random() * 900000) + 100000);

        HttpSession session = request.getSession();

        session.setAttribute("registerOTP", otp);

        session.setAttribute("registerFullName", fullName);
        session.setAttribute("registerEmail", email);
        session.setAttribute("registerPassword", password);
        session.setAttribute("registerSdt", sdt);
        session.setAttribute("registerAddress", address);

        try {

            MailUtil.sendOTP(email, otp);

        } catch (Exception e) {

            throw new ServletException(e);

        }

        response.sendRedirect("verify-register-otp.jsp");
    }
}