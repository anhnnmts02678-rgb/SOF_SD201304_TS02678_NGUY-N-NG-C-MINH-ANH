
package nhom3.Servlet;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import nhom3.Dao.GiohangCTDao;
import nhom3.Dao.SanphamDao;
import nhom3.Model.GiohangCT;
import nhom3.Model.Sanpham;
import nhom3.Model.Users;

class UpdateCartServletTest {

    private GiohangCTDao giohangCTDao;
    private SanphamDao sanphamDao;
    private UpdateCartServlet servlet;

    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;

    @BeforeEach
    void setUp() {
        // Giả lập DAO
        giohangCTDao = mock(GiohangCTDao.class);
        sanphamDao = mock(SanphamDao.class);

        servlet = new UpdateCartServlet(giohangCTDao, sanphamDao);

        // Giả lập Request, Response, Session
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);

        when(request.getSession()).thenReturn(session);
    }

    // 1. Chưa đăng nhập
    @Test
    void doPost_WhenUserNotLoggedIn_ShouldRedirectLogin()
            throws Exception {

        when(session.getAttribute("user")).thenReturn(null);

        servlet.doPost(request, response);

        verify(response).sendRedirect("login.jsp");

        verifyNoInteractions(giohangCTDao, sanphamDao);
    }

    // 2. Tham số không phải số nguyên
    @ParameterizedTest
    @CsvSource({
        "abc, 2",
        "1, xyz"
    })
    void doPost_WhenParametersInvalid_ShouldRedirectError(
            String maGHCT, String soLuong) throws Exception {

        when(session.getAttribute("user"))
                .thenReturn(mock(Users.class));

        when(request.getParameter("maGHCT"))
                .thenReturn(maGHCT);

        when(request.getParameter("soLuong"))
                .thenReturn(soLuong);

        servlet.doPost(request, response);

        verify(response).sendRedirect(
                "CartServlet?error=Tham+so+khong+hop+le");
    }

    // 3. Số lượng phải lớn hơn 0
    @ParameterizedTest
    @CsvSource({
        "0",
        "-1",
        "-5"
    })
    void doPost_WhenQuantityInvalid_ShouldRedirectError(
            String soLuong) throws Exception {

        when(session.getAttribute("user"))
                .thenReturn(mock(Users.class));

        when(request.getParameter("maGHCT"))
                .thenReturn("1");

        when(request.getParameter("soLuong"))
                .thenReturn(soLuong);

        servlet.doPost(request, response);

        verify(response).sendRedirect(
                "CartServlet?error=So+luong+phai+lon+hon+0");
    }

    // 4. Không tìm thấy sản phẩm trong giỏ
    @Test
    void doPost_WhenCartItemNotFound_ShouldRedirectError()
            throws Exception {

        when(session.getAttribute("user"))
                .thenReturn(mock(Users.class));

        when(request.getParameter("maGHCT")).thenReturn("1");
        when(request.getParameter("soLuong")).thenReturn("2");

        when(giohangCTDao.getById(1)).thenReturn(null);

        servlet.doPost(request, response);

        verify(response).sendRedirect(
                "CartServlet?error=Khong+tim+thay+san+pham+trong+gio");
    }

    // 5. Không đủ hàng trong kho
    @Test
    void doPost_WhenStockInsufficient_ShouldRedirectError()
            throws Exception {

        when(session.getAttribute("user"))
                .thenReturn(mock(Users.class));

        when(request.getParameter("maGHCT")).thenReturn("1");
        when(request.getParameter("soLuong")).thenReturn("10");

        GiohangCT item = mock(GiohangCT.class);
        when(giohangCTDao.getById(1)).thenReturn(item);
        when(item.getMaSP()).thenReturn(1);

        Sanpham sp = mock(Sanpham.class);
        when(sanphamDao.getById(1)).thenReturn(sp);
        when(sp.getSoLuong()).thenReturn(5);

        servlet.doPost(request, response);

        verify(response).sendRedirect(
                "CartServlet?error=Khong+du+hang+trong+kho");
    }

    // 6. Cập nhật thành công
    @Test
    void doPost_WhenUpdateSuccess_ShouldRedirectSuccess()
            throws Exception {

        when(session.getAttribute("user"))
                .thenReturn(mock(Users.class));

        when(request.getParameter("maGHCT")).thenReturn("1");
        when(request.getParameter("soLuong")).thenReturn("2");

        GiohangCT item = mock(GiohangCT.class);
        when(giohangCTDao.getById(1)).thenReturn(item);
        when(item.getMaSP()).thenReturn(1);

        Sanpham sp = mock(Sanpham.class);
        when(sanphamDao.getById(1)).thenReturn(sp);
        when(sp.getSoLuong()).thenReturn(10);
        when(sp.getGiaThanh()).thenReturn(100000.0);

        when(giohangCTDao.updateSoLuong(1, 2, 200000.0))
                .thenReturn(true);

        servlet.doPost(request, response);

        verify(response).sendRedirect(
                "CartServlet?message=Cap+nhat+gio+hang+thanh+cong");
    }

    // 7. Cập nhật thất bại
    @Test
    void doPost_WhenUpdateFailed_ShouldRedirectError()
            throws Exception {

        when(session.getAttribute("user"))
                .thenReturn(mock(Users.class));

        when(request.getParameter("maGHCT")).thenReturn("1");
        when(request.getParameter("soLuong")).thenReturn("2");

        GiohangCT item = mock(GiohangCT.class);
        when(giohangCTDao.getById(1)).thenReturn(item);
        when(item.getMaSP()).thenReturn(1);

        Sanpham sp = mock(Sanpham.class);
        when(sanphamDao.getById(1)).thenReturn(sp);
        when(sp.getSoLuong()).thenReturn(10);
        when(sp.getGiaThanh()).thenReturn(100000.0);

        when(giohangCTDao.updateSoLuong(1, 2, 200000.0))
                .thenReturn(false);

        servlet.doPost(request, response);

        verify(response).sendRedirect(
                "CartServlet?error=Loi+cap+nhat+gio+hang");
    }
}
