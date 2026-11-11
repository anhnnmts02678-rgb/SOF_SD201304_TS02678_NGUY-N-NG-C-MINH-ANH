package nhom3.Servlet;

import java.io.IOException;
import java.sql.Timestamp;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import nhom3.Dao.DanhgiaDao;
import nhom3.Dao.HoadonCTDao;
import nhom3.Model.Danhgia;
import nhom3.Model.Users;

/**
 * Use case: Danh gia san pham.
 * Khach hang da dang nhap co the gui danh gia (sao + noi dung + anh) cho 1 san pham.
 * Neu khach hang da danh gia san pham nay truoc do thi cap nhat lai danh gia cu
 * (moi khach hang chi co 1 danh gia cho 1 san pham).
 */
@WebServlet("/DanhgiaServlet")
public class DanhgiaServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final DanhgiaDao danhgiaDao = new DanhgiaDao();
    private final HoadonCTDao hoadonCTDao = new HoadonCTDao();

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);

        Object userObj = session != null ? session.getAttribute("user") : null;

        if (userObj == null) {

            response.sendRedirect(request.getContextPath()
                    + "/login.jsp");

            return;
        }

        Users user = (Users) userObj;

        int maSP = parseIntSafe(request.getParameter("maSP"), 0);

        if (maSP <= 0) {

            response.sendRedirect(request.getContextPath() + "/products.jsp");

            return;
        }

        // Neu form duoc gui tu products.jsp (modal danh gia) thi quay lai
        // dung trang/anchor do thay vi luon ve product-detail.
        String redirectTo = request.getParameter("redirectTo");
        String backUrl = (redirectTo != null && !redirectTo.trim().isEmpty())
                ? redirectTo.trim()
                : request.getContextPath() + "/product-detail?id=" + maSP;

        // Chi cho phep danh gia neu khach hang DA MUA VA DA NHAN HANG
        // san pham nay (Hoadon.trangThai = DA_GIAO). Kiem tra lai o server
        // de tranh bi bypass bang cach gui thang request toi servlet.
        if (!hoadonCTDao.daMuaVaDaNhan(user.getMaKH(), maSP)) {

            response.sendRedirect(appendParam(backUrl, "error",
                    "Bạn cần mua sản phẩm này và đã nhận hàng trước khi đánh giá!"));

            return;
        }

        int soSao = parseIntSafe(request.getParameter("soSao"), 0);

        String noiDung = request.getParameter("noiDungDanhGia");

        String anh = request.getParameter("anh");

        if (soSao < 1 || soSao > 5) {

            response.sendRedirect(appendParam(backUrl, "error",
                    "Vui lòng chọn số sao từ 1 đến 5!"));

            return;
        }

        if (noiDung == null || noiDung.trim().isEmpty()) {

            response.sendRedirect(appendParam(backUrl, "error",
                    "Vui lòng nhập nội dung đánh giá!"));

            return;
        }

        Danhgia existing = danhgiaDao.getByKhachHangVaSanPham(user.getMaKH(), maSP);

        boolean ok;

        if (existing != null) {

            existing.setSoSao(soSao);
            existing.setNoiDungDanhGia(noiDung.trim());
            existing.setAnh(anh != null ? anh.trim() : null);
            existing.setThoiGian(new Timestamp(System.currentTimeMillis()));

            ok = danhgiaDao.update(existing);

        } else {

            Danhgia dg = new Danhgia();

            dg.setMaKH(user.getMaKH());
            dg.setMaSP(maSP);
            dg.setSoSao(soSao);
            dg.setNoiDungDanhGia(noiDung.trim());
            dg.setAnh(anh != null ? anh.trim() : null);
            dg.setThoiGian(new Timestamp(System.currentTimeMillis()));

            ok = danhgiaDao.insert(dg);

        }

        if (ok) {

            response.sendRedirect(appendParam(backUrl, "message",
                    "Cảm ơn bạn đã gửi đánh giá!"));

        } else {

            response.sendRedirect(appendParam(backUrl, "error",
                    "Có lỗi xảy ra, vui lòng thử lại!"));

        }

    }

    /**
     * Them 1 query param vao URL, giu nguyen #fragment (neu co) o cuoi cung
     * de anchor (vd #review-123) van hoat dong sau khi redirect.
     */
    private String appendParam(String url, String key, String value) {

        String base = url;
        String fragment = "";

        int hashIdx = url.indexOf('#');
        if (hashIdx >= 0) {
            base = url.substring(0, hashIdx);
            fragment = url.substring(hashIdx);
        }

        String sep = base.contains("?") ? "&" : "?";

        return base + sep + key + "=" + encode(value) + fragment;
    }

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        int maSP = parseIntSafe(request.getParameter("maSP"), 0);

        response.sendRedirect(request.getContextPath()
                + "/product-detail?id=" + maSP);

    }

    private int parseIntSafe(String s, int def) {

        try {

            return Integer.parseInt(s.trim());

        } catch (Exception e) {

            return def;

        }

    }

    private String encode(String s) {

        try {

            return java.net.URLEncoder.encode(s, "UTF-8");

        } catch (Exception e) {

            return s;

        }

    }

}
