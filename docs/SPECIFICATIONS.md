# ĐẶC TẢ NGHIỆP VỤ WEBSITE BÁN LINH KIỆN MÁY TÍNH (SPECIFICATIONS)

- **Dự án:** Nhom3_Weblinhkien
- **Sinh viên:** Nguyễn Ngọc Minh Anh
- **MSSV:** TS02678
- **Môn học:** SOF309 - Kiểm thử phần mềm với AI
- **Cơ sở dữ liệu:** `Nhom3_SD2012_Weblinhkien`

---

# SPECIFICATIONS.md

# 1. Tổng quan dự án

## 1.1. Giới thiệu

Website bán linh kiện máy tính được xây dựng nhằm hỗ trợ khách hàng tìm kiếm, xem thông tin và mua các sản phẩm linh kiện máy tính trực tuyến.

Hệ thống cung cấp các chức năng quản lý sản phẩm, khách hàng, nhân viên, đơn hàng, khuyến mãi và đánh giá sản phẩm. Đồng thời, hệ thống hỗ trợ nhân viên và quản trị viên thực hiện các nghiệp vụ quản lý thông qua giao diện riêng.

## 1.2. Công nghệ sử dụng

| Thành phần | Công nghệ |
|---|---|
| Backend | Java Servlet, JSP |
| Frontend | HTML, CSS, JavaScript |
| Database | Microsoft SQL Server |
| ORM | Hibernate |
| Build tool | Maven |
| Server | Apache Tomcat |
| IDE | IntelliJ IDEA / Eclipse |

## 1.3. Địa chỉ chạy hệ thống

- Localhost: `http://localhost:8080/sof309-assignment-1.0-SNAPSHOT/`
- Database: `Nhom3_SD2012_Weblinhkien`

# 2. Đối tượng sử dụng hệ thống

Hệ thống có 4 nhóm người dùng chính:

## 2.1. Khách vãng lai (Guest)

Người chưa đăng nhập vào hệ thống.

Các chức năng:
- Xem trang chủ.
- Xem danh sách sản phẩm.
- Tìm kiếm sản phẩm.
- Xem thông tin chi tiết sản phẩm.
- Đăng ký tài khoản.
- Đăng nhập.

## 2.2. Khách hàng (Customer)

Người đã đăng ký và đăng nhập vào hệ thống.

Các chức năng:
- Quản lý thông tin cá nhân.
- Xem và tìm kiếm sản phẩm.
- Thêm sản phẩm vào giỏ hàng.
- Cập nhật số lượng sản phẩm trong giỏ hàng.
- Xóa sản phẩm khỏi giỏ hàng.
- Đặt hàng và thực hiện thanh toán.
- Xem lịch sử đơn hàng.
- Xem chi tiết đơn hàng.
- Sử dụng mã khuyến mãi.
- Đánh giá sản phẩm.
- Đăng xuất.

## 2.3. Nhân viên (Employee)

Người được cấp tài khoản nhân viên để quản lý các nghiệp vụ của cửa hàng.

Các chức năng:
- Quản lý sản phẩm.
- Quản lý thông tin khách hàng.
- Quản lý đơn hàng.
- Quản lý đánh giá sản phẩm.
- Xem nhật ký hoạt động.
- Đăng xuất.

## 2.4. Quản trị viên (Admin)

Người có quyền quản lý và giám sát hoạt động của hệ thống.

Các chức năng:
- Quản lý sản phẩm.
- Quản lý khách hàng.
- Quản lý nhân viên.
- Quản lý chương trình khuyến mãi.
- Quản lý đơn hàng.
- Xem thống kê hoạt động kinh doanh.
- Đăng xuất.

# 3. Mô tả các chức năng chính

## 3.1. Quản lý tài khoản và xác thực

Hệ thống hỗ trợ đăng ký, đăng nhập, đăng xuất và khôi phục mật khẩu.

- Khách hàng đăng ký tài khoản bằng thông tin cá nhân.
- Hệ thống sử dụng mã OTP để xác minh quá trình đăng ký.
- Người dùng đăng nhập bằng thông tin tài khoản và mật khẩu.
- Hệ thống phân biệt quyền truy cập giữa khách hàng, nhân viên và quản trị viên.
- Người dùng có thể đăng xuất khỏi hệ thống.
- Hỗ trợ chức năng quên mật khẩu và đặt lại mật khẩu thông qua xác minh OTP.

## 3.2. Quản lý sản phẩm

Hệ thống cho phép hiển thị và quản lý thông tin sản phẩm linh kiện máy tính.

Thông tin sản phẩm gồm:
- Mã sản phẩm.
- Tên sản phẩm.
- Số lượng tồn kho.
- Mô tả sản phẩm.
- Giá thành.
- Hình ảnh.
- Danh mục sản phẩm.
- Nhân viên phụ trách.
- Chương trình khuyến mãi.
- Nguồn gốc.
- Trạng thái sản phẩm.

Khách hàng có thể xem danh sách và thông tin chi tiết sản phẩm. Nhân viên và quản trị viên có thể thực hiện các nghiệp vụ quản lý sản phẩm theo quyền được cấp.

