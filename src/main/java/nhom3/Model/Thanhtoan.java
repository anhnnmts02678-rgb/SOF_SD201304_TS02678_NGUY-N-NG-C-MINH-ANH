package nhom3.Model;

import java.sql.Timestamp;

import jakarta.persistence.*;

@Entity
@Table(name = "ThanhToan")
public class Thanhtoan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaThanhToan")
    private int maThanhToan;

    @Column(name = "PhuongThuc")
    private String phuongThuc;

    @Column(name = "TrangThai")
    private String trangThai;

    @Column(name = "ThoiGian")
    private Timestamp thoiGian;

    @Column(name = "MaGiaoDich")
    private String maGiaoDich;

	public int getMaThanhToan() {
		return maThanhToan;
	}
	public void setMaThanhToan(int maThanhToan) {
		this.maThanhToan = maThanhToan;
	}
	public String getPhuongThuc() {
		return phuongThuc;
	}
	public void setPhuongThuc(String phuongThuc) {
		this.phuongThuc = phuongThuc;
	}
	public String getTrangThai() {
		return trangThai;
	}
	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
	}
	public Timestamp getThoiGian() {
		return thoiGian;
	}
	public void setThoiGian(Timestamp thoiGian) {
		this.thoiGian = thoiGian;
	}
	public String getMaGiaoDich() {
		return maGiaoDich;
	}
	public void setMaGiaoDich(String maGiaoDich) {
		this.maGiaoDich = maGiaoDich;
	}
	public Thanhtoan(int maThanhToan, String phuongThuc, String trangThai, Timestamp thoiGian, String maGiaoDich) {
		super();
		this.maThanhToan = maThanhToan;
		this.phuongThuc = phuongThuc;
		this.trangThai = trangThai;
		this.thoiGian = thoiGian;
		this.maGiaoDich = maGiaoDich;
	}
	public Thanhtoan() {
		super();
	}
	
}
