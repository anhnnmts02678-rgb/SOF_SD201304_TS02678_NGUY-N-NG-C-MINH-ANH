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

import nhom3.Dao.DanhgiaDao;
import nhom3.Dao.SanphamDao;
import nhom3.Dao.UsersDao;
import nhom3.Model.Danhgia;
import nhom3.Model.Sanpham;
import nhom3.Model.Users;

/**
 * Quan ly danh gia san pham: Xem danh sach, tim kiem, loc theo so sao va xoa danh gia.
 * Duoc phep truy cap boi nhan vien va admin (xem EmployeeFilter, ap dung cho "/employee/*").
 * Danh gia chi do khach hang tao (qua DanhgiaServlet) nen trang nay khong co chuc nang them/sua noi dung.
 */
@WebServlet(urlPatterns = {
        "/admin/EmployeeDanhgiaServlet"
})
public class EmployeeDanhgiaServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final DanhgiaDao danhgiaDao = new DanhgiaDao();

    private final UsersDao usersDao = new UsersDao();

    private final SanphamDao sanphamDao = new SanphamDao();

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

        case "delete":
            handleDelete(request, response);
            break;

        case "list":
        default:
            handleList(request, response);
            break;
        }

    }

    // ==========================
    // DANH SÁCH ĐÁNH GIÁ
    // ==========================
    private void handleList(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String keyword = request.getParameter("keyword");
        String saoParam = request.getParameter("soSao");

        List<Danhgia> list = danhgiaDao.getAll();

        if (list == null) {
            list = new ArrayList<>();
        }

        // Gan them ten khach hang va ten san pham de hien thi (khong luu DB)
        for (Danhgia dg : list) {

            Users nguoiDanhGia = usersDao.getById(dg.getMaKH());

            dg.setTenKhachHang(nguoiDanhGia != null ? nguoiDanhGia.getFullName() : "Khách hàng #" + dg.getMaKH());

            Sanpham sp = sanphamDao.getById(dg.getMaSP());

            dg.setTenSanPham(sp != null ? sp.getTenSP() : "Sản phẩm #" + dg.getMaSP());

        }

        if (keyword != null && !keyword.trim().isEmpty()) {

            String kw = keyword.trim().toLowerCase();

            list = list.stream()
                    .filter(dg ->
                        (dg.getNoiDungDanhGia() != null && dg.getNoiDungDanhGia().toLowerCase().contains(kw))
                        || (dg.getTenKhachHang() != null && dg.getTenKhachHang().toLowerCase().contains(kw))
                        || (dg.getTenSanPham() != null && dg.getTenSanPham().toLowerCase().contains(kw)))
                    .collect(Collectors.toList());

            request.setAttribute("keyword", keyword.trim());
        }

        if (saoParam != null && !saoParam.trim().isEmpty()) {

            try {

                int sao = Integer.parseInt(saoParam.trim());

                list = list.stream()
                        .filter(dg -> dg.getSoSao() == sao)
                        .collect(Collectors.toList());

                request.setAttribute("selectedSao", sao);

            } catch (Exception e) {
                // bo qua loc neu tham so khong hop le
            }
        }

        request.setAttribute("dgList", list);

        request.getRequestDispatcher("danhgia.jsp")
                .forward(request, response);
    }

    // ==========================
    // DUYỆT / ẨN ĐÁNH GIÁ
    // ==========================

    // ==========================
    // XÓA ĐÁNH GIÁ VI PHẠM
    // ==========================
    private void handleDelete(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            int id = Integer.parseInt(request.getParameter("id"));

            danhgiaDao.delete(id);

        } catch (Exception e) {

            e.printStackTrace();

        }

        redirectBackToList(request, response);

    }

    // ==========================
    // GIỮ LẠI BỘ LỌC KHI QUAY VỀ DANH SÁCH
    // ==========================
    private void redirectBackToList(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        StringBuilder url = new StringBuilder("EmployeeDanhgiaServlet?action=list");

        appendParam(url, request, "keyword");
        appendParam(url, request, "soSao");

        response.sendRedirect(url.toString());

    }

    private void appendParam(StringBuilder url, HttpServletRequest request, String name) {

        String value = request.getParameter(name);

        if (value != null && !value.trim().isEmpty()) {

            url.append("&").append(name).append("=")
               .append(java.net.URLEncoder.encode(value.trim(), java.nio.charset.StandardCharsets.UTF_8));

        }

    }

}
