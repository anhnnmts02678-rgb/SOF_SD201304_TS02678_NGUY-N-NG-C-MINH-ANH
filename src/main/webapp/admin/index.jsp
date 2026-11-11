<%@ page contentType="text/html;charset=UTF-8" %>

<%
if(session.getAttribute("admin")==null){
    response.sendRedirect("../login.jsp");
    return;
}

request.setAttribute("activeMenu", "index");
%>


<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<title>Admin Dashboard</title>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

<link rel="stylesheet" href="css/admin.css">

</head>


<body>


<div class="admin-wrapper">


    <!-- SIDEBAR -->
    <%@ include file="_sidebar.jsp" %>



    <!-- CONTENT -->

    <div class="main-content">
        <div class="page-header">
            <div>
                <h1>
                    Trang quản trị Admin
                </h1>

                <div class="welcome">
                    Xin chào,
                    <b>${admin.hoTen}</b>
                    👋
                </div>
            </div>
        </div>

        <div class="card">

            <h2 style="margin-bottom:20px">
                Chức năng quản lý
            </h2>



            <div class="actions">
                <a class="btn btn-primary"
                   href="EmployeeServlet?action=list">
                    <i class="fa-solid fa-user-tie"></i>
                    Quản lý nhân viên
                </a>
                
                <a class="btn btn-primary"
                   href="UserServlet?action=list">
                    <i class="fa-solid fa-users"></i>
                    Quản lý khách hàng
                </a>
				<a class="btn btn-primary"
                   href="EmployeeDanhgiaServlet?action=list">
                    <i class="fa-solid fa-users"></i>
                    Quản lý đánh giá
                </a>
                
                <a class="btn btn-primary"
                   href="EmployeeKhuyenmaiServlet?action=list">
                    <i class="fa-solid fa-users"></i>
                    Quản lý khuyến mãi
                </a>
                <a class="btn btn-primary"
                   href="orders?action=list">
                    <i class="fa-solid fa-users"></i>
                    Quản lý đơn hàng
                </a>
					<a class="btn btn-primary"
                   href="ThongkeServlet">
                    <i class="fa-solid fa-chart-line"></i>
                    Thống kê


                </a>

            </div>



        </div>




        <br>



        <div class="card">

            <h2>
                Thông tin hệ thống
            </h2>


            <p style="margin-top:15px;color:#666">

                Hệ thống quản lý bán linh kiện máy tính HAKY TECH.

            </p>
        </div>

    </div>
</div>

</body>
</html>