package nhom3.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "GioHangChiTiet")
public class GiohangCT {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaGHCT")
    private int maGHCT;

    @Column(name = "MaGH")
    private int maGH;

    @Column(name = "MaSP")
    private int maSP;

    @Column(name = "SoLuong")
    private int soLuong;

    @Column(name = "DonGia")
    private double donGia;

    @Column(name = "TongGia")
    private double tongGia;

	public int getMaGHCT() {
		return maGHCT;
	}
	public void setMaGHCT(int maGHCT) {
		this.maGHCT = maGHCT;
	}
	public int getMaGH() {
		return maGH;
	}
	public void setMaGH(int maGH) {
		this.maGH = maGH;
	}
	public int getMaSP() {
		return maSP;
	}
	public void setMaSP(int maSP) {
		this.maSP = maSP;
	}
	public int getSoLuong() {
		return soLuong;
	}
	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}
	public double getDonGia() {
		return donGia;
	}
	public void setDonGia(double donGia) {
		this.donGia = donGia;
	}
	public double getTongGia() {
		return tongGia;
	}
	public void setTongGia(double tongGia) {
		this.tongGia = tongGia;
	}
	public GiohangCT(int maGHCT, int maGH, int maSP, int soLuong, double donGia, double tongGia) {
		super();
		this.maGHCT = maGHCT;
		this.maGH = maGH;
		this.maSP = maSP;
		this.soLuong = soLuong;
		this.donGia = donGia;
		this.tongGia = tongGia;
	}
	public GiohangCT() {
		super();
	}
	
	
}
