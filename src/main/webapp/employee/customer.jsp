<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="nhom3.Model.Users" %>

<%
if (session.getAttribute("employee") == null) {
    response.sendRedirect("../login.jsp");
    return;
}
request.setAttribute("activeMenu", "customer");
List<Users> userList = (List<Users>) request.getAttribute("userList");
String keyword = (String) request.getAttribute("keyword");
String message = (String) request.getAttribute("message");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Quản lý khách hàng - HAKY TECH Admin</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
<link rel="stylesheet" href="css/admin.css">
</head>
<body>

<div class="admin-wrapper">

    <%@ include file="_sidebar.jsp" %>

    <div class="main-content">

        <div class="page-header">
            <div>
                <h1>Quản lý khách hàng</h1>
                <div class="welcome">Danh sách toàn bộ khách hàng đã đăng ký trên hệ thống</div>
            </div>
            <a class="btn btn-primary" href="EmployeeUserServlet?action=add">
                <i class="fa-solid fa-plus"></i> Thêm khách hàng
            </a>
        </div>

        <% if (message != null) { %>
        <div class="alert alert-error"><%= message %></div>
        <% } %>

        <div class="card">

            <div class="toolbar">
                <form class="search-box" action="EmployeeUserServlet" method="get">
                    <input type="hidden" name="action" value="list">
                    <input type="text" name="keyword" placeholder="Tìm theo họ tên..."
                           value="<%= keyword != null ? keyword : "" %>">
                    <button type="submit" class="btn btn-secondary btn-sm">
                        <i class="fa-solid fa-magnifying-glass"></i> Tìm kiếm
                    </button>
                    <% if (keyword != null && !keyword.isEmpty()) { %>
                    <a class="btn btn-secondary btn-sm" href="EmployeeUserServlet?action=list">Xóa lọc</a>
                    <% } %>
                </form>
            </div>

            <% if (userList == null || userList.isEmpty()) { %>

            <div class="empty-state">
                <i class="fa-solid fa-user-slash" style="font-size:34px;margin-bottom:10px;"></i>
                <p>Không tìm thấy khách hàng nào.</p>
            </div>

            <% } else { %>

            <table>
                <thead>
                <tr>
                    <th>Mã KH</th>
                    <th>Họ tên</th>
                    <th>SĐT</th>
                    <th>Email</th>
                    <th>Địa chỉ</th>
                    <th>Trạng thái</th>
                    <th>Thao tác</th>
                </tr>
                </thead>
                <tbody>
                <% for (Users u : userList) { %>
                <tr>
                    <td>#<%= u.getMaKH() %></td>
                    <td><%= u.getFullName() %></td>
                    <td><%= u.getSdt() != null ? u.getSdt() : "-" %></td>
                    <td><%= u.getEmail() %></td>
                    <td><%= u.getAddress() != null ? u.getAddress() : "-" %></td>
                    <td>
                        <% if (u.isTrangThai()) { %>
                        <span class="badge badge-active">Hoạt động</span>
                        <% } else { %>
                        <span class="badge badge-locked">Đã khóa</span>
                        <% } %>
                    </td>
                    <td>
                        <div class="actions">
                            <a class="btn btn-secondary btn-sm" href="EmployeeUserServlet?action=edit&id=<%= u.getMaKH() %>">
                                <i class="fa-solid fa-pen"></i> Sửa
                            </a>
                            <a class="btn <%= u.isTrangThai() ? "btn-warning" : "btn-success" %> btn-sm"
                               href="EmployeeUserServlet?action=toggle&id=<%= u.getMaKH() %>">
                                <% if (u.isTrangThai()) { %>
                                    <i class="fa-solid fa-lock"></i> Khóa
                                <% } else { %>
                                    <i class="fa-solid fa-lock-open"></i> Mở khóa
                                <% } %>
                            </a>
                            <a class="btn btn-danger btn-sm" href="EmployeeUserServlet?action=delete&id=<%= u.getMaKH() %>"
                               onclick="return confirm('Bạn có chắc muốn xóa khách hàng này?');">
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
