package nhom3.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "DanhMuc")
public class Danhmuc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaDM")
    private int maDM;

    @Column(name = "TenDanhMuc")
    private String tenDanhMuc;

    @Column(name = "GhiChu")
    private String ghiChu;

	public int getMaDM() {
		return maDM;
	}
	public void setMaDM(int maDM) {
		this.maDM = maDM;
	}
	public String getTenDanhMuc() {
		return tenDanhMuc;
	}
	public void setTenDanhMuc(String tenDanhMuc) {
		this.tenDanhMuc = tenDanhMuc;
	}
	public String getGhiChu() {
		return ghiChu;
	}
	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
	}
	public Danhmuc(int maDM, String tenDanhMuc, String ghiChu) {
		super();
		this.maDM = maDM;
		this.tenDanhMuc = tenDanhMuc;
		this.ghiChu = ghiChu;
	}
	public Danhmuc() {
		super();
	}
	
	
}
