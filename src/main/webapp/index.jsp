<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>HAKY TECH</title>
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

/*================ BUTTON (dùng cho banner) =================*/

.btn{
    background:#2563eb;
    color:white;
    padding:10px 22px;
    border-radius:8px;
    text-decoration:none;
    font-weight:bold;
    transition:.3s;
}

.btn:hover{
    background:#114fd3;
}

/*================ BANNER =================*/

.banner{
    width:1200px;
    margin:35px auto;
    background:linear-gradient(90deg,#0d1526,#2563eb);
    border-radius:18px;
    display:flex;
    justify-content:space-between;
    align-items:center;
    padding:60px;
    color:white;
}

.banner-left{
    width:50%;
}

.banner-left h1{
    font-size:56px;
    margin-bottom:15px;
}

.banner-left h2{
    font-size:34px;
    margin-bottom:20px;
}

.banner-left p{
    line-height:32px;
    color:#e4e4e4;
    margin-bottom:35px;
}

.banner img{
    width:430px;
}

/*================ TITLE =================*/

.title{
    text-align:center;
    margin:60px 0 40px;
    font-size:34px;
    color:#0d1526;
}

/*================ CATEGORY =================*/

.category{
    width:1200px;
    margin:auto;
    display:grid;
    grid-template-columns:repeat(4,1fr);
    gap:22px;
}

.card{
    background:white;
    border-radius:15px;
    padding:35px;
    text-align:center;
    cursor:pointer;
    transition:.35s;
    border:1px solid #eee;
}

.card:hover{
    transform:translateY(-8px);
    box-shadow:0 10px 30px rgba(0,0,0,.08);
}

.card i{
    font-size:42px;
    color:#2563eb;
    margin-bottom:18px;
}

.card h3{
    font-size:20px;
}

/*================ PRODUCTS =================*/

.products{
    width:1200px;
    margin:40px auto;
    display:grid;
    grid-template-columns:repeat(4,1fr);
    gap:25px;
}

.product{
    background:white;
    border-radius:15px;
    overflow:hidden;
    border:1px solid #eee;
    transition:.35s;
}

.product:hover{
    transform:translateY(-6px);
    box-shadow:0 12px 30px rgba(0,0,0,.08);
}

.product img{
    width:100%;
    height:220px;
    object-fit:contain;
    padding:20px;
}

.product h3{
    font-size:18px;
    padding:0 18px;
    height:55px;
}

.price{
    color:#e60023;
    font-size:24px;
    font-weight:bold;
    padding:15px 18px;
}

.buy{
    display:block;
    width:90%;
    margin:15px auto 20px;
    text-align:center;
    text-decoration:none;
    background:#2563eb;
    color:white;
    padding:13px;
    border-radius:8px;
    font-weight:bold;
    transition:.3s;
}

.buy:hover{
    background:#114fd3;
}

.category .card{
    text-decoration:none;
    color:#222;
    display:flex;
    flex-direction:column;
    align-items:center;
    justify-content:center;
}

.category .card:hover{
    color:#2563eb;
}

@media(max-width:1200px){

.banner,
.products,
.category{
    width:95%;
}

}
</style>

</head>

<body>

<% request.setAttribute("activePage", "index"); %>
<%@ include file="_header.jsp" %>

<section class="banner">
<div class="banner-left">
<div class="logo">
    <a href="index.jsp" style="color:white;text-decoration:none;">
        HAKY TECH
    </a>
</div>
<h2>Linh kiện PC chính hãng</h2>
<p>
Hiệu năng mạnh mẽ<br>
Giá tốt mỗi ngày<br>
Bảo hành uy tín
</p>
<a class="btn" href="products">
MUA NGAY
</a>
</div>
<img src="images/banner.png">
</section>
<h2 class="title">
Danh mục nổi bật
</h2>

<div class="category">

    <a class="card" href="products?maDM=1">
        <i class="fa-solid fa-microchip"></i>
        <h3>CPU</h3>
    </a>

    <a class="card" href="products?maDM=2">
        <i class="fa-solid fa-memory"></i>
        <h3>RAM</h3>
    </a>

    <a class="card" href="products?maDM=3">
        <i class="fa-solid fa-hard-drive"></i>
        <h3>SSD</h3>
    </a>

    <a class="card" href="products?maDM=4">
        <i class="fa-solid fa-tv"></i>
        <h3>Màn hình</h3>
    </a>

    <a class="card" href="products?maDM=5">
        <i class="fa-solid fa-gamepad"></i>
        <h3>VGA</h3>
    </a>

    <a class="card" href="products?maDM=6">
        <i class="fa-solid fa-server"></i>
        <h3>Mainboard</h3>
    </a>

    <a class="card" href="products?maDM=7">
        <i class="fa-solid fa-plug"></i>
        <h3>Nguồn</h3>
    </a>

    <a class="card" href="products?maDM=8">
        <i class="fa-solid fa-desktop"></i>
        <h3>Case PC</h3>
    </a>

</div>
<h2 class="title">
    Sản phẩm nổi bật
</h2>

<div class="products">

<c:forEach items="${products}" var="sp">

    <div class="product">

        <a href="product-detail?id=${sp.maSP}">
            <img src="${sp.urlHinhAnh}"
                 onerror="this.src='images/no-image.png'">
        </a>

        <h3>${sp.tenSP}</h3>

        <div class="price">
            <fmt:formatNumber value="${sp.giaThanh}"
                              pattern="#,##0"/>
            đ
        </div>

        <a class="buy"
           href="product-detail?id=${sp.maSP}">
            Xem chi tiết
        </a>

    </div>

</c:forEach>

</div>
<%@ include file="_footer.jsp" %>
</body>
</html>