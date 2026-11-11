package nhom3.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "HoaDonChiTiet")
public class HoadonCT {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaHDCT")
    private int maHDCT;

    @Column(name = "MaHD")
    private int maHD;

    @Column(name = "MaSP")
    private int maSP;

    @Column(name = "SoLuong")
    private int soLuong;

    @Column(name = "DonGia")
    private double donGia;

    @Column(name = "TongGia")
    private double tongGia;

		public int getMaHDCT() {
			return maHDCT;
		}
		public void setMaHDCT(int maHDCT) {
			this.maHDCT = maHDCT;
		}
		public int getMaHD() {
			return maHD;
		}
		public void setMaHD(int maHD) {
			this.maHD = maHD;
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
		public HoadonCT(int maHDCT, int maHD, int maSP, int soLuong, double donGia, double tongGia) {
			super();
			this.maHDCT = maHDCT;
			this.maHD = maHD;
			this.maSP = maSP;
			this.soLuong = soLuong;
			this.donGia = donGia;
			this.tongGia = tongGia;
		}
		public HoadonCT() {
			super();
		}
		
	}
