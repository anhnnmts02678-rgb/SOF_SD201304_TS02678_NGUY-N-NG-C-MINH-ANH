package nhom3.Servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;


	@WebServlet("/VerifyOtpServlet")
	public class VerifyOtpServlet
	        extends HttpServlet {

	    @Override
	    protected void doPost(
	            HttpServletRequest request,
	            HttpServletResponse response)
	            throws ServletException, IOException {

	        HttpSession session =
	                request.getSession();

	        String otpSession =
	                (String) session.getAttribute("otp");

	        String otpInput =
	                request.getParameter("otp");

	        if(otpSession != null &&
	           otpSession.equals(otpInput)){

	            response.sendRedirect(
	                    "reset-password.jsp");

	        }else{

	            request.setAttribute(
	                    "error",
	                    "OTP không đúng");

	            request.getRequestDispatcher(
	                    "verify-otp.jsp")
	                    .forward(request,response);
	        }
	    }
	}
