package nhom3.Servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import nhom3.Dao.SanphamDao;
import nhom3.Dao.ThongkeDao;
import nhom3.Model.Sanpham;

/**
 * Thong ke tong quan cho ADMIN.
 * URL: /admin/ThongkeServlet
 * Forward den: /admin/thongke.jsp
 *
 * Truoc day trang thong ke duoc mo TRUC TIEP qua thongke.jsp (sidebar/index.jsp
 * link thang toi file jsp) nen servlet nay khong bao gio chay -> du lieu
 * (tongHoaDon, tongDoanhThu, barData...) luon la null, khien thong ke
 * khong bao gio phan anh don hang/hoa don moi. Sua bang cach bat buoc
 * moi lien ket phai di qua servlet nay truoc khi forward sang JSP.
 */
@WebServlet("/admin/ThongkeServlet")
public class AdminThongkeServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final ThongkeDao thongkeDao = new ThongkeDao();

    private final SanphamDao sanphamDao = new SanphamDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getSession().getAttribute("admin") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        int tongHoaDon = (int) thongkeDao.tongHoaDon();
        double tongDoanhThu = thongkeDao.tongDoanhThu();

        List<Object[]> barData = thongkeDao.soHoaDonTheoThang();
        List<Object[]> sanPhamDaBan = thongkeDao.thongKeSanPhamDaBan();

        request.setAttribute("tongHoaDon", tongHoaDon);
        request.setAttribute("tongDoanhThu", tongDoanhThu);
        request.setAttribute("barData", barData);
        request.setAttribute("sanPhamDaBan", sanPhamDaBan);

        request.getRequestDispatcher("/admin/thongke.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
