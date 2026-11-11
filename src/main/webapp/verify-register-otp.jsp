<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Xác thực Email - HAKY TECH</title>

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
    background:#eef3fb;
    display:flex;
    justify-content:center;
    align-items:center;
    height:100vh;
}

.container{
    width:500px;
}

.card{
    background:white;
    padding:40px;
    border-radius:20px;
    box-shadow:0 10px 30px rgba(0,0,0,.08);
}

.logo{
    text-align:center;
    font-size:36px;
    font-weight:bold;
    margin-bottom:15px;
}

.logo span{
    color:#2f73ff;
}

.title{
    text-align:center;
    font-size:28px;
    font-weight:bold;
    margin-bottom:10px;
}

.desc{
    text-align:center;
    color:#666;
    margin-bottom:30px;
    line-height:24px;
}

.success{
    color:#16a34a;
    text-align:center;
    margin-bottom:20px;
    font-weight:bold;
}

.error{
    color:red;
    text-align:center;
    margin-bottom:20px;
    font-weight:bold;
}

.form-group{
    margin-bottom:25px;
}

.form-group label{
    display:block;
    margin-bottom:10px;
    font-weight:bold;
}

.input{
    display:flex;
    align-items:center;
    border:1px solid #ddd;
    border-radius:10px;
    padding:14px;
}

.input i{
    width:35px;
    color:#888;
}

.input input{
    border:none;
    outline:none;
    width:100%;
    font-size:18px;
    text-align:center;
    letter-spacing:5px;
}

.btn{
    width:100%;
    height:50px;
    border:none;
    border-radius:10px;
    background:#2f73ff;
    color:white;
    font-size:18px;
    cursor:pointer;
    transition:.3s;
}

.btn:hover{
    background:#1457df;
}

.back{
    margin-top:20px;
    text-align:center;
}

.back a{
    text-decoration:none;
    color:#2f73ff;
    font-weight:bold;
}

</style>

</head>
<body>

<div class="container">

<div class="card">

<div class="logo">
HAKY <span>TECH</span>
</div>

<div class="title">
XÁC THỰC EMAIL
</div>

<div class="desc">
Chúng tôi đã gửi mã OTP gồm 6 chữ số đến email của bạn.<br>
Vui lòng nhập mã để hoàn tất đăng ký tài khoản.
</div>

<%
String message =
        (String)request.getAttribute("message");

if(message != null){
%>

<div class="error">
<%= message %>
</div>

<%
}
%>

<form action="<%=request.getContextPath()%>/VerifyRegisterOtpServlet"
      method="post">

    <div class="form-group">

        <label>Mã OTP</label>

        <div class="input">

            <i class="fa-solid fa-envelope"></i>

            <input type="text"
                   name="otp"
                   maxlength="6"
                   placeholder="000000"
                   required>

        </div>

    </div>

    <button type="submit" class="btn">
        Xác nhận tài khoản
    </button>

</form>

<div class="back">
    <a href="register.jsp">
        ← Quay lại đăng ký
    </a>
</div>

</div>

</div>

</body>
</html>
```
