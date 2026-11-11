package nhom3.Servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import nhom3.Dao.DanhgiaDao;
import nhom3.Dao.HoadonCTDao;
import nhom3.Dao.SanphamDao;
import nhom3.Model.Danhgia;
import nhom3.Model.Sanpham;
import nhom3.Model.Users;

@WebServlet("/product-detail")
public class SanphamCTServlet extends HttpServlet {

    private SanphamDao dao = new SanphamDao();
    private DanhgiaDao danhgiaDao = new DanhgiaDao();
    private HoadonCTDao hoadonCTDao = new HoadonCTDao();

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));

        Sanpham sp = dao.getById(id);

        request.setAttribute("product", sp);

        // ===== Du lieu cho khu vuc danh gia (unlock review) =====

        HttpSession session = request.getSession(false);
        Users user = (session != null) ? (Users) session.getAttribute("user") : null;

        List<Danhgia> reviews = danhgiaDao.getBySanPham(id);
        request.setAttribute("reviews", reviews);

        boolean daMua = false;
        Danhgia myReview = null;

        if (user != null) {
            daMua = hoadonCTDao.daMuaVaDaNhan(user.getMaKH(), id);
            myReview = danhgiaDao.getByKhachHangVaSanPham(user.getMaKH(), id);
        }

        request.setAttribute("daMua", daMua);
        request.setAttribute("myReview", myReview);

        request.getRequestDispatcher("/product-detail.jsp")
               .forward(request, response);

    }

}