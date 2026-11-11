<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<title>Sản phẩm</title>

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

/* Title */

.title{
    text-align:center;
    margin:40px 0;
    font-size:50px;
    color:#0d1526;
}

/* Content */

.content{
    width:95%;
    max-width:1500px;
    margin:auto;
}

/* Search */

.search{
    display:flex;
    background:white;
    border-radius:12px;
    overflow:hidden;
    box-shadow:0 2px 10px rgba(0,0,0,.08);
    margin-bottom:30px;
}

.search input{
    flex:1;
    border:none;
    padding:18px;
    font-size:16px;
    outline:none;
}

.search button{
    width:70px;
    border:none;
    background:#2563eb;
    color:white;
    cursor:pointer;
    font-size:22px;
}

.search button:hover{
    background:#1d4ed8;
}

.category-bar{
    display:flex;
    flex-wrap:wrap;
    gap:12px;
    margin-bottom:30px;
}

.category-bar a{
    text-decoration:none;
    background:white;
    color:#222;
    padding:10px 18px;
    border-radius:30px;
    box-shadow:0 2px 8px rgba(0,0,0,.08);
    transition:.3s;
    font-weight:bold;
}

.category-bar a:hover{
    background:#2563eb;
    color:white;
}

.category-bar a.active{
    background:#2563eb;
    color:white;
}
/* Product Grid */

.products{

    display:grid;

    grid-template-columns:repeat(auto-fill,minmax(280px,1fr));

    gap:25px;

}

/* Card */

.product{

    background:white;

    border-radius:15px;

    overflow:hidden;

    box-shadow:0 5px 18px rgba(0,0,0,.08);

    transition:.3s;

}

.product:hover{

    transform:translateY(-8px);

    box-shadow:0 10px 30px rgba(0,0,0,.15);

}

.product img{

    width:100%;

    height:260px;

    object-fit:contain;

    background:#fafafa;

    padding:20px;

}

.product-info{

    padding:18px;

    display:flex;

    flex-direction:column;

}

.product-info h2{

    font-size:24px;

    height:65px;

    overflow:hidden;

}

.desc{
    color:#666;

    height:45px;

    overflow:hidden;

    margin:15px 0;

}
.price{
    color:red;
    font-size:34px;
    font-weight:bold;
}
.action{
    display:flex;
    gap:10px;
    margin-top:20px;
}
.btn{
    flex:1;
    text-align:center;
    padding:12px;
    border-radius:8px;
    color:white;
    text-decoration:none;
    font-weight:bold;
}
.detail{
    background:#2563eb;
}
.detail:hover{
    background:#1d4ed8;
}
.cart{
    background:#10b981;
}
.cart:hover{
    background:#059669;
}

/* -------- Đánh giá sản phẩm -------- */

.rating-line{
    display:flex;
    align-items:center;
    gap:8px;
    margin-top:8px;
    font-size:14px;
    color:#666;
}

.rating-line .stars i{
    color:#f5a623;
    font-size:14px;
}

.rating-line .stars i.empty{
    color:#e2e6ec;
}

.review-btn{
    width:100%;
    margin-top:10px;
    padding:10px;
    border-radius:8px;
    border:1.5px solid #2563eb;
    background:#fff;
    color:#2563eb;
    font-weight:700;
    cursor:pointer;
    transition:.2s;
    font-size:14px;
}

.review-btn:hover{
    background:#2563eb;
    color:#fff;
}

/* -------- Modal đánh giá -------- */

.review-modal-overlay{
    display:none;
    position:fixed;
    inset:0;
    background:rgba(13,21,38,.6);
    z-index:1000;
    align-items:center;
    justify-content:center;
    padding:20px;
}

.review-modal-overlay.show{
    display:flex;
}

.review-modal{
    background:#fff;
    border-radius:16px;
    width:100%;
    max-width:560px;
    max-height:85vh;
    overflow:hidden;
    display:flex;
    flex-direction:column;
}

.review-modal-head{
    display:flex;
    justify-content:space-between;
    align-items:center;
    padding:20px 24px;
    border-bottom:1px solid #eef1f5;
}

