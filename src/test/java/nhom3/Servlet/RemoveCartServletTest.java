
package nhom3.Servlet;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import nhom3.Dao.GiohangCTDao;
import nhom3.Model.Users;

class RemoveCartServletTest {

    private GiohangCTDao giohangCTDao;
    private RemoveCartServlet servlet;

    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;

    @BeforeEach
    void setUp() {

        // Tao DAO gia
        giohangCTDao = mock(GiohangCTDao.class);

        servlet = new RemoveCartServlet(giohangCTDao);

        // Tao Request, Response va Session gia
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);

        when(request.getSession()).thenReturn(session);
    }

    // 1. Nguoi dung chua dang nhap
    @Test
    void doGet_WhenUserNotLoggedIn_ShouldRedirectLogin()
            throws Exception {

        when(session.getAttribute("user")).thenReturn(null);

        servlet.doGet(request, response);

        verify(response).sendRedirect("login.jsp");

        verifyNoInteractions(giohangCTDao);
    }

    // 2. Ma gio hang khong hop le
    @Test
    void doGet_WhenCartIdInvalid_ShouldRedirectCart()
            throws Exception {

        when(session.getAttribute("user"))
                .thenReturn(mock(Users.class));

        when(request.getParameter("maGHCT"))
                .thenReturn("abc");

        servlet.doGet(request, response);

        verify(response).sendRedirect("CartServlet");

        verifyNoInteractions(giohangCTDao);
    }

    // 3. Xoa san pham thanh cong
    @Test
    void doGet_WhenRemoveSuccess_ShouldRedirectSuccess()
            throws Exception {

        when(session.getAttribute("user"))
                .thenReturn(mock(Users.class));

        when(request.getParameter("maGHCT"))
                .thenReturn("10");

        servlet.doGet(request, response);

        verify(giohangCTDao).delete(10);

        verify(response).sendRedirect(
                "CartServlet?message=Xoa+thanh+cong"
        );
    }

    // 4. DAO phat sinh exception khi xoa
    @Test
    void doGet_WhenDaoThrowsException_ShouldThrowException()
            throws Exception {

        when(session.getAttribute("user"))
                .thenReturn(mock(Users.class));

        when(request.getParameter("maGHCT"))
                .thenReturn("10");

        doThrow(new IllegalStateException("Loi khi xoa gio hang"))
                .when(giohangCTDao).delete(10);

        // Kiem tra exception bang assertThrows
        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> servlet.doGet(request, response)
        );

        assertEquals(
                "Loi khi xoa gio hang",
                exception.getMessage()
        );

        verify(giohangCTDao).delete(10);
    }
}
