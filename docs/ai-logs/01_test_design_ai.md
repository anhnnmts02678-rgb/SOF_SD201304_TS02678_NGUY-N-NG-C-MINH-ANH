# Nhật ký AI: Phân tích thiết kế kiểm thử (Test Plan & Test Case)

## 1. Ngữ cảnh & Mục tiêu

- **Dự án:** SOF309 - Assignment AI Driven Test Automation.
- **Chức năng kiểm thử:** Website bán linh kiện máy tính của nhóm 3.
- **Đối tượng kiểm thử:** Các chức năng Servlet xử lý đăng nhập, đăng ký, giỏ hàng và cập nhật giỏ hàng.
- **Mục tiêu:**
  - Sử dụng AI hỗ trợ phân tích logic xử lý của các Servlet.
  - Xác định các trường hợp kiểm thử hợp lệ và không hợp lệ.
  - Xây dựng các Test Case dựa trên từng nhánh xử lý của chương trình.
  - Áp dụng kỹ thuật kiểm thử như Boundary Value Analysis, Parameterized Test và kiểm tra ngoại lệ.
  - Đảm bảo các chức năng được kiểm thử đúng với yêu cầu và logic hiện tại của dự án.

## 2. Câu lệnh đã gửi cho AI (Prompt)

### Prompt 1: Phân tích chức năng cần kiểm thử

> Tôi đang thực hiện bài tập SOF309 về kiểm thử phần mềm bằng AI. Hãy phân tích source code Java Servlet của dự án và xác định các nhánh xử lý cần kiểm thử.
>
> Với mỗi chức năng, hãy liệt kê:
> - Trường hợp kiểm thử thành công.
> - Trường hợp dữ liệu đầu vào không hợp lệ.
> - Trường hợp người dùng chưa đăng nhập.
> - Trường hợp dữ liệu không tồn tại hoặc đã tồn tại trong database.
> - Kết quả mong đợi của từng trường hợp.
>
> Ưu tiên các trường hợp có thể kiểm thử độc lập bằng JUnit 5 và Mockito.

### Prompt 2: Thiết kế Test Case cho RegisterServlet

> Dựa trên code RegisterServlet tôi cung cấp, hãy thiết kế các Test Case cho chức năng đăng ký tài khoản.
>
> Các trường hợp cần kiểm tra:
> - Mật khẩu nhập lại không khớp.
> - Email đã tồn tại.
> - Số điện thoại đã tồn tại.
> - Đăng ký thành công.
> - Kiểm tra OTP được tạo và lưu vào HttpSession.
> - Kiểm tra thông tin đăng ký được lưu vào Session.
> - Kiểm tra phương thức gửi email OTP được gọi.
>
> Hãy sử dụng JUnit 5, Mockito và giải thích cách kiểm tra từng trường hợp.

### Prompt 3: Thiết kế Test Case cho giỏ hàng

> Hãy phân tích các Servlet xử lý giỏ hàng và đề xuất Test Case cho các chức năng cập nhật và xóa sản phẩm khỏi giỏ hàng.
>
> Cần kiểm tra:
> - Người dùng chưa đăng nhập.
> - Tham số đầu vào không hợp lệ.
> - Số lượng sản phẩm không hợp lệ.
> - Sản phẩm không tồn tại.
> - Số lượng vượt quá tồn kho.
> - Cập nhật giỏ hàng thành công.
> - Xóa sản phẩm thành công.
> - Trường hợp DAO phát sinh ngoại lệ.
>
> Sử dụng Mockito để giả lập DAO và kiểm tra hành vi của Servlet.

## 3. Kết quả do AI sinh ra (Raw Output)

AI hỗ trợ phân tích source code và đề xuất các nhóm Test Case tương ứng với từng chức năng.

### 3.1. Nhóm Test Case đăng nhập (LoginServlet)

- Kiểm tra thông tin đăng nhập không hợp lệ.
- Kiểm tra các trường hợp tài khoản hoặc mật khẩu không chính xác.
- Kiểm tra kết quả xử lý và thông báo trả về.
- Sử dụng `@ParameterizedTest` và `@CsvSource` để kiểm thử nhiều bộ dữ liệu.

### 3.2. Nhóm Test Case đăng ký (RegisterServlet)

- Mật khẩu xác nhận không khớp.
- Email đã được sử dụng.
- Số điện thoại đã được sử dụng.
- Đăng ký hợp lệ.
- OTP được tạo với 6 chữ số.
- Thông tin đăng ký được lưu vào HttpSession.
- Phương thức gửi OTP qua email được gọi.
- Chuyển hướng đến trang xác minh OTP.

