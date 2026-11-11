<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="nhom3.Model.Hoadon" %>

<%
if (session.getAttribute("employee") == null && session.getAttribute("admin") == null) {
    response.sendRedirect("../login.jsp");
    return;
}

request.setAttribute("activeMenu", "orders");

List<Hoadon> orderList =
        (List<Hoadon>) request.getAttribute("orderList");

String trangThai =
        (String) request.getAttribute("trangThai");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Quản lý đơn hàng - HAKY TECH</title>

<link rel="stylesheet"
href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

<link rel="stylesheet" href="css/admin.css">

<style>

.filter-bar{
    display:flex;
    gap:10px;
    flex-wrap:wrap;
    margin-bottom:20px;
}

.filter-bar a{
    text-decoration:none;
    padding:10px 18px;
    border-radius:8px;
    background:#edf2f7;
    color:#333;
    font-weight:600;
    transition:.25s;
}

.filter-bar a:hover{
    background:#2563eb;
    color:#fff;
}

.filter-bar a.active{
    background:#2563eb;
    color:#fff;
}

table{
    width:100%;
    border-collapse:collapse;
}

table thead{
    background:#2563eb;
    color:#fff;
}

table th{
    padding:14px;
    text-align:center;
}

table td{
    padding:14px;
    border-bottom:1px solid #ececec;
    text-align:center;
}

table tbody tr:hover{
    background:#fafafa;
}

.badge{
    display:inline-block;
    padding:6px 14px;
    border-radius:20px;
    color:#fff;
    font-size:13px;
    font-weight:bold;
}

.wait{
    background:#f59e0b;
}

.process{
    background:#3b82f6;
}

.ship{
    background:#06b6d4;
}

.done{
    background:#16a34a;
}

.cancel{
    background:#dc2626;
}

.actions{
    display:flex;
    justify-content:center;
    gap:8px;
    flex-wrap:wrap;
}

.empty-state{
    text-align:center;
    padding:70px 20px;
    color:#777;
}

.empty-state i{
    font-size:45px;
    margin-bottom:12px;
    color:#2563eb;
}

</style>

</head>

<body>

<div class="admin-wrapper">

    <%@ include file="_sidebar.jsp" %>

    <div class="main-content">

        <div class="page-header">

            <div>

                <h1>Quản lý đơn hàng</h1>

                <div class="welcome">
                    Danh sách đơn hàng của khách hàng
                </div>

            </div>

        </div>

        <div class="card">

            <div class="filter-bar">

                <a href="orders"
                class="<%= (trangThai==null || trangThai.isEmpty()) ? "active" : "" %>">

                    Tất cả

                </a>

                <a href="orders?trangThai=CHO_XAC_NHAN"
                class="<%= "CHO_XAC_NHAN".equals(trangThai) ? "active" : "" %>">

                    Chờ xác nhận

                </a>

                <a href="orders?trangThai=DANG_XU_LY"
                class="<%= "DANG_XU_LY".equals(trangThai) ? "active" : "" %>">

                    Đang xử lý

                </a>

                <a href="orders?trangThai=DANG_GIAO"
                class="<%= "DANG_GIAO".equals(trangThai) ? "active" : "" %>">

                    Đang giao

                </a>

                <a href="orders?trangThai=DA_GIAO"
                class="<%= "DA_GIAO".equals(trangThai) ? "active" : "" %>">

                    Đã giao

                </a>

                <a href="orders?trangThai=DA_HUY"
                class="<%= "DA_HUY".equals(trangThai) ? "active" : "" %>">

                    Đã hủy

                </a>

            </div>

<%
if(orderList == null || orderList.isEmpty()){
%>

<div class="empty-state">

    <i class="fa-solid fa-box-open"></i>

    <h3>Hiện chưa có đơn hàng nào.</h3>

</div>

<%
}else{
%>

<table>

<thead>

<tr>

    <th>Mã HD</th>

    <th>Mã KH</th>

    <th>Tổng tiền</th>

    <th>Ngày thanh toán</th>

    <th>Trạng thái</th>

    <th>Thao tác</th>

</tr>

</thead>

<tbody>
<%
for(Hoadon hd : orderList){

    String tt = hd.getTrangThai();
%>

<tr>

    <td>
        #<%= hd.getMaHD() %>
    </td>

    <td>
        KH<%= hd.getMaKH() %>
    </td>

    <td style="font-weight:bold;color:#dc2626;">
        <%= String.format("%,.0f", hd.getTongGia()) %> đ
    </td>

    <td>
        <%= hd.getNgayThanhToan() %>
    </td>

    <td>

    <% if("CHO_XAC_NHAN".equals(tt)){ %>

        <span class="badge wait">
            Chờ xác nhận
        </span>

    <% }else if("DANG_XU_LY".equals(tt)){ %>

        <span class="badge process">
            Đang xử lý
        </span>

    <% }else if("DANG_GIAO".equals(tt)){ %>

        <span class="badge ship">
            Đang giao
        </span>

    <% }else if("DA_GIAO".equals(tt)){ %>

        <span class="badge done">
            Đã giao
        </span>

    <% }else{ %>

        <span class="badge cancel">
            Đã hủy
        </span>

    <% } %>

    </td>

    <td>

        <div class="actions">

            <a class="btn btn-secondary btn-sm"
               href="orders?action=detail&maHD=<%=hd.getMaHD()%>">

                <i class="fa-solid fa-eye"></i>
                Chi tiết

            </a>

            <% if("CHO_XAC_NHAN".equals(tt)){ %>

            <a class="btn btn-success btn-sm"
               href="orders?action=process&maHD=<%=hd.getMaHD()%>"
               onclick="return confirm('Xác nhận đơn hàng này?');">

                <i class="fa-solid fa-check"></i>
                Xác nhận

            </a>

            <a class="btn btn-danger btn-sm"
               href="orders?action=cancel&maHD=<%=hd.getMaHD()%>"
               onclick="return confirm('Hủy đơn hàng này?');">

                <i class="fa-solid fa-xmark"></i>
                Hủy

            </a>

            <% } %>

            <% if("DANG_XU_LY".equals(tt)){ %>

            <a class="btn btn-primary btn-sm"
               href="orders?action=ship&maHD=<%=hd.getMaHD()%>"
               onclick="return confirm('Bắt đầu giao đơn này?');">

                <i class="fa-solid fa-truck"></i>
                Giao hàng

            </a>

            <% } %>

            <% if("DANG_GIAO".equals(tt)){ %>

            <a class="btn btn-success btn-sm"
               href="orders?action=done&maHD=<%=hd.getMaHD()%>"
               onclick="return confirm('Xác nhận đã giao thành công?');">

                <i class="fa-solid fa-box-open"></i>
                Hoàn thành

            </a>

            <% } %>

        </div>

    </td>

</tr>

<%
}
%>

</tbody>

</table>

<%
}
%>

        </div>

    </div>

</div>

</body>
</html>