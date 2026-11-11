<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="nhom3.Model.Hoadon"%>
<%@ page import="nhom3.Model.Users"%>

<%
Object obj = request.getAttribute("orders");

List<Hoadon> orderList = null;

if(obj instanceof List<?>){
    orderList = (List<Hoadon>) obj;
}

Users user = (Users) session.getAttribute("user");
%>
	<!DOCTYPE html>
	<html>
	<head>
	<meta charset="UTF-8">
	<title>Lịch sử đơn hàng</title>
	
	<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
	
	<link rel="stylesheet" href="css/site-header.css">
	
	<style>
	
	body{
	    margin:0;
	    background:#f5f6fa;
	    font-family:Arial, Helvetica, sans-serif;
	}
	
	.page-content{
    max-width:1200px;
    margin:30px auto;
    padding:0 20px;
}
	
	.order-container{
	    background:#fff;
	    padding:25px;
	    border-radius:12px;
	    box-shadow:0 2px 10px rgba(0,0,0,.08);
	}
	
	.order-title{
	    margin-bottom:20px;
	    color:#222;
	    font-size:28px;
	    font-weight:700;
	}
	
	.order-top{
	    display:flex;
	    justify-content:space-between;
	    align-items:center;
	    margin-bottom:20px;
	    flex-wrap:wrap;
	    gap:10px;
	}
	
	.order-table{
	    width:100%;
	    border-collapse:collapse;
	}
	
	.order-table thead{
	    background:#0d6efd;
	    color:white;
	}
	
	.order-table th{
	    padding:15px;
	    text-align:center;
	}
	
	.order-table td{
	    padding:15px;
	    text-align:center;
	    border-bottom:1px solid #eee;
	}
	
	.order-table tbody tr:hover{
	    background:#f8f9fa;
	}
	
	.status{
	    display:inline-block;
	    padding:6px 12px;
	    border-radius:20px;
	    font-size:13px;
	    font-weight:600;
	}
	
	.status-wait{
	    background:#fff3cd;
	    color:#856404;
	}
	
	.status-process{
	    background:#cfe2ff;
	    color:#084298;
	}
	
	.status-delivery{
	    background:#cff4fc;
	    color:#055160;
	}
	
	.status-success{
	    background:#d1e7dd;
	    color:#0f5132;
	}
	
	.status-cancel{
	    background:#f8d7da;
	    color:#842029;
	}
	
	.btn-detail{
	    display:inline-block;
	    text-decoration:none;
	    padding:8px 14px;
	    background:#0d6efd;
	    color:white;
	    border-radius:6px;
	    font-size:14px;
	}
	
	.btn-detail:hover{
	    background:#0b5ed7;
	}
	
	.btn-green{
	    background:#198754;
	}
	
	.btn-green:hover{
	    background:#157347;
	}
	
	.empty-order{
	    text-align:center;
	    padding:50px;
	    color:#666;
	}
	
	@media(max-width:768px){
	
	    .order-table{
	        font-size:13px;
	    }
	
	    .order-table th,
	    .order-table td{
	        padding:10px;
	    }
	
	    .order-top{
	        flex-direction:column;
	        align-items:flex-start;
	    }
	}
	
</style>
	</head>
	<body>
<% request.setAttribute("activePage", "donhang"); %>
<%@ include file="_header.jsp" %>
	
	<div class="page-content">
	
	    <div class="order-container">
	
	        <h2 class="order-title">
	            Lịch sử đơn hàng
	        </h2>
	
	        <div class="order-top">
	
	            <div>
	
	                <a href="index.jsp"
	                   class="btn-detail">
	                    ← Tiếp tục mua hàng
	                </a>
	
	                <a href="CartServlet"
	                   class="btn-detail btn-green">
	                    Giỏ hàng
	                </a>
	
	            </div>
	
	            <div>
	                Tổng đơn hàng:
	                <b>
	                    <%= orderList != null ? orderList.size() : 0 %>
	                </b>
	            </div>
	
	        </div>
	
	        <%
	        if(orderList != null && !orderList.isEmpty()){
	        %>
	
	        <table class="order-table">
	
	            <thead>
	                <tr>
	                    <th>Mã HD</th>
	                    <th>Ngày đặt</th>
	                    <th>Tổng tiền</th>
	                    <th>Trạng thái</th>
	                    <th>Chi tiết</th>
	                </tr>
	            </thead>
	
	            <tbody>
	
	            <%
	            for(Hoadon hd : orderList){
	
	                String css = "status-wait";
	
	                if("CHO_XAC_NHAN".equalsIgnoreCase(hd.getTrangThai())){
	                    css = "status-wait";
	                }
	                else if("DANG_XU_LY".equalsIgnoreCase(hd.getTrangThai())){
	                    css = "status-process";
	                }
	                else if("DANG_GIAO".equalsIgnoreCase(hd.getTrangThai())){
	                    css = "status-delivery";
	                }
	                else if("DA_GIAO".equalsIgnoreCase(hd.getTrangThai())
	                      || "DA_THANH_TOAN".equalsIgnoreCase(hd.getTrangThai())
	                      || "Đã thanh toán".equalsIgnoreCase(hd.getTrangThai())){
	                    css = "status-success";
	                }
	                else if("DA_HUY".equalsIgnoreCase(hd.getTrangThai())){
	                    css = "status-cancel";
	                }
	            %>
	
	                <tr>
	
	                    <td>
	                        #<%= hd.getMaHD() %>
	                    </td>
	
	                    <td>
	                        <%= hd.getNgayThanhToan() %>
	                    </td>
	
	                    <td>
	                        <%= String.format("%,.0f", hd.getTongGia()) %> ₫
	                    </td>
	
	                    <td>
	                        <span class="status <%= css %>">
	                            <%= hd.getTrangThai() %>
	                        </span>
	                    </td>
	
	                    <td>
	                        <a class="btn-detail"
	                           href="OrderDetailServlet?maHD=<%= hd.getMaHD() %>">
	                            Xem chi tiết
	                        </a>
	                    </td>
	
	                </tr>
	
	            <%
	            }
	            %>
	
	            </tbody>
	
	        </table>
	
	        <%
	        }else{
	        %>
	
	            <div class="empty-order">
	                <i class="fa-solid fa-box-open fa-3x"></i>
	                <p>Bạn chưa có đơn hàng nào.</p>
	            </div>
	
	        <%
	        }
	        %>
	
	    </div>
	
	</div>
	

	<%@ include file="_footer.jsp" %>
	</body>
	</html>
