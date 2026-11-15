# KẾ HOẠCH KIỂM THỬ (TEST PLAN)

- **Dự án:** Website bán linh kiện máy tính Nhom3_Weblinhkien
- **Sinh viên:** Nguyễn Ngọc Minh Anh
- **MSSV:** TS02678
- **Môn học:** SOF309 - Kiểm thử phần mềm với AI

---

## 1. MỤC TIÊU KIỂM THỬ

Mục tiêu của quá trình kiểm thử là đánh giá chất lượng và tính chính xác của các chức năng trong website bán linh kiện máy tính.

Các mục tiêu cụ thể:

- Kiểm tra chức năng đăng ký, đăng nhập và đăng xuất tài khoản.
- Kiểm tra quá trình xác thực OTP và khôi phục mật khẩu.
- Kiểm tra phân quyền truy cập giữa khách hàng, nhân viên và quản trị viên.
- Kiểm tra chức năng tìm kiếm, lọc và xem chi tiết sản phẩm.
- Kiểm tra các thao tác giỏ hàng và đặt hàng.
- Kiểm tra chức năng quản lý sản phẩm và đơn hàng.
- Kiểm tra chức năng đánh giá sản phẩm.
- Phát hiện lỗi trong quá trình nhập dữ liệu và xử lý nghiệp vụ.
- Sử dụng AI hỗ trợ phân tích yêu cầu, xây dựng Test Plan và Test Case.

## 2. PHẠM VI KIỂM THỬ

### 2.1. In-Scope (Trong phạm vi kiểm thử)

**Nhóm chức năng tài khoản:**
- Đăng ký tài khoản.
- Đăng nhập và đăng xuất.
- Xác thực OTP khi đăng ký.
- Quên mật khẩu và đặt lại mật khẩu.

**Nhóm chức năng sản phẩm:**
- Hiển thị danh sách sản phẩm.
- Tìm kiếm sản phẩm theo tên.
- Lọc sản phẩm theo danh mục.
- Xem chi tiết sản phẩm.

**Nhóm chức năng mua hàng:**
- Thêm sản phẩm vào giỏ hàng.
- Cập nhật số lượng sản phẩm.
- Xóa sản phẩm khỏi giỏ hàng.
- Kiểm tra số lượng tồn kho.
- Áp dụng khuyến mãi khi thanh toán.
- Tạo đơn hàng và kiểm tra thông tin đơn hàng.

**Nhóm chức năng đánh giá:**
- Hiển thị đánh giá sản phẩm.
- Gửi đánh giá sản phẩm.
- Kiểm tra điều kiện khách hàng đã mua và nhận hàng.
- Kiểm tra cập nhật đánh giá đã tồn tại.

**Nhóm chức năng quản trị và nhân viên:**
- Quản lý sản phẩm.
- Quản lý khách hàng và nhân viên.
- Quản lý khuyến mãi.
- Quản lý đơn hàng và trạng thái đơn hàng.
- Kiểm tra quyền truy cập các trang quản trị.

### 2.2. Out-of-Scope (Ngoài phạm vi kiểm thử)

- Kiểm thử tải với số lượng lớn người dùng đồng thời.
- Kiểm thử hiệu năng chuyên sâu (Performance Testing).
- Kiểm thử bảo mật chuyên sâu (Security Testing).
- Kiểm thử tích hợp với các dịch vụ bên thứ ba chưa được xác nhận.
- Kiểm thử trên nhiều hệ điều hành và thiết bị khác nhau.

## 3. CHIẾN LƯỢC KIỂM THỬ (TEST STRATEGY)

### 3.1. Kiểm thử chức năng (Functional Testing)

- Kiểm tra chức năng theo yêu cầu nghiệp vụ.
- Kiểm tra dữ liệu đầu vào hợp lệ và không hợp lệ.
- Kiểm tra kết quả hiển thị sau mỗi thao tác.
- Kiểm tra dữ liệu được lưu và cập nhật trong cơ sở dữ liệu.

### 3.2. Kiểm thử giao diện (UI Testing)

- Kiểm tra các biểu mẫu đăng ký, đăng nhập và đặt hàng.
- Kiểm tra các nút chức năng và liên kết điều hướng.
- Kiểm tra thông báo lỗi khi nhập dữ liệu không hợp lệ.
- Kiểm tra khả năng hiển thị danh sách sản phẩm và thông tin đơn hàng.

### 3.3. Kiểm thử phân quyền (Access Control Testing)

- Khách chưa đăng nhập không được truy cập chức năng yêu cầu đăng nhập.
- Khách hàng không được truy cập khu vực quản trị.
- Nhân viên chỉ được truy cập các khu vực phù hợp với quyền được cấp.
- Quản trị viên có thể truy cập khu vực quản trị theo phân quyền hệ thống.

### 3.4. Phương pháp thực hiện

- Kiểm thử thủ công bằng cách thao tác trực tiếp trên trình duyệt.
- Kiểm tra dữ liệu và kết quả xử lý thông qua website và cơ sở dữ liệu.
- Sử dụng Selenium WebDriver để tự động hóa giao diện nếu được cấu hình trong môi trường kiểm thử.
- Sử dụng AI hỗ trợ phân tích yêu cầu và đề xuất các trường hợp kiểm thử.

### 3.5. Kỹ thuật thiết kế kiểm thử

Dự án sử dụng 4 kỹ thuật thiết kế kiểm thử chính:

