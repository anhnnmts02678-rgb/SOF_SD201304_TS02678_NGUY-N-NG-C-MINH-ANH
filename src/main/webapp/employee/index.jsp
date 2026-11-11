<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
if (session.getAttribute("employee") == null && session.getAttribute("admin") == null) {
    response.sendRedirect("../login.jsp");
    return;
}

request.setAttribute("activeMenu", "index");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Trang nhân viên - HAKY TECH</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
<link rel="stylesheet" href="css/admin.css">
</head>
<body>

<div class="admin-wrapper">

    <%@ include file="_sidebar.jsp" %>

    <div class="main-content">

        <div class="page-header">
            <div>
                <h1>Trang nhân viên</h1>
                <div class="welcome">
                    Xin chào,
                    <b><%= session.getAttribute("ten") %></b>
                </div>
            </div>
        </div>

        <div class="card">

            <h2 style="margin-bottom:20px">Chức năng làm việc</h2>

            <div class="employee-actions">

                <a class="btn btn-primary" href="EmployeeSanphamServlet?action=list">
                    <i class="fa-solid fa-box"></i>
                    Quản lý sản phẩm
                </a>

                <a class="btn btn-primary" href="${pageContext.request.contextPath}/employee/orders">
                    <i class="fa-solid fa-receipt"></i>
                    Quản lý đơn hàng
                </a>

                <a class="btn btn-primary" href="EmployeeUserServlet?action=list">
                    <i class="fa-solid fa-users"></i>
                    Quản lý khách hàng
                </a>

                <a class="btn btn-primary" href="ThongkeServlet">
                    <i class="fa-solid fa-chart-line"></i>
                    Thống kê
                </a>

            </div>

        </div>

        <br>

        <div class="card">
            <h2>Ghi chú</h2>
            <p class="note">
                Nhân viên chỉ được phép thao tác các chức năng được phân quyền.
            </p>
        </div>

    </div>

</div>

</body>
</html>
