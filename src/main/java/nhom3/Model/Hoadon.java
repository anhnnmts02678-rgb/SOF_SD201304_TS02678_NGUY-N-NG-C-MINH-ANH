package nhom3.Model;

import java.sql.Timestamp;

import jakarta.persistence.*;

@Entity
@Table(name = "HoaDon")
public class Hoadon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaHD")
    private int maHD;

    @Column(name = "MaKH")
    private int maKH;

    @Column(name = "MaThanhToan")
    private int maThanhToan;

    @Column(name = "TongGia")
    private double tongGia;

    @Column(name = "QRThanhToan")
    private String qrThanhToan;

    @Column(name = "NgayThanhToan")
    private Timestamp ngayThanhToan;

    @Column(name = "TrangThai")
    private String trangThai;

	public int getMaHD() {
		return maHD;
	}
	public void setMaHD(int maHD) {
		this.maHD = maHD;
	}
	public int getMaKH() {
		return maKH;
	}
	public void setMaKH(int maKH) {
		this.maKH = maKH;
	}
	public int getMaThanhToan() {
		return maThanhToan;
	}
	public void setMaThanhToan(int maThanhToan) {
		this.maThanhToan = maThanhToan;
	}
	public double getTongGia() {
		return tongGia;
	}
	public void setTongGia(double tongGia) {
		this.tongGia = tongGia;
	}
	public String getQrThanhToan() {
		return qrThanhToan;
	}
	public void setQrThanhToan(String qrThanhToan) {
		this.qrThanhToan = qrThanhToan;
	}
	public Timestamp getNgayThanhToan() {
		return ngayThanhToan;
	}
	public void setNgayThanhToan(Timestamp ngayThanhToan) {
		this.ngayThanhToan = ngayThanhToan;
	}
	public String getTrangThai() {
		return trangThai;
	}
	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
	}
	public Hoadon(int maHD, int maKH, int maThanhToan, double tongGia, String qrThanhToan, Timestamp ngayThanhToan,
			String trangThai) {
		super();
		this.maHD = maHD;
		this.maKH = maKH;
		this.maThanhToan = maThanhToan;
		this.tongGia = tongGia;
		this.qrThanhToan = qrThanhToan;
		this.ngayThanhToan = ngayThanhToan;
		this.trangThai = trangThai;
	}
	public Hoadon() {
		super();
	}
	
	
}
