# TEST_CASES.md

# 1. Thông tin chung

## 1.1. Thông tin dự án

| Thành phần | Nội dung |
|---|---|
| Tên dự án | Website bán linh kiện máy tính Nhom3_Weblinhkien |
| Môn học | SOF309 - Kiểm thử phần mềm với AI |
| Người thực hiện | Nguyễn Ngọc Minh Anh |
| MSSV | TS02678 |
| Phiên bản | 1.0 |

## 1.2. Mục đích

Tài liệu mô tả các trường hợp kiểm thử được xây dựng dựa trên yêu cầu nghiệp vụ, source code và cấu trúc cơ sở dữ liệu của website.

Các kỹ thuật kiểm thử được sử dụng:

- EP - Equivalence Partitioning.
- BVA - Boundary Value Analysis.
- Decision Table Testing.
- State Transition Testing.

## 1.3. Quy ước kết quả

| Trạng thái | Ý nghĩa |
|---|---|
| Not Run | Chưa thực hiện kiểm thử |
| Pass | Kết quả thực tế đúng với kết quả mong đợi |
| Fail | Kết quả thực tế không đúng với kết quả mong đợi |

# 2. Equivalence Partitioning (EP) - Phân vùng tương đương

## 2.1. Chức năng đăng nhập

Phân chia dữ liệu đăng nhập thành các nhóm:

| Nhóm | Dữ liệu đầu vào | Phân loại |
|---|---|---|
| EP-AUTH-01 | Tài khoản tồn tại, mật khẩu đúng, tài khoản hoạt động | Hợp lệ |
| EP-AUTH-02 | Tài khoản không tồn tại | Không hợp lệ |
| EP-AUTH-03 | Mật khẩu không chính xác | Không hợp lệ |
| EP-AUTH-04 | Tài khoản bị khóa hoặc không hoạt động | Không hợp lệ |
| EP-AUTH-05 | Tài khoản quản trị viên hoặc nhân viên sử dụng Username, Email hoặc SĐT | Hợp lệ nếu thông tin xác thực đúng |
| EP-AUTH-06 | Tài khoản khách hàng sử dụng Email hoặc SĐT | Hợp lệ nếu thông tin xác thực đúng |

### Test Case

| ID | Dữ liệu kiểm thử | Các bước thực hiện | Kết quả mong đợi | Trạng thái |
|---|---|---|---|---|
| EP-LOGIN-01 | Tài khoản khách hàng đang hoạt động, mật khẩu đúng | Nhập Email và mật khẩu, nhấn Đăng nhập | Đăng nhập thành công và chuyển đến trang chủ | Not Run |
| EP-LOGIN-02 | Tài khoản nhân viên đang hoạt động, mật khẩu đúng | Nhập Username và mật khẩu | Đăng nhập thành công và chuyển đến trang nhân viên | Not Run |
| EP-LOGIN-03 | Tài khoản quản trị viên đang hoạt động, mật khẩu đúng | Nhập Email và mật khẩu | Đăng nhập thành công và chuyển đến trang quản trị | Not Run |
| EP-LOGIN-04 | Tài khoản không tồn tại | Nhập thông tin tài khoản không tồn tại | Hiển thị thông báo sai tài khoản hoặc mật khẩu | Not Run |
| EP-LOGIN-05 | Tài khoản tồn tại nhưng mật khẩu sai | Nhập Email và mật khẩu không chính xác | Không đăng nhập được, hiển thị thông báo lỗi | Not Run |
| EP-LOGIN-06 | Tài khoản không hoạt động | Nhập đúng thông tin của tài khoản bị khóa | Không cho phép đăng nhập | Not Run |

## 2.2. Chức năng đăng ký tài khoản

Phân chia dữ liệu đăng ký:

| Nhóm | Dữ liệu đầu vào | Phân loại |
|---|---|---|
| EP-REG-01 | Mật khẩu và xác nhận mật khẩu giống nhau | Hợp lệ |
| EP-REG-02 | Mật khẩu và xác nhận mật khẩu khác nhau | Không hợp lệ |
| EP-REG-03 | Email đã được sử dụng | Không hợp lệ |
| EP-REG-04 | Số điện thoại đã được sử dụng | Không hợp lệ |
| EP-REG-05 | Email và số điện thoại chưa tồn tại | Hợp lệ để tiếp tục xác minh OTP |

### Test Case

