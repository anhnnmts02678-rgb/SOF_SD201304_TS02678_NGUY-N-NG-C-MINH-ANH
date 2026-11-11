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

    /**
	 * 
	 */
	private static final long serialVersionUID = 8103580682188401088L;

	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        HttpSession httpSession = request.getSession();

        // Kiem tra dang nhap
        Users user = (Users) httpSession.getAttribute("user");
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int maGHCT;
        int soLuongMoi;
        try {
            maGHCT     = Integer.parseInt(request.getParameter("maGHCT"));
            soLuongMoi = Integer.parseInt(request.getParameter("soLuong"));
        } catch (NumberFormatException e) {
            response.sendRedirect("CartServlet?error=Tham+so+khong+hop+le");
            return;
        }

        if (soLuongMoi <= 0) {
            response.sendRedirect("CartServlet?error=So+luong+phai+lon+hon+0");
            return;
        }

        GiohangCTDao giohangCTDao = new GiohangCTDao();
        SanphamDao   sanphamDao   = new SanphamDao();

        GiohangCT item = giohangCTDao.getById(maGHCT);
        if (item == null) {
            response.sendRedirect("CartServlet?error=Khong+tim+thay+san+pham+trong+gio");
            return;
        }

        // Kiem tra ton kho
        Sanpham sp = sanphamDao.getById(item.getMaSP());
        if (sp == null || sp.getSoLuong() < soLuongMoi) {
            response.sendRedirect("CartServlet?error=Khong+du+hang+trong+kho");
            return;
        }

        double tongGiaMoi = sp.getGiaThanh() * soLuongMoi;
        boolean ok = giohangCTDao.updateSoLuong(maGHCT, soLuongMoi,tongGiaMoi);

        if (ok) {
            response.sendRedirect("CartServlet?message=Cap+nhat+gio+hang+thanh+cong");
        } else {
            response.sendRedirect("CartServlet?error=Loi+cap+nhat+gio+hang");
        }
    }
}