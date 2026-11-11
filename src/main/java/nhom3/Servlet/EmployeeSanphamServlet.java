package nhom3.Servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import nhom3.Dao.DanhmucDao;
import nhom3.Dao.KhuyenmaiDao;
import nhom3.Dao.SanphamDao;
import nhom3.Model.Danhmuc;
import nhom3.Model.Employee;
import nhom3.Model.Sanpham;

@WebServlet(urlPatterns = {
        "/employee/EmployeeSanphamServlet"
})
public class EmployeeSanphamServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final SanphamDao sanphamDao = new SanphamDao();
    private final DanhmucDao danhmucDao = new DanhmucDao();
    private final KhuyenmaiDao khuyenmaiDao = new KhuyenmaiDao();

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        if (action == null) {
            action = "list";
        }

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
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        handleSave(request, response);

    }

    // ==========================
    // DANH SÁCH SẢN PHẨM
    // ==========================
    private void handleList(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String keyword = request.getParameter("keyword");
        String dmParam = request.getParameter("maDM");

        List<Sanpham> list;

        if (keyword != null && !keyword.trim().isEmpty()) {

            list = sanphamDao.getByTen(keyword.trim());
            request.setAttribute("keyword", keyword.trim());

        } else if (dmParam != null && !dmParam.trim().isEmpty()) {

            try {

                int maDM = Integer.parseInt(dmParam);

                list = sanphamDao.getByDanhMuc(maDM);

                request.setAttribute("selectedDM", maDM);

            } catch (Exception e) {

                list = sanphamDao.getAll();

            }

        } else {
            list = sanphamDao.getAll();
        }

        request.setAttribute("productList", list);
        request.setAttribute("danhMucList", danhmucDao.getAll());

        request.getRequestDispatcher("product.jsp")
                .forward(request, response);
    }

    // ==========================
    // HIỂN THỊ FORM
    // ==========================
    private void handleShowEditForm(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            int id = Integer.parseInt(request.getParameter("id"));

            Sanpham sp = sanphamDao.getById(id);

            if (sp == null) {

                request.setAttribute("message",
                        "Không tìm thấy sản phẩm!");

                handleList(request, response);

                return;
            }

            showForm(request, response, sp);

        } catch (Exception e) {

            response.sendRedirect(currentServletName(request)
                    + "?action=list");

        }

    }

    private void showForm(HttpServletRequest request,
            HttpServletResponse response,
            Sanpham sp)
            throws ServletException, IOException {

        request.setAttribute("sp", sp);

        request.setAttribute("danhMucList",
                danhmucDao.getAll());

        request.setAttribute("khuyenMaiList",
                khuyenmaiDao.getAll());

        request.getRequestDispatcher("product_form.jsp")
                .forward(request, response);

    }

    // ==========================
    // LƯU SẢN PHẨM
    // ==========================
    private void handleSave(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("maSP");

        String tenSP = request.getParameter("tenSP");

        String soLuongStr = request.getParameter("soLuong");

        String moTa = request.getParameter("moTa");

        String giaThanhStr = request.getParameter("giaThanh");

        String urlHinhAnh = request.getParameter("urlHinhAnh");

        String maDMStr = request.getParameter("maDM");

        String maKMStr = request.getParameter("maKM");

        String nguonGoc = request.getParameter("nguonGoc");

        boolean trangThai =
                "on".equals(request.getParameter("trangThai"));

        boolean isEdit =
                idStr != null && !idStr.trim().isEmpty();

        if (tenSP == null || tenSP.trim().isEmpty()) {

            request.setAttribute("message",
                    "Tên sản phẩm không được để trống!");

            showForm(request, response,
                    new Sanpham());

            return;

        }

        int soLuong = parseIntSafe(soLuongStr, 0);

        double giaThanh = parseDoubleSafe(giaThanhStr, 0);
        
        int maDM = parseIntSafe(maDMStr, 0);

        int maKM = parseIntSafe(maKMStr, 0);
        
        boolean ok;

        int savedId;

        if (isEdit) {

            int id = Integer.parseInt(idStr);

            Sanpham sp = sanphamDao.getById(id);

            if (sp == null) {

                response.sendRedirect(
                        currentServletName(request)
                        + "?action=list");

                return;

            }

            sp.setTenSP(tenSP.trim());

            sp.setSoLuong(soLuong);

            sp.setMoTa(moTa);

            sp.setGiaThanh(giaThanh);

            sp.setUrlHinhAnh(urlHinhAnh);

            sp.setMaDM(maDM);

            sp.setMaKM(maKM);

            sp.setNguonGoc(nguonGoc);

            sp.setTrangThai(trangThai);

            ok = sanphamDao.update(sp);

            savedId = id;

        } else {

            Sanpham sp = new Sanpham();

            sp.setTenSP(tenSP.trim());

            sp.setSoLuong(soLuong);

            sp.setMoTa(moTa);

            sp.setGiaThanh(giaThanh);

            sp.setUrlHinhAnh(urlHinhAnh);

            sp.setMaDM(maDM);

            sp.setMaKM(maKM);

            sp.setNguonGoc(nguonGoc);

            sp.setTrangThai(true);

            sp.setMaNV(getCurrentEmployeeId(request));

            ok = sanphamDao.insert(sp);

            savedId = sp.getMaSP();

        }

        if (ok) {

            response.sendRedirect(
                    currentServletName(request)
                    + "?action=edit&id=" + savedId);

        } else {

            request.setAttribute("message",
                    "Có lỗi xảy ra, vui lòng thử lại!");

            showForm(request, response,
                    isEdit
                    ? sanphamDao.getById(
                            Integer.parseInt(idStr))
                    : new Sanpham());

        }

    }

    // ==========================
    // XÓA SẢN PHẨM
    // ==========================
    private void handleDelete(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            int id = Integer.parseInt(
                    request.getParameter("id"));

            sanphamDao.delete(id);

        } catch (Exception e) {

            e.printStackTrace();

        }

        response.sendRedirect(
                currentServletName(request)
                + "?action=list");

    }

    // ==========================
    // ẨN / HIỆN SẢN PHẨM
    // ==========================
    private void handleToggle(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {
        try {

            int id = Integer.parseInt(
                    request.getParameter("id"));

            Sanpham sp = sanphamDao.getById(id);

            if (sp != null) {

                sp.setTrangThai(!sp.isTrangThai());

                sanphamDao.update(sp);

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        response.sendRedirect(
                currentServletName(request)
                + "?action=list");

    }

    // ==========================
    // LẤY MÃ NHÂN VIÊN ĐĂNG NHẬP
    // ==========================
    private int getCurrentEmployeeId(
            HttpServletRequest request) {

        HttpSession session =
                request.getSession(false);

        if (session != null) {

            Object emp =
                    session.getAttribute("employee");

            if (emp instanceof Employee) {

                return ((Employee) emp).getMaNV();

            }

        }

        return 0;

    }

    // ==========================
    // LẤY TÊN SERVLET HIỆN TẠI
    // ==========================
    private String currentServletName(
            HttpServletRequest request) {

        String uri = request.getRequestURI();

        return uri.substring(
                uri.lastIndexOf('/') + 1);

    }

    // ==========================
    // PARSE INT
    // ==========================
    private int parseIntSafe(
            String s,
            int def) {

        try {

            return Integer.parseInt(s.trim());

        } catch (Exception e) {

            return def;

        }

    }

    // ==========================
    // PARSE DOUBLE
    // ==========================
    private double parseDoubleSafe(
            String s,
            double def) {

        try {

            return Double.parseDouble(s.trim());

        } catch (Exception e) {

            return def;

        }

    }

}