| ID | Dữ liệu kiểm thử | Các bước thực hiện | Kết quả mong đợi | Trạng thái |
|---|---|---|---|---|
| EP-REG-01 | Mật khẩu và xác nhận mật khẩu giống nhau, Email và SĐT mới | Nhập thông tin đăng ký và gửi biểu mẫu | Hệ thống tạo OTP và chuyển đến trang xác minh | Not Run |
| EP-REG-02 | Mật khẩu và xác nhận mật khẩu khác nhau | Nhập thông tin đăng ký và gửi biểu mẫu | Hiển thị thông báo mật khẩu không khớp | Not Run |
| EP-REG-03 | Email đã tồn tại trong hệ thống | Nhập Email đã đăng ký trước đó | Hiển thị thông báo Email đã được sử dụng | Not Run |
| EP-REG-04 | Số điện thoại đã tồn tại | Nhập SĐT đã được đăng ký | Hiển thị thông báo SĐT đã được sử dụng | Not Run |
| EP-REG-05 | Email và SĐT chưa tồn tại | Nhập thông tin hợp lệ và gửi biểu mẫu | Hệ thống gửi OTP đến Email đăng ký | Not Run |

## 2.3. Chức năng giỏ hàng

Phân chia dữ liệu:

| Nhóm | Dữ liệu đầu vào | Phân loại |
|---|---|---|
| EP-CART-01 | Khách hàng đã đăng nhập | Hợp lệ |
| EP-CART-02 | Người dùng chưa đăng nhập | Không hợp lệ |
| EP-CART-03 | Sản phẩm tồn tại và đang hoạt động | Hợp lệ |
| EP-CART-04 | Sản phẩm không tồn tại hoặc không hoạt động | Không hợp lệ |
| EP-CART-05 | Số lượng không vượt quá tồn kho | Hợp lệ |
| EP-CART-06 | Số lượng vượt quá tồn kho | Không hợp lệ |
| EP-CART-07 | Mã sản phẩm hoặc số lượng không phải số nguyên hợp lệ | Không hợp lệ |

### Test Case

| ID | Dữ liệu kiểm thử | Các bước thực hiện | Kết quả mong đợi | Trạng thái |
|---|---|---|---|---|
| EP-CART-01 | Khách hàng chưa đăng nhập | Truy cập chức năng thêm sản phẩm vào giỏ | Chuyển đến trang đăng nhập | Not Run |
| EP-CART-02 | Sản phẩm tồn tại, đang hoạt động và còn hàng | Đăng nhập, chọn sản phẩm và thêm vào giỏ | Sản phẩm được thêm vào giỏ hàng | Not Run |
| EP-CART-03 | Mã sản phẩm không tồn tại | Gửi yêu cầu thêm sản phẩm với mã không tồn tại | Hệ thống thông báo sản phẩm không tồn tại | Not Run |
| EP-CART-04 | Sản phẩm không hoạt động | Gửi yêu cầu thêm sản phẩm đã ngừng hoạt động | Hệ thống từ chối thêm sản phẩm | Not Run |
| EP-CART-05 | Số lượng lớn hơn tồn kho | Thêm sản phẩm với số lượng vượt quá tồn kho | Hệ thống thông báo không đủ hàng | Not Run |
| EP-CART-06 | Mã sản phẩm hoặc số lượng chứa ký tự không phải số | Gửi yêu cầu thêm sản phẩm | Hệ thống từ chối tham số không hợp lệ | Not Run |

## 2.4. Chức năng đánh giá sản phẩm

Phân chia dữ liệu:

| Nhóm | Dữ liệu đầu vào | Phân loại |
|---|---|---|
| EP-REVIEW-01 | Khách hàng đã đăng nhập và đã mua, nhận hàng | Hợp lệ |
| EP-REVIEW-02 | Khách hàng chưa đăng nhập | Không hợp lệ |
| EP-REVIEW-03 | Khách hàng chưa mua hoặc chưa nhận hàng | Không hợp lệ |
| EP-REVIEW-04 | Nội dung đánh giá không được để trống | Hợp lệ |
| EP-REVIEW-05 | Nội dung đánh giá trống hoặc chỉ chứa khoảng trắng | Không hợp lệ |
| EP-REVIEW-06 | Số sao từ 1 đến 5 | Hợp lệ |
| EP-REVIEW-07 | Số sao nhỏ hơn 1 hoặc lớn hơn 5 | Không hợp lệ |

