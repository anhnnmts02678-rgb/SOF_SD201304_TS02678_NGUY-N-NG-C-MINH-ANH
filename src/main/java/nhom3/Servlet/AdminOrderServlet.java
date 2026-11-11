package nhom3.Servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import nhom3.Dao.*;
import nhom3.Model.*;

/**
 * Use case: Quan ly hoa don cho ADMIN.
 * URL: /admin/orders
 *
 * Cho phep admin xem danh sach hoa don (ai dat, gia tien, trang thai),
 * xem chi tiet 1 hoa don (san pham, so luong, don gia, dia chi giao hang
 * lay tu ho so khach hang), va cap nhat trang thai don hang qua tung buoc:
 * CHO_XAC_NHAN -> DANG_XU_LY -> DANG_GIAO -> DA_GIAO (da nhan hang),
 * hoac huy don khi con o CHO_XAC_NHAN.
 *
 * Truoc day chi co OrderManagementServlet ("/employee/orders") phuc vu
 * nhan vien; admin/order-management.jsp va admin/order-detail.jsp da
 * duoc thiet ke san (co check session admin, sidebar rieng) nhung
 * khong co servlet nao map toi "/admin/orders" nen admin khong the
 * truy cap trang quan ly hoa don. Servlet nay bo sung phan con thieu do.
 */
@WebServlet("/admin/orders")
public class AdminOrderServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private HoadonDao hoadonDao = new HoadonDao();
    private HoadonCTDao hdctDao = new HoadonCTDao();
    private UsersDao usersDao = new UsersDao();
    private ThanhtoanDao thanhtoanDao = new ThanhtoanDao();
    private SanphamDao sanphamDao = new SanphamDao();

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        if (!isAdmin(request)) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/login.jsp");
            return;
        }

        String action =
                request.getParameter("action");

        if ("detail".equals(action)) {

            showDetail(request, response);

        } else if (
                "process".equals(action)
                || "ship".equals(action)
                || "done".equals(action)
                || "cancel".equals(action)) {

            updateStatus(request, response);

        } else {

            showList(request, response);
        }
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        if (!isAdmin(request)) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/login.jsp");
            return;
        }

        updateStatus(request, response);
    }

    private boolean isAdmin(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        return session != null
                && session.getAttribute("admin") != null;
    }

    private void showList(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String trangThai =
                request.getParameter("trangThai");

        List<Hoadon> orderList;

        if (trangThai != null &&
            !trangThai.trim().isEmpty()) {

            orderList =
                    hoadonDao.getByTrangThai(trangThai);

        } else {

            orderList =
                    hoadonDao.getAll();
        }

        request.setAttribute(
                "orderList",
                orderList);

        // Ten khach hang de hien thi ngay tren bang danh sach, khoi phai
        // bam vao tung don moi biet "ai" da dat don do.
        java.util.Map<Integer, String> customerNames = new java.util.HashMap<>();

        if (orderList != null) {
            for (Hoadon hd : orderList) {
                if (!customerNames.containsKey(hd.getMaKH())) {
                    Users kh = usersDao.getById(hd.getMaKH());
                    customerNames.put(
                            hd.getMaKH(),
                            kh != null ? kh.getFullName() : ("KH" + hd.getMaKH()));
                }
            }
        }

        request.setAttribute(
                "customerNames",
                customerNames);

        request.setAttribute(
                "trangThai",
                trangThai);

        request.getRequestDispatcher(
                "/admin/order-management.jsp")
                .forward(request, response);
    }

    private void showDetail(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            int maHD = Integer.parseInt(
                    request.getParameter("maHD"));

            Hoadon hd =
                    hoadonDao.getById(maHD);

            if (hd == null) {

                response.sendRedirect(
                        request.getContextPath()
                        + "/admin/orders");

                return;
            }

            List<HoadonCT> items =
                    hdctDao.getByHoaDon(maHD);

            Users customer =
                    usersDao.getById(
                            hd.getMaKH());

            Thanhtoan thanhToan = null;

            if (hd.getMaThanhToan() > 0) {

                thanhToan =
                        thanhtoanDao.getById(
                                hd.getMaThanhToan());
            }

            request.setAttribute("hoadon", hd);
            request.setAttribute("items", items);
            request.setAttribute("customer", customer);
            request.setAttribute("thanhtoan", thanhToan);
            request.setAttribute("sanphamDao", sanphamDao);

            request.getRequestDispatcher(
                    "/admin/order-detail.jsp")
                    .forward(request, response);

        } catch (Exception e) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/admin/orders");
        }
    }

    private void updateStatus(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            int maHD =
                    Integer.parseInt(
                            request.getParameter("maHD"));

            String action =
                    request.getParameter("action");

            Hoadon hd =
                    hoadonDao.getById(maHD);

            if (hd == null) {

                response.sendRedirect(
                        "orders");

                return;
            }

            switch (action) {

            case "process":

                if ("CHO_XAC_NHAN".equals(hd.getTrangThai())) {

                    hoadonDao.updateTrangThai(
                            maHD,
                            "DANG_XU_LY");
                }

                break;

            case "ship":

                if ("DANG_XU_LY".equals(hd.getTrangThai())) {

                    hoadonDao.updateTrangThai(
                            maHD,
                            "DANG_GIAO");
                }

                break;

            case "done":

                if ("DANG_GIAO".equals(hd.getTrangThai())) {

                    hoadonDao.updateTrangThai(
                            maHD,
                            "DA_GIAO");
                }

                break;

            case "cancel":

                if ("CHO_XAC_NHAN".equals(hd.getTrangThai())) {

                    List<HoadonCT> items =
                            hdctDao.getByHoaDon(maHD);

                    if (items != null) {

                        for (HoadonCT ct : items) {

                            sanphamDao.congTonKho(
                                    ct.getMaSP(),
                                    ct.getSoLuong());
                        }
                    }

                    hoadonDao.updateTrangThai(
                            maHD,
                            "DA_HUY");

                    if (hd.getMaThanhToan() > 0) {

                        thanhtoanDao.updateTrangThai(
                                hd.getMaThanhToan(),
                                "DA_HUY");
                    }
                }

                break;
            }

            response.sendRedirect(
                    "orders?action=detail&maHD=" + maHD);

        } catch (Exception e) {

            e.printStackTrace();

            response.sendRedirect(
                    "orders");
        }
    }
}
