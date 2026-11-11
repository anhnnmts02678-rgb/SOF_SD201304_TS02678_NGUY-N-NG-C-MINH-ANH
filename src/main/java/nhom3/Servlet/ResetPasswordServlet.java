package nhom3.Servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import nhom3.Dao.UsersDao;
import nhom3.Model.Users;

@WebServlet("/ResetPasswordServlet")
public class ResetPasswordServlet extends HttpServlet {

    private UsersDao usersDao = new UsersDao();

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        String email = (String) session.getAttribute("email");

        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        if (!newPassword.equals(confirmPassword)) {

            request.setAttribute("message",
                    "Hai mật khẩu không khớp");

            request.getRequestDispatcher("reset-password.jsp")
                    .forward(request, response);
            return;
        }

        Users user = usersDao.getByEmail(email);

        if (user == null) {

            request.setAttribute("message",
                    "Không tìm thấy tài khoản");

            request.getRequestDispatcher("reset-password.jsp")
                    .forward(request, response);
            return;
        }

        user.setPassword(newPassword);

        usersDao.update(user);

        session.removeAttribute("otp");
        session.removeAttribute("email");

        response.sendRedirect("login.jsp");
    }
}