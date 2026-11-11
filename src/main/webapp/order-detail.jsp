<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="nhom3.Model.*" %>
<%@ page import="nhom3.Dao.SanphamDao" %>

<%
Hoadon hoadon = (Hoadon) request.getAttribute("hoadon");
List<HoadonCT> items = (List<HoadonCT>) request.getAttribute("items");
Thanhtoan thanhtoan = (Thanhtoan) request.getAttribute("thanhtoan");
SanphamDao sanphamDao = (SanphamDao) request.getAttribute("sanphamDao");

Boolean coTheHuy = (Boolean) request.getAttribute("coTheHuy");
if(coTheHuy == null){
    coTheHuy = false;
}

Long deadlineMillis = (Long) request.getAttribute("deadlineMillis");
if(deadlineMillis == null){
    deadlineMillis = 0L;
}

String message = (String) request.getAttribute("message");
String error = (String) request.getAttribute("error");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Chi Tiết Đơn Hàng</title>

<style>

*{
    margin:0;
    padding:0;
    box-sizing:border-box;
    font-family:Arial,sans-serif;
}

body{
    background:#f5f7fa;
}

.header{
    background:#0d1526;
    color:white;
    padding:20px;
    text-align:center;
    font-size:28px;
    font-weight:bold;
}

.container{
    width:1100px;
    margin:30px auto;
}

.card{
    background:white;
    border-radius:15px;
    padding:25px;
    box-shadow:0 5px 20px rgba(0,0,0,.1);
    margin-bottom:20px;
}

.title{
    font-size:28px;
    color:#0d1526;
    margin-bottom:20px;
}

.info-row{
    display:flex;
    justify-content:space-between;
    margin-bottom:12px;
    font-size:16px;
}

.status{
    padding:6px 12px;
    border-radius:20px;
    color:white;
    font-weight:bold;
}

.success{
    background:#16a34a;
}

.pending{
    background:#f59e0b;
}

.cancel{
    background:#dc2626;
}

table{
    width:100%;
    border-collapse:collapse;
}

table th{
    background:#0d1526;
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
    margin-top:20px;
    font-size:24px;
    color:red;
	font-weight:bold;
}

.btn{
    display:inline-block;
    padding:12px 25px;
    border:none;
    border-radius:8px;
    cursor:pointer;
    text-decoration:none;
    font-size:15px;
}

.btn-back{
    background:#2563eb;
    color:white;
}

.btn-cancel{
    background:#dc2626;
    color:white;
}

.message{
    background:#dcfce7;
    color:#166534;
    padding:12px;
    border-radius:8px;
    margin-bottom:20px;
}

.error{
    background:#fee2e2;
    color:#991b1b;
    padding:12px;
    border-radius:8px;
    margin-bottom:20px;
}

.countdown{
    background:#fff7ed;
    color:#9a3412;
    border:1px solid #fdba74;
    padding:12px 16px;
    border-radius:8px;
    margin-top:10px;
    font-size:14px;
}

.countdown b{
    font-size:16px;
}

</style>
</head>
<body>

<div class="header">
    HAKY TECH
</div>

