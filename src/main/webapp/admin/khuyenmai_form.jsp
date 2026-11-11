<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="nhom3.Model.Khuyenmai"%>

<%
if (session.getAttribute("employee") == null
        && session.getAttribute("admin") == null) {
    response.sendRedirect("../login.jsp");
    return;
}

request.setAttribute("activeMenu", "khuyenmai");

Khuyenmai km = (Khuyenmai) request.getAttribute("km");

boolean isEdit = (km != null && km.getMaKM() > 0);

String message = (String) request.getAttribute("message");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><%= isEdit ? "Cập nhật khuyến mãi" : "Thêm khuyến mãi" %> - HAKY TECH</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
<link rel="stylesheet" href="css/admin.css">
<style>
.form-group select,
.form-group input[type="number"],
.form-group input[type="date"] {
    width: 100%;
    height: 46px;
    border: 1px solid #ddd;
    border-radius: 8px;
    padding: 0 14px;
    outline: none;
    font-size: 14.5px;
}
.form-group select:focus,
.form-group input[type="number"]:focus,
.form-group input[type="date"]:focus {
    border-color: #2f73ff;
}
</style>
</head>
<body>

<div class="admin-wrapper">

<%@ include file="_sidebar.jsp"%>

<div class="main-content">

<div class="page-header">
<div>
<h1><%= isEdit ? "Cập nhật khuyến mãi" : "Thêm khuyến mãi mới" %></h1>
<div class="welcome">
<a href="EmployeeKhuyenmaiServlet" style="color:#2f73ff;font-weight:bold;">
<i class="fa-solid fa-arrow-left"></i> Quay lại danh sách
</a>
</div>
</div>
</div>

<% if (message != null) { %>
<div class="alert alert-error"><%= message %></div>
<% } %>

<div class="form-card">

<form action="EmployeeKhuyenmaiServlet" method="post">

<% if (isEdit) { %>
<input type="hidden" name="maKM" value="<%= km.getMaKM() %>">
<% } %>

<div class="form-grid">

<div class="form-group full">
<label>Tên / Loại khuyến mãi *</label>
<input type="text" name="loaiKM" required
placeholder="VD: Khuyến mãi hè, Giảm giá cuối tuần..."
value="<%= km != null && km.getLoaiKM() != null ? km.getLoaiKM() : "" %>">
</div>

<div class="form-group">
<label>Phần trăm giảm (%) *</label>
<input type="number" name="phanTramGiam" min="0" max="100" step="0.1" required
value="<%= km != null ? km.getPhanTramGiam() : "" %>">
</div>

<div class="form-group"></div>

<div class="form-group">
<label>Ngày bắt đầu *</label>
<input type="date" name="ngayBatDau" required
value="<%= km != null && km.getNgayBatDau() != null ? km.getNgayBatDau() : "" %>">
</div>

<div class="form-group">
<label>Ngày kết thúc *</label>
<input type="date" name="ngayKetThuc" required
value="<%= km != null && km.getNgayKetThuc() != null ? km.getNgayKetThuc() : "" %>">
</div>

<div class="form-group full">
<label>Mô tả</label>
<textarea name="moTa" rows="4"><%= km != null && km.getMoTa() != null ? km.getMoTa() : "" %></textarea>
</div>

<div class="form-group full">
<label>Trạng thái</label>
<div class="form-check">
<input type="checkbox" id="trangThai" name="trangThai"
<%= (km == null || km.isTrangThai()) ? "checked" : "" %>>
<label for="trangThai" style="margin:0;font-weight:normal;">Đang áp dụng</label>
</div>
</div>

</div>

<div class="form-actions">
<button type="submit" class="btn btn-primary">
<i class="fa-solid fa-floppy-disk"></i>
<%= isEdit ? "Lưu thay đổi" : "Thêm khuyến mãi" %>
</button>
<a href="EmployeeKhuyenmaiServlet?action=list" class="btn btn-secondary">Hủy</a>
</div>

</form>

</div>

</div>

</div>

</body>
</html>
