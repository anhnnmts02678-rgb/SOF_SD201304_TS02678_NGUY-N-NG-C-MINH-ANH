<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="nhom3.Model.*"%>
<%@ page import="nhom3.Dao.*"%>

<%
String message = (String) request.getAttribute("message");
String error = (String) request.getAttribute("error");

List<GiohangCT> gioHang = (List<GiohangCT>) request.getAttribute("cartItems");

SanphamDao sanphamDao = (SanphamDao) request.getAttribute("sanphamDao");

Double tongTien = (Double) request.getAttribute("tongTien");
if (tongTien == null) tongTien = 0.0;

Integer soLuongSP = (Integer) request.getAttribute("soLuongSP");
if (soLuongSP == null) soLuongSP = 0;

request.setAttribute("activePage", "giohang");
%>
<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<title>Giỏ hàng - HAKY TECH</title>

<link rel="stylesheet"
href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
<link rel="stylesheet" href="css/site-header.css">

<style>

*{
    margin:0;
    padding:0;
    box-sizing:border-box;
    font-family:Arial,Helvetica,sans-serif;
}

body{
    background:#f5f7fa;
    color:#222;
}

/* -------- Tiêu đề trang -------- */

.page-title{
    text-align:center;
    margin:45px 0 35px;
    font-size:36px;
    color:#0d1526;
}

/* -------- Layout chính: danh sách (trái) + tổng tiền (phải) -------- */

.cart-wrap{
    max-width:1300px;
    margin:0 auto 70px;
    padding:0 20px;
    display:grid;
    grid-template-columns:1fr 380px;
    gap:28px;
    align-items:start;
}

/* -------- Thông báo -------- */

.alert{
    padding:14px 18px;
    border-radius:10px;
    margin-bottom:22px;
    font-weight:600;
    grid-column:1 / -1;
}

.alert-success{
    background:#dcfce7;
    color:#166534;
}

.alert-danger{
    background:#fee2e2;
    color:#b91c1c;
}

/* -------- Giỏ hàng trống -------- */

.empty-cart{
    grid-column:1 / -1;
    background:#fff;
    border-radius:16px;
    box-shadow:0 8px 25px rgba(0,0,0,.06);
    text-align:center;
    padding:70px 20px;
}

.empty-cart i{
    font-size:64px;
    color:#c7d2e0;
    margin-bottom:22px;
}

.empty-cart h4{
    font-size:22px;
    color:#0d1526;
    margin-bottom:10px;
}

.empty-cart p{
    color:#777;
    margin-bottom:26px;
}

/* -------- Cột trái: danh sách sản phẩm -------- */

.cart-items{
    background:#fff;
    border-radius:16px;
    box-shadow:0 8px 25px rgba(0,0,0,.06);
    overflow:hidden;
}

.cart-items-head{
    padding:22px 26px;
    border-bottom:1px solid #eef1f5;
    font-size:19px;
    font-weight:700;
    color:#0d1526;
}

.cart-row{
    display:grid;
    grid-template-columns:88px 1fr 150px 150px 40px;
    gap:18px;
    align-items:center;
    padding:20px 26px;
    border-bottom:1px solid #f0f2f5;
}

.cart-row:last-child{
    border-bottom:none;
}

.cart-row img{
    width:80px;
    height:80px;
    object-fit:contain;
    background:#fafbfc;
    border:1px solid #eef1f5;
    border-radius:10px;
    padding:6px;
}

.item-name{
    font-weight:700;
    font-size:16px;
    color:#0d1526;
    margin-bottom:6px;
}

.item-price{
    color:#888;
    font-size:14px;
}

.qty-form{
    display:flex;
    align-items:center;
    gap:8px;
}

.qty-form input[type="number"]{
    width:64px;
    height:38px;
    text-align:center;
    border:1px solid #ddd;
    border-radius:8px;
    font-size:14.5px;
}

.qty-form input[type="number"]:focus{
    outline:none;
    border-color:#2563eb;
}

.icon-btn-sm{
    width:38px;
    height:38px;
    border:none;
    border-radius:8px;
    background:#eef2f9;
    color:#2563eb;
    cursor:pointer;
    display:flex;
    align-items:center;
    justify-content:center;
    transition:.2s;
    flex-shrink:0;
}

.icon-btn-sm:hover{
    background:#2563eb;
    color:#fff;
}

.item-total{
    font-weight:800;
    font-size:16.5px;
    color:#e60023;
    white-space:nowrap;
}

.remove-btn{
    width:38px;
    height:38px;
    border-radius:8px;
    background:#fef2f2;
    color:#dc3545;
    border:none;
    display:flex;
    align-items:center;
    justify-content:center;
    text-decoration:none;
    transition:.2s;
}

.remove-btn:hover{
    background:#dc3545;
    color:#fff;
}

.continue-link{
    display:inline-flex;
    align-items:center;
    gap:8px;
    margin:22px 0 0;
    color:#2563eb;
    text-decoration:none;
    font-weight:700;
    font-size:15px;
}

.continue-link:hover{
    text-decoration:underline;
}

/* -------- Cột phải: tổng tiền / thanh toán -------- */

