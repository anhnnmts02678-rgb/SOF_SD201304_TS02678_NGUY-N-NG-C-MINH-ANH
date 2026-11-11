<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Quên mật khẩu</title>

<style>
body{
    font-family:Arial,sans-serif;
    background:#f4f6f9;
}

.container{
    width:420px;
    margin:80px auto;
}

.card{
    background:white;
    padding:30px;
    border-radius:12px;
    box-shadow:0 4px 15px rgba(0,0,0,.1);
}

h2{
    text-align:center;
    margin-bottom:25px;
}

input{
    width:100%;
    padding:12px;
    margin-bottom:15px;
    border:1px solid #ddd;
    border-radius:8px;
}

button{
    width:100%;
    padding:12px;
    background:#2563eb;
    color:white;
    border:none;
    border-radius:8px;
    cursor:pointer;
}

.error{
    background:#fee2e2;
    color:#991b1b;
    padding:10px;
    border-radius:8px;
    margin-bottom:15px;
}
</style>

</head>
<body>

<div class="container">

<div class="card">

<h2>Quên mật khẩu</h2>

<%
String error =
(String)request.getAttribute("error");

if(error != null){
%>

<div class="error">
    <%= error %>
</div>

<%
}
%>

<form action="ForgotPasswordServlet"
      method="post">

    <input type="email"
           name="email"
           placeholder="Nhập email của bạn"
           required>

    <button type="submit">
        Gửi mã OTP
    </button>

</form>

</div>

</div>

</body>
</html>