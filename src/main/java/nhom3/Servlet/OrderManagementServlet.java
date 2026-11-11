package nhom3.Servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import nhom3.Dao.*;
import nhom3.Model.*;

@WebServlet("/employee/orders")
public class OrderManagementServlet extends HttpServlet {

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

        HttpSession session = request.getSession(false);

        if (session == null ||
            session.getAttribute("employee") == null) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/employee-login.jsp");
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

        HttpSession session = request.getSession(false);

        if (session == null ||
            session.getAttribute("employee") == null) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/employee-login.jsp");
            return;
        }

        updateStatus(request, response);
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

        request.setAttribute(
                "trangThai",
                trangThai);

        request.getRequestDispatcher(
                "/employee/order-management.jsp")
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
                        + "/employee/orders");

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
                    "/employee/order-detail.jsp")
                    .forward(request, response);

        } catch (Exception e) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/employee/orders");
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

            System.out.println("========== DEBUG ==========");
            System.out.println("ACTION = " + action);
            System.out.println("MAHD = " + maHD);
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