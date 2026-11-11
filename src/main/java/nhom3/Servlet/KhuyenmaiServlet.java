package nhom3.Servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import nhom3.Dao.KhuyenmaiDao;
import nhom3.Model.Khuyenmai;

@WebServlet("/KhuyenmaiServlet")
public class KhuyenmaiServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private KhuyenmaiDao dao = new KhuyenmaiDao();

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

    	List<Khuyenmai> list = dao.getAll();
    	System.out.println("===== KM =====");
    	System.out.println("So luong: " + list.size());

    	for (Khuyenmai km : list) {
    	    System.out.println(km.getLoaiKM());
    	    System.out.println(km.getNgayBatDau());
    	    System.out.println(km.getNgayKetThuc());
    	}
        request.setAttribute("kmList", list);

        request.getRequestDispatcher("khuyenmai.jsp")
                .forward(request, response);
    }
}