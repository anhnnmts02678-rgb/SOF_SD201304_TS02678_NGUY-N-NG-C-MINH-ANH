package nhom3.Model;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "NhatKy")
public class Nhatky {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaNK")
    private int maNK;

    @Column(name = "MaNV")
    private Integer maNV;

    @Column(name = "MaAdmin")
    private Integer maAdmin;

    @Column(name = "HanhDong")
    private String hanhDong;

    @Column(name = "MoTa")
    private String moTa;

    @Column(name = "ThoiGian")
    private Timestamp thoiGian;

	
	public int getMaNK() {
		return maNK;
	}

	public void setMaNK(int maNK) {
		this.maNK = maNK;
	}

	public Integer getMaNV() {
		return maNV;
	}

	public void setMaNV(Integer maNV) {
		this.maNV = maNV;
	}

	public Integer getMaAdmin() {
		return maAdmin;
	}

	public void setMaAdmin(Integer maAdmin) {
		this.maAdmin = maAdmin;
	}

	public String getHanhDong() {
		return hanhDong;
	}

	public void setHanhDong(String hanhDong) {
		this.hanhDong = hanhDong;
	}

	public String getMoTa() {
		return moTa;
	}

	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}

	public Timestamp getThoiGian() {
		return thoiGian;
	}

	public void setThoiGian(Timestamp thoiGian) {
		this.thoiGian = thoiGian;
	}

	public Nhatky(int maNK, Integer maNV, Integer maAdmin, String hanhDong, String moTa, Timestamp thoiGian) {
		super();
		this.maNK = maNK;
		this.maNV = maNV;
		this.maAdmin = maAdmin;
		this.hanhDong = hanhDong;
		this.moTa = moTa;
		this.thoiGian = thoiGian;
	}

	public Nhatky() {
		super();
	}
	
}