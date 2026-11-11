package nhom3.Servlet;
 
import java.io.IOException;
import java.sql.Timestamp;
 
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
 
/**
 * Them san pham vao gio hang.
 * URL: /AddToCartServlet?maSP=1&soLuong=2
 */
@WebServlet("/AddToCartServlet")
public class AddToCartServlet extends HttpServlet {
 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
 
        request.setCharacterEncoding("UTF-8");
        HttpSession httpSession = request.getSession();
 
        // Kiem tra dang nhap
        Users user = (Users) httpSession.getAttribute("user");
        if (user == null) {
            response.sendRedirect("login.jsp?message=Vui+long+dang+nhap+truoc");
            return;
        }
 
        // Lay tham so
        int maSP;
        int soLuong;
        try {
            maSP    = Integer.parseInt(request.getParameter("maSP"));
            soLuong = Integer.parseInt(request.getParameter("soLuong"));
            if (soLuong <= 0) soLuong = 1;
        } catch (NumberFormatException e) {
            response.sendRedirect("index.jsp?error=Tham+so+khong+hop+le");
            return;
        }
 
        SanphamDao sanphamDao     = new SanphamDao();
        GiohangDao giohangDao     = new GiohangDao();
        GiohangCTDao giohangCTDao = new GiohangCTDao();
 
        // Kiem tra san pham ton tai
        Sanpham sp = sanphamDao.getById(maSP);
        if (sp == null || !sp.isTrangThai()) {
            response.sendRedirect("index.jsp?error=San+pham+khong+ton+tai");
            return;
        }
 
        // Kiem tra ton kho
        if (sp.getSoLuong() < soLuong) {
        	response.sendRedirect(
        		    "product-detail?id=" + maSP +
        		    "&error=Khong+du+hang+trong+kho"
        		);
            return;
        }
 
        int maKH = user.getMaKH();
        System.out.println(
        	    "User login MaKH = "
        	    + maKH
        	);
        // Lay gio hang dang mo, neu chua co thi tao moi
        Giohang gh = giohangDao.getActiveByKhachHang(maKH);
        if (gh == null) {
            gh = new Giohang();
            gh.setMaKH(maKH);
            gh.setNgayTao(new Timestamp(System.currentTimeMillis()));
            gh.setTrangThai(true);
            if (!giohangDao.insert(gh)) {
                response.sendRedirect("index.jsp?error=Loi+tao+gio+hang");
                return;
            }
            // Lay lai gio hang vua tao (co maGH)
            gh = giohangDao.getActiveByKhachHang(maKH);
            System.out.println("Lan 1: gh = " + gh);
        }
 
        // Kiem tra san pham da co trong gio chua
        GiohangCT item = giohangCTDao.getByGioHangVaSanPham(gh.getMaGH(), maSP);
        if (item != null) {
            // Cap nhat so luong
        	int soLuongMoi =
        			item.getSoLuong() + soLuong;

        			if(soLuongMoi > sp.getSoLuong()){

        				response.sendRedirect(
        					    "product-detail?id=" + maSP +
        					    "&error=Vuot+qua+so+luong+ton+kho"
        					);
        			    return;
        			}
            double tongGiaMoi = sp.getGiaThanh() * soLuongMoi;
            giohangCTDao.updateSoLuong(item.getMaGHCT(), soLuongMoi,(tongGiaMoi));
        } else {
            // Them moi vao gio
            GiohangCT newItem = new GiohangCT();
            newItem.setMaGH(gh.getMaGH());
            newItem.setMaSP(maSP);
            newItem.setSoLuong(soLuong);
            newItem.setDonGia(sp.getGiaThanh());
            newItem.setTongGia(sp.getGiaThanh() * soLuong);
            giohangCTDao.insert(newItem);
        }
 
        response.sendRedirect("CartServlet?message=Them+vao+gio+hang+thanh+cong");
    }
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}