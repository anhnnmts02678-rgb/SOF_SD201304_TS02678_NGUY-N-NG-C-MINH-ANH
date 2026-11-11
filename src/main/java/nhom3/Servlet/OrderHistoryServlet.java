package nhom3.Servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import nhom3.Dao.HoadonDao;
import nhom3.Dao.ThanhtoanDao;
import nhom3.Model.Hoadon;
import nhom3.Model.Users;

/**
 * Hien thi lich su dat hang cua khach hang.
 * URL: /OrderHistoryServlet
 * Cho phep loc theo trang thai: ?trangThai=Cho_xac_nhan
 * Forward den: order-history.jsp
 */
@WebServlet("/OrderHistoryServlet")
public class OrderHistoryServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        HttpSession httpSession = request.getSession();

        Users user = (Users) httpSession.getAttribute("user");
        if (user == null) {
            response.sendRedirect("login.jsp?message=Vui+long+dang+nhap+truoc");
            return;
        }

        HoadonDao    hoadonDao    = new HoadonDao();
        ThanhtoanDao thanhtoanDao = new ThanhtoanDao();

        int maKH = user.getMaKH();

        // Lay toan bo don hang cua khach, co the loc theo trang thai
        String trangThaiFilter = request.getParameter("trangThai");
        List<Hoadon> orders;

        if (trangThaiFilter != null && !trangThaiFilter.isEmpty()) {
            // Loc theo trang thai + khach hang
            orders = hoadonDao.getByKhachHang(maKH);
            if (orders != null) {
                final String filter = trangThaiFilter;
                orders.removeIf(hd -> !filter.equalsIgnoreCase(hd.getTrangThai()));
            }
        } else {
            orders = hoadonDao.getByKhachHang(maKH);
        }

        // Cac nhan trang thai de hien thi bo loc
        String[] dsTrangThai = {
        	    "CHO_XAC_NHAN",
        	    "DANG_XU_LY",
        	    "DANG_GIAO",
        	    "DA_GIAO",
        	    "DA_HUY"
        	};

        request.setAttribute("orders",          orders);
        request.setAttribute("dsTrangThai",     dsTrangThai);
        request.setAttribute("trangThaiFilter", trangThaiFilter);
        request.setAttribute("thanhtoanDao",    thanhtoanDao);

        request.getRequestDispatcher("order-history.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}