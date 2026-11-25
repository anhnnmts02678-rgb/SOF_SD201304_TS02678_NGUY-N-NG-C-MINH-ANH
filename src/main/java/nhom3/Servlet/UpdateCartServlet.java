
package nhom3.Servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import nhom3.Dao.GiohangCTDao;
import nhom3.Dao.SanphamDao;
import nhom3.Model.GiohangCT;
import nhom3.Model.Sanpham;
import nhom3.Model.Users;

/**
 * Cap nhat so luong san pham trong gio hang.
 * URL: POST /UpdateCartServlet?maGHCT=1&soLuong=3
 */
@WebServlet("/UpdateCartServlet")
public class UpdateCartServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // 1. Khai bao DAO
    private GiohangCTDao giohangCTDao;
    private SanphamDao sanphamDao;

    // 2. Constructor mac dinh khi website chay
    public UpdateCartServlet() {
        this.giohangCTDao = new GiohangCTDao();
        this.sanphamDao = new SanphamDao();
    }

    // Constructor danh rieng cho Unit Test
    UpdateCartServlet(GiohangCTDao giohangCTDao,
                      SanphamDao sanphamDao) {
        this.giohangCTDao = giohangCTDao;
        this.sanphamDao = sanphamDao;
    }

    // 3. Xu ly cap nhat gio hang
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession httpSession = request.getSession();

        // Kiem tra dang nhap
        Users user = (Users) httpSession.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Lay ma gio hang chi tiet va so luong moi
        int maGHCT;
        int soLuongMoi;

        try {
            maGHCT = Integer.parseInt(request.getParameter("maGHCT"));
            soLuongMoi = Integer.parseInt(request.getParameter("soLuong"));

        } catch (NumberFormatException e) {
            response.sendRedirect(
                "CartServlet?error=Tham+so+khong+hop+le"
            );
            return;
        }

        // Kiem tra so luong
        if (soLuongMoi <= 0) {
            response.sendRedirect(
                "CartServlet?error=So+luong+phai+lon+hon+0"
            );
            return;
        }

        // Tim san pham trong gio hang
        GiohangCT item = giohangCTDao.getById(maGHCT);

        if (item == null) {
            response.sendRedirect(
                "CartServlet?error=Khong+tim+thay+san+pham+trong+gio"
            );
            return;
        }

        // Kiem tra ton kho
        Sanpham sp = sanphamDao.getById(item.getMaSP());

        if (sp == null || sp.getSoLuong() < soLuongMoi) {
            response.sendRedirect(
                "CartServlet?error=Khong+du+hang+trong+kho"
            );
            return;
        }

        // Tinh tong gia moi
        double tongGiaMoi = sp.getGiaThanh() * soLuongMoi;

        // Cap nhat so luong trong gio hang
        boolean ok = giohangCTDao.updateSoLuong(
            maGHCT, soLuongMoi, tongGiaMoi
        );

        if (ok) {
            response.sendRedirect(
                "CartServlet?message=Cap+nhat+gio+hang+thanh+cong"
            );
        } else {
            response.sendRedirect(
                "CartServlet?error=Loi+cap+nhat+gio+hang"
            );
        }
    }
}
