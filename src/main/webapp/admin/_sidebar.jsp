<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%
// Doc activeMenu tu request attribute (do trang cha set truoc khi include file nay).
String activeMenu = (String) request.getAttribute("activeMenu");
if (activeMenu == null) activeMenu = "";
%>
<div class="sidebar">
    <div class="logo">
        HAKY <span>TECH</span>
        <span class="role-badge">TRANG QUẢN TRỊ</span>
    </div>
    <nav>
        <a href="index.jsp" class="<%= "index".equals(activeMenu) ? "active" : "" %>">
            <i class="fa-solid fa-gauge"></i> Tổng quan
        </a>
        <a href="EmployeeServlet?action=list" class="<%= "employee".equals(activeMenu) ? "active" : "" %>">
            <i class="fa-solid fa-user-tie"></i> Quản lý nhân viên
        </a>
        <a href="UserServlet?action=list" class="<%= "customer".equals(activeMenu) ? "active" : "" %>">
            <i class="fa-solid fa-users"></i> Quản lý khách hàng
        </a>

        <a href="orders" class="<%= "orders".equals(activeMenu) ? "active" : "" %>">
            <i class="fa-solid fa-file-invoice-dollar"></i> Quản lý hóa đơn
        </a>
        
		<a href="danhgia.jsp"
		class="<%= "order".equals(activeMenu)?"active":"" %>">
		<i class="fa-solid fa-receipt"></i>
		Đánh giá
		</a>
		
		<a href="EmployeeKhuyenmaiServlet?action=list"
		class="<%= "khuyenmai".equals(activeMenu) ? "active" : "" %>">
		    <i class="fa-solid fa-tags"></i>
		    Khuyến mãi
		</a>
		
        <a href="ThongkeServlet" class="<%= "thongke".equals(activeMenu) ? "active" : "" %>">
            <i class="fa-solid fa-chart-line"></i> Thống kê
        </a>
        <a href="${pageContext.request.contextPath}/admin/NhatkyServlet"
           class="<%= "nhatky".equals(activeMenu) ? "active" : "" %>">
            <i class="fa-solid fa-clock-rotate-left"></i> Nhật ký
        </a>
        <div class="logout-link">
            <a href="${pageContext.request.contextPath}/LogoutServlet">
                <i class="fa-solid fa-right-from-bracket"></i> Đăng xuất
            </a>
        </div>
    </nav>
</div>
