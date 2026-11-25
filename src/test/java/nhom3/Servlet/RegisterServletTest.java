
package nhom3.Servlet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import nhom3.Dao.UsersDao;
import nhom3.Model.Users;
import nhom3.Utils.MailUtil;

class RegisterServletTest {

    private UsersDao usersDao;
    private RegisterServlet servlet;

    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;

    @BeforeEach
    void setUp() {
        usersDao = mock(UsersDao.class);
        servlet = new RegisterServlet(usersDao);

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        dispatcher = mock(RequestDispatcher.class);

        when(request.getParameter("fullName")).thenReturn("Nguyen Van A");
        when(request.getParameter("email")).thenReturn("test@gmail.com");
        when(request.getParameter("sdt")).thenReturn("0901234567");
        when(request.getParameter("password")).thenReturn("123456");
        when(request.getParameter("confirmPassword")).thenReturn("123456");
        when(request.getParameter("address")).thenReturn("TP HCM");

        when(request.getRequestDispatcher("register.jsp"))
                .thenReturn(dispatcher);

        when(request.getSession()).thenReturn(session);
    }

    // 1. Kiểm tra mật khẩu nhập lại không khớp
    @ParameterizedTest
    @CsvSource({
        "123456, 1234567",
        "abc123, abc456",
        "password, password1"
    })
    void testPasswordMismatch(String password, String confirmPassword)
            throws Exception {

        when(request.getParameter("password")).thenReturn(password);
        when(request.getParameter("confirmPassword"))
                .thenReturn(confirmPassword);

        servlet.doPost(request, response);

        verify(request).setAttribute(
                "message", "Mật khẩu nhập lại không khớp!");

        verify(dispatcher).forward(request, response);

        verifyNoInteractions(usersDao);
        verify(response, never()).sendRedirect(anyString());
    }

    // 2. Kiểm tra email đã tồn tại
    @Test
    void testEmailAlreadyExists() throws Exception {

        when(usersDao.getByEmail("test@gmail.com"))
                .thenReturn(mock(Users.class));

        servlet.doPost(request, response);

        verify(request).setAttribute(
                "message", "Email đã được sử dụng!");

        verify(dispatcher).forward(request, response);

        verify(usersDao).getByEmail("test@gmail.com");
        verify(usersDao, never()).getBySdt(anyString());

        verify(response, never()).sendRedirect(anyString());
    }

    // 3. Kiểm tra số điện thoại đã tồn tại
    @Test
    void testPhoneAlreadyExists() throws Exception {

        when(usersDao.getByEmail("test@gmail.com"))
                .thenReturn(null);

        when(usersDao.getBySdt("0901234567"))
                .thenReturn(mock(Users.class));

        servlet.doPost(request, response);

        verify(request).setAttribute(
                "message", "Số điện thoại đã được sử dụng!");

        verify(dispatcher).forward(request, response);

        verify(usersDao).getByEmail("test@gmail.com");
        verify(usersDao).getBySdt("0901234567");

        verify(response, never()).sendRedirect(anyString());
    }

    // 4. Đăng ký hợp lệ: tạo OTP, lưu Session và gửi email
    @Test
    void testRegisterSuccess() throws Exception {

        when(usersDao.getByEmail("test@gmail.com")).thenReturn(null);
        when(usersDao.getBySdt("0901234567")).thenReturn(null);

        try (MockedStatic<MailUtil> mailUtil =
                mockStatic(MailUtil.class)) {

            servlet.doPost(request, response);

            // Kiểm tra OTP được lưu vào Session
            ArgumentCaptor<String> otpCaptor =
                    ArgumentCaptor.forClass(String.class);

            verify(session).setAttribute(
                    eq("registerOTP"), otpCaptor.capture());

            String otp = otpCaptor.getValue();

            assertTrue(
                    Pattern.matches("\\d{6}", otp),
                    "OTP phải gồm đúng 6 chữ số"
            );

            // Kiểm tra thông tin đăng ký được lưu vào Session
            verify(session).setAttribute(
                    "registerFullName", "Nguyen Van A");

            verify(session).setAttribute(
                    "registerEmail", "test@gmail.com");

            verify(session).setAttribute(
                    "registerPassword", "123456");

            verify(session).setAttribute(
                    "registerSdt", "0901234567");

            verify(session).setAttribute(
                    "registerAddress", "TP HCM");

            // Kiểm tra gửi OTP qua email
            mailUtil.verify(() ->
                    MailUtil.sendOTP("test@gmail.com", otp));

            // Kiểm tra chuyển sang trang xác minh OTP
            verify(response).sendRedirect(
                    "verify-register-otp.jsp");

            verify(dispatcher, never()).forward(
                    any(), any());
        }
    }
}