.summary-box{
    background:#fff;
    border-radius:16px;
    box-shadow:0 8px 25px rgba(0,0,0,.06);
    padding:26px;
    position:sticky;
    top:96px;
}

.summary-box h3{
    font-size:19px;
    color:#0d1526;
    margin-bottom:20px;
    padding-bottom:16px;
    border-bottom:1px solid #eef1f5;
}

.summary-line{
    display:flex;
    justify-content:space-between;
    align-items:center;
    margin-bottom:14px;
    color:#555;
    font-size:15px;
}

.summary-line span:last-child{
    font-weight:600;
    color:#222;
}

.summary-total{
    display:flex;
    justify-content:space-between;
    align-items:center;
    margin-top:18px;
    padding-top:18px;
    border-top:1px dashed #e2e6ec;
}

.summary-total .label{
    font-size:16px;
    font-weight:700;
    color:#0d1526;
}

.summary-total .value{
    font-size:26px;
    font-weight:800;
    color:#e60023;
}

.checkout-btn{
    display:block;
    width:100%;
    text-align:center;
    margin-top:22px;
    padding:15px;
    background:#2563eb;
    color:#fff;
    border-radius:10px;
    text-decoration:none;
    font-weight:700;
    font-size:16px;
    transition:.25s;
}

.checkout-btn:hover{
    background:#114fd3;
}

.summary-note{
    display:flex;
    align-items:center;
    gap:8px;
    margin-top:16px;
    color:#16a34a;
    font-size:13.5px;
}

</style>
</head>
<body>

<%@ include file="_header.jsp" %>

<h1 class="page-title">
    <i class="fa-solid fa-cart-shopping"></i>
    Giỏ hàng của bạn
</h1>

<div class="cart-wrap">

<% if (message != null) { %>
<div class="alert alert-success"><%=message%></div>
<% } %>

<% if (error != null) { %>
<div class="alert alert-danger"><%=error%></div>
<% } %>

<% if (gioHang == null || gioHang.isEmpty()) { %>

<div class="empty-cart">
    <i class="fa-solid fa-cart-shopping"></i>
    <h4>Giỏ hàng đang trống</h4>
    <p>Hãy thêm sản phẩm để tiếp tục mua sắm.</p>
    <a href="products" class="checkout-btn" style="display:inline-block;width:auto;padding:14px 32px;">
        Tiếp tục mua hàng
    </a>
</div>

<% } else { %>

    <div class="cart-items">

        <div class="cart-items-head">
            Sản phẩm (<%=soLuongSP%>)
        </div>

        <%
        for (GiohangCT item : gioHang) {

            Sanpham sp = sanphamDao.getById(item.getMaSP());

            if (sp == null) continue;
        %>

        <div class="cart-row">

            <img src="<%=sp.getUrlHinhAnh()%>"
                 onerror="this.src='images/no-image.png'"
                 alt="<%=sp.getTenSP()%>">

            <div>
                <div class="item-name"><%=sp.getTenSP()%></div>
                <div class="item-price"><%=String.format("%,.0f", sp.getGiaThanh())%> đ / sản phẩm</div>
            </div>

            <form class="qty-form" action="UpdateCartServlet" method="post">
                <input type="hidden" name="maGHCT" value="<%=item.getMaGHCT()%>">
                <input type="number" name="soLuong" value="<%=item.getSoLuong()%>"
                       min="1" max="<%=sp.getSoLuong()%>">
                <button type="submit" class="icon-btn-sm" title="Cập nhật số lượng">
                    <i class="fa-solid fa-rotate"></i>
                </button>
            </form>

            <div class="item-total">
                <%=String.format("%,.0f", item.getTongGia())%> đ
            </div>

            <a class="remove-btn" title="Xóa sản phẩm"
               href="RemoveCartServlet?maGHCT=<%=item.getMaGHCT()%>"
               onclick="return confirm('Bạn có chắc muốn xóa sản phẩm này?');">
                <i class="fa-solid fa-trash"></i>
            </a>

        </div>

        <% } %>

    </div>

    <a href="${pageContext.request.contextPath}/products" class="continue-link">
        <i class="fa-solid fa-arrow-left"></i>
        Tiếp tục mua hàng
    </a>

    <div class="summary-box">

        <h3>Tổng thanh toán</h3>

        <div class="summary-line">
            <span>Số lượng sản phẩm</span>
            <span><%=soLuongSP%></span>
        </div>

        <div class="summary-line">
            <span>Tạm tính</span>
            <span><%=String.format("%,.0f", tongTien)%> đ</span>
        </div>

        <div class="summary-total">
            <span class="label">Tổng cộng</span>
            <span class="value"><%=String.format("%,.0f", tongTien)%> đ</span>
        </div>

        <a href="CheckoutServlet" class="checkout-btn">
            Tiến hành đặt hàng
        </a>

        <div class="summary-note">
            <i class="fa-solid fa-shield-halved"></i>
            Bảo hành chính hãng - Đổi trả trong 7 ngày
        </div>

    </div>

<% } %>

</div>

<%@ include file="_footer.jsp" %>

</body>
</html>