.review-modal-head h3{
    font-size:18px;
    color:#0d1526;
}

.review-modal-head button{
    border:none;
    background:#f1f4f9;
    width:34px;
    height:34px;
    border-radius:50%;
    cursor:pointer;
    font-size:15px;
    color:#555;
}

.review-modal-body{
    padding:20px 24px;
    overflow-y:auto;
}

.review-item{
    padding:14px 0;
    border-bottom:1px solid #f0f2f5;
}

.review-item:last-child{
    border-bottom:none;
}

.review-item .rv-head{
    display:flex;
    justify-content:space-between;
    align-items:center;
    margin-bottom:6px;
}

.review-item .rv-name{
    font-weight:700;
    color:#0d1526;
    font-size:14.5px;
}

.review-item .rv-date{
    font-size:12px;
    color:#999;
}

.review-item .rv-content{
    color:#444;
    font-size:14px;
    line-height:1.5;
}

.review-item img{
    width:70px;
    height:70px;
    object-fit:cover;
    border-radius:8px;
    margin-top:8px;
    border:1px solid #eee;
}

.review-empty{
    text-align:center;
    color:#888;
    padding:30px 0;
    font-size:14px;
}

.review-lock{
    margin-top:14px;
    background:#fff7ed;
    border:1px solid #fed7aa;
    color:#9a5b13;
    padding:14px;
    border-radius:10px;
    font-size:13.5px;
    display:flex;
    gap:10px;
    align-items:flex-start;
}

.review-lock i{
    margin-top:2px;
}

.review-lock a{
    color:#2563eb;
    font-weight:700;
    text-decoration:underline;
}

.review-form{
    margin-top:16px;
    padding-top:16px;
    border-top:1px solid #eef1f5;
}

.review-form .star-input{
    display:flex;
    gap:4px;
    margin-bottom:12px;
    font-size:26px;
}

.review-form .star-input label{
    cursor:pointer;
    color:#e2e6ec;
    transition:.15s;
}

.review-form .star-input input{
    display:none;
}

.review-form .star-input label.active{
    color:#f5a623;
}

.review-form textarea{
    width:100%;
    border:1px solid #ddd;
    border-radius:8px;
    padding:10px 12px;
    font-size:14px;
    resize:vertical;
    min-height:70px;
    font-family:inherit;
}

.review-form input[type="url"]{
    width:100%;
    border:1px solid #ddd;
    border-radius:8px;
    padding:10px 12px;
    font-size:14px;
    margin-top:10px;
}

.review-form button{
    margin-top:12px;
    background:#2563eb;
    color:#fff;
    border:none;
    padding:11px 22px;
    border-radius:8px;
    font-weight:700;
    cursor:pointer;
}

.review-form button:hover{
    background:#1d4ed8;
}
</style>

<script>
/* Hien/an modal danh gia */
function openReviewModal(id){
    var el = document.getElementById('review-modal-' + id);
    if (el) el.classList.add('show');
    document.body.style.overflow = 'hidden';
}
function closeReviewModal(id){
    var el = document.getElementById('review-modal-' + id);
    if (el) el.classList.remove('show');
    document.body.style.overflow = '';
}
/* To vang cac ngoi sao tu 1 den gia tri duoc chon */
function updateStars(id, value){
    var container = document.getElementById('star-input-' + id);
    if (!container) return;
    var labels = container.querySelectorAll('label');
    labels.forEach(function (lb) {
        var v = parseInt(lb.getAttribute('data-val'), 10);
        if (v <= value) lb.classList.add('active');
        else lb.classList.remove('active');
    });
}
document.addEventListener('DOMContentLoaded', function () {

    // Khoi tao mau sao ban dau cho cac danh gia da co san (khi sua lai)
    document.querySelectorAll('.star-input').forEach(function (container) {
        var checked = container.querySelector('input:checked');
        if (checked) {
            var id = container.id.replace('star-input-', '');
            updateStars(id, parseInt(checked.value, 10));
        }
    });

    // Neu URL co #review-123 (sau khi gui danh gia xong) thi tu mo lai modal do
    if (location.hash && location.hash.indexOf('#review-') === 0) {
        var id = location.hash.replace('#review-', '');
        openReviewModal(id);
    }
});
</script>

