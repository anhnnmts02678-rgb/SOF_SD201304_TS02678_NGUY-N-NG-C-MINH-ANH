<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<%
if (session.getAttribute("employee") == null && session.getAttribute("admin") == null) {
    response.sendRedirect("../login.jsp");
    return;
}
request.setAttribute("activeMenu", "thongke");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Thống kê - HAKY TECH</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
<link rel="stylesheet" href="css/admin.css">
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

<style>
.cards{
    display:grid;
    grid-template-columns:repeat(auto-fit,minmax(220px,1fr));
    gap:20px;
    margin-bottom:25px;
}

.kpi-card{
    background:#fff;
    border-radius:15px;
    padding:25px;
    box-shadow:0 5px 15px rgba(0,0,0,.05);
    display:flex;
    align-items:center;
    gap:18px;
}

.kpi-card .icon{
    width:56px;
    height:56px;
    border-radius:14px;
    display:flex;
    align-items:center;
    justify-content:center;
    font-size:24px;
    color:#fff;
    flex-shrink:0;
}

.kpi-card .icon.blue{ background:#2f73ff; }
.kpi-card .icon.green{ background:#10b981; }

.kpi-card h3{
    color:#666;
    font-size:14px;
    margin-bottom:6px;
    font-weight:600;
}

.kpi-card h2{
    color:#222;
    font-size:26px;
}

canvas{
    width:100% !important;
    max-height:380px;
}

.product-img{
    width:60px;
    height:60px;
    object-fit:cover;
    border-radius:8px;
    border:1px solid #ddd;
}
</style>

</head>

<body>

<div class="admin-wrapper">

    <%@ include file="_sidebar.jsp" %>

    <div class="main-content">

        <div class="page-header">
            <div>
                <h1>Thống kê tổng quan</h1>
                <div class="welcome">Doanh thu, số lượng hóa đơn và sản phẩm trên toàn hệ thống</div>
            </div>
            <a class="btn btn-primary" href="ThongkeServlet">
                <i class="fa-solid fa-rotate"></i> Tải lại
            </a>
        </div>

        <div class="cards">

            <div class="kpi-card">
                <div class="icon blue"><i class="fa-solid fa-receipt"></i></div>
                <div>
                    <h3>Tổng hóa đơn</h3>
                    <h2>${tongHoaDon}</h2>
                </div>
            </div>

            <div class="kpi-card">
                <div class="icon green"><i class="fa-solid fa-sack-dollar"></i></div>
                <div>
                    <h3>Doanh thu</h3>
                    <h2><fmt:formatNumber value="${tongDoanhThu}" pattern="#,##0"/> đ</h2>
                </div>
            </div>

        </div>

        <div class="card">
            <h3 style="margin-bottom:20px;">Biểu đồ số hóa đơn theo tháng</h3>
            <canvas id="chart"></canvas>
        </div>

<div class="card">

    <h3 style="margin-bottom:15px;">
        Danh sách sản phẩm đã bán
    </h3>

    <c:if test="${empty sanPhamDaBan}">
        <div class="empty-state">
            <i class="fa-solid fa-box-open"
               style="font-size:34px;margin-bottom:10px;"></i>
            <p>Chưa có sản phẩm nào được bán.</p>
        </div>
    </c:if>

    <c:if test="${not empty sanPhamDaBan}">

        <table>

            <thead>
            <tr>
                <th>Mã SP</th>
                <th>Tên sản phẩm</th>
                <th>Đã bán</th>
                <th>Doanh thu</th>
            </tr>
            </thead>

            <tbody>

            <c:forEach var="sp" items="${sanPhamDaBan}">

                <tr>

                    <td>#${sp[0]}</td>

                    <td>${sp[1]}</td>

                    <td>${sp[2]}</td>

                    <td>
                        <fmt:formatNumber
                            value="${sp[3]}"
                            pattern="#,##0"/> đ
                    </td>

                </tr>

            </c:forEach>

            </tbody>

        </table>

    </c:if>

</div>
    </div>

</div>

<script>
    const labels = [];
    const data = [];

    for (let i = 1; i <= 12; i++) {
        labels.push("Tháng " + i);
        data.push(0);
    }

    <c:forEach var="item" items="${barData}">
        data[${item[0]} - 1] = ${item[1]};
    </c:forEach>

    new Chart(document.getElementById("chart"), {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [{
                label: 'Số hóa đơn',
                data: data,
                backgroundColor: '#2f73ff',
                borderRadius: 6
            }]
        },
        options: {
            plugins: { legend: { display: false } },
            scales: { y: { beginAtZero: true, ticks: { precision: 0 } } }
        }
    });
</script>

</body>
</html>