**3.5.1. Equivalence Partitioning (EP) - Phân vùng tương đương**

Chia dữ liệu đầu vào thành các nhóm hợp lệ và không hợp lệ có cách xử lý tương tự nhau. Áp dụng cho đăng ký, đăng nhập, tìm kiếm sản phẩm và nhập số lượng.

**3.5.2. Boundary Value Analysis (BVA) - Phân tích giá trị biên**

Kiểm tra các giá trị tại, gần và ngoài giới hạn dữ liệu đầu vào. Áp dụng cho độ dài thông tin, số lượng sản phẩm và số sao đánh giá.

**3.5.3. Decision Table Testing - Kiểm thử bảng quyết định**

Kiểm tra các tổ hợp điều kiện và kết quả xử lý tương ứng. Áp dụng cho đăng nhập, áp dụng khuyến mãi, đặt hàng và đánh giá sản phẩm.

**3.5.4. State Transition Testing - Kiểm thử chuyển trạng thái**

Kiểm tra sự thay đổi trạng thái khi người dùng thực hiện các thao tác. Áp dụng cho đăng nhập, xác minh OTP và trạng thái đơn hàng.
### 3.5. Kỹ thuật thiết kế kiểm thử

Dự án sử dụng 4 kỹ thuật thiết kế kiểm thử chính:

**3.5.1. Equivalence Partitioning (EP) - Phân vùng tương đương**

Chia dữ liệu đầu vào thành các nhóm hợp lệ và không hợp lệ có cách xử lý tương tự nhau. Áp dụng cho đăng ký, đăng nhập, tìm kiếm sản phẩm và nhập số lượng.

**3.5.2. Boundary Value Analysis (BVA) - Phân tích giá trị biên**

Kiểm tra các giá trị tại, gần và ngoài giới hạn dữ liệu đầu vào. Áp dụng cho độ dài thông tin, số lượng sản phẩm và số sao đánh giá.

**3.5.3. Decision Table Testing - Kiểm thử bảng quyết định**

Kiểm tra các tổ hợp điều kiện và kết quả xử lý tương ứng. Áp dụng cho đăng nhập, áp dụng khuyến mãi, đặt hàng và đánh giá sản phẩm.

**3.5.4. State Transition Testing - Kiểm thử chuyển trạng thái**

Kiểm tra sự thay đổi trạng thái khi người dùng thực hiện các thao tác. Áp dụng cho đăng nhập, xác minh OTP và trạng thái đơn hàng.

## 4. MÔI TRƯỜNG KIỂM THỬ (TEST ENVIRONMENT)

| Thành phần | Môi trường |
|---|---|
| Hệ điều hành | Windows 11 |
| Ngôn ngữ lập trình | Java 25 |
| Backend | Java Servlet |
| Frontend | JSP, HTML, CSS, JavaScript |
| ORM | Hibernate 7 |
| Cơ sở dữ liệu | Microsoft SQL Server |
| Build Tool | Maven |
| Web Server | Apache Tomcat 10.1.61 |
| IDE | Visual Studio Code / IntelliJ IDEA |
| Trình duyệt | Google Chrome / Microsoft Edge |
| URL kiểm thử | http://localhost:8080/sof309-assignment-1.0-SNAPSHOT/ |

## 5. TIÊU CHÍ BẮT ĐẦU KIỂM THỬ (ENTRY CRITERIA)

- Website đã được triển khai và chạy trên Apache Tomcat.
- Cơ sở dữ liệu SQL Server đã được cấu hình và kết nối thành công.
- Các chức năng nằm trong phạm vi kiểm thử đã sẵn sàng.
- Có dữ liệu kiểm thử cần thiết cho từng chức năng.
- Tài khoản khách hàng, nhân viên và quản trị viên đã được chuẩn bị.

## 6. TIÊU CHÍ HOÀN THÀNH KIỂM THỬ (EXIT CRITERIA)

- Các Test Case trong phạm vi đã được thực hiện.
- Kết quả thực tế được ghi nhận đầy đủ.
- Các lỗi phát hiện được ghi nhận và phân loại.
- Không còn lỗi nghiêm trọng chưa được xử lý trước khi nghiệm thu.
- Các Test Case chưa thực hiện phải được đánh dấu Not Run.
- Kết quả kiểm thử được tổng hợp trong báo cáo.

## 7. RỦI RO VÀ GIẢI PHÁP

| Rủi ro | Giải pháp |
|---|---|
| Database không kết nối được | Kiểm tra cấu hình Hibernate và SQL Server |
| Dữ liệu kiểm thử không đầy đủ | Chuẩn bị tài khoản và dữ liệu mẫu |
| Email OTP không gửi được | Kiểm tra cấu hình email và kết nối mạng |
| Người dùng không đủ quyền truy cập | Chuẩn bị tài khoản theo từng vai trò |
| Website phát sinh lỗi khi chạy | Kiểm tra log Tomcat và log ứng dụng |
| Kết quả kiểm thử không ổn định | Sử dụng dữ liệu kiểm thử nhất quán |

## 8. KẾT QUẢ MONG ĐỢI

- Xác định được các lỗi trong chức năng của website.
- Đánh giá được mức độ đáp ứng yêu cầu nghiệp vụ.
- Đảm bảo các chức năng quan trọng hoạt động đúng theo thiết kế.
- Có bộ Test Case phục vụ kiểm thử và ghi nhận kết quả.
- Sử dụng AI hỗ trợ quá trình thiết kế và phân tích kiểm thử.