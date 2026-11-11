<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%
Object headerUser = session.getAttribute("user");
String activePage = (String) request.getAttribute("activePage");
if (activePage == null) activePage = "";
String cp = request.getContextPath();
%>
<header>
    <div class="header-container">

        <div class="logo">
            <a href="<%=cp%>/index.jsp">
                <i class="fa-solid fa-microchip"></i>
                HAKY TECH
            </a>
        </div>

        <div class="nav-group">

            <nav>
                <a href="<%=cp%>/products" class="<%= "products".equals(activePage) ? "active" : "" %>">
                    Sản phẩm
                </a>
                <a href="<%=cp%>/KhuyenmaiServlet" class="<%= "khuyenmai".equals(activePage) ? "active" : "" %>">
                    Khuyến mãi
                </a>
                <% if (headerUser != null) { %>
                <a href="<%=cp%>/OrderHistoryServlet" class="<%= "donhang".equals(activePage) ? "active" : "" %>">
                    Đơn hàng
                </a>
                <% } %>
            </nav>

            <div class="divider"></div>

            <div class="actions">

                <a class="icon-btn" href="<%=cp%>/CartServlet" title="Giỏ hàng">
                    <i class="fa-solid fa-cart-shopping"></i>
                </a>

                <% if (headerUser == null) { %>

                    <a class="btn btn-outline" href="<%=cp%>/login.jsp">Đăng nhập</a>
                    <a class="btn" href="<%=cp%>/register.jsp">Đăng kí</a>

                <% } else { %>

                    <div class="user-box">
                        <i class="fa-solid fa-circle-user"></i>
                        <span>Xin chào, <b><%= session.getAttribute("ten") %></b></span>
                        <a class="btn btn-outline" href="<%=cp%>/LogoutServlet">Đăng xuất</a>
                    </div>

                <% } %>

            </div> 

        </div>

    </div>
</header>