## 3.3. Quản lý danh mục sản phẩm

Hệ thống phân loại sản phẩm theo danh mục nhằm hỗ trợ khách hàng tìm kiếm và lựa chọn sản phẩm thuận tiện hơn.

Thông tin danh mục gồm mã danh mục, tên danh mục và ghi chú.

## 3.4. Tìm kiếm sản phẩm

Hệ thống hỗ trợ tìm kiếm sản phẩm theo tên và lọc sản phẩm theo danh mục.

Kết quả tìm kiếm được hiển thị dưới dạng danh sách sản phẩm phù hợp với thông tin người dùng cung cấp.

## 3.5. Quản lý giỏ hàng

Khách hàng có thể thêm sản phẩm vào giỏ hàng trước khi đặt mua.

Các chức năng:
- Thêm sản phẩm vào giỏ hàng.
- Cập nhật số lượng sản phẩm.
- Xóa sản phẩm khỏi giỏ hàng.
- Xem thông tin sản phẩm trong giỏ hàng.
- Tính tổng giá trị giỏ hàng.

## 3.6. Quản lý đơn hàng

Hệ thống hỗ trợ khách hàng tạo và theo dõi đơn hàng.

Các chức năng:
- Tạo đơn hàng từ giỏ hàng.
- Lưu thông tin sản phẩm trong đơn hàng.
- Tính tổng giá trị đơn hàng.
- Xem lịch sử đơn hàng.
- Xem chi tiết đơn hàng.
- Theo dõi trạng thái đơn hàng.
- Nhân viên và quản trị viên thực hiện nghiệp vụ quản lý đơn hàng theo quyền được cấp.

## 3.7. Quản lý thanh toán

Hệ thống lưu trữ thông tin thanh toán liên quan đến đơn hàng.

Thông tin thanh toán gồm:
- Mã thanh toán.
- Phương thức thanh toán.
- Trạng thái thanh toán.
- Thời gian thanh toán.
- Mã giao dịch.

Thông tin thanh toán được liên kết với hóa đơn tương ứng.

## 3.8. Quản lý khuyến mãi

Hệ thống hỗ trợ quản lý các chương trình khuyến mãi dành cho sản phẩm hoặc đơn hàng.

Thông tin khuyến mãi gồm:
- Mã khuyến mãi.
- Loại khuyến mãi.
- Phần trăm giảm giá.
- Ngày bắt đầu.
- Ngày kết thúc.
- Mô tả.
- Trạng thái.

Quản trị viên có thể quản lý thông tin chương trình khuyến mãi. Khách hàng có thể sử dụng chương trình khuyến mãi khi mua hàng theo điều kiện áp dụng của hệ thống.

## 3.9. Đánh giá sản phẩm

Khách hàng có thể gửi đánh giá đối với sản phẩm đã mua.

Thông tin đánh giá gồm:
- Mã đánh giá.
- Khách hàng thực hiện đánh giá.
- Sản phẩm được đánh giá.
- Số sao.
- Nội dung đánh giá.
- Hình ảnh đính kèm.
- Thời gian đánh giá.

Hệ thống hỗ trợ hiển thị đánh giá sản phẩm và quản lý các đánh giá đã gửi.

## 3.10. Quản lý khách hàng

Hệ thống lưu trữ và quản lý thông tin khách hàng.

Thông tin khách hàng gồm:
- Mã khách hàng.
- Họ và tên.
- Số điện thoại.
- Email.
- Mật khẩu.
- Địa chỉ.
- Trạng thái tài khoản.

Nhân viên và quản trị viên có thể quản lý thông tin khách hàng theo quyền được cấp.

## 3.11. Quản lý nhân viên

Hệ thống lưu trữ thông tin tài khoản nhân viên phục vụ hoạt động quản lý cửa hàng.

Thông tin nhân viên gồm:
- Mã nhân viên.
- Họ và tên.
- Tên đăng nhập.
- Email.
- Mật khẩu.
- Số điện thoại.
- Ngày tạo tài khoản.
- Trạng thái tài khoản.

Quản trị viên có thể quản lý danh sách nhân viên.

## 3.12. Nhật ký hoạt động

Hệ thống lưu lại thông tin về các hoạt động quản lý được thực hiện bởi nhân viên hoặc quản trị viên.

Thông tin nhật ký gồm:
- Mã nhật ký.
- Nhân viên thực hiện.
- Quản trị viên thực hiện.
- Hành động.
- Mô tả.
- Thời gian thực hiện.

## 3.13. Thống kê

Hệ thống hỗ trợ thống kê một số thông tin tổng quan phục vụ quản lý cửa hàng.

Các thông tin thống kê gồm:
- Tổng số sản phẩm.
- Tổng số khách hàng.
- Tổng số nhân viên.
- Tổng số hóa đơn.
- Thời gian cập nhật thống kê.

# 4. Đặc tả cơ sở dữ liệu

Cơ sở dữ liệu `Nhom3_SD2012_Weblinhkien` gồm các bảng chính sau:

