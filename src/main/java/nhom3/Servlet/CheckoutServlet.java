package nhom3.Servlet;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import nhom3.Dao.GiohangCTDao;
import nhom3.Dao.GiohangDao;
import nhom3.Dao.HoadonCTDao;
import nhom3.Dao.HoadonDao;
import nhom3.Dao.KhuyenmaiDao;
import nhom3.Dao.SanphamDao;
import nhom3.Dao.ThanhtoanDao;

import nhom3.Model.Giohang;
import nhom3.Model.GiohangCT;
import nhom3.Model.Hoadon;
import nhom3.Model.HoadonCT;
import nhom3.Model.Khuyenmai;
import nhom3.Model.Sanpham;
import nhom3.Model.Thanhtoan;
import nhom3.Model.Users;

@WebServlet("/CheckoutServlet")
public class CheckoutServlet extends HttpServlet {

    // =====================================================
    // HIỂN THỊ TRANG CHECKOUT
    // =====================================================
    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        Users user =
                (Users) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        GiohangDao giohangDao = new GiohangDao();
        GiohangCTDao giohangCTDao = new GiohangCTDao();
        SanphamDao sanphamDao = new SanphamDao();

        Giohang gh =
                giohangDao.getActiveByKhachHang(
                        user.getMaKH()
                );

        if (gh == null) {
            response.sendRedirect("CartServlet");
            return;
        }

        List<GiohangCT> list =
                giohangCTDao.getByGioHang(
                        gh.getMaGH()
                );

        if (list == null || list.isEmpty()) {
            response.sendRedirect("CartServlet");
            return;
        }

        // =====================================================
        // DANH SÁCH KHUYẾN MÃI
        // =====================================================

        KhuyenmaiDao khuyenmaiDao =
                new KhuyenmaiDao();

        List<Khuyenmai> kmList =
                khuyenmaiDao.getAllActive();

        request.setAttribute("kmList", kmList);

        // =====================================================
        // TÍNH TỔNG TIỀN
        // =====================================================

        double tongTienGoc = 0;

        for (GiohangCT item : list) {
            tongTienGoc += item.getTongGia();
        }

        // Khuyến mãi đã chọn
        Khuyenmai km =
                (Khuyenmai) request.getAttribute("selectedKM");

        double giamGia = 0;

        if (km != null) {

            giamGia =
                    tongTienGoc
                    * km.getPhanTramGiam()
                    / 100;
        }

        double tongTien =
                tongTienGoc - giamGia;

        request.setAttribute(
                "cartItems",
                list
        );

        request.setAttribute(
                "sanphamDao",
                sanphamDao
        );

        request.setAttribute(
                "tongTienGoc",
                tongTienGoc
        );

        request.setAttribute(
                "giamGia",
                giamGia
        );

        request.setAttribute(
                "tongTien",
                tongTien
        );

        request.setAttribute(
                "user",
                user
        );

