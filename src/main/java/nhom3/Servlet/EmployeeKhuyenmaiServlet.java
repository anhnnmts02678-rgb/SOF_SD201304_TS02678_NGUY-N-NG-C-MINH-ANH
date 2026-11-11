package nhom3.Servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import nhom3.Dao.KhuyenmaiDao;
import nhom3.Model.Khuyenmai;
import java.sql.Date;

/**
 * Quan ly khuyen mai (CRUD): danh sach, them, sua, an/hien, xoa.
 * Duoc phep truy cap boi nhan vien va admin (xem EmployeeFilter, ap dung cho "/employee/*").
 */
@WebServlet(urlPatterns = {
        "/admin/EmployeeKhuyenmaiServlet"
})
public class EmployeeKhuyenmaiServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

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
    // DANH SÁCH KHUYẾN MÃI
    // ==========================
    private void handleList(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String keyword = request.getParameter("keyword");
        String status = request.getParameter("status");

        List<Khuyenmai> list = khuyenmaiDao.getAll();

        if (list == null) {
            list = new ArrayList<>();
        }

        if (keyword != null && !keyword.trim().isEmpty()) {

            String kw = keyword.trim().toLowerCase();

            list = list.stream()
                    .filter(km ->
                        (km.getLoaiKM() != null && km.getLoaiKM().toLowerCase().contains(kw))
                        || (km.getMoTa() != null && km.getMoTa().toLowerCase().contains(kw)))
                    .collect(Collectors.toList());

            request.setAttribute("keyword", keyword.trim());
        }

        if (status != null && !status.trim().isEmpty()) {

            boolean wantActive = "active".equals(status);

            list = list.stream()
                    .filter(km -> km.isTrangThai() == wantActive)
                    .collect(Collectors.toList());

            request.setAttribute("selectedStatus", status);
        }

        request.setAttribute("kmList", list);

        request.getRequestDispatcher("khuyenmai.jsp")
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

            Khuyenmai km = khuyenmaiDao.getById(id);

            if (km == null) {

                request.setAttribute("message", "Không tìm thấy khuyến mãi!");

                handleList(request, response);

                return;
            }

            showForm(request, response, km);

        } catch (Exception e) {

            response.sendRedirect(currentServletName(request) + "?action=list");

        }

    }

    private void showForm(HttpServletRequest request,
            HttpServletResponse response,
            Khuyenmai km)
            throws ServletException, IOException {

        request.setAttribute("km", km);

        request.getRequestDispatcher("khuyenmai_form.jsp")
                .forward(request, response);

    }

    // ==========================
    // LƯU KHUYẾN MÃI
    // ==========================
    private void handleSave(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("maKM");

        String loaiKM = request.getParameter("loaiKM");

        String phanTramStr = request.getParameter("phanTramGiam");

        String ngayBatDau = request.getParameter("ngayBatDau");

        String ngayKetThuc = request.getParameter("ngayKetThuc");

        String moTa = request.getParameter("moTa");

        boolean trangThai = "on".equals(request.getParameter("trangThai"));

        boolean isEdit = idStr != null && !idStr.trim().isEmpty();

        Khuyenmai formValue = isEdit ? khuyenmaiDao.getById(Integer.parseInt(idStr)) : new Khuyenmai();

        if (formValue == null) {
            formValue = new Khuyenmai();
        }

        // gan tam cac gia tri nguoi dung vua nhap de neu loi thi khong bi mat du lieu
        formValue.setLoaiKM(loaiKM);
        formValue.setNgayBatDau(Date.valueOf(ngayBatDau));
        formValue.setNgayKetThuc(Date.valueOf(ngayKetThuc));
        formValue.setMoTa(moTa);
        formValue.setTrangThai(trangThai);

        if (loaiKM == null || loaiKM.trim().isEmpty()) {

            request.setAttribute("message", "Tên/Loại khuyến mãi không được để trống!");

            showForm(request, response, formValue);

            return;
        }

        double phanTramGiam = parseDoubleSafe(phanTramStr, -1);

        if (phanTramGiam < 0 || phanTramGiam > 100) {

            request.setAttribute("message", "Phần trăm giảm phải trong khoảng 0 - 100!");

            showForm(request, response, formValue);

            return;
        }

        if (ngayBatDau == null || ngayBatDau.trim().isEmpty()
                || ngayKetThuc == null || ngayKetThuc.trim().isEmpty()) {

            request.setAttribute("message", "Vui lòng chọn ngày bắt đầu và ngày kết thúc!");

            showForm(request, response, formValue);

            return;
        }

        if (ngayBatDau.compareTo(ngayKetThuc) > 0) {

            request.setAttribute("message", "Ngày bắt đầu phải trước hoặc bằng ngày kết thúc!");

            showForm(request, response, formValue);

            return;
        }

        formValue.setPhanTramGiam(phanTramGiam);

        boolean ok;

        int savedId;

        if (isEdit) {

            int id = Integer.parseInt(idStr);

            formValue.setMaKM(id);

            ok = khuyenmaiDao.update(formValue);

            savedId = id;

        } else {

            ok = khuyenmaiDao.insert(formValue);

            savedId = formValue.getMaKM();

        }

        if (ok) {

            response.sendRedirect(currentServletName(request) + "?action=edit&id=" + savedId);

        } else {

            request.setAttribute("message", "Có lỗi xảy ra, vui lòng thử lại!");

            showForm(request, response, formValue);

        }

    }

    // ==========================
    // XÓA KHUYẾN MÃI
    // ==========================
    private void handleDelete(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            int id = Integer.parseInt(request.getParameter("id"));

            khuyenmaiDao.delete(id);

        } catch (Exception e) {

            e.printStackTrace();

        }

        response.sendRedirect(currentServletName(request) + "?action=list");

    }

    // ==========================
    // BẬT / TẮT KHUYẾN MÃI
    // ==========================
    private void handleToggle(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            int id = Integer.parseInt(request.getParameter("id"));

            Khuyenmai km = khuyenmaiDao.getById(id);

            if (km != null) {

                khuyenmaiDao.updateTrangThai(id, !km.isTrangThai());

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        response.sendRedirect(currentServletName(request) + "?action=list");

    }

    // ==========================
    // LẤY TÊN SERVLET HIỆN TẠI
    // ==========================
    private String currentServletName(HttpServletRequest request) {

        String uri = request.getRequestURI();

        return uri.substring(uri.lastIndexOf('/') + 1);

    }

    // ==========================
    // PARSE DOUBLE
    // ==========================
    private double parseDoubleSafe(String s, double def) {

        try {

            return Double.parseDouble(s.trim());

        } catch (Exception e) {

            return def;

        }

    }

}
