package nhom3.Servlet;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import nhom3.Dao.EmployeeDao;
import nhom3.Model.Employee;

/**
 * Servlet quan ly Nhan vien (Employee) - danh cho khu vuc /admin.
 * Cac hanh dong (action) ho tro:
 *  - list    : danh sach + tim kiem
 *  - add     : hien thi form them moi
 *  - edit    : hien thi form chinh sua
 *  - save    : luu (them moi hoac cap nhat)
 *  - delete  : xoa
 *  - toggle  : doi trang thai hoat dong / khoa
 */
@WebServlet("/admin/EmployeeServlet")
public class EmployeeServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final EmployeeDao employeeDao = new EmployeeDao();

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
        List<Employee> list;

        if (keyword != null && !keyword.trim().isEmpty()) {
            list = employeeDao.searchByKeyword(keyword.trim());
            request.setAttribute("keyword", keyword.trim());
        } else {
            list = employeeDao.getAll();
        }

        request.setAttribute("employeeList", list);
        request.getRequestDispatcher("employee.jsp").forward(request, response);
    }

    // ================= FORM ADD/EDIT =================
    private void handleShowEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Employee emp = employeeDao.getById(id);

            if (emp == null) {
                request.setAttribute("message", "Khong tim thay nhan vien!");
                handleList(request, response);
                return;
            }

            showForm(request, response, emp);

        } catch (NumberFormatException e) {
            response.sendRedirect("EmployeeServlet?action=list");
        }
    }

    private void showForm(HttpServletRequest request, HttpServletResponse response, Employee emp)
            throws ServletException, IOException {
        request.setAttribute("emp", emp);
        request.getRequestDispatcher("employee_form.jsp").forward(request, response);
    }

    // ================= SAVE (ADD / UPDATE) =================
    private void handleSave(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("maNV");
        String hoTen = request.getParameter("hoTen");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String sdt = request.getParameter("sdt");
        boolean trangThai = "on".equals(request.getParameter("trangThai"));

        boolean isEdit = idStr != null && !idStr.trim().isEmpty();

        // Kiem tra username da ton tai chua (tranh trung)
        Employee existed = employeeDao.getByUsername(username);
        if (existed != null && (!isEdit || existed.getMaNV() != Integer.parseInt(idStr))) {
            request.setAttribute("message", "Ten dang nhap nay da duoc su dung!");
            Employee formData = new Employee();
            if (isEdit) formData.setMaNV(Integer.parseInt(idStr));
            formData.setHoTen(hoTen);
            formData.setUsername(username);
            formData.setEmail(email);
            formData.setSdt(sdt);
            formData.setTrangThai(trangThai);
            request.setAttribute("emp", formData);
            request.getRequestDispatcher("employee_form.jsp").forward(request, response);
            return;
        }

        boolean ok;

        if (isEdit) {
            Employee emp = employeeDao.getById(Integer.parseInt(idStr));
            if (emp == null) {
                response.sendRedirect("EmployeeServlet?action=list");
                return;
            }
            emp.setHoTen(hoTen);
            emp.setUsername(username);
            emp.setEmail(email);
            emp.setSdt(sdt);
            emp.setTrangThai(trangThai);

            // Chi doi mat khau neu nguoi dung nhap moi
            if (password != null && !password.trim().isEmpty()) {
                emp.setPassword(password);
            }

            ok = employeeDao.update(emp);
        } else {
            Employee emp = new Employee();
            emp.setHoTen(hoTen);
            emp.setUsername(username);
            emp.setEmail(email);
            emp.setSdt(sdt);
            emp.setPassword(password != null && !password.trim().isEmpty() ? password : "123456");
            emp.setNgayTao(new Timestamp(System.currentTimeMillis()));
            emp.setTrangThai(trangThai);

            ok = employeeDao.insert(emp);
        }

        if (ok) {
            response.sendRedirect("EmployeeServlet?action=list");
        } else {
            request.setAttribute("message", "Co loi xay ra, vui long thu lai!");
            request.getRequestDispatcher("employee_form.jsp").forward(request, response);
        }
    }

    // ================= DELETE =================
    private void handleDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            employeeDao.delete(id);
        } catch (NumberFormatException ignored) {
        }
        response.sendRedirect("EmployeeServlet?action=list");
    }

    // ================= TOGGLE STATUS =================
    private void handleToggle(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Employee emp = employeeDao.getById(id);
            if (emp != null) {
                employeeDao.updateTrangThai(id, !emp.isTrangThai());
            }
        } catch (NumberFormatException ignored) {
        }
        response.sendRedirect("EmployeeServlet?action=list");
    }
}
