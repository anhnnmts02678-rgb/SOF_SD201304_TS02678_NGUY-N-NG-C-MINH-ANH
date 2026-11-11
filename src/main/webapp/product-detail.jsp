<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt"%>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<title>Chi tiết sản phẩm</title>

<link rel="stylesheet"
href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

<link rel="stylesheet" href="css/site-header.css">

<style>

*{
margin:0;
padding:0;
box-sizing:border-box;
font-family:Arial,sans-serif;
}

body{
background:#f5f5f5;
}

/* CONTAINER */

.container{

width:95%;
max-width:1300px;
margin:40px auto;
display:flex;
gap:35px;
align-items:flex-start;

}
/* IMAGE */

.left{

width:40%;

}

.left img{

width:100%;
height:500px;
background:white;
padding:20px;
object-fit:contain;
border-radius:15px;
box-shadow:0 3px 10px rgba(0,0,0,.08);

}

/* INFO */

.right{

flex:1;
background:white;
padding:35px;
border-radius:15px;
box-shadow:0 3px 10px rgba(0,0,0,.08);

}

.right h1{

font-size:38px;
margin-bottom:15px;

}

.price{

font-size:42px;
font-weight:bold;
color:#ff0000;
margin:25px 0;

}

.desc{

margin-top:10px;
line-height:1.7;
color:#555;

}

.stock{

margin:20px 0;
font-size:18px;
font-weight:bold;

}

.stock span{

color:#16a34a;

}

/* BUTTON */

.btn{

display:inline-flex;

align-items:center;

justify-content:center;

gap:10px;

padding:15px 35px;

font-size:17px;

border-radius:10px;

color:#fff;

text-decoration:none;

font-weight:bold;

transition:.25s;

}

.cart{

background:#2563eb;

}

.cart:hover{

background:#1d4ed8;

}

.buy{

background:#10b981;

margin-left:12px;

}

.buy:hover{

background:#059669;

}

/* TABLE */

.spec{

margin-top:35px;

width:100%;

border-collapse:collapse;

}

.spec td{

padding:16px;

border-bottom:1px solid #eee;

}

.spec td:first-child{

font-weight:bold;

width:220px;

background:#fafafa;

}

</style>

</head>

<body>

<% request.setAttribute("activePage", "products"); %>
<%@ include file="_header.jsp" %>

<div class="container">

<div class="left">

<c:choose>

<c:when test="${not empty product.urlHinhAnh}">

<img src="${product.urlHinhAnh}"
     alt="${product.tenSP}"
     onerror="this.src='images/no-image.png'">

</c:when>

<c:otherwise>

<img src="images/no-image.png">

</c:otherwise>

</c:choose>


</div>

<div class="right">

<h1>

${product.tenSP}

</h1>

<c:if test="${not empty param.error}">
    <div style="
        margin:15px 0;
        padding:12px;
        background:#fee2e2;
        color:#b91c1c;
        border-radius:8px;">
        ${param.error}
    </div>
</c:if>

<c:if test="${not empty param.message}">
    <div style="
        margin:15px 0;
        padding:12px;
        background:#dcfce7;
        color:#166534;
        border-radius:8px;">
        ${param.message}
    </div>
</c:if>

<div class="price">

    <fmt:formatNumber
        value="${product.giaThanh}"
        pattern="#,##0"/>

    đ

</div>

<h3>Mô tả</h3>

<p class="desc">

${product.moTa}

</p>

<div class="stock">

<c:choose>

<c:when test="${product.soLuong>0}">

Tình trạng :

<span>

Còn ${product.soLuong} sản phẩm

</span>

</c:when>

<c:otherwise>

<span style="color:red">

Hết hàng

</span>

</c:otherwise>

</c:choose>

</div>

<div style="margin-top:30px;">

<form action="AddToCartServlet" method="post">

    <input type="hidden"
           name="maSP"
           value="${product.maSP}">

    <input type="hidden"
           name="soLuong"
           value="1">

    <button class="btn cart" type="submit">
        <i class="fa-solid fa-cart-shopping"></i>
        Thêm giỏ hàng
    </button>

</form>

<form action="${pageContext.request.contextPath}/CheckoutServlet" method="post">

    <input type="hidden"
           name="maSP"
           value="${product.maSP}">

    <input type="hidden"
           name="soLuong"
           value="1">

    <input type="hidden"
           name="action"
           value="buyNow">

    <button class="btn buy" type="submit">
        <i class="fa-solid fa-bolt"></i>
        Mua ngay
    </button>

</form>

</div>