### 3.3. Nhóm Test Case cập nhật giỏ hàng (UpdateCartServlet)

- Người dùng chưa đăng nhập.
- Tham số mã giỏ hàng hoặc số lượng không hợp lệ.
- Số lượng sản phẩm nhỏ hơn hoặc bằng 0.
- Sản phẩm trong giỏ hàng không tồn tại.
- Số lượng yêu cầu vượt quá số lượng tồn kho.
- Cập nhật số lượng thành công.
- DAO trả về kết quả cập nhật thất bại.

### 3.4. Nhóm Test Case xóa giỏ hàng (RemoveCartServlet)

- Người dùng chưa đăng nhập.
- Mã chi tiết giỏ hàng không hợp lệ.
- Xóa sản phẩm thành công.
- DAO phát sinh ngoại lệ khi thực hiện xóa.

### 3.5. Kỹ thuật kiểm thử được đề xuất

- **Parameterized Test:** Kiểm thử nhiều bộ dữ liệu bằng một phương thức test.
- **CsvSource:** Cung cấp các dữ liệu đầu vào khác nhau cho Parameterized Test.
- **Mock:** Giả lập các đối tượng DAO, Request, Response và Session.
- **Verify:** Kiểm tra các phương thức có được gọi đúng hay không.
- **ArgumentCaptor:** Lấy giá trị OTP được lưu vào Session để kiểm tra.
- **assertThrows():** Kiểm tra ngoại lệ được phát sinh trong quá trình xử lý.

## 4. Chỉnh sửa của Sinh viên & Lý do sửa

### 4.1. Những điểm AI cần điều chỉnh

- AI chỉ có thể đề xuất Test Case dựa trên source code được cung cấp, vì vậy sinh viên phải kiểm tra lại logic thực tế của từng Servlet.
- Một số trường hợp cần thay đổi để phù hợp với phương thức và kiểu dữ liệu đang sử dụng trong DAO.
- Không kiểm tra giá trị OTP cố định vì OTP được tạo ngẫu nhiên. Thay vào đó, sử dụng `ArgumentCaptor` và biểu thức chính quy để kiểm tra OTP có đúng 6 chữ số.
- Sử dụng Mockito để giả lập `MailUtil.sendOTP()` nhằm tránh gửi email thật khi chạy Unit Test.
- Trường hợp DAO phát sinh ngoại lệ được kiểm tra bằng `assertThrows()`.

### 4.2. Kết quả Test Case sau khi sinh viên hoàn thiện

| Test Class | Số Test | Kết quả |
|---|---:|---|
| SampleControllerTest | 2 | Passed |
| LoginTest | 1 | Skipped |
| CartServletTest | 1 | Passed |
| LoginServletTest | 3 | Passed |
| RegisterServletTest | 6 | Passed |
| RemoveCartServletTest | 4 | Passed |
| UpdateCartServletTest | 10 | Passed |
| **Tổng cộng** | **27** | **26 Passed, 1 Skipped** |

### 4.3. Lệnh kiểm thử và kết quả thực tế

**Lệnh thực hiện:**

```bash
mvn clean test
```

**Kết quả:**

```text
Tests run: 27, Failures: 0, Errors: 0, Skipped: 1

BUILD SUCCESS
```

- Toàn bộ 26 test được thực thi đều thành công.
- Không có test nào bị Failure hoặc Error.
- Có 1 test bị Skip là `LoginTest`.
- Các Test Case đã được chạy bằng Maven Surefire và JUnit Platform.

### 4.4. Đánh giá của sinh viên

- AI giúp tiết kiệm thời gian phân tích các nhánh xử lý và xây dựng Test Case.
- Sinh viên đã kiểm tra, điều chỉnh và chạy thử các Test Case trên source code thực tế.
- Việc sử dụng Mockito giúp giảm sự phụ thuộc vào các đối tượng bên ngoài khi kiểm thử.
- Kết quả kiểm thử cho thấy các Test Case hiện tại đều đạt yêu cầu thực thi.

## 5. Kết luận

Thông qua quá trình sử dụng AI, sinh viên đã xây dựng được bộ Test Case cho các chức năng đăng nhập, đăng ký và quản lý giỏ hàng.

Các Test Case được triển khai bằng JUnit 5 và Mockito, có sử dụng Parameterized Test, CsvSource, ArgumentCaptor và assertThrows().

Kết quả chạy `mvn clean test` đạt BUILD SUCCESS với 27 test, trong đó 26 Passed và 1 Skipped.