package nhom3.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "SanPham")
public class Sanpham {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaSP")
    private int maSP;

    @Column(name = "TenSP")
    private String tenSP;

    @Column(name = "SoLuong")
    private int soLuong;

    @Column(name = "MoTa")
    private String moTa;

    @Column(name = "GiaThanh")
    private double giaThanh;
    
    @Column(name = "UrlHinhAnh")
    private String urlHinhAnh;

    @Column(name="MaDM")
    private int maDM;
    
    @Column(name = "MaNV")
    private int maNV;

    @Column(name = "MaKM")
    private int maKM;

    @Column(name = "NguonGoc")
    private String nguonGoc;

    @Column(name = "TrangThai")
    private boolean trangThai;

	public int getMaSP() {
		return maSP;
	}
	public void setMaSP(int maSP) {
		this.maSP = maSP;
	}
	public String getTenSP() {
		return tenSP;
	}
	public void setTenSP(String tenSP) {
		this.tenSP = tenSP;
	}
	public int getSoLuong() {
		return soLuong;
	}
	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}
	public String getMoTa() {
		return moTa;
	}
	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}
	public double getGiaThanh() {
		return giaThanh;
	}
	public void setGiaThanh(double giaThanh) {
		this.giaThanh = giaThanh;
	}
	
	public String getUrlHinhAnh() {
	    return urlHinhAnh;
	}

	public void setUrlHinhAnh(String urlHinhAnh) {
	    this.urlHinhAnh = urlHinhAnh;
	}
	
	public int getMaDM() {
	    return maDM;
	}

	public void setMaDM(int maDM) {
	    this.maDM = maDM;
	}
	public int getMaNV() {
		return maNV;
	}
	public void setMaNV(int maNV) {
		this.maNV = maNV;
	}
	public int getMaKM() {
		return maKM;
	}
	public void setMaKM(int maKM) {
		this.maKM = maKM;
	}
	public String getNguonGoc() {
		return nguonGoc;
	}
	public void setNguonGoc(String nguonGoc) {
		this.nguonGoc = nguonGoc;
	}
	public boolean isTrangThai() {
		return trangThai;
	}
	public void setTrangThai(boolean trangThai) {
		this.trangThai = trangThai;
	}
	
	public Sanpham(int maSP, String tenSP, int soLuong, String moTa, double giaThanh, String urlHinhAnh, int maDM,
			int maNV, int maKM, String nguonGoc, boolean trangThai) {
		super();
		this.maSP = maSP;
		this.tenSP = tenSP;
		this.soLuong = soLuong;
		this.moTa = moTa;
		this.giaThanh = giaThanh;
		this.urlHinhAnh = urlHinhAnh;
		this.maDM = maDM;
		this.maNV = maNV;
		this.maKM = maKM;
		this.nguonGoc = nguonGoc;
		this.trangThai = trangThai;
	}
	public Sanpham() {
		super();
	}
	
	
}
