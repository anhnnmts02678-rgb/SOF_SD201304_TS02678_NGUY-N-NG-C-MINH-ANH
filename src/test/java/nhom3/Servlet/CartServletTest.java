package nhom3.Servlet;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

class CartServletTest {

    @Test
    void doGet_WhenUserNotLoggedIn_ShouldRedirectToLogin()
            throws Exception {

        // Arrange: Chuẩn bị dữ liệu kiểm thử
        CartServlet servlet = new CartServlet();

        HttpServletRequest request =
                mock(HttpServletRequest.class);

        HttpServletResponse response =
                mock(HttpServletResponse.class);

        HttpSession session =
                mock(HttpSession.class);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(null);

        // Act: Gọi chức năng cần kiểm thử
        servlet.doGet(request, response);

        // Assert: Kiểm tra kết quả
        verify(response).sendRedirect(
                "login.jsp?message=Vui+long+dang+nhap+truoc"
        );
    }
}
