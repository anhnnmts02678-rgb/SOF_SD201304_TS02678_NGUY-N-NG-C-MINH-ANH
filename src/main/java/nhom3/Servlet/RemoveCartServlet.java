package nhom3.Servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import nhom3.Dao.GiohangCTDao;
import nhom3.Model.Users;

@WebServlet("/RemoveCartServlet")
public class RemoveCartServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // DAO xu ly gio hang
    private GiohangCTDao giohangCTDao;

    // Constructor mac dinh khi website chay
    public RemoveCartServlet() {
        this.giohangCTDao = new GiohangCTDao();
    }

    // Constructor danh rieng cho Unit Test
    RemoveCartServlet(GiohangCTDao giohangCTDao) {
        this.giohangCTDao = giohangCTDao;
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // Lay session
        HttpSession session = request.getSession();

        // Kiem tra dang nhap
        Users user = (Users) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Lay ma gio hang chi tiet
        int maGHCT;

        try {
            maGHCT = Integer.parseInt(
                    request.getParameter("maGHCT")
            );

        } catch (NumberFormatException e) {
            response.sendRedirect("CartServlet");
            return;
        }

        // Xoa san pham khoi gio hang
        giohangCTDao.delete(maGHCT);

        // Chuyen ve trang gio hang
        response.sendRedirect(
                "CartServlet?message=Xoa+thanh+cong"
        );
    }
}
