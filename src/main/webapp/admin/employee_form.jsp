<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="nhom3.Model.Employee" %>

<%
if (session.getAttribute("admin") == null) {
    response.sendRedirect("../login.jsp");
    return;
}
request.setAttribute("activeMenu", "employee");
Employee emp = (Employee) request.getAttribute("emp");
boolean isEdit = (emp != null && emp.getMaNV() > 0);
String message = (String) request.getAttribute("message");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><%= isEdit ? "Cập nhật nhân viên" : "Thêm nhân viên" %> - HAKY TECH Admin</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
<link rel="stylesheet" href="css/admin.css">
</head>
<body>

<div class="admin-wrapper">

    <%@ include file="_sidebar.jsp" %>

    <div class="main-content">

        <div class="page-header">
            <div>
                <h1><%= isEdit ? "Cập nhật nhân viên" : "Thêm nhân viên mới" %></h1>
                <div class="welcome">
                    <a href="employee.jsp" style="color:#2f73ff;font-weight:bold;">
                        <i class="fa-solid fa-arrow-left"></i> Quay lại danh sách
                    </a>
                </div>
            </div>
        </div>

        <% if (message != null) { %>
        <div class="alert alert-error"><%= message %></div>
        <% } %>

        <div class="form-card">
            <form action="EmployeeServlet" method="post">

                <% if (isEdit) { %>
                <input type="hidden" name="maNV" value="<%= emp.getMaNV() %>">
                <% } %>

                <div class="form-grid">

                    <div class="form-group full">
                        <label>Họ và tên *</label>
                        <input type="text" name="hoTen" required
                               value="<%= (emp != null && emp.getHoTen() != null) ? emp.getHoTen() : "" %>">
                    </div>

                    <div class="form-group">
                        <label>Tên đăng nhập *</label>
                        <input type="text" name="username" required
                               value="<%= (emp != null && emp.getUsername() != null) ? emp.getUsername() : "" %>">
                    </div>

                    <div class="form-group">
                        <label>Số điện thoại</label>
                        <input type="tel" name="sdt"
                               value="<%= (emp != null && emp.getSdt() != null) ? emp.getSdt() : "" %>">
                    </div>

                    <div class="form-group full">
                        <label>Email *</label>
                        <input type="email" name="email" required
                               value="<%= (emp != null && emp.getEmail() != null) ? emp.getEmail() : "" %>">
                    </div>

                    <div class="form-group full">
                        <label>Mật khẩu <%= isEdit ? "" : "*" %></label>
                        <input type="password" name="password" <%= isEdit ? "" : "required" %>
                               placeholder="<%= isEdit ? "Để trống nếu không đổi mật khẩu" : "Nhập mật khẩu" %>">
                        <% if (isEdit) { %>
                        <div class="hint">Chỉ nhập nếu muốn đặt lại mật khẩu cho nhân viên này.</div>
                        <% } else { %>
                        <div class="hint">Nếu để trống, hệ thống sẽ đặt mật khẩu mặc định là 123456.</div>
                        <% } %>
                    </div>

                    <div class="form-group full">
                        <label>Trạng thái làm việc</label>
                        <div class="form-check">
                            <input type="checkbox" id="trangThai" name="trangThai"
                                   <%= (emp == null || emp.isTrangThai()) ? "checked" : "" %>>
                            <label for="trangThai" style="margin:0;font-weight:normal;">Đang làm việc (bỏ chọn = khóa tài khoản)</label>
                        </div>
                    </div>

                </div>

                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">
                        <i class="fa-solid fa-floppy-disk"></i> <%= isEdit ? "Lưu thay đổi" : "Thêm nhân viên" %>
                    </button>
                    <a href="employee.jsp" class="btn btn-secondary">Hủy</a>
                </div>

            </form>
        </div>

    </div>

</div>

</body>
</html>
