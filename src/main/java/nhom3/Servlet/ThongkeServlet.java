package nhom3.Servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import nhom3.Dao.ThongkeDao;

@WebServlet("/employee/ThongkeServlet")
public class ThongkeServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final ThongkeDao thongkeDao = new ThongkeDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int tongHoaDon = (int) thongkeDao.tongHoaDon();
        double tongDoanhThu = thongkeDao.tongDoanhThu();

        List<Object[]> barData = thongkeDao.soHoaDonTheoThang();
        List<Object[]> sanPhamDaBan = thongkeDao.thongKeSanPhamDaBan();

        request.setAttribute("tongHoaDon", tongHoaDon);
        request.setAttribute("tongDoanhThu", tongDoanhThu);
        request.setAttribute("barData", barData);
        request.setAttribute("sanPhamDaBan", sanPhamDaBan);

        request.getRequestDispatcher("/employee/thongke.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}