</head>

<body>

<% request.setAttribute("activePage", "products"); %>
<%@ include file="_header.jsp" %>

<h1 class="title">

TẤT CẢ SẢN PHẨM

</h1>

<c:set var="pageQuery" value="${not empty param.keyword ? '?keyword='.concat(param.keyword) : (not empty param.maDM ? '?maDM='.concat(param.maDM) : '')}" />

<div class="content">

    <c:if test="${not empty param.message}">
        <div class="alert alert-success" style="background:#dcfce7;color:#166534;padding:14px 18px;border-radius:10px;margin-bottom:20px;font-weight:600;">
            ${param.message}
        </div>
    </c:if>

    <c:if test="${not empty param.error}">
        <div class="alert alert-danger" style="background:#fee2e2;color:#b91c1c;padding:14px 18px;border-radius:10px;margin-bottom:20px;font-weight:600;">
            ${param.error}
        </div>
    </c:if>


    <form class="search" action="products" method="get">
        <input type="text"
               name="keyword"
               value="${param.keyword}"
               placeholder="Tìm kiếm sản phẩm...">

        <button type="submit">
            <i class="fa-solid fa-search"></i>
        </button>
    </form>

<div class="category-bar">

    <a href="products"
       class="${empty param.maDM ? 'active' : ''}">
        Tất cả
    </a>

    <a href="products?maDM=1"
       class="${param.maDM=='1'?'active':''}">
        CPU
    </a>

    <a href="products?maDM=2"
       class="${param.maDM=='2'?'active':''}">
        Maniboard
    </a>

    <a href="products?maDM=3"
       class="${param.maDM=='3'?'active':''}">
        Ram
    </a>

    <a href="products?maDM=4"
       class="${param.maDM=='4'?'active':''}">
        SSD
    </a>

    <a href="products?maDM=5"
       class="${param.maDM=='5'?'active':''}">
        HDD
    </a>

    <a href="products?maDM=6"
       class="${param.maDM=='6'?'active':''}">
        VGA
    </a>

    <a href="products?maDM=7"
       class="${param.maDM=='7'?'active':''}">
        Nguồn
    </a>

    <a href="products?maDM=8"
       class="${param.maDM=='8'?'active':''}">
        Case PC
    </a>

</div>

    <div class="products">

<c:forEach items="${products}" var="sp">

<c:if test="${sp.trangThai}">

<div class="product">

<a href="product-detail?id=${sp.maSP}">

<c:choose>

<c:when test="${not empty sp.urlHinhAnh}">

<img src="${sp.urlHinhAnh}"
     alt="${sp.tenSP}"
     onerror="this.src='images/no-image.png'">

</c:when>

<c:otherwise>

<img src="images/no-image.png">

</c:otherwise>

</c:choose>

</a>

<div class="product-info">

<h2>${sp.tenSP}</h2>

<p class="desc">

${sp.moTa}

</p>

<div class="price">

<fmt:formatNumber
value="${sp.giaThanh}"
pattern="#,##0"/>

đ

</div>

<div class="rating-line">
    <span class="stars">
        <c:forEach begin="1" end="5" var="i">
            <c:choose>
                <c:when test="${i <= avgMap[sp.maSP]}"><i class="fa-solid fa-star"></i></c:when>
                <c:otherwise><i class="fa-solid fa-star empty"></i></c:otherwise>
            </c:choose>
        </c:forEach>
    </span>
    <span>
        <c:if test="${avgMap[sp.maSP] > 0}"><fmt:formatNumber value="${avgMap[sp.maSP]}" pattern="#.#"/> · </c:if>
        ${reviewsMap[sp.maSP].size()} đánh giá
    </span>
</div>

<div class="action">

<a class="btn detail"
href="product-detail?id=${sp.maSP}">

Chi tiết

</a>

