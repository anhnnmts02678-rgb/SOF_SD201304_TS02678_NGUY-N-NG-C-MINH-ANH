<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page import="java.util.List" %>
<%@ page import="nhom3.Model.Nhatky" %>

<%
if (session.getAttribute("employee") == null && session.getAttribute("admin") == null) {
    response.sendRedirect("../login.jsp");
    return;
}
request.setAttribute("activeMenu", "nhatky");
List<Nhatky> listNK = (List<Nhatky>) request.getAttribute("listNK");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Nhật ký hệ thống - HAKY TECH</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
<link rel="stylesheet" href="css/admin.css">
</head>
<body>

<div class="admin-wrapper">

    <%@ include file="_sidebar.jsp" %>

    <div class="main-content">

        <div class="page-header">
            <div>
                <h1>Nhật ký hệ thống</h1>
                <div class="welcome">Lịch sử thao tác của admin và nhân viên trên hệ thống</div>
            </div>
            <a class="btn btn-primary" href="EmployeeNhatkyServlet">
                <i class="fa-solid fa-rotate"></i> Tải lại
            </a>
        </div>

        <div class="card">

            <c:choose>

                <c:when test="${not empty listNK}">

                    <table>
                        <thead>
                        <tr>
                            <th>STT</th>
                            <th>Mã NV</th>
                            <th>Mã Admin</th>
                            <th>Hành động</th>
                            <th>Mô tả</th>
                            <th>Thời gian</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="nk" items="${listNK}" varStatus="st">
                            <tr>
                                <td>${st.index + 1}</td>
                                <td>${nk.maNV != null ? nk.maNV : "-"}</td>
                                <td>${nk.maAdmin != null ? nk.maAdmin : "-"}</td>
                                <td>${nk.hanhDong}</td>
                                <td>${nk.moTa}</td>
                                <td>${nk.thoiGian}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>

                </c:when>

                <c:otherwise>
                    <div class="empty-state">
                        <i class="fa-solid fa-clock-rotate-left" style="font-size:34px;margin-bottom:10px;"></i>
                        <p>Chưa có dữ liệu nhật ký.</p>
                    </div>
                </c:otherwise>

            </c:choose>

        </div>

    </div>

</div>

</body>
</html>