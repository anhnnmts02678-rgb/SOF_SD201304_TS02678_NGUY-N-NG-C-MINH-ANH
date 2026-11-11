package nhom3.Model;

import java.sql.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "KhuyenMai")
public class Khuyenmai {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaKM")
    private int maKM;

    @Column(name = "LoaiKM")
    private String loaiKM;

    @Column(name = "PhanTramGiam")
    private double phanTramGiam;

    @Column(name = "NgayBatDau")
    private Date ngayBatDau;

    @Column(name = "NgayKetThuc")
    private Date ngayKetThuc;

    @Column(name = "MoTa")
    private String moTa;

    @Column(name = "TrangThai")
    private boolean trangThai;

	public int getMaKM() {
		return maKM;
	}
	public void setMaKM(int maKM) {
		this.maKM = maKM;
	}
	public String getLoaiKM() {
		return loaiKM;
	}
	public void setLoaiKM(String loaiKM) {
		this.loaiKM = loaiKM;
	}
	public double getPhanTramGiam() {
		return phanTramGiam;
	}
	public void setPhanTramGiam(double phanTramGiam) {
		this.phanTramGiam = phanTramGiam;
	}
	public Date getNgayBatDau() {
		return ngayBatDau;
	}
	public void setNgayBatDau(Date ngayBatDau) {
		this.ngayBatDau = ngayBatDau;
	}
	public Date getNgayKetThuc() {
		return ngayKetThuc;
	}
	public void setNgayKetThuc(Date ngayKetThuc) {
		this.ngayKetThuc = ngayKetThuc;
	}
	public String getMoTa() {
		return moTa;
	}
	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}
	public boolean isTrangThai() {
		return trangThai;
	}
	public void setTrangThai(boolean trangThai) {
		this.trangThai = trangThai;
	}
	
	public Khuyenmai(int maKM, String loaiKM, double phanTramGiam, Date ngayBatDau, Date ngayKetThuc, String moTa,
			boolean trangThai) {
		super();
		this.maKM = maKM;
		this.loaiKM = loaiKM;
		this.phanTramGiam = phanTramGiam;
		this.ngayBatDau = ngayBatDau;
		this.ngayKetThuc = ngayKetThuc;
		this.moTa = moTa;
		this.trangThai = trangThai;
	}
	public Khuyenmai() {
		super();
	}
	
	
}
