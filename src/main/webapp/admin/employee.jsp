<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="nhom3.Model.Employee" %>

<%
if (session.getAttribute("admin") == null) {
    response.sendRedirect("../login.jsp");
    return;
}
request.setAttribute("activeMenu", "employee");
List<Employee> employeeList = (List<Employee>) request.getAttribute("employeeList");
String keyword = (String) request.getAttribute("keyword");
String message = (String) request.getAttribute("message");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Quản lý nhân viên - HAKY TECH Admin</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
<link rel="stylesheet" href="css/admin.css">
</head>
<body>

<div class="admin-wrapper">

    <%@ include file="_sidebar.jsp" %>

    <div class="main-content">

        <div class="page-header">
            <div>
                <h1>Quản lý nhân viên</h1>
                <div class="welcome">Danh sách nhân viên đang làm việc tại hệ thống</div>
            </div>
            <a class="btn btn-primary" href="EmployeeServlet?action=add">
                <i class="fa-solid fa-plus"></i> Thêm nhân viên
            </a>
        </div>

        <% if (message != null) { %>
        <div class="alert alert-error"><%= message %></div>
        <% } %>

        <div class="card">

            <div class="toolbar">
                <form class="search-box" action="EmployeeServlet" method="get">
                    <input type="hidden" name="action" value="list">
                    <input type="text" name="keyword" placeholder="Tìm theo tên, username, email..."
                           value="<%= keyword != null ? keyword : "" %>">
                    <button type="submit" class="btn btn-secondary btn-sm">
                        <i class="fa-solid fa-magnifying-glass"></i> Tìm kiếm
                    </button>
                    <% if (keyword != null && !keyword.isEmpty()) { %>
                    <a class="btn btn-secondary btn-sm" href="EmployeeServlet?action=list">Xóa lọc</a>
                    <% } %>
                </form>
            </div>

            <% if (employeeList == null || employeeList.isEmpty()) { %>

            <div class="empty-state">
                <i class="fa-solid fa-user-slash" style="font-size:34px;margin-bottom:10px;"></i>
                <p>Không tìm thấy nhân viên nào.</p>
            </div>

            <% } else { %>

            <table>
                <thead>
                <tr>
                    <th>Mã NV</th>
                    <th>Họ tên</th>
                    <th>Tên đăng nhập</th>
                    <th>SĐT</th>
                    <th>Email</th>
                    <th>Ngày tạo</th>
                    <th>Trạng thái</th>
                    <th>Thao tác</th>
                </tr>
                </thead>
                <tbody>
                <% for (Employee e : employeeList) { %>
                <tr>
                    <td>#<%= e.getMaNV() %></td>
                    <td><%= e.getHoTen() %></td>
                    <td><%= e.getUsername() %></td>
                    <td><%= e.getSdt() != null ? e.getSdt() : "-" %></td>
                    <td><%= e.getEmail() %></td>
                    <td><%= e.getNgayTao() != null ? e.getNgayTao().toString().substring(0, 10) : "-" %></td>
                    <td>
                        <% if (e.isTrangThai()) { %>
                        <span class="badge badge-active">Đang làm việc</span>
                        <% } else { %>
                        <span class="badge badge-locked">Đã khóa</span>
                        <% } %>
                    </td>
                    <td>
                        <div class="actions">
                            <a class="btn btn-secondary btn-sm" href="EmployeeServlet?action=edit&id=<%= e.getMaNV() %>">
                                <i class="fa-solid fa-pen"></i> Sửa
                            </a>
                            <a class="btn <%= e.isTrangThai() ? "btn-warning" : "btn-success" %> btn-sm"
                               href="EmployeeServlet?action=toggle&id=<%= e.getMaNV() %>">
                                <% if (e.isTrangThai()) { %>
                                    <i class="fa-solid fa-lock"></i> Khóa
                                <% } else { %>
                                    <i class="fa-solid fa-lock-open"></i> Mở khóa
                                <% } %>
                            </a>
                            <a class="btn btn-danger btn-sm" href="EmployeeServlet?action=delete&id=<%= e.getMaNV() %>"
                               onclick="return confirm('Bạn có chắc muốn xóa nhân viên này?');">
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
