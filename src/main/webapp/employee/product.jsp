<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="nhom3.Model.Sanpham" %>
<%@ page import="nhom3.Model.Danhmuc" %>

<%
if (session.getAttribute("employee") == null && session.getAttribute("admin") == null) {
    response.sendRedirect("../login.jsp");
    return;
}
request.setAttribute("activeMenu", "product");
List<Sanpham> productList = (List<Sanpham>) request.getAttribute("productList");
List<Danhmuc> danhMucList = (List<Danhmuc>) request.getAttribute("danhMucList");
String keyword = (String) request.getAttribute("keyword");
Integer selectedDM = (Integer) request.getAttribute("selectedDM");
String message = (String) request.getAttribute("message");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Quản lý sản phẩm - HAKY TECH</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
<link rel="stylesheet" href="css/admin.css">
</head>
<body>

<div class="admin-wrapper">

    <%@ include file="_sidebar.jsp" %>

    <div class="main-content">

        <div class="page-header">
            <div>
                <h1>Quản lý sản phẩm</h1>
                <div class="welcome">Danh sách toàn bộ sản phẩm đang kinh doanh</div>
            </div>
            <a class="btn btn-primary" href="EmployeeSanphamServlet?action=add">
                <i class="fa-solid fa-plus"></i> Thêm sản phẩm
            </a>
        </div>

        <% if (message != null) { %>
        <div class="alert alert-error"><%= message %></div>
        <% } %>

        <div class="card">

            <div class="toolbar">
                <form class="search-box" action="EmployeeSanphamServlet" method="get">
                    <input type="hidden" name="action" value="list">
                    <input type="text" name="keyword" placeholder="Tìm theo tên sản phẩm..."
                           value="<%= keyword != null ? keyword : "" %>">
                    <button type="submit" class="btn btn-secondary btn-sm">
                        <i class="fa-solid fa-magnifying-glass"></i> Tìm kiếm
                    </button>
                </form>

                <form class="search-box" action="EmployeeSanphamServlet" method="get">
                    <input type="hidden" name="action" value="list">
                    <select name="maDM" onchange="this.form.submit()">
                        <option value="">-- Tất cả danh mục --</option>
                        <% if (danhMucList != null) {
                            for (Danhmuc dm : danhMucList) { %>
                        <option value="<%= dm.getMaDM() %>"
                            <%= (selectedDM != null && selectedDM == dm.getMaDM()) ? "selected" : "" %>>
                            <%= dm.getTenDanhMuc() %>
                        </option>
                        <% } } %>
                    </select>
                </form>

                <% if ((keyword != null && !keyword.isEmpty()) || selectedDM != null) { %>
                <a class="btn btn-secondary btn-sm"
					href="EmployeeSanphamServlet">
				Xóa lọc
				</a>
                <% } %>
            </div>

            <% if (productList == null || productList.isEmpty()) { %>

            <div class="empty-state">
                <i class="fa-solid fa-box-open" style="font-size:34px;margin-bottom:10px;"></i>
                <p>Không tìm thấy sản phẩm nào.</p>
            </div>

            <% } else { %>

            <table>
                <thead>
                <tr>
                    <th>Mã SP</th>
                    <th>Tên sản phẩm</th>
                    <th>Ảnh</th>
                    <th>Giá bán</th>
                    <th>Tồn kho</th>
                    <th>Nguồn gốc</th>
                    <th>Trạng thái</th>
                    <th>Thao tác</th>
                </tr>
                </thead>
                <tbody>
                <% for (Sanpham sp : productList) { %>
                <tr>
                    <td>#<%= sp.getMaSP() %></td>
                    <td>
					<img
					src="<%= sp.getUrlHinhAnh()!=null && !sp.getUrlHinhAnh().isEmpty()
					? sp.getUrlHinhAnh()
					: "https://via.placeholder.com/70x70?text=No+Image"%>"
					
					style="width:70px;
					height:70px;
					object-fit:cover;
					border-radius:8px;"
					
					onerror="this.src='https://via.placeholder.com/70x70?text=Error'">
				
					</td>
					
                    <td><%= sp.getTenSP() %></td>
                    <td><%= String.format("%,.0f", sp.getGiaThanh()) %> đ</td>
                    <td><%= sp.getSoLuong() %></td>
                    <td><%= sp.getNguonGoc() != null ? sp.getNguonGoc() : "-" %></td>
                    <td>
                        <% if (sp.isTrangThai()) { %>
                        <span class="badge badge-active">Đang bán</span>
                        <% } else { %>
                        <span class="badge badge-locked">Đã ẩn</span>
                        <% } %>
                    </td>
                    <td>
                        <div class="actions">
                            <a class="btn btn-secondary btn-sm" href="EmployeeSanphamServlet?action=edit&id=<%= sp.getMaSP() %>">
                                <i class="fa-solid fa-pen"></i> Sửa
                            </a>
                            <a class="btn <%= sp.isTrangThai() ? "btn-warning" : "btn-success" %> btn-sm"
                               href="EmployeeSanphamServlet?action=toggle&id=<%= sp.getMaSP() %>">
                                <% if (sp.isTrangThai()) { %>
                                    <i class="fa-solid fa-eye-slash"></i> Ẩn
                                <% } else { %>
                                    <i class="fa-solid fa-eye"></i> Hiện
                                <% } %>
                            </a>
                            <a class="btn btn-danger btn-sm" href="EmployeeSanphamServlet?action=delete&id=<%= sp.getMaSP() %>"
                               onclick="return confirm('Bạn có chắc muốn xóa sản phẩm này?');">
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
