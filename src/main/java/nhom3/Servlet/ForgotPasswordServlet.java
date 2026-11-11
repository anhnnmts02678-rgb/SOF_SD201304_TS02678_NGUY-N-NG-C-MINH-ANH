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
import nhom3.Utils.MailUtil;

	@WebServlet("/ForgotPasswordServlet")
	public class ForgotPasswordServlet
	        extends HttpServlet {

	    private UsersDao usersDao =
	            new UsersDao();

	    @Override
	    protected void doPost(
	            HttpServletRequest request,
	            HttpServletResponse response)
	            throws ServletException, IOException {

	        String email =
	                request.getParameter("email");

	        Users user =
	                usersDao.getByEmail(email);

	        if(user == null){

	            request.setAttribute(
	                    "error",
	                    "Email không tồn tại");

	            request.getRequestDispatcher(
	                    "forgot-password.jsp")
	                    .forward(request,response);

	            return;
	        }

	        String otp =
	                String.valueOf(
	                        (int)(Math.random()*900000)
	                        +100000);

	        HttpSession session =
	                request.getSession();

	        session.setAttribute(
	                "otp",
	                otp);

	        session.setAttribute(
	                "email",
	                email);

	        try {

	            MailUtil.sendOTP(
	                    email,
	                    otp);

	        } catch (Exception e) {

	            throw new ServletException(e);
	        }

	        response.sendRedirect(
	                "verify-otp.jsp");
	    }
	}
