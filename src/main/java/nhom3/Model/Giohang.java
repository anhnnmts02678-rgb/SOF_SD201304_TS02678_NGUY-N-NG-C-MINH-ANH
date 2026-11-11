package nhom3.Model;

import java.sql.Timestamp;

import jakarta.persistence.*;

@Entity
@Table(name = "GioHang")
public class Giohang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaGH")
    private int maGH;

    @Column(name = "MaKH")
    private int maKH;

    @Column(name = "NgayTao")
    private Timestamp ngayTao;

    @Column(name = "TrangThai")
    private boolean trangThai;


	public int getMaGH() {
		return maGH;
	}

	public void setMaGH(int maGH) {
		this.maGH = maGH;
	}

	public int getMaKH() {
		return maKH;
	}

	public void setMaKH(int maKH) {
		this.maKH = maKH;
	}

	public Timestamp getNgayTao() {
		return ngayTao;
	}

	public void setNgayTao(Timestamp ngayTao) {
		this.ngayTao = ngayTao;
	}

	public boolean isTrangThai() {
		return trangThai;
	}

	public void setTrangThai(boolean trangThai) {
		this.trangThai = trangThai;
	}

	public Giohang(int maGH, int maKH, Timestamp ngayTao, boolean trangThai) {
		super();
		this.maGH = maGH;
		this.maKH = maKH;
		this.ngayTao = ngayTao;
		this.trangThai = trangThai;
	}

	public Giohang() {
		super();
	}
	
}