<div class="container">

    <% if(message != null){ %>
        <div class="message">
            <%= message %>
        </div>
    <% } %>

    <% if(error != null){ %>
        <div class="error">
            <%= error %>
        </div>
    <% } %>

    <div class="card">

        <div class="title">
            Chi Tiết Đơn Hàng #<%= hoadon.getMaHD() %>
        </div>

        <div class="info-row">
            <span><b>Mã hóa đơn:</b></span>
            <span>#<%= hoadon.getMaHD() %></span>
        </div>

        <div class="info-row">
            <span><b>Ngày đặt:</b></span>
            <span><%= hoadon.getNgayThanhToan() %></span>
        </div>

        <div class="info-row">
            <span><b>Phương thức thanh toán:</b></span>
            <span>
                <%= thanhtoan != null ? thanhtoan.getPhuongThuc() : "N/A" %>
            </span>
        </div>

        <div class="info-row">
            <span><b>Trạng thái:</b></span>
            <span>

            <%
				String trangThai = hoadon.getTrangThai();
				
				if("CHO_XAC_NHAN".equals(trangThai)){
				%>
				
				    <span class="status pending">
				        Chờ xác nhận
				    </span>
				
				<%
				}else if("DANG_XU_LY".equals(trangThai)){
				%>
				
				    <span class="status pending">
				        Đang xử lý
				    </span>
				
				<%
				}else if("DANG_GIAO".equals(trangThai)){
				%>
				
				    <span class="status pending">
				        Đang giao
				    </span>
				
				<%
				}else if("DA_GIAO".equals(trangThai)){
				%>
				
				    <span class="status success">
				        Đã giao
				    </span>
				
				<%
				}else if("DA_HUY".equals(trangThai)){
				%>
				
				    <span class="status cancel">
				        Đã hủy
				    </span>
				
				<%
				}
				%>

            </span>
        </div>

        <% if(coTheHuy && deadlineMillis > 0){ %>
            <div class="countdown" id="countdownBox">
                Bạn còn <b id="countdownText">--:--:--</b> để hủy đơn hàng này.
                Sau thời gian trên, đơn sẽ tự động được xác nhận và chuyển sang xử lý,
                khi đó bạn sẽ không thể hủy nữa.
            </div>
        <% } %>

    </div>



    <div class="card">

        <div class="title">
            Danh Sách Sản Phẩm
        </div>

        <table>

            <thead>
                <tr>
                    <th>STT</th>
                    <th>Tên Sản Phẩm</th>
                    <th>Số Lượng</th>
                    <th>Đơn Giá</th>
                    <th>Thành Tiền</th>
                </tr>
            </thead>

            <tbody>

            <%
            int stt = 1;

            if(items != null){
                for(HoadonCT item : items){

                    Sanpham sp =
                        sanphamDao.getById(
                            item.getMaSP()
                        );
            %>

                <tr>

                    <td>
                        <%= stt++ %>
                    </td>

                    <td>
                        <%= sp != null ?
                            sp.getTenSP()
                            :
                            "Sản phẩm đã xóa"
                        %>
                    </td>

                    <td>
                        <%= item.getSoLuong() %>
                    </td>

                    <td>
                        <%= String.format(
                            "%,.0f",
                            item.getDonGia()
                        ) %> đ
                    </td>

                    <td>
                        <%= String.format(
                            "%,.0f",
                            item.getTongGia()
                        ) %> đ
                    </td>

                </tr>

            <%
                }
            }
            %>

            </tbody>

        </table>

        <div class="total">
            Tổng cộng:
            <%= String.format(
                "%,.0f",
                hoadon.getTongGia()
            ) %> đ
        </div>

    </div>



    <div class="card">

        <a href="OrderHistoryServlet"
           class="btn btn-back">
            Quay lại danh sách đơn hàng
        </a>

        <% if(coTheHuy){ %>

        <form action="OrderDetailServlet"
              method="post"
              style="display:inline;">

            <input type="hidden"
                   name="action"
                   value="huy">

            <input type="hidden"
                   name="maHD"
                   value="<%= hoadon.getMaHD() %>">

            <button type="submit"
                    class="btn btn-cancel"
                    onclick="return confirm('Bạn có chắc muốn hủy đơn hàng?');">

                Hủy đơn hàng

            </button>

        </form>

        <% } %>

    </div>

</div>

<% if(coTheHuy && deadlineMillis > 0){ %>
<script>
(function(){
    var deadline = <%= deadlineMillis %>; // epoch millis - server tra ve
    var textEl = document.getElementById("countdownText");
    var boxEl  = document.getElementById("countdownBox");
    if(!textEl) return;

    function tick(){
        var remain = deadline - Date.now();

        if(remain <= 0){
            textEl.textContent = "00:00:00";
            if(boxEl){
                boxEl.textContent = "Đơn hàng đã quá hạn hủy, hệ thống đang tự động xác nhận. Vui lòng tải lại trang.";
            }
            clearInterval(timer);
            return;
        }

        var totalSeconds = Math.floor(remain / 1000);
        var hh = Math.floor(totalSeconds / 3600);
        var mm = Math.floor((totalSeconds % 3600) / 60);
        var ss = totalSeconds % 60;

        function pad(n){ return n < 10 ? "0" + n : "" + n; }

        textEl.textContent = pad(hh) + ":" + pad(mm) + ":" + pad(ss);
    }

    tick();
    var timer = setInterval(tick, 1000);
})();
</script>
<% } %>

</body>
</html>
