<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="nhom3.Model.Users" %>

<%
if (session.getAttribute("admin") == null) {
    response.sendRedirect("../login.jsp");
    return;
}
request.setAttribute("activeMenu", "customer");
Users user = (Users) request.getAttribute("user");
boolean isEdit = (user != null && user.getMaKH() > 0);
String message = (String) request.getAttribute("message");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><%= isEdit ? "Cập nhật khách hàng" : "Thêm khách hàng" %> - HAKY TECH Admin</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
<link rel="stylesheet" href="css/admin.css">
</head>
<body>

<div class="admin-wrapper">

    <%@ include file="_sidebar.jsp" %>

    <div class="main-content">

        <div class="page-header">
            <div>
                <h1><%= isEdit ? "Cập nhật khách hàng" : "Thêm khách hàng mới" %></h1>
                <div class="welcome">
                    <a href="customer.jsp" style="color:#2f73ff;font-weight:bold;">
                        <i class="fa-solid fa-arrow-left"></i> Quay lại danh sách
                    </a>
                </div>
            </div>
        </div>

        <% if (message != null) { %>
        <div class="alert alert-error"><%= message %></div>
        <% } %>

        <div class="form-card">
            <form action="UserServlet" method="post">

                <% if (isEdit) { %>
                <input type="hidden" name="maKH" value="<%= user.getMaKH() %>">
                <% } %>

                <div class="form-grid">

                    <div class="form-group full">
                        <label>Họ và tên *</label>
                        <input type="text" name="fullName" required
                               value="<%= (user != null && user.getFullName() != null) ? user.getFullName() : "" %>">
                    </div>

                    <div class="form-group">
                        <label>Số điện thoại</label>
                        <input type="tel" name="sdt"
                               value="<%= (user != null && user.getSdt() != null) ? user.getSdt() : "" %>">
                    </div>

                    <div class="form-group">
                        <label>Email *</label>
                        <input type="email" name="email" required
                               value="<%= (user != null && user.getEmail() != null) ? user.getEmail() : "" %>">
                    </div>

                    <div class="form-group full">
                        <label>Địa chỉ</label>
                        <input type="text" name="address"
                               value="<%= (user != null && user.getAddress() != null) ? user.getAddress() : "" %>">
                    </div>

                    <div class="form-group full">
                        <label>Mật khẩu <%= isEdit ? "" : "*" %></label>
                        <input type="password" name="password" <%= isEdit ? "" : "required" %>
                               placeholder="<%= isEdit ? "Để trống nếu không đổi mật khẩu" : "Nhập mật khẩu" %>">
                        <% if (isEdit) { %>
                        <div class="hint">Chỉ nhập nếu muốn đặt lại mật khẩu cho khách hàng này.</div>
                        <% } %>
                    </div>

                    <div class="form-group full">
                        <label>Trạng thái tài khoản</label>
                        <div class="form-check">
                            <input type="checkbox" id="trangThai" name="trangThai"
                                   <%= (user == null || user.isTrangThai()) ? "checked" : "" %>>
                            <label for="trangThai" style="margin:0;font-weight:normal;">Cho phép hoạt động (bỏ chọn = khóa tài khoản)</label>
                        </div>
                    </div>

                </div>

                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">
                        <i class="fa-solid fa-floppy-disk"></i> <%= isEdit ? "Lưu thay đổi" : "Thêm khách hàng" %>
                    </button>
                    <a href="customer.jsp" class="btn btn-secondary">Hủy</a>
                </div>

            </form>
        </div>

    </div>

</div>

</body>
</html>