| STT | Bảng | Mô tả |
|---|---|---|
| 1 | Admin | Lưu thông tin tài khoản quản trị viên |
| 2 | Employee | Lưu thông tin tài khoản nhân viên |
| 3 | Users | Lưu thông tin tài khoản khách hàng |
| 4 | DanhMuc | Lưu danh mục sản phẩm |
| 5 | KhuyenMai | Lưu thông tin chương trình khuyến mãi |
| 6 | SanPham | Lưu thông tin sản phẩm |
| 7 | ThanhToan | Lưu thông tin thanh toán |
| 8 | HoaDon | Lưu thông tin hóa đơn |
| 9 | HoaDonChiTiet | Lưu chi tiết sản phẩm trong hóa đơn |
| 10 | DanhGia | Lưu đánh giá của khách hàng |
| 11 | NhatKy | Lưu nhật ký hoạt động |
| 12 | GioHang | Lưu thông tin giỏ hàng |
| 13 | GioHangChiTiet | Lưu chi tiết sản phẩm trong giỏ hàng |
| 14 | ThongKe | Lưu thông tin thống kê |

## 4.1. Mối quan hệ giữa các bảng

- `Users` liên kết với `HoaDon` thông qua `MaKH`.
- `Users` liên kết với `DanhGia` thông qua `MaKH`.
- `Users` liên kết với `GioHang` thông qua `MaKH`.
- `DanhMuc` liên kết với `SanPham` thông qua `MaDM`.
- `SanPham` liên kết với `HoaDonChiTiet` thông qua `MaSP`.
- `SanPham` liên kết với `GioHangChiTiet` thông qua `MaSP`.
- `SanPham` liên kết với `DanhGia` thông qua `MaSP`.
- `KhuyenMai` liên kết với `SanPham` thông qua `MaKM`.
- `Employee` liên kết với `SanPham` thông qua `MaNV`.
- `HoaDon` liên kết với `HoaDonChiTiet` thông qua `MaHD`.
- `HoaDon` liên kết với `ThanhToan` thông qua `MaThanhToan`.
- `GioHang` liên kết với `GioHangChiTiet` thông qua `MaGH`.
- `Employee` và `Admin` được tham chiếu trong bảng `NhatKy`.

# 5. Quy trình nghiệp vụ tổng quát

## 5.1. Quy trình mua hàng

1. Khách hàng truy cập website.
2. Khách hàng tìm kiếm hoặc lựa chọn sản phẩm.
3. Khách hàng thêm sản phẩm vào giỏ hàng.
4. Khách hàng kiểm tra giỏ hàng.
5. Khách hàng tiến hành đặt hàng.
6. Hệ thống tạo hóa đơn và lưu thông tin chi tiết đơn hàng.
7. Khách hàng thực hiện thanh toán theo phương thức hệ thống hỗ trợ.
8. Khách hàng theo dõi trạng thái đơn hàng.

## 5.2. Quy trình đăng ký tài khoản

1. Khách hàng truy cập chức năng đăng ký.
2. Nhập thông tin tài khoản.
3. Hệ thống tiếp nhận thông tin đăng ký.
4. Hệ thống gửi mã OTP để xác minh.
5. Khách hàng nhập mã OTP.
6. Hệ thống xác minh và xử lý đăng ký tài khoản.

## 5.3. Quy trình đánh giá sản phẩm

1. Khách hàng truy cập sản phẩm đã mua.
2. Chọn chức năng đánh giá.
3. Nhập số sao và nội dung đánh giá.
4. Gửi đánh giá.
5. Hệ thống lưu thông tin và hiển thị đánh giá.

# 6. Phân quyền truy cập

| Chức năng | Guest | Customer | Employee | Admin |
|---|---|---|---|---|
| Xem sản phẩm | Có | Có | Có | Có |
| Tìm kiếm sản phẩm | Có | Có | Có | Có |
| Đăng ký | Có | Không | Không | Không |
| Đăng nhập | Có | Có | Có | Có |
| Quản lý giỏ hàng | Không | Có | Không | Không |
| Đặt hàng | Không | Có | Không | Không |
| Xem lịch sử đơn hàng | Không | Có | Không | Không |
| Đánh giá sản phẩm | Không | Có | Không | Không |
| Quản lý sản phẩm | Không | Không | Có | Có |
| Quản lý khách hàng | Không | Không | Có | Có |
| Quản lý nhân viên | Không | Không | Không | Có |
| Quản lý khuyến mãi | Không | Không | Không | Có |
| Quản lý đơn hàng | Không | Không | Có | Có |
| Xem thống kê | Không | Không | Không | Có |

# 7. Tài liệu tham khảo

- Source code dự án website bán linh kiện máy tính.
- File SQL tạo cơ sở dữ liệu `Nhom3_SD2012_Weblinhkien`.
- Cấu hình Maven trong `pom.xml`.
- Cấu hình Hibernate trong `hibernate.cfg.xml`.
- Tài liệu hướng dẫn và yêu cầu bài tập SOF309.