### Test Case

| ID | Dữ liệu kiểm thử | Các bước thực hiện | Kết quả mong đợi | Trạng thái |
|---|---|---|---|---|
| EP-REVIEW-01 | Khách hàng đã mua và nhận hàng, số sao hợp lệ, nội dung có dữ liệu | Đăng nhập và gửi đánh giá | Đánh giá được lưu thành công | Not Run |
| EP-REVIEW-02 | Khách hàng chưa đăng nhập | Gửi yêu cầu đánh giá sản phẩm | Chuyển đến trang đăng nhập | Not Run |
| EP-REVIEW-03 | Khách hàng chưa mua hoặc chưa nhận sản phẩm | Đăng nhập và gửi đánh giá | Hệ thống từ chối đánh giá | Not Run |
| EP-REVIEW-04 | Nội dung đánh giá chỉ chứa khoảng trắng | Nhập nội dung khoảng trắng và gửi đánh giá | Hiển thị thông báo yêu cầu nhập nội dung | Not Run |
| EP-REVIEW-05 | Khách hàng đã đánh giá sản phẩm trước đó | Gửi đánh giá mới cho cùng sản phẩm | Hệ thống cập nhật đánh giá cũ thay vì tạo đánh giá mới | Not Run |

# 3. Boundary Value Analysis (BVA) - Phân tích giá trị biên

## 3.1. Kiểm tra số lượng sản phẩm trong giỏ hàng

Giả sử sản phẩm có số lượng tồn kho là 10.
Các giá trị kiểm thử được chọn:

- 0: Giá trị không hợp lệ đối với cập nhật giỏ hàng.
- 1: Giá trị nhỏ nhất hợp lệ.
- 9: Giá trị gần giới hạn tồn kho.
- 10: Giá trị bằng tồn kho.
- 11: Giá trị vượt quá tồn kho.

### Test Case

| ID | Chức năng | Số lượng đầu vào | Kết quả mong đợi | Trạng thái |
|---|---|---:|---|---|
| BVA-CART-01 | Cập nhật giỏ hàng | 0 | Từ chối cập nhật vì số lượng phải lớn hơn 0 | Not Run |
| BVA-CART-02 | Cập nhật giỏ hàng | 1 | Cập nhật thành công | Not Run |
| BVA-CART-03 | Cập nhật giỏ hàng | 9 | Cập nhật thành công nếu tồn kho là 10 | Not Run |
| BVA-CART-04 | Cập nhật giỏ hàng | 10 | Cập nhật thành công | Not Run |
| BVA-CART-05 | Cập nhật giỏ hàng | 11 | Từ chối vì vượt quá tồn kho | Not Run |

**Kiểm tra thêm với chức năng thêm vào giỏ hàng:**

| ID | Số lượng đầu vào | Kết quả theo source code hiện tại | Trạng thái |
|---|---:|---|---|
| BVA-ADD-01 | 0 | Hệ thống tự chuyển số lượng thành 1 trước khi thêm | Not Run |
| BVA-ADD-02 | 1 | Thêm thành công nếu sản phẩm còn hàng | Not Run |
| BVA-ADD-03 | 10 | Thêm thành công nếu tồn kho là 10 | Not Run |
| BVA-ADD-04 | 11 | Từ chối vì vượt quá tồn kho | Not Run |

## 3.2. Kiểm tra số sao đánh giá sản phẩm

Cơ sở dữ liệu quy định số sao đánh giá từ 1 đến 5. Chức năng đánh giá trong Servlet cũng kiểm tra giới hạn này.

Các giá trị kiểm thử:

- 0: Nhỏ hơn giới hạn.
- 1: Giá trị nhỏ nhất hợp lệ.
- 2: Giá trị hợp lệ.
- 4: Giá trị hợp lệ.
- 5: Giá trị lớn nhất hợp lệ.
- 6: Lớn hơn giới hạn.

### Test Case

| ID | Số sao đầu vào | Kết quả mong đợi | Trạng thái |
|---|---:|---|---|
| BVA-REVIEW-01 | 0 | Từ chối đánh giá | Not Run |
| BVA-REVIEW-02 | 1 | Chấp nhận đánh giá | Not Run |
| BVA-REVIEW-03 | 2 | Chấp nhận đánh giá | Not Run |
| BVA-REVIEW-04 | 4 | Chấp nhận đánh giá | Not Run |
| BVA-REVIEW-05 | 5 | Chấp nhận đánh giá | Not Run |
| BVA-REVIEW-06 | 6 | Từ chối đánh giá | Not Run |

