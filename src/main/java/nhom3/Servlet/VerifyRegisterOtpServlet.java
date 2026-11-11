	package nhom3.Servlet;
	
	import java.io.IOException;
	
	import org.hibernate.Session;
	import org.hibernate.Transaction;
	
	import jakarta.servlet.ServletException;
	import jakarta.servlet.annotation.WebServlet;
	import jakarta.servlet.http.HttpServlet;
	import jakarta.servlet.http.HttpServletRequest;
	import jakarta.servlet.http.HttpServletResponse;
	import jakarta.servlet.http.HttpSession;
	import nhom3.Model.Users;
	import nhom3.Utils.HibernateUtils;
	
	@WebServlet("/VerifyRegisterOtpServlet")
		public class VerifyRegisterOtpServlet
		        extends HttpServlet {
	
		    @Override
		    protected void doPost(
		            HttpServletRequest request,
		            HttpServletResponse response)
		            throws ServletException, IOException {
	
		        String otpInput =
		                request.getParameter("otp");
	
		        HttpSession session =
		                request.getSession();
	
		        String otpSession =
		                (String) session.getAttribute(
		                        "registerOTP");
		        System.out.println("OTP INPUT = " + otpInput);
		        System.out.println("OTP SESSION = " + otpSession);
		        
		        System.out.println("FULLNAME = " +
		                session.getAttribute("registerFullName"));
	
		        System.out.println("EMAIL = " +
		                session.getAttribute("registerEmail"));
	
		        System.out.println("SDT = " +
		                session.getAttribute("registerSdt"));
	
		        System.out.println("PASSWORD = " +
		                session.getAttribute("registerPassword"));
	
		        System.out.println("ADDRESS = " +
		                session.getAttribute("registerAddress"));
		        
		        String fullName =
		                (String) session.getAttribute("registerFullName");
	
		        String email =
		                (String) session.getAttribute("registerEmail");
	
		        String password =
		                (String) session.getAttribute("registerPassword");
	
		        String sdt =
		                (String) session.getAttribute("registerSdt");
	
		        String address =
		                (String) session.getAttribute("registerAddress");
	
		        if (otpSession != null
		                && otpSession.equals(otpInput)) {
		        	System.out.println("FULLNAME = "
		        	        + session.getAttribute("registerFullName"));
	
		        	System.out.println("EMAIL = "
		        	        + session.getAttribute("registerEmail"));
	
		        	System.out.println("PASSWORD = "
		        	        + session.getAttribute("registerPassword"));
	
		        	System.out.println("SDT = "
		        	        + session.getAttribute("registerSdt"));
	
		        	System.out.println("ADDRESS = "
		        	        + session.getAttribute("registerAddress"));
		            Users user = new Users();
	
		            user.setFullName(
		                    (String) session.getAttribute("registerFullName"));
	
		            user.setEmail(
		                    (String) session.getAttribute("registerEmail"));
	
		            user.setPassword(
		                    (String) session.getAttribute("registerPassword"));
	
		            user.setSdt(
		                    (String) session.getAttribute("registerSdt"));
	
		            user.setAddress(
		                    (String) session.getAttribute("registerAddress"));
	
		            user.setTrangThai(true);
	
		            Session hSession =
		                    HibernateUtils
		                    .getSessionFactory()
		                    .openSession();
	
		            Transaction tx =
		                    hSession.beginTransaction();
	
		            try {
		            	System.out.println("FULLNAME = " + user.getFullName());
		            	System.out.println("EMAIL = " + user.getEmail());
		            	System.out.println("SDT = " + user.getSdt());
		            	System.out.println("PASSWORD = " + user.getPassword());
		                hSession.persist(user);
	
		                tx.commit();
	
		                session.removeAttribute(
		                        "registerOTP");
	
		                session.removeAttribute(
		                        "registerUser");
	
		                response.sendRedirect(
		                        "login.jsp");
	
		            } catch (Exception e) {
	
		                    tx.rollback();
	
		                    e.printStackTrace();
	
		                    request.setAttribute(
		                            "message",
		                            "Lỗi lưu tài khoản: " + e.getMessage());
	
		                    request.getRequestDispatcher(
		                            "/verify-register-otp.jsp")
		                            .forward(request, response);
	
		                    return;
		                }
		        } else {
	
		            request.setAttribute(
		                    "message",
		                    "OTP không đúng!");
	
		            request.getRequestDispatcher(
		                    "verify-register-otp.jsp")
		                    .forward(request, response);
		        }
		    }
		}
	
