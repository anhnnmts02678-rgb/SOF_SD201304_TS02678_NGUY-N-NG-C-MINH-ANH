<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="nhom3.Model.Danhgia" %>

<%
if (session.getAttribute("employee") == null && session.getAttribute("admin") == null) {
    response.sendRedirect("../login.jsp");
    return;
}
request.setAttribute("activeMenu", "danhgia");
List<Danhgia> dgList = (List<Danhgia>) request.getAttribute("dgList");
String keyword = (String) request.getAttribute("keyword");
Integer selectedSao = (Integer) request.getAttribute("selectedSao");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Quản lý đánh giá sản phẩm - HAKY TECH</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
<link rel="stylesheet" href="css/admin.css">
<style>
.review-content-cell {
    max-width: 260px;
    white-space: normal;
    line-height: 1.5;
}
.review-thumb {
    width: 56px;
    height: 56px;
    object-fit: cover;
    border-radius: 8px;
    border: 1px solid #eee;
    margin-top: 6px;
    display: block;
}
.stars-cell i {
    color: #f5a623;
    font-size: 14px;
}
</style>
</head>
<body>

<div class="admin-wrapper">

    <%@ include file="_sidebar.jsp" %>

    <div class="main-content">

        <div class="page-header">
            <div>
                <h1>Quản lý đánh giá sản phẩm</h1>
                <div class="welcome">
					Danh sách đánh giá của khách hàng
				</div>
            </div>
        </div>

        <div class="card">

            <div class="toolbar">
                <form class="search-box" action="EmployeeDanhgiaServlet" method="get">
                    <input type="hidden" name="action" value="list">
                    <input type="text" name="keyword" placeholder="Tìm theo khách hàng, sản phẩm, nội dung..."
                           value="<%= keyword != null ? keyword : "" %>">
                    <button type="submit" class="btn btn-secondary btn-sm">
                        <i class="fa-solid fa-magnifying-glass"></i> Tìm kiếm
                    </button>
                </form>


                <form class="search-box" action="EmployeeDanhgiaServlet" method="get">
                    <input type="hidden" name="action" value="list">
                    <select name="soSao" onchange="this.form.submit()">
                        <option value="">-- Tất cả số sao --</option>
                        <% for (int i = 5; i >= 1; i--) { %>
                        <option value="<%= i %>" <%= (selectedSao != null && selectedSao == i) ? "selected" : "" %>><%= i %> sao</option>
                        <% } %>
                    </select>
                </form>

                <% if ((keyword != null && !keyword.isEmpty())
        			|| selectedSao != null) { %>
                <a class="btn btn-secondary btn-sm" href="EmployeeDanhgiaServlet">Xóa lọc</a>
                <% } %>
            </div>

            <% if (dgList == null || dgList.isEmpty()) { %>

            <div class="empty-state">
                <i class="fa-solid fa-star-half-stroke" style="font-size:34px;margin-bottom:10px;"></i>
                <p>Không tìm thấy đánh giá nào.</p>
            </div>

            <% } else { %>

            <table>
                <thead>
                <tr>
                    <th>Mã ĐG</th>
                    <th>Khách hàng</th>
                    <th>Sản phẩm</th>
                    <th>Số sao</th>
                    <th>Nội dung / Ảnh</th>
                    <th>Thời gian</th>
                    <th>Thao tác</th>
                </tr>
                </thead>
                <tbody>
                <% for (Danhgia dg : dgList) { %>
                <tr>
                    <td>#<%= dg.getMaDG() %></td>
                    <td><%= dg.getTenKhachHang() %></td>
                    <td><%= dg.getTenSanPham() %></td>
                    <td class="stars-cell">
                        <% for (int i = 1; i <= 5; i++) { %>
                            <% if (i <= dg.getSoSao()) { %>
                            <i class="fa-solid fa-star"></i>
                            <% } else { %>
                            <i class="fa-regular fa-star"></i>
                            <% } %>
                        <% } %>
                    </td>
                    <td class="review-content-cell">
                        <%= dg.getNoiDungDanhGia() != null ? dg.getNoiDungDanhGia() : "" %>
                        <% if (dg.getAnh() != null && !dg.getAnh().trim().isEmpty()) { %>
                        <img class="review-thumb" src="<%= dg.getAnh() %>"
                             onerror="this.style.display='none'">
                        <% } %>
                    </td>
                    <td><%= dg.getThoiGian() %></td>
                    
                    <td>

<div class="actions">

<a class="btn btn-danger btn-sm"
   href="EmployeeDanhgiaServlet?action=delete&id=<%= dg.getMaDG() %>"
   onclick="return confirm('Bạn có chắc muốn xóa đánh giá này?');">

<i class="fa-solid fa-trash"></i>

Xóa

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
