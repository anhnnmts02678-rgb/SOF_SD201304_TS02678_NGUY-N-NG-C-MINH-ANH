package nhom3.Model;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "Admin")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaAdmin")
    private int maAdmin;

    @Column(name = "HoTen")
    private String hoTen;

    @Column(name = "Username")
    private String username;

    @Column(name = "Email")
    private String email;

    @Column(name = "Password")
    private String password;

    @Column(name = "SDT")
    private String sdt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "NgayTao")
    private Timestamp ngayTao;

    @Column(name = "TrangThai")
    private boolean trangThai;

	public int getMaAdmin() {
		return maAdmin;
	}

	public void setMaAdmin(int maAdmin) {
		this.maAdmin = maAdmin;
	}

	public String getHoTen() {
		return hoTen;
	}

	public void setHoTen(String hoTen) {
		this.hoTen = hoTen;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSdt() {
		return sdt;
	}

	public void setSdt(String sdt) {
		this.sdt = sdt;
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

	public Admin(int maAdmin, String hoTen, String username, String email, String password, String sdt, Timestamp ngayTao,
			boolean trangThai) {
		super();
		this.maAdmin = maAdmin;
		this.hoTen = hoTen;
		this.username = username;
		this.email = email;
		this.password = password;
		this.sdt = sdt;
		this.ngayTao = ngayTao;
		this.trangThai = trangThai;
	}

	public Admin() {
		super();
	}
    
    

}