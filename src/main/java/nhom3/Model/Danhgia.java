package nhom3.Model;

import java.sql.Timestamp;

import jakarta.persistence.*;

@Entity
@Table(name = "DanhGia")
public class Danhgia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaDG")
    private int maDG;

    @Column(name = "MaKH")
    private int maKH;
    
    @Transient
    private String tenKhachHang;

    @Transient
    private String tenSanPham;

    @Column(name = "MaSP")
    private int maSP;

    @Column(name = "SoSao")
    private int soSao;

    @Column(name = "NoiDungDanhGia")
    private String noiDungDanhGia;

    @Column(name = "Anh")
    private String anh;

    @Column(name = "ThoiGian")
    private Timestamp thoiGian;

	public int getMaDG() {
		return maDG;
	}
	public void setMaDG(int maDG) {
		this.maDG = maDG;
	}
	public int getMaKH() {
		return maKH;
	}
	public void setMaKH(int maKH) {
		this.maKH = maKH;
	}
	
	public String getTenKhachHang() {
	    return tenKhachHang;
	}

	public void setTenKhachHang(String tenKhachHang) {
	    this.tenKhachHang = tenKhachHang;
	}

	public String getTenSanPham() {
	    return tenSanPham;
	}

	public void setTenSanPham(String tenSanPham) {
	    this.tenSanPham = tenSanPham;
	}
	
	public int getMaSP() {
		return maSP;
	}
	public void setMaSP(int maSP) {
		this.maSP = maSP;
	}
	public int getSoSao() {
		return soSao;
	}
	public void setSoSao(int soSao) {
		this.soSao = soSao;
	}
	public String getNoiDungDanhGia() {
		return noiDungDanhGia;
	}
	public void setNoiDungDanhGia(String noiDungDanhGia) {
		this.noiDungDanhGia = noiDungDanhGia;
	}
	public String getAnh() {
		return anh;
	}
	public void setAnh(String anh) {
		this.anh = anh;
	}
	public Timestamp getThoiGian() {
		return thoiGian;
	}
	public void setThoiGian(Timestamp thoiGian) {
		this.thoiGian = thoiGian;
	}
	public Danhgia(int maDG, int maKH, int maSP, int soSao, String noiDungDanhGia, String anh, Timestamp thoiGian) {
		super();
		this.maDG = maDG;
		this.maKH = maKH;
		this.maSP = maSP;
		this.soSao = soSao;
		this.noiDungDanhGia = noiDungDanhGia;
		this.anh = anh;
		this.thoiGian = thoiGian;
	}
	public Danhgia() {
		super();
	}
	
	
}
