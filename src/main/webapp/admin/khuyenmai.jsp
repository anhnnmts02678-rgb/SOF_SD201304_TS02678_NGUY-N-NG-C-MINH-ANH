<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="nhom3.Model.Khuyenmai" %>

<%
if (session.getAttribute("employee") == null && session.getAttribute("admin") == null) {
    response.sendRedirect("../login.jsp");
    return;
}
request.setAttribute("activeMenu", "khuyenmai");
List<Khuyenmai> kmList = (List<Khuyenmai>) request.getAttribute("kmList");

String keyword = (String) request.getAttribute("keyword");
String selectedStatus = (String) request.getAttribute("selectedStatus");
String message = (String) request.getAttribute("message");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Quản lý khuyến mãi - HAKY TECH</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
<link rel="stylesheet" href="css/admin.css">
</head>
<body>

<div class="admin-wrapper">

    <%@ include file="/admin/_sidebar.jsp" %>

    <div class="main-content">

        <div class="page-header">
            <div>
                <h1>Quản lý khuyến mãi</h1>
                <div class="welcome">Danh sách toàn bộ chương trình khuyến mãi</div>
            </div>
            <a class="btn btn-primary" href="EmployeeKhuyenmaiServlet?action=add">
                <i class="fa-solid fa-plus"></i> Thêm khuyến mãi
            </a>
        </div>

        <% if (message != null) { %>
        <div class="alert alert-error"><%= message %></div>
        <% } %>

        <div class="card">

            <div class="toolbar">
                <form class="search-box" action="EmployeeKhuyenmaiServlet" method="get">
                    <input type="hidden" name="action" value="list">
                    <input type="text" name="keyword" placeholder="Tìm theo tên/mô tả khuyến mãi..."
                           value="<%= keyword != null ? keyword : "" %>">
                    <button type="submit" class="btn btn-secondary btn-sm">
                        <i class="fa-solid fa-magnifying-glass"></i> Tìm kiếm
                    </button>
                </form>

                <form class="search-box" action="EmployeeKhuyenmaiServlet" method="get">
                    <input type="hidden" name="action" value="list">
                    <select name="status" onchange="this.form.submit()">
                        <option value="">-- Tất cả trạng thái --</option>
                        <option value="active" <%= "active".equals(selectedStatus) ? "selected" : "" %>>Đang áp dụng</option>
                        <option value="inactive" <%= "inactive".equals(selectedStatus) ? "selected" : "" %>>Đã tắt</option>
                    </select>
                </form>

                <% if ((keyword != null && !keyword.isEmpty()) || (selectedStatus != null && !selectedStatus.isEmpty())) { %>
                <a class="btn btn-secondary btn-sm" href="EmployeeKhuyenmaiServlet">Xóa lọc</a>
                <% } %>
            </div>

            <% if (kmList == null || kmList.isEmpty()) { %>

            <div class="empty-state">
                <i class="fa-solid fa-tags" style="font-size:34px;margin-bottom:10px;"></i>
                <p>Không tìm thấy khuyến mãi nào.</p>
            </div>

            <% } else { %>

            <table>
                <thead>
                <tr>
                    <th>Mã KM</th>
                    <th>Tên / Loại khuyến mãi</th>
                    <th>Giảm giá</th>
                    <th>Ngày bắt đầu</th>
                    <th>Ngày kết thúc</th>
                    <th>Mô tả</th>
                    <th>Trạng thái</th>
                    <th>Thao tác</th>
                </tr>
                </thead>
                <tbody>
                <% for (Khuyenmai km : kmList) { %>
                <tr>
                    <td>#<%= km.getMaKM() %></td>
                    <td><%= km.getLoaiKM() %></td>
                    <td><%= String.format("%,.0f", km.getPhanTramGiam()) %>%</td>
                    <td><%= km.getNgayBatDau() %></td>
                    <td><%= km.getNgayKetThuc() %></td>
                    <td><%= km.getMoTa() != null && !km.getMoTa().isEmpty() ? km.getMoTa() : "-" %></td>
                    <td>
                        <% if (km.isTrangThai()) { %>
                        <span class="badge badge-active">Đang áp dụng</span>
                        <% } else { %>
                        <span class="badge badge-locked">Đã tắt</span>
                        <% } %>
                    </td>
                    <td>
                        <div class="actions">
                            <a class="btn btn-secondary btn-sm" href="EmployeeKhuyenmaiServlet?action=edit&id=<%= km.getMaKM() %>">
                                <i class="fa-solid fa-pen"></i> Sửa
                            </a>
                            <a class="btn <%= km.isTrangThai() ? "btn-warning" : "btn-success" %> btn-sm"
                               href="EmployeeKhuyenmaiServlet?action=toggle&id=<%= km.getMaKM() %>">
                                <% if (km.isTrangThai()) { %>
                                    <i class="fa-solid fa-eye-slash"></i> Tắt
                                <% } else { %>
                                    <i class="fa-solid fa-eye"></i> Bật
                                <% } %>
                            </a>
                            <a class="btn btn-danger btn-sm" href="EmployeeKhuyenmaiServlet?action=delete&id=<%= km.getMaKM() %>"
                               onclick="return confirm('Bạn có chắc muốn xóa khuyến mãi này?');">
                                <i class="fa-solid fa-trash"></i> Xóa
                            </a>
                        </div>
                    </td>
                </tr>
                <% } %>
                </tbody>
            </table>

            <% } %>

        </div>

    </div>

</div>

</body>
</html>