<table class="spec">

<tr>

<td>Mã sản phẩm</td>

<td>${product.maSP}</td>

</tr>

<tr>

<td>Mã danh mục</td>

<td>${product.maDM}</td>

</tr>

<tr>

<td>Xuất xứ</td>

<td>${product.nguonGoc}</td>

</tr>

<tr>

<td>Số lượng</td>

<td>${product.soLuong}</td>

</tr>

<tr>

<td>Mã khuyến mãi</td>

<td>

${product.maKM}

</td>

</tr>

</table>

<!-- ============================= -->
<!-- ĐÁNH GIÁ SẢN PHẨM -->
<!-- ============================= -->

<div class="review-section">

    <h2>Đánh giá sản phẩm</h2>

    <c:choose>

        <c:when test="${empty reviews}">
            <p>Sản phẩm này chưa có đánh giá nào.</p>
        </c:when>

        <c:otherwise>
            <c:forEach items="${reviews}" var="dg">
                <div class="review-item">
                    <div class="rv-head">
                        <span class="rv-name">${dg.tenKhachHang}</span>
                        <span class="rv-date"><fmt:formatDate value="${dg.thoiGian}" pattern="dd/MM/yyyy"/></span>
                    </div>
                    <span class="stars">
                        <c:forEach begin="1" end="5" var="i">
                            <c:choose>
                                <c:when test="${i <= dg.soSao}"><i class="fa-solid fa-star"></i></c:when>
                                <c:otherwise><i class="fa-solid fa-star empty"></i></c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </span>
                    <div class="rv-content">${dg.noiDungDanhGia}</div>
                    <c:if test="${not empty dg.anh}">
                        <img src="${dg.anh}" onerror="this.style.display='none'">
                    </c:if>
                </div>
            </c:forEach>
        </c:otherwise>

    </c:choose>

    <c:choose>

        <%-- Chua dang nhap --%>
        <c:when test="${empty sessionScope.user}">
            <p>
                Bạn chỉ có thể xem đánh giá. Vui lòng
                <a href="${pageContext.request.contextPath}/login.jsp">đăng nhập</a>
                và mua sản phẩm để viết đánh giá.
            </p>
        </c:when>

        <%-- Da dang nhap nhung chua mua & nhan san pham nay --%>
        <c:when test="${!daMua}">
            <p>
                Bạn cần <b>mua sản phẩm này và xác nhận đã nhận hàng</b>
                trước khi có thể viết đánh giá. Hiện tại bạn chỉ được xem.
            </p>
        </c:when>

        <%-- Da mua & da nhan hang -> duoc danh gia --%>
        <c:otherwise>

            <form action="${pageContext.request.contextPath}/DanhgiaServlet"
                  method="post">

                <input type="hidden"
                       name="maSP"
                       value="${product.maSP}">

                <div class="review-stars">

                    <label>Đánh giá:</label>

                    <select name="soSao" required>

                        <option value="">-- Chọn số sao --</option>

                        <option value="5" ${not empty myReview && myReview.soSao == 5 ? 'selected' : ''}>★★★★★ - 5 sao</option>
                        <option value="4" ${not empty myReview && myReview.soSao == 4 ? 'selected' : ''}>★★★★ - 4 sao</option>
                        <option value="3" ${not empty myReview && myReview.soSao == 3 ? 'selected' : ''}>★★★ - 3 sao</option>
                        <option value="2" ${not empty myReview && myReview.soSao == 2 ? 'selected' : ''}>★★ - 2 sao</option>
                        <option value="1" ${not empty myReview && myReview.soSao == 1 ? 'selected' : ''}>★ - 1 sao</option>

                    </select>

                </div>

                <div class="review-content">

                    <label>Nội dung đánh giá:</label>

                    <textarea name="noiDungDanhGia"
                              rows="5"
                              required
                              placeholder="Nhập đánh giá của bạn...">${not empty myReview ? myReview.noiDungDanhGia : ''}</textarea>

                </div>

                <div class="review-image">

                    <label>Ảnh sản phẩm:</label>

                    <input type="text"
                           name="anh"
                           value="${not empty myReview ? myReview.anh : ''}"
                           placeholder="URL hình ảnh (nếu có)">

                </div>

                <button type="submit">
                    <i class="fa-solid fa-star"></i>
                    ${not empty myReview ? 'Cập nhật đánh giá' : 'Gửi đánh giá'}
                </button>

            </form>

        </c:otherwise>

    </c:choose>

</div>

</div>

</div>

<%@ include file="_footer.jsp" %>

</body>

</html>