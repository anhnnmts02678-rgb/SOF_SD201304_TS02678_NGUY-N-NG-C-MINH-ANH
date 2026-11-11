package nhom3.Servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import nhom3.Dao.UsersDao;
import nhom3.Model.Users;

/**
 * Servlet quan ly Khach hang (Users) - danh cho khu vuc /admin.
 * Cac hanh dong (action) ho tro:
 *  - list    : danh sach + tim kiem
 *  - add     : hien thi form them moi
 *  - edit    : hien thi form chinh sua
 *  - save    : luu (them moi hoac cap nhat)
 *  - delete  : xoa
 *  - toggle  : doi trang thai hoat dong / khoa
 */
@WebServlet("/employee/EmployeeUserServlet")
public class EmployeeUserServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final UsersDao usersDao = new UsersDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "add":
                showForm(request, response, null);
                break;

            case "edit":
                handleShowEditForm(request, response);
                break;

            case "delete":
                handleDelete(request, response);
                break;

            case "toggle":
                handleToggle(request, response);
                break;

            case "list":
            default:
                handleList(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        handleSave(request, response);
    }

    // ================= LIST + SEARCH =================
    private void handleList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String keyword = request.getParameter("keyword");
        List<Users> list;

        if (keyword != null && !keyword.trim().isEmpty()) {
            list = usersDao.searchByName(keyword.trim());
            request.setAttribute("keyword", keyword.trim());
        } else {
            list = usersDao.getAll();
        }

        request.setAttribute("userList", list);
        request.getRequestDispatcher("customer.jsp").forward(request, response);
    }

    // ================= FORM ADD/EDIT =================
    private void handleShowEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Users user = usersDao.getById(id);

            if (user == null) {
                request.setAttribute("message", "Khong tim thay khach hang!");
                handleList(request, response);
                return;
            }

            showForm(request, response, user);

        } catch (NumberFormatException e) {
        	response.sendRedirect("EmployeeUserServlet?action=list");
        }
    }

    private void showForm(HttpServletRequest request, HttpServletResponse response, Users user)
            throws ServletException, IOException {
        request.setAttribute("user", user);
        request.getRequestDispatcher("customer_form.jsp").forward(request, response);
    }

    // ================= SAVE (ADD / UPDATE) =================
    private void handleSave(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("maKH");
        String fullName = request.getParameter("fullName");
        String sdt = request.getParameter("sdt");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String address = request.getParameter("address");
        boolean trangThai = "on".equals(request.getParameter("trangThai"));

        boolean isEdit = idStr != null && !idStr.trim().isEmpty();

        // Kiem tra email da ton tai chua (tranh trung)
        Users existed = usersDao.getByEmail(email);
        if (existed != null && (!isEdit || existed.getMaKH() != Integer.parseInt(idStr))) {
            request.setAttribute("message", "Email nay da duoc su dung boi khach hang khac!");
            Users formData = new Users();
            if (isEdit) formData.setMaKH(Integer.parseInt(idStr));
            formData.setFullName(fullName);
            formData.setSdt(sdt);
            formData.setEmail(email);
            formData.setAddress(address);
            formData.setTrangThai(trangThai);
            request.setAttribute("user", formData);
            request.getRequestDispatcher("customer_form.jsp").forward(request, response);
            return;
        }

        boolean ok;

        if (isEdit) {
            Users user = usersDao.getById(Integer.parseInt(idStr));
            if (user == null) {
            	response.sendRedirect("EmployeeUserServlet?action=list");
                return;
            }
            user.setFullName(fullName);
            user.setSdt(sdt);
            user.setEmail(email);
            user.setAddress(address);
            user.setTrangThai(trangThai);

            // Chi doi mat khau neu nguoi dung nhap moi
            if (password != null && !password.trim().isEmpty()) {
                user.setPassword(password);
            }

            ok = usersDao.update(user);
        } else {
            Users user = new Users();
            user.setFullName(fullName);
            user.setSdt(sdt);
            user.setEmail(email);
            user.setAddress(address);
            user.setPassword(password != null ? password : "123456");
            user.setTrangThai(true);

            ok = usersDao.insert(user);
        }

        if (ok) {
        	response.sendRedirect("EmployeeUserServlet?action=list");
        } else {
            request.setAttribute("message", "Co loi xay ra, vui long thu lai!");
            request.getRequestDispatcher("customer_form.jsp").forward(request, response);
        }
    }

    // ================= DELETE =================
    private void handleDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            usersDao.delete(id);
        } catch (NumberFormatException ignored) {
        }
        response.sendRedirect("EmployeeUserServlet?action=list");
    }

    // ================= TOGGLE STATUS =================
    private void handleToggle(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Users user = usersDao.getById(id);
            if (user != null) {
                user.setTrangThai(!user.isTrangThai());
                usersDao.update(user);
            }
        } catch (NumberFormatException ignored) {
        }
        response.sendRedirect("EmployeeUserServlet?action=list");
    }
}