        request.getRequestDispatcher(
                "checkout.jsp"
        ).forward(request, response);
    }


    // =====================================================
    // XỬ LÝ CHECKOUT
    // =====================================================
    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session =
                request.getSession();

        Users user =
                (Users) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String action =
                request.getParameter("action");


        // =================================================
        // 1. ÁP DỤNG KHUYẾN MÃI
        // =================================================

        if ("applyKM".equals(action)) {

            String kmParam =
                    request.getParameter("maKM");

            if (kmParam != null &&
                !kmParam.isEmpty()) {

                try {

                    int maKM =
                            Integer.parseInt(kmParam);

                    KhuyenmaiDao kmDao =
                            new KhuyenmaiDao();

                    Khuyenmai km =
                            kmDao.getById(maKM);

                    request.setAttribute(
                            "selectedKM",
                            km
                    );

                } catch (Exception e) {

                    e.printStackTrace();
                }
            }

            doGet(request, response);
            return;
        }


        // =================================================
        // 2. XÁC NHẬN ĐẶT HÀNG
        // =================================================

        if ("confirmOrder".equals(action)) {

            String phuongThuc =
                    request.getParameter(
                            "phuongThuc"
                    );

            if (phuongThuc == null ||
                phuongThuc.trim().isEmpty()) {

                request.setAttribute(
                        "error",
                        "Vui lòng chọn phương thức thanh toán."
                );

                doGet(request, response);
                return;
            }


            try {

                // =============================================
                // LẤY GIỎ HÀNG
                // =============================================

                GiohangDao giohangDao =
                        new GiohangDao();

                GiohangCTDao giohangCTDao =
                        new GiohangCTDao();

                SanphamDao sanphamDao =
                        new SanphamDao();

                Giohang gh =
                        giohangDao.getActiveByKhachHang(
                                user.getMaKH()
                        );

                if (gh == null) {

                    request.setAttribute(
                            "error",
                            "Không tìm thấy giỏ hàng."
                    );

                    doGet(request, response);
                    return;
                }


                List<GiohangCT> cartItems =
                        giohangCTDao.getByGioHang(
                                gh.getMaGH()
                        );


                if (cartItems == null ||
                    cartItems.isEmpty()) {

                    request.setAttribute(
                            "error",
                            "Giỏ hàng đang trống."
                    );

                    doGet(request, response);
                    return;
                }


                // =============================================
                // TÍNH TỔNG TIỀN
                // =============================================

                double tongTienGoc = 0;

                for (GiohangCT item : cartItems) {

                    tongTienGoc +=
                            item.getTongGia();
                }


                // =============================================
                // LẤY KHUYẾN MÃI
                // =============================================

                double giamGia = 0;

                String maKMParam =
                        request.getParameter("maKM");

                Khuyenmai km = null;

                if (maKMParam != null &&
                    !maKMParam.isEmpty()) {

                    try {

                        int maKM =
                                Integer.parseInt(
                                        maKMParam
                                );

                        if (maKM > 0) {

                            KhuyenmaiDao kmDao =
                                    new KhuyenmaiDao();

                            km =
                                    kmDao.getById(
                                            maKM
                                    );

                            if (km != null) {

                                giamGia =
                                        tongTienGoc
                                        * km.getPhanTramGiam()
                                        / 100;
                            }
                        }

                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                }


                double tongTien =
                        tongTienGoc - giamGia;


                // =============================================
                // KIỂM TRA TỒN KHO
                // =============================================

                for (GiohangCT item : cartItems) {

                    Sanpham sp =
                            sanphamDao.getById(
                                    item.getMaSP()
                            );

                    if (sp == null) {

                        throw new Exception(
                                "Không tìm thấy sản phẩm mã "
                                + item.getMaSP()
                        );
                    }

                    if (sp.getSoLuong()
                            < item.getSoLuong()) {

                        throw new Exception(
                                "Sản phẩm "
                                + sp.getTenSP()
                                + " không đủ số lượng."
                        );
                    }
                }


                // =============================================
                // TẠO THANH TOÁN
                // =============================================

                Thanhtoan thanhtoan =
                        new Thanhtoan();

                thanhtoan.setPhuongThuc(
                        phuongThuc
                );

                if ("COD".equals(phuongThuc)) {

                    thanhtoan.setTrangThai(
                            "CHUA_THANH_TOAN"
                    );

                } else {

                    thanhtoan.setTrangThai(
                            "CHO_THANH_TOAN"
                    );
                }

                thanhtoan.setThoiGian(
                        new Timestamp(
                                System.currentTimeMillis()
                        )
                );

                // Mã giao dịch nội bộ
                thanhtoan.setMaGiaoDich(
                        "HD-" +
                        System.currentTimeMillis()
                );


                ThanhtoanDao thanhtoanDao =
                        new ThanhtoanDao();

                boolean paymentCreated =
                        thanhtoanDao.insert(
                                thanhtoan
                        );


                if (!paymentCreated) {

                    throw new Exception(
                            "Không thể tạo thông tin thanh toán."
                    );
                }


                // =============================================
                // LẤY MÃ THANH TOÁN
                // =============================================

                // Sau persist Hibernate sẽ tự sinh MaThanhToan
                int maThanhToan =
                        thanhtoan.getMaThanhToan();


                // =============================================
                // TẠO HÓA ĐƠN
                // =============================================

                Hoadon hd =
                        new Hoadon();

                hd.setMaKH(
                        user.getMaKH()
                );

                hd.setMaThanhToan(
                        maThanhToan
                );

                hd.setTongGia(
                        tongTien
                );

                hd.setQrThanhToan(
                        null
                );

                hd.setNgayThanhToan(
                        new Timestamp(
                                System.currentTimeMillis()
                        )
                );


                if ("COD".equals(phuongThuc)) {

                    hd.setTrangThai(
                            "CHO_XAC_NHAN"
                    );

                } else {

                    hd.setTrangThai(
                            "CHO_THANH_TOAN"
                    );
                }


                HoadonDao hoadonDao =
                        new HoadonDao();

                boolean hoaDonCreated =
                        hoadonDao.insert(hd);


                if (!hoaDonCreated) {

                    throw new Exception(
                            "Không thể tạo hóa đơn."
                    );
                }


                // =============================================
                // TẠO CHI TIẾT HÓA ĐƠN
                // =============================================

                HoadonCTDao hoadonCTDao =
                        new HoadonCTDao();

                for (GiohangCT item : cartItems) {

                    Sanpham sp =
                            sanphamDao.getById(
                                    item.getMaSP()
                            );

                    HoadonCT hdct =
                            new HoadonCT();

                    hdct.setMaHD(
                            hd.getMaHD()
                    );

                    hdct.setMaSP(
                            item.getMaSP()
                    );

                    hdct.setSoLuong(
                            item.getSoLuong()
                    );

                    hdct.setDonGia(
                            sp.getGiaThanh()
                    );

                    hdct.setTongGia(
                            item.getTongGia()
                    );


                    boolean detailCreated =
                            hoadonCTDao.insert(
                                    hdct
                            );


                    if (!detailCreated) {

                        throw new Exception(
                                "Không thể tạo chi tiết hóa đơn."
                        );
                    }


                    // =========================================
                    // TRỪ TỒN KHO
                    // =========================================

                    boolean stockUpdated =
                            sanphamDao.truTonKho(
                                    item.getMaSP(),
                                    item.getSoLuong()
                            );


                    if (!stockUpdated) {

                        throw new Exception(
                                "Không thể cập nhật tồn kho."
                        );
                    }
                }


                // =============================================
                // XÓA SẢN PHẨM KHỎI GIỎ HÀNG
                // =============================================

                boolean cartDeleted =
                        giohangCTDao.deleteByGioHang(
                                gh.getMaGH()
                        );


                if (!cartDeleted) {

                    System.out.println(
                            "Cảnh báo: Không xóa được chi tiết giỏ hàng."
                    );
                }


                // =============================================
                // LƯU MÃ HÓA ĐƠN VÀO SESSION
                // =============================================

                session.setAttribute(
                        "lastOrderId",
                        hd.getMaHD()
                );


                // =============================================
                // CHUYỂN SANG LỊCH SỬ ĐƠN HÀNG
                // =============================================

                response.sendRedirect(
                        "OrderHistoryServlet"
                );

                return;


            } catch (Exception e) {

                e.printStackTrace();

                request.setAttribute(
                        "error",
                        "Đặt hàng thất bại: "
                        + e.getMessage()
                );

                doGet(request, response);
                return;
            }
        }


        // =================================================
        // NẾU KHÔNG CÓ ACTION
        // =================================================

        doGet(request, response);
    }
}