package nhom3.Servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import nhom3.Dao.NhatkyDao;
import nhom3.Model.Employee;
import nhom3.Model.Nhatky;

@WebServlet("/employee/EmployeeNhatkyServlet")
public class EmployeeNhatkyServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final NhatkyDao dao = new NhatkyDao();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        Employee nv = (Employee) request.getSession()
                .getAttribute("employee");

        if (nv == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }


        NhatkyDao dao = new NhatkyDao();

        List<Nhatky> list = dao.getAll();


        request.setAttribute("listNK", list);


        request.getRequestDispatcher("/employee/nhatky.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}