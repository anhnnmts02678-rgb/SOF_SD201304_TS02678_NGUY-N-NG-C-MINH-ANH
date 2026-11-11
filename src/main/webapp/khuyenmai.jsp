<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List"%>
<%@ page import="nhom3.Model.Khuyenmai"%>

<%
List<Khuyenmai> list = (List<Khuyenmai>) request.getAttribute("kmList");
request.setAttribute("activePage", "khuyenmai");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Khuyến mãi - HAKY TECH</title>

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

.title{
    text-align:center;
    color:#0d1526;
    margin:45px 0 40px;
    font-size:38px;
}

.container{
    width:95%;
    max-width:1200px;
    margin:0 auto 60px;
}

.empty{
    background:white;
    border-radius:18px;
    padding:80px 20px;
    text-align:center;
    box-shadow:0 5px 18px rgba(0,0,0,.08);
}

.empty h2{
    color:#0d1526;
    font-size:22px;
}

.list{
    display:grid;
    grid-template-columns:repeat(auto-fill,minmax(340px,1fr));
    gap:25px;
}

.card{
    background:white;
    border-radius:18px;
    overflow:hidden;
    box-shadow:0 8px 25px rgba(0,0,0,.08);
    transition:.3s;
}

.card:hover{
    transform:translateY(-8px);
    box-shadow:0 15px 35px rgba(0,0,0,.15);
}

.top{
    background:linear-gradient(135deg,#2563eb,#3b82f6);
    padding:22px;
    color:white;
}

.top h2{
    font-size:22px;
}

.content{
    padding:25px;
}

.percent{
    font-size:44px;
    font-weight:bold;
    color:#e60023;
    text-align:center;
    margin:10px 0 20px;
}

.desc{
    color:#555;
    line-height:26px;
    min-height:78px;
}

.badge{
    display:inline-block;
    margin-top:18px;
    background:#10b981;
    color:white;
    padding:8px 18px;
    border-radius:30px;
    font-size:14px;
}

.date{
    margin-top:20px;
    padding-top:15px;
    border-top:1px solid #eee;
    color:#666;
}

.date p{
    margin:4px 0;
}

</style>

</head>
<body>

<%@ include file="_header.jsp" %>

<h1 class="title">
🎉 KHUYẾN MÃI HOT
</h1>

<div class="container">

<%
if (list == null || list.isEmpty()) {
%>

<div class="empty">
    <i class="fa-solid fa-tags"
       style="font-size:60px;color:#2563eb;margin-bottom:20px;"></i>

    <h2>Hiện chưa có chương trình khuyến mãi.</h2>

    <p style="margin-top:15px;color:#777;">
        Hãy quay lại sau để không bỏ lỡ những ưu đãi hấp dẫn.
    </p>

</div>

<%
} else {
%>

<div class="list">

<%
for (Khuyenmai km : list) {
%>

<div class="card">

    <div class="top">
        <h2>
            <i class="fa-solid fa-bolt"></i>
            <%=km.getLoaiKM()%>
        </h2>
    </div>

    <div class="content">

        <div class="percent">
            GIẢM <%=String.format("%,.0f", km.getPhanTramGiam())%>%
        </div>

        <div class="desc">
            <%=km.getMoTa()%>
        </div>

        <div class="badge">
            <i class="fa-solid fa-circle-check"></i>
            Đang áp dụng
        </div>

        <div class="date">
            <p>
                <i class="fa-solid fa-calendar-days"></i>
                <b>Bắt đầu:</b> <%=km.getNgayBatDau()%>
            </p>
            <p>
                <i class="fa-solid fa-calendar-xmark"></i>
                <b>Kết thúc:</b> <%=km.getNgayKetThuc()%>
            </p>
        </div>

    </div>

</div>

<%
}
%>

</div>

<%
}
%>

</div>

<%@ include file="_footer.jsp" %>

</body>
</html>
