<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
String activeMenu =
(String)request.getAttribute("activeMenu");
if(activeMenu == null){
    activeMenu="";
}
%>
<div class="sidebar">
<div class="logo">
HAKY <span>TECH</span>
<span class="role-badge">
NHÂN VIÊN
</span>
</div>
<nav>

<a href="index.jsp"
class="<%= "index".equals(activeMenu)?"active":"" %>">
<i class="fa-solid fa-gauge"></i>
Tổng quan
</a>
<a href="EmployeeSanphamServlet?action=list"
class="<%= "product".equals(activeMenu)?"active":"" %>">
<i class="fa-solid fa-box"></i>
Quản lý sản phẩm
</a>

<a href="EmployeeUserServlet?action=list"
class="<%= "customer".equals(activeMenu)?"active":"" %>">
<i class="fa-solid fa-users"></i>
Khách hàng
</a>
<a href="${pageContext.request.contextPath}/employee/orders"
class="<%= "orders".equals(activeMenu) ? "active" : "" %>">
<i class="fa-solid fa-receipt"></i>
Đơn hàng
</a>

<a href="${pageContext.request.contextPath}/employee/EmployeeNhatkyServlet"
class="<%= "nhatky".equals(activeMenu) ? "active" : "" %>">
<i class="fa-solid fa-clock-rotate-left"></i>
Nhật ký
</a>

<a href="ThongkeServlet" class="<%= "thongke".equals(activeMenu) ? "active" : "" %>">
     <i class="fa-solid fa-chart-line"></i>
     Thống kê
     </a>

<div class="logout-link">
<a href="${pageContext.request.contextPath}/LogoutServlet">
<i class="fa-solid fa-right-from-bracket"></i>
Đăng xuất
</a>
</div>
</nav>
</div>