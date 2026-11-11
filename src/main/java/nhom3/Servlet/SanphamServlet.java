package nhom3.Servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import nhom3.Dao.DanhgiaDao;
import nhom3.Dao.HoadonCTDao;
import nhom3.Dao.SanphamDao;
import nhom3.Model.Danhgia;
import nhom3.Model.Sanpham;
import nhom3.Model.Users;

@WebServlet("/products")
public class SanphamServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private SanphamDao dao = new SanphamDao();
    private DanhgiaDao danhgiaDao = new DanhgiaDao();
    private HoadonCTDao hoadonCTDao = new HoadonCTDao();

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String keyword = request.getParameter("keyword");
        String maDM = request.getParameter("maDM");

        List<Sanpham> list;

        if (keyword != null && !keyword.trim().isEmpty()) {

            list = dao.getByTen(keyword.trim());

        } else if (maDM != null && !maDM.trim().isEmpty()) {

            list = dao.getByDanhMuc(Integer.parseInt(maDM));

        } else {

            list = dao.getByTrangThai(true);

        }

        request.setAttribute("products", list);

        // ===== Du lieu cho khu vuc danh gia (unlock review) =====

        HttpSession session = request.getSession(false);
        Users user = (session != null) ? (Users) session.getAttribute("user") : null;

        // Danh sach danh gia hien co cua tung san pham (luon hien thi, ke ca khach chua dang nhap)
        Map<Integer, List<Danhgia>> reviewsMap = new HashMap<>();

        // Danh gia cua chinh nguoi dung dang dang nhap cho tung san pham (de sua lai)
        Map<Integer, Danhgia> myReviewMap = new HashMap<>();

        if (list != null) {
            for (Sanpham sp : list) {
                reviewsMap.put(sp.getMaSP(), danhgiaDao.getBySanPham(sp.getMaSP()));

                if (user != null) {
                    Danhgia myReview = danhgiaDao.getByKhachHangVaSanPham(user.getMaKH(), sp.getMaSP());
                    if (myReview != null) {
                        myReviewMap.put(sp.getMaSP(), myReview);
                    }
                }
            }
        }

        // Tap hop maSP ma nguoi dung da mua VA da nhan hang (Hoadon.trangThai = DA_GIAO)
        Set<Integer> purchasedSet = (user != null)
                ? hoadonCTDao.getPurchasedSanPhamIds(user.getMaKH())
                : java.util.Collections.emptySet();

        request.setAttribute("reviewsMap", reviewsMap);
        request.setAttribute("myReviewMap", myReviewMap);
        request.setAttribute("purchasedSet", purchasedSet);

        request.getRequestDispatcher("products.jsp")
               .forward(request, response);
    }
}