# 4. Decision Table Testing - Kiểm thử bảng quyết định

## 4.1. Chức năng đăng nhập

Các điều kiện kiểm thử:

- Tài khoản có tồn tại hay không.
- Mật khẩu có chính xác hay không.
- Tài khoản có đang hoạt động hay không.

### Bảng quyết định

| Điều kiện / Quy tắc | R1 | R2 | R3 | R4 |
|---|---|---|---|---|
| Tài khoản tồn tại | Có | Có | Có | Không |
| Mật khẩu chính xác | Có | Có | Không | - |
| Tài khoản đang hoạt động | Có | Không | Có | - |
| **Kết quả** | | | | |
| Cho phép đăng nhập | Có | Không | Không | Không |
| Hiển thị thông báo lỗi | Không | Có | Có | Có |

### Test Case

| ID | Quy tắc | Dữ liệu kiểm thử | Kết quả mong đợi | Trạng thái |
|---|---|---|---|---|
| DT-LOGIN-01 | R1 | Tài khoản tồn tại, mật khẩu đúng, đang hoạt động | Đăng nhập thành công | Not Run |
| DT-LOGIN-02 | R2 | Tài khoản tồn tại, mật khẩu đúng, không hoạt động | Từ chối đăng nhập | Not Run |
| DT-LOGIN-03 | R3 | Tài khoản tồn tại, mật khẩu sai, đang hoạt động | Hiển thị thông báo lỗi | Not Run |
| DT-LOGIN-04 | R4 | Tài khoản không tồn tại | Hiển thị thông báo lỗi | Not Run |

## 4.2. Chức năng đặt hàng

Các điều kiện kiểm thử:

- Khách hàng đã đăng nhập.
- Giỏ hàng có sản phẩm.
- Đã chọn phương thức thanh toán.
- Sản phẩm trong giỏ còn đủ hàng.

### Bảng quyết định

| Điều kiện / Quy tắc | R1 | R2 | R3 | R4 | R5 |
|---|---|---|---|---|---|
| Đã đăng nhập | Không | Có | Có | Có | Có |
| Giỏ hàng có sản phẩm | - | Không | Có | Có | Có |
| Đã chọn phương thức thanh toán | - | - | Không | Có | Có |
| Tồn kho đủ | - | - | - | Không | Có |
| **Kết quả** | | | | | |
| Cho phép tạo đơn hàng | Không | Không | Không | Không | Có |
| Chuyển đến trang đăng nhập | Có | Không | Không | Không | Không |
| Yêu cầu kiểm tra giỏ hàng | Không | Có | Không | Không | Không |
| Hiển thị lỗi phương thức thanh toán | Không | Không | Có | Không | Không |
| Hiển thị lỗi tồn kho | Không | Không | Không | Có | Không |

### Test Case

| ID | Quy tắc | Dữ liệu kiểm thử | Kết quả mong đợi | Trạng thái |
|---|---|---|---|---|
| DT-ORDER-01 | R1 | Khách hàng chưa đăng nhập | Chuyển đến trang đăng nhập | Not Run |
| DT-ORDER-02 | R2 | Đã đăng nhập nhưng giỏ hàng trống | Chuyển về giỏ hàng | Not Run |
| DT-ORDER-03 | R3 | Giỏ hàng có sản phẩm nhưng chưa chọn phương thức thanh toán | Hiển thị yêu cầu chọn phương thức thanh toán | Not Run |
| DT-ORDER-04 | R4 | Đã chọn thanh toán nhưng tồn kho không đủ | Từ chối đặt hàng và hiển thị lỗi | Not Run |
| DT-ORDER-05 | R5 | Đã đăng nhập, giỏ hàng có sản phẩm, chọn phương thức thanh toán và tồn kho đủ | Tạo thanh toán, hóa đơn và chi tiết hóa đơn thành công | Not Run |

## 4.3. Chức năng đánh giá sản phẩm

Các điều kiện kiểm thử:

- Khách hàng đã đăng nhập.
- Khách hàng đã mua và nhận hàng.
- Số sao nằm trong khoảng 1 đến 5.
- Nội dung đánh giá không được để trống.

