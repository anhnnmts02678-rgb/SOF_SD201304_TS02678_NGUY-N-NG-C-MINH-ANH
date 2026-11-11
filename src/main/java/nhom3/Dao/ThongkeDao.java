package nhom3.Dao;

import java.util.List;

import org.hibernate.Session;

import nhom3.Model.Hoadon;
import nhom3.Model.Sanpham;
import nhom3.Utils.HibernateUtils;

public class ThongkeDao {

	public long tongHoaDon() {

	    Session session = HibernateUtils.getSessionFactory().openSession();

	    try {

	        Long count = session.createQuery(
	                "select count(h.maHD) " +
	                "from Hoadon h " +
	                "where h.trangThai='DA_GIAO'",
	                Long.class).uniqueResult();

	        return count == null ? 0 : count;

	    } finally {
	        session.close();
	    }
	}

    public double tongDoanhThu() {
        Session session = HibernateUtils.getSessionFactory().openSession();
        try {
            Double sum = session.createQuery(
                    "select sum(h.tongGia) " +
                    "from Hoadon h " +
                    "where h.trangThai = 'DA_GIAO'",
                    Double.class).uniqueResult();

            return sum == null ? 0 : sum;

        } finally {
            session.close();
        }
    }

    public List<Hoadon> getAllHoaDon() {
        Session session = HibernateUtils.getSessionFactory().openSession();
        try {
            return session.createQuery(
                    "from Hoadon order by maHD desc",
                    Hoadon.class).list();
        } finally {
            session.close();
        }
    }

    // 🔥 BIỂU ĐỒ CỘT
    public List<Object[]> soHoaDonTheoThang() {
        Session session = HibernateUtils.getSessionFactory().openSession();
        try {
            String sql = """
SELECT MONTH(NgayThanhToan) AS Thang,
       COUNT(*) AS SoHoaDon
FROM HoaDon
WHERE NgayThanhToan IS NOT NULL
AND TrangThai='DA_GIAO'
GROUP BY MONTH(NgayThanhToan)
ORDER BY Thang
            """;

            return session.createNativeQuery(sql).getResultList();

        } finally {
            session.close();
        }
    }
    
    public List<Object[]> thongKeSanPhamDaBan() {

        Session session = HibernateUtils.getSessionFactory().openSession();

        try {

            String sql = """
                SELECT
                    sp.MaSP,
                    sp.TenSP,
                    SUM(ct.SoLuong) AS DaBan,
                    SUM(ct.TongGia) AS DoanhThu
                FROM HoaDonChiTiet ct
                INNER JOIN HoaDon hd
                    ON hd.MaHD = ct.MaHD
                INNER JOIN SanPham sp
                    ON sp.MaSP = ct.MaSP
                WHERE hd.TrangThai = 'DA_GIAO'
                GROUP BY sp.MaSP, sp.TenSP
                ORDER BY DaBan DESC
            """;

            return session.createNativeQuery(sql).getResultList();

        } finally {

            session.close();

        }
    }
    
    public List<Sanpham> getAllSanpham() {
        Session session = HibernateUtils.getSessionFactory().openSession();
        List<Sanpham> list = session
                .createQuery("FROM Sanpham", Sanpham.class)
                .getResultList();
        session.close();
        return list;
    }
}