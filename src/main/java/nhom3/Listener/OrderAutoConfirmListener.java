package nhom3.Listener;

import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import nhom3.Dao.HoadonDao;
import nhom3.Model.Hoadon;

/**
 * Tu dong xac nhan don hang (CHO_XAC_NHAN -> DANG_XU_LY) sau mot khoang
 * thoi gian cho phep khach huy don.
 *
 * - Trong khoang AUTO_CONFIRM_MINUTES ke tu luc dat hang (Hoadon.ngayThanhToan),
 *   khach hang van con nut "Huy don hang" (xem OrderDetailServlet / order-detail.jsp).
 * - Sau khi het thoi gian do, job nay se tu dong chuyen don sang "DANG_XU_LY".
 * - Khi da o trang thai "DANG_XU_LY" (do job tu dong hoac do nhan vien bam
 *   nut "Dang xu ly"), khach hang KHONG con duoc huy don nua
 *   (xem dieu kien coTheHuy trong OrderDetailServlet).
 *
 * De doi thoi gian cho, chi can sua hang so AUTO_CONFIRM_MINUTES ben duoi:
 *   - 60  -> cho 1 tieng
 *   - 30  -> cho nua tieng
 */
@WebListener
public class OrderAutoConfirmListener implements ServletContextListener {

    /**
     * So phut khach hang duoc phep huy don truoc khi don tu dong duoc xac nhan.
     * Doi thanh 30 neu muon cho "nua tieng" thay vi "1 tieng".
     */
    public static final long AUTO_CONFIRM_MINUTES = 60; // = 1 tieng. Doi thanh 30 neu can nua tieng.

    /** Chu ky quet don qua han (chay moi 1 phut la du min). */
    private static final long CHECK_INTERVAL_SECONDS = 60;

    private ScheduledExecutorService scheduler;

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t = new Thread(r, "order-auto-confirm-job");
            t.setDaemon(true);
            return t;
        });

        scheduler.scheduleAtFixedRate(
                this::autoConfirmExpiredOrders,
                CHECK_INTERVAL_SECONDS,
                CHECK_INTERVAL_SECONDS,
                TimeUnit.SECONDS
        );

        System.out.println(
                "[OrderAutoConfirmListener] Da khoi dong job tu dong xac nhan don "
                + "(sau " + AUTO_CONFIRM_MINUTES + " phut, quet moi "
                + CHECK_INTERVAL_SECONDS + " giay).");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (scheduler != null) {
            scheduler.shutdownNow();
        }
    }

    private void autoConfirmExpiredOrders() {
        try {
            HoadonDao hoadonDao = new HoadonDao();

            Timestamp beforeTime = new Timestamp(
                    System.currentTimeMillis() - AUTO_CONFIRM_MINUTES * 60_000L
            );

            List<Hoadon> expired = hoadonDao.getExpiredChoXacNhan(beforeTime);

            if (expired == null || expired.isEmpty()) {
                return;
            }

            for (Hoadon hd : expired) {
                boolean ok = hoadonDao.updateTrangThai(hd.getMaHD(), "DANG_XU_LY");
                System.out.println(
                        "[OrderAutoConfirmListener] Tu dong xac nhan don #"
                        + hd.getMaHD() + " -> DANG_XU_LY: "
                        + (ok ? "OK" : "THAT BAI"));
            }

        } catch (Exception e) {
            System.out.println(
                    "[OrderAutoConfirmListener] Loi khi tu dong xac nhan don: "
                    + e.getMessage());
        }
    }
}
