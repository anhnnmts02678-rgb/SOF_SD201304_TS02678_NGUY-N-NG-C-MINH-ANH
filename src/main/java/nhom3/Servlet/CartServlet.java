package nhom3.Servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import nhom3.Dao.GiohangDao;
import nhom3.Dao.GiohangCTDao;
import nhom3.Dao.SanphamDao;
import nhom3.Model.Giohang;
import nhom3.Model.GiohangCT;
import nhom3.Model.Sanpham;
import nhom3.Model.Users;
import java.util.HashMap;
import java.util.Map;


/**
 * Hien thi gio hang cua khach hang.
 * URL: /CartServlet
 * Forward den: cart.jsp
 */
@WebServlet("/CartServlet")
public class CartServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        HttpSession httpSession = request.getSession();

        // Kiem tra dang nhap
        Users user = (Users) httpSession.getAttribute("user");
        if (user == null) {
            response.sendRedirect("login.jsp?message=Vui+long+dang+nhap+truoc");
            return;
        }

        GiohangDao    giohangDao    = new GiohangDao();
        GiohangCTDao  giohangCTDao  = new GiohangCTDao();
        SanphamDao    sanphamDao    = new SanphamDao();

        int maKH = user.getMaKH();

        // Lay gio hang dang mo
        Giohang gh = giohangDao.getActiveByKhachHang(maKH);

        if (gh == null) {
            // Gio hang trong
            request.setAttribute("cartItems",  null);
            request.setAttribute("tongTien",   0.0);
            request.setAttribute("soLuongSP",  0);
            request.getRequestDispatcher("cart.jsp").forward(request, response);
            return;
        }

        // Lay chi tiet gio hang
        List<GiohangCT> items = giohangCTDao.getByGioHang(gh.getMaGH());

        double tongTien = 0;
        int soLuongSP = 0;

        Map<Integer, Sanpham> sanphamMap = new HashMap<>();

        if (items != null) {

            for (GiohangCT item : items) {

                Sanpham sp = sanphamDao.getById(item.getMaSP());

                sanphamMap.put(item.getMaSP(), sp);

                tongTien += item.getTongGia();

                soLuongSP += item.getSoLuong();
            }
        }

        request.setAttribute("giohang",   gh);
        request.setAttribute("cartItems", items);
        request.setAttribute("tongTien",  tongTien);
        request.setAttribute("soLuongSP", soLuongSP);
        request.setAttribute("sanphamDao", sanphamDao);

        // Truyen message neu co (vi du sau khi them sp)
        String message = request.getParameter("message");
        if (message != null) request.setAttribute("message", message);

        request.getRequestDispatcher("cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}