<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="nhom3.Model.*" %>
<%@ page import="nhom3.Dao.SanphamDao" %>

<%
Hoadon hoadon = (Hoadon) request.getAttribute("hoadon");
List<HoadonCT> items = (List<HoadonCT>) request.getAttribute("items");
Users customer = (Users) request.getAttribute("customer");
Thanhtoan thanhtoan = (Thanhtoan) request.getAttribute("thanhtoan");
SanphamDao sanphamDao = (SanphamDao) request.getAttribute("sanphamDao");

String trangThai = hoadon.getTrangThai();
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Chi Tiết Đơn Hàng</title>

<link rel="stylesheet"
href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

<style>

*{
    margin:0;
    padding:0;
    box-sizing:border-box;
    font-family:Arial,sans-serif;
}

body{
    background:#f4f6f9;
}

.container{
    width:1100px;
    margin:30px auto;
}

.card{
    background:white;
    padding:25px;
    border-radius:12px;
    margin-bottom:20px;
    box-shadow:0 3px 10px rgba(0,0,0,.08);
}

.title{
    font-size:28px;
    font-weight:bold;
    color:#1f2937;
    margin-bottom:20px;
}

.info-row{
    display:flex;
    justify-content:space-between;
    padding:10px 0;
    border-bottom:1px solid #eee;
}

.info-row:last-child{
    border-bottom:none;
}

table{
    width:100%;
    border-collapse:collapse;
}

table th{
    background:#2563eb;
    color:white;
    padding:12px;
}

table td{
    padding:12px;
    border-bottom:1px solid #ddd;
    text-align:center;
}

.total{
    text-align:right;
    font-size:24px;
    color:red;
    font-weight:bold;
    margin-top:20px;
}

.badge{
    padding:8px 15px;
    border-radius:20px;
    color:white;
    font-weight:bold;
}

.wait{
    background:#f59e0b;
}

.process{
    background:#3b82f6;
}

.ship{
    background:#0ea5e9;
}

.done{
    background:#22c55e;
}

.cancel{
    background:#ef4444;
}

.btn{
    display:inline-block;
    text-decoration:none;
    padding:10px 18px;
    border-radius:8px;
    color:white;
    margin-right:10px;
    margin-top:10px;
    font-size:14px;
}

.btn-back{
    background:#6b7280;
}

.btn-process{
    background:#3b82f6;
}

.btn-ship{
    background:#0ea5e9;
}

.btn-done{
    background:#22c55e;
}

.btn-cancel{
    background:#ef4444;
}

</style>

</head>
<body>

<div class="container">

<div class="card">

<div class="title">
    Đơn hàng #<%= hoadon.getMaHD() %>
</div>

<div class="info-row">
    <b>Mã đơn hàng</b>
    <span>#<%= hoadon.getMaHD() %></span>
</div>

<div class="info-row">
    <b>Khách hàng</b>
    <span>
        <%= customer != null ? customer.getFullName() : "N/A" %>
    </span>
</div>

<div class="info-row">
    <b>SĐT</b>
    <span>
        <%= customer != null ? customer.getSdt() : "N/A" %>
    </span>
</div>

<div class="info-row">
    <b>Email</b>
    <span>
        <%= customer != null ? customer.getEmail() : "N/A" %>
    </span>
</div>

<div class="info-row">
    <b>Địa chỉ</b>
    <span>
        <%= customer != null ? customer.getAddress() : "N/A" %>
    </span>
</div>

<div class="info-row">
    <b>Ngày thanh toán</b>
    <span>
        <%= hoadon.getNgayThanhToan() %>
    </span>
</div>

<div class="info-row">
    <b>Phương thức thanh toán</b>
    <span>
        <%= thanhtoan != null ? thanhtoan.getPhuongThuc() : "N/A" %>
    </span>
</div>

<div class="info-row">
    <b>Trạng thái thanh toán</b>
    <span>
        <%= thanhtoan != null ? thanhtoan.getTrangThai() : "N/A" %>
    </span>
</div>

<div class="info-row">
    <b>Trạng thái đơn hàng</b>

    <span>

    <% if("CHO_XAC_NHAN".equals(trangThai)){ %>
        <span class="badge wait">CHỜ XÁC NHẬN</span>

    <% } else if("DANG_XU_LY".equals(trangThai)){ %>
        <span class="badge process">ĐANG XỬ LÝ</span>

    <% } else if("DANG_GIAO".equals(trangThai)){ %>
        <span class="badge ship">ĐANG GIAO</span>

    <% } else if("DA_GIAO".equals(trangThai)){ %>
        <span class="badge done">ĐÃ GIAO</span>

    <% } else { %>
        <span class="badge cancel">ĐÃ HỦY</span>
    <% } %>

    </span>

</div>

</div>





<div class="card">

<div class="title">
Danh sách sản phẩm
</div>

<table>

<thead>
<tr>
    <th>STT</th>
    <th>Tên sản phẩm</th>
    <th>Số lượng</th>
    <th>Đơn giá</th>
    <th>Thành tiền</th>
</tr>
</thead>

<tbody>

<%
int stt = 1;

for(HoadonCT ct : items){

    Sanpham sp =
            sanphamDao.getById(
                    ct.getMaSP());
%>

<tr>

<td>
<%= stt++ %>
</td>

<td>
<%= sp != null ? sp.getTenSP() : "Sản phẩm đã xóa" %>
</td>

<td>
<%= ct.getSoLuong() %>
</td>

<td>
<%= String.format("%,.0f", ct.getDonGia()) %> đ
</td>

<td>
<%= String.format("%,.0f", ct.getTongGia()) %> đ
</td>

</tr>

<%
}
%>

</tbody>

</table>

<div class="total">
Tổng cộng:
<%= String.format("%,.0f", hoadon.getTongGia()) %> đ
</div>

</div>





<div class="card">

<a href="orders"
class="btn btn-back">

<i class="fa-solid fa-arrow-left"></i>
Quay lại

</a>

<%
if("CHO_XAC_NHAN".equals(trangThai)){
%>

<a href="orders?action=process&maHD=<%=hoadon.getMaHD()%>"
class="btn btn-process">

<i class="fa-solid fa-check"></i>
Xác nhận đơn

</a>

<a href="orders?action=cancel&maHD=<%=hoadon.getMaHD()%>"
class="btn btn-cancel"
onclick="return confirm('Hủy đơn hàng này?')">

<i class="fa-solid fa-xmark"></i>
Hủy đơn

</a>

<%
}
%>

<%
if("DANG_XU_LY".equals(trangThai)){
%>

<a href="orders?action=ship&maHD=<%=hoadon.getMaHD()%>"
class="btn btn-ship">

<i class="fa-solid fa-truck"></i>
Bắt đầu giao

</a>

<%
}
%>

<%
if("DANG_GIAO".equals(trangThai)){
%>

<a href="orders?action=done&maHD=<%=hoadon.getMaHD()%>"
class="btn btn-done">

<i class="fa-solid fa-box-open"></i>
Hoàn thành đơn

</a>

<%
}
%>

</div>

</div>

</body>
</html>