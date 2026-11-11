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
import nhom3.Dao.HoadonCTDao;
import nhom3.Dao.SanphamDao;
import nhom3.Dao.ThanhtoanDao;
import nhom3.Model.Hoadon;
import nhom3.Model.HoadonCT;
import nhom3.Model.Sanpham;
import nhom3.Model.Thanhtoan;
import nhom3.Model.Users;

/**
 * Hien thi chi tiet mot don hang.
 * URL: /OrderDetailServlet?maHD=5
 * Cho phep huy don: POST /OrderDetailServlet?maHD=5&action=huy
 * Forward den: order-detail.jsp
 */
@WebServlet("/OrderDetailServlet")
public class OrderDetailServlet extends HttpServlet {

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
            response.sendRedirect("login.jsp");
            return;
        }

        int maHD;
        try {
            maHD = Integer.parseInt(request.getParameter("maHD"));
        } catch (NumberFormatException e) {
            response.sendRedirect("OrderHistoryServlet?error=Hoa+don+khong+hop+le");
            return;
        }

        HoadonDao    hoadonDao    = new HoadonDao();
        HoadonCTDao  hoadonCTDao  = new HoadonCTDao();
        ThanhtoanDao thanhtoanDao = new ThanhtoanDao();
        SanphamDao   sanphamDao   = new SanphamDao();

        Hoadon hd = hoadonDao.getById(maHD);

        // Bao dam hoa don thuoc ve chinh khach hang nay
        if (hd == null || hd.getMaKH() != user.getMaKH()) {
            response.sendRedirect("OrderHistoryServlet?error=Khong+tim+thay+don+hang");
            return;
        }

        List<HoadonCT> items     = hoadonCTDao.getByHoaDon(maHD);
        Thanhtoan      thanhtoan = thanhtoanDao.getById(hd.getMaThanhToan());

        // Tong san pham
        int soLuongSP = 0;
        if (items != null) {
            soLuongSP = items.stream().mapToInt(HoadonCT::getSoLuong).sum();
        }

        // Cho phep huy neu don dang "CHO_XAC_NHAN" va chua qua han tu dong xac nhan
        boolean coTheHuy = "CHO_XAC_NHAN".equals(hd.getTrangThai());

        // Thoi diem don se tu dong duoc xac nhan (de hien dem nguoc cho khach)
        long deadlineMillis = 0;
        if (coTheHuy && hd.getNgayThanhToan() != null) {
            deadlineMillis = hd.getNgayThanhToan().getTime()
                    + nhom3.Listener.OrderAutoConfirmListener.AUTO_CONFIRM_MINUTES * 60_000L;
        }
        request.setAttribute("deadlineMillis", deadlineMillis);

        request.setAttribute("hoadon",     hd);
        request.setAttribute("items",      items);
        request.setAttribute("thanhtoan",  thanhtoan);
        request.setAttribute("sanphamDao", sanphamDao);
        request.setAttribute("soLuongSP",  soLuongSP);
        request.setAttribute("coTheHuy",   coTheHuy);

        String message = request.getParameter("message");
        if (message != null) request.setAttribute("message", message);
        String error = request.getParameter("error");
        if (error != null) request.setAttribute("error", error);

        request.getRequestDispatcher("order-detail.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        HttpSession httpSession = request.getSession();

        Users user = (Users) httpSession.getAttribute("user");
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");
        int    maHD;
        try {
            maHD = Integer.parseInt(request.getParameter("maHD"));
        } catch (NumberFormatException e) {
            response.sendRedirect("OrderHistoryServlet?error=Hoa+don+khong+hop+le");
            return;
        }

        HoadonDao   hoadonDao   = new HoadonDao();
        HoadonCTDao hoadonCTDao = new HoadonCTDao();
        SanphamDao  sanphamDao  = new SanphamDao();

        Hoadon hd = hoadonDao.getById(maHD);

        if (hd == null || hd.getMaKH() != user.getMaKH()) {
            response.sendRedirect("OrderHistoryServlet?error=Khong+tim+thay+don+hang");
            return;
        }

        // ========== HUY DON HANG ==========
        if ("huy".equalsIgnoreCase(action)) {

            // Chi cho phep huy khi don van dang "CHO_XAC_NHAN".
            // Neu da tu dong xac nhan (chuyen sang DANG_XU_LY) hoac da o
            // trang thai khac thi khong duoc huy nua.
            if (!"CHO_XAC_NHAN".equals(hd.getTrangThai())) {
                response.sendRedirect("OrderDetailServlet?maHD=" + maHD
                        + "&error=Don+hang+da+duoc+xu+ly+hoac+khong+the+huy+o+trang+thai+hien+tai");
                return;
            }

            // Hoan lai ton kho
            List<HoadonCT> items = hoadonCTDao.getByHoaDon(maHD);
            if (items != null) {
                for (HoadonCT item : items) {
                    Sanpham sp = sanphamDao.getById(item.getMaSP());
                    if(sp != null){

                        sanphamDao.congTonKho(
                            item.getMaSP(),
                            item.getSoLuong()
                        );

                    }
                }
            }

            // Cap nhat trang thai hoa don
            ThanhtoanDao thanhtoanDao = new ThanhtoanDao();

            thanhtoanDao.updateTrangThai(
                hd.getMaThanhToan(),
                "Da_huy"
            );
            
            hd.setTrangThai("DA_HUY");
            boolean ok = hoadonDao.update(hd);

            if (ok) {
                response.sendRedirect("OrderDetailServlet?maHD=" + maHD
                        + "&message=Da+huy+don+hang+thanh+cong");
            } else {
                response.sendRedirect("OrderDetailServlet?maHD=" + maHD
                        + "&error=Loi+huy+don+hang");
            }
            return;
        }

        // Action khong hop le
        response.sendRedirect("OrderDetailServlet?maHD=" + maHD);
    }
}