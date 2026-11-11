package nhom3.Servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import nhom3.Dao.NhatkyDao;
import nhom3.Model.Nhatky;

@WebServlet("/admin/NhatkyServlet")
public class NhatkyServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private NhatkyDao dao = new NhatkyDao();

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        List<Nhatky> list = dao.getAll();

        request.setAttribute("listNK", list);

        request.getRequestDispatcher("/admin/nhatky.jsp")
                .forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);

    }

}