<form action="AddToCartServlet" method="post">

    <input type="hidden"
           name="maSP"
           value="${sp.maSP}">

    <input type="hidden"
           name="soLuong"
           value="1">

    <button class="btn cart" type="submit">
        <i class="fa-solid fa-cart-shopping"></i>
    </button>

</form>

</div>

<button type="button" class="review-btn" onclick="openReviewModal(${sp.maSP})">
    <i class="fa-solid fa-star"></i> Xem &amp; viết đánh giá
</button>

</div>

</div>

<!-- Modal đánh giá của sản phẩm ${sp.maSP} -->
<div class="review-modal-overlay" id="review-modal-${sp.maSP}" onclick="if(event.target===this) closeReviewModal(${sp.maSP})">
<div class="review-modal">

    <div class="review-modal-head">
        <h3>Đánh giá: ${sp.tenSP}</h3>
        <button type="button" onclick="closeReviewModal(${sp.maSP})"><i class="fa-solid fa-xmark"></i></button>
    </div>

    <div class="review-modal-body">

        <c:choose>
            <c:when test="${empty reviewsMap[sp.maSP]}">
                <div class="review-empty">Sản phẩm này chưa có đánh giá nào.</div>
            </c:when>
            <c:otherwise>
                <c:forEach items="${reviewsMap[sp.maSP]}" var="dg">
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
                <div class="review-lock">
                    <i class="fa-solid fa-lock"></i>
                    <span>Bạn chỉ có thể xem đánh giá. Vui lòng <a href="${pageContext.request.contextPath}/login.jsp">đăng nhập</a> và mua sản phẩm để viết đánh giá.</span>
                </div>
            </c:when>

            <%-- Da dang nhap nhung chua mua san pham nay --%>
            <c:when test="${!purchasedSet.contains(sp.maSP)}">
                <div class="review-lock">
                    <i class="fa-solid fa-lock"></i>
                    <span>Bạn cần <b>mua sản phẩm này</b> trước khi có thể viết đánh giá. Hiện tại bạn chỉ được xem.</span>
                </div>
            </c:when>

            <%-- Da mua -> duoc danh gia --%>
            <c:otherwise>
                <form class="review-form" action="${pageContext.request.contextPath}/DanhgiaServlet" method="post">
                    <input type="hidden" name="maSP" value="${sp.maSP}">
                    <input type="hidden" name="redirectTo" value="${pageContext.request.contextPath}/products${pageQuery}#review-${sp.maSP}">

                    <label style="font-weight:700;font-size:14px;color:#0d1526;">
                        ${not empty myReviewMap[sp.maSP] ? 'Cập nhật đánh giá của bạn' : 'Viết đánh giá của bạn'}
                    </label>

                    <div class="star-input" id="star-input-${sp.maSP}">
                        <c:forEach begin="1" end="5" var="i">
                            <input type="radio" name="soSao" id="star-${sp.maSP}-${i}" value="${i}"
                                   ${not empty myReviewMap[sp.maSP] && myReviewMap[sp.maSP].soSao == i ? 'checked' : ''}
                                   onchange="updateStars(${sp.maSP}, ${i})">
                            <label for="star-${sp.maSP}-${i}" data-val="${i}">★</label>
                        </c:forEach>
                    </div>

                    <textarea name="noiDungDanhGia"
				          placeholder="Chia sẻ cảm nhận của bạn về sản phẩm..."
				          required>${not empty myReviewMap[sp.maSP] ? myReviewMap[sp.maSP].noiDungDanhGia : ''}</textarea>

                    <input type="url" name="anh" placeholder="Link ảnh minh họa (không bắt buộc)" value="${not empty myReviewMap[sp.maSP] ? myReviewMap[sp.maSP].anh : ''}">

                    <button type="submit">
                        <i class="fa-solid fa-paper-plane"></i>
                        ${not empty myReviewMap[sp.maSP] ? 'Cập nhật đánh giá' : 'Gửi đánh giá'}
                    </button>
                </form>
            </c:otherwise>

        </c:choose>

    </div>

</div>
</div>

</c:if>

</c:forEach>

</div>

</div>
<%@ include file="_footer.jsp" %>
</body>
</html>