### Bảng quyết định

| Điều kiện / Quy tắc | R1 | R2 | R3 | R4 | R5 |
|---|---|---|---|---|---|
| Đã đăng nhập | Không | Có | Có | Có | Có |
| Đã mua và nhận hàng | - | Không | Có | Có | Có |
| Số sao hợp lệ | - | - | Không | Có | Có |
| Nội dung không trống | - | - | - | Không | Có |
| **Kết quả** | | | | | |
| Cho phép gửi đánh giá | Không | Không | Không | Không | Có |
| Chuyển đến trang đăng nhập | Có | Không | Không | Không | Không |
| Từ chối đánh giá | Không | Có | Có | Có | Không |

### Test Case

| ID | Quy tắc | Dữ liệu kiểm thử | Kết quả mong đợi | Trạng thái |
|---|---|---|---|---|
| DT-REVIEW-01 | R1 | Chưa đăng nhập | Chuyển đến trang đăng nhập | Not Run |
| DT-REVIEW-02 | R2 | Đã đăng nhập nhưng chưa mua hoặc chưa nhận hàng | Từ chối đánh giá | Not Run |
| DT-REVIEW-03 | R3 | Đã mua và nhận hàng nhưng số sao không hợp lệ | Từ chối đánh giá | Not Run |
| DT-REVIEW-04 | R4 | Số sao hợp lệ nhưng nội dung trống | Yêu cầu nhập nội dung | Not Run |
| DT-REVIEW-05 | R5 | Đủ điều kiện và nội dung hợp lệ | Lưu hoặc cập nhật đánh giá thành công | Not Run |

# 5. State Transition Testing - Kiểm thử chuyển trạng thái

## 5.1. Trạng thái đăng nhập

Các trạng thái:

- S0: Người dùng chưa đăng nhập.
- S1: Khách hàng đã đăng nhập.
- S2: Nhân viên đã đăng nhập.
- S3: Quản trị viên đã đăng nhập.

### Bảng chuyển trạng thái

| Trạng thái hiện tại | Sự kiện | Trạng thái tiếp theo | Kết quả mong đợi |
|---|---|---|---|
| S0 | Đăng nhập khách hàng thành công | S1 | Chuyển đến trang chủ |
| S0 | Đăng nhập nhân viên thành công | S2 | Chuyển đến trang nhân viên |
| S0 | Đăng nhập quản trị viên thành công | S3 | Chuyển đến trang quản trị |
| S0 | Đăng nhập thất bại | S0 | Hiển thị thông báo lỗi |
| S1 | Đăng xuất | S0 | Hủy Session và chuyển đến trang đăng nhập |
| S2 | Đăng xuất | S0 | Hủy Session và chuyển đến trang đăng nhập |
| S3 | Đăng xuất | S0 | Hủy Session và chuyển đến trang đăng nhập |

### Test Case

| ID | Trạng thái ban đầu | Thao tác | Kết quả mong đợi | Trạng thái |
|---|---|---|---|---|
| ST-LOGIN-01 | Chưa đăng nhập | Đăng nhập khách hàng hợp lệ | Chuyển sang trạng thái khách hàng đã đăng nhập | Not Run |
| ST-LOGIN-02 | Chưa đăng nhập | Đăng nhập nhân viên hợp lệ | Chuyển sang trạng thái nhân viên đã đăng nhập | Not Run |
| ST-LOGIN-03 | Chưa đăng nhập | Đăng nhập quản trị viên hợp lệ | Chuyển sang trạng thái quản trị viên đã đăng nhập | Not Run |
| ST-LOGIN-04 | Chưa đăng nhập | Nhập sai mật khẩu | Vẫn ở trạng thái chưa đăng nhập | Not Run |
| ST-LOGIN-05 | Đã đăng nhập | Nhấn Đăng xuất | Session bị hủy và chuyển về trang đăng nhập | Not Run |

## 5.2. Trạng thái xác minh OTP đăng ký

Các trạng thái:

- S0: Chưa gửi thông tin đăng ký.
- S1: Đã gửi thông tin và đang chờ xác minh OTP.
- S2: OTP hợp lệ, tài khoản được tạo.
- S3: OTP không hợp lệ.

### Bảng chuyển trạng thái

| Trạng thái hiện tại | Sự kiện | Trạng thái tiếp theo | Kết quả mong đợi |
|---|---|---|---|
| S0 | Gửi thông tin đăng ký hợp lệ | S1 | Hệ thống tạo OTP và chuyển đến trang xác minh |
| S1 | Nhập OTP chính xác | S2 | Tạo tài khoản và chuyển đến trang đăng nhập |
| S1 | Nhập OTP sai | S3 | Hiển thị thông báo OTP không đúng |
| S3 | Nhập lại OTP chính xác | S2 | Tạo tài khoản thành công |

### Test Case

| ID | Trạng thái ban đầu | Thao tác | Kết quả mong đợi | Trạng thái |
|---|---|---|---|---|
| ST-OTP-01 | Chưa đăng ký | Gửi thông tin đăng ký hợp lệ | Chuyển đến trang xác minh OTP | Not Run |
| ST-OTP-02 | Đang chờ OTP | Nhập OTP chính xác | Tài khoản được tạo trong database | Not Run |
| ST-OTP-03 | Đang chờ OTP | Nhập OTP sai | Hiển thị thông báo OTP không đúng | Not Run |
| ST-OTP-04 | OTP không hợp lệ | Nhập lại OTP chính xác | Tạo tài khoản thành công | Not Run |

## 5.3. Trạng thái đơn hàng

Các trạng thái được sử dụng trong nghiệp vụ quản lý đơn hàng:

- S0: CHO_XAC_NHAN.
- S1: DANG_XU_LY.
- S2: DANG_GIAO.
- S3: DA_GIAO.
- S4: DA_HUY.

### Sơ đồ chuyển trạng thái

```text
CHO_XAC_NHAN
      |
      | process
      v
DANG_XU_LY
      |
      | ship
      v
DANG_GIAO
      |
      | done
      v
DA_GIAO

CHO_XAC_NHAN
      |
      | cancel
      v
DA_HUY
```

### Bảng chuyển trạng thái

| Trạng thái hiện tại | Thao tác | Trạng thái tiếp theo | Kết quả mong đợi |
|---|---|---|---|
| CHO_XAC_NHAN | process | DANG_XU_LY | Cập nhật trạng thái thành công |
| DANG_XU_LY | ship | DANG_GIAO | Cập nhật trạng thái thành công |
| DANG_GIAO | done | DA_GIAO | Cập nhật trạng thái thành công |
| CHO_XAC_NHAN | cancel | DA_HUY | Hủy đơn và hoàn lại tồn kho |
| DA_GIAO | cancel | DA_GIAO | Không thay đổi trạng thái |
| DA_HUY | process | DA_HUY | Không thay đổi trạng thái |

### Test Case

| ID | Trạng thái ban đầu | Thao tác | Kết quả mong đợi | Trạng thái |
|---|---|---|---|---|
| ST-ORDER-01 | CHO_XAC_NHAN | Nhân viên xác nhận xử lý đơn | Chuyển sang DANG_XU_LY | Not Run |
| ST-ORDER-02 | DANG_XU_LY | Nhân viên chuyển đơn sang giao hàng | Chuyển sang DANG_GIAO | Not Run |
| ST-ORDER-03 | DANG_GIAO | Nhân viên xác nhận đã giao | Chuyển sang DA_GIAO | Not Run |
| ST-ORDER-04 | CHO_XAC_NHAN | Nhân viên hủy đơn | Chuyển sang DA_HUY và hoàn lại tồn kho | Not Run |
| ST-ORDER-05 | DA_GIAO | Thực hiện thao tác hủy đơn | Không thay đổi trạng thái | Not Run |
| ST-ORDER-06 | DA_HUY | Thực hiện thao tác xử lý đơn | Không thay đổi trạng thái | Not Run |

# 6. Tổng kết

Tài liệu Test Case được xây dựng dựa trên 4 kỹ thuật thiết kế kiểm thử:

1. EP - Phân vùng tương đương.
2. BVA - Phân tích giá trị biên.
3. Decision Table - Kiểm thử bảng quyết định.
4. State Transition - Kiểm thử chuyển trạng thái.

Các trường hợp kiểm thử tập trung vào những nghiệp vụ chính của website như tài khoản, sản phẩm, giỏ hàng, đặt hàng, đánh giá và quản lý đơn hàng.

Kết quả kiểm thử sẽ được cập nhật sau khi thực hiện trên môi trường thực tế. Những trường hợp chưa thực hiện được giữ trạng thái Not Run.