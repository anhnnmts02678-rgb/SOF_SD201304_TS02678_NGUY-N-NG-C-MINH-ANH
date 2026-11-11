<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Đăng nhập - HAKY TECH</title>

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
}

/*================ HEADER =================*/

header{
height:75px;
background:#0d1325;
display:flex;
justify-content:space-between;
align-items:center;
padding:0 70px;
    position:sticky;
}

.logo{
font-size:34px;
font-weight:bold;
color:white;
}

.logo span{
color:#2f73ff;
}

nav{
display:flex;
align-items:center;
gap:35px;
}

nav a{
color:white;
text-decoration:none;
font-weight:bold;
font-size:16px;
transition:.3s;
}

nav a:hover{
color:#2f73ff;
}

.right{
display:flex;
align-items:center;
gap:18px;
}

.search{
width:330px;
height:42px;
border:none;
outline:none;
padding:0 15px;
border-radius:10px;
background:#1d2538;
color:white;
}

.search::placeholder{
color:#aaa;
}

.icon{
color:white;
font-size:25px;
cursor:pointer;
}

.login-btn{
background:#2f73ff;
padding:12px 28px;
border-radius:8px;
color:white;
text-decoration:none;
font-weight:bold;
transition:.3s;
}

.login-btn:hover{
background:#1457df;
}

/*================ CONTAINER =================*/

.container{

width:1250px;

margin:35px auto;

display:flex;

background:white;

border-radius:20px;

overflow:hidden;

box-shadow:0 10px 30px rgba(0,0,0,.08);

}

/*================ LEFT =================*/

.left{

width:50%;

padding:55px;

background:#f8fbff;

}

.left h1{

font-size:46px;

margin-bottom:10px;

color:#111;

}

.left h2{

font-size:48px;

color:#2f73ff;

margin-bottom:30px;

}

.line{

width:70px;

height:4px;

background:#2f73ff;

border-radius:20px;

margin-bottom:25px;

}

.left p{

font-size:18px;

line-height:35px;

color:#555;

margin-bottom:35px;

}

.left img{

width:90%;

display:block;

margin:auto;

}

/*================ FEATURE =================*/

.feature{

display:flex;

justify-content:space-around;

margin-top:30px;

}

.feature div{

text-align:center;

}

.feature i{

font-size:34px;

color:#2f73ff;

margin-bottom:12px;

}

.feature p{

font-size:14px;

font-weight:bold;

line-height:24px;

}

/*================ RIGHT =================*/

.right-panel{

width:50%;

display:flex;

justify-content:center;

align-items:center;

background:white;

}

.login-box{

width:500px;

padding:45px;

border:1px solid #eee;

border-radius:18px;

box-shadow:0 5px 18px rgba(0,0,0,.08);

}

.login-box h1{

text-align:center;

font-size:42px;

margin-bottom:10px;

}

.login-box .sub{

text-align:center;

color:#666;

margin-bottom:35px;

}

.error{

text-align:center;

color:red;

margin-bottom:20px;

}

.form-group{

margin-bottom:22px;

}

.form-group label{

display:block;

font-weight:bold;

margin-bottom:10px;

}

.input{

display:flex;

align-items:center;

border:1px solid #ddd;

border-radius:8px;

padding:14px;

}

.input i{

color:#888;

width:30px;

}

.input input{

border:none;

outline:none;

width:100%;

font-size:15px;

}

.row{

display:flex;

justify-content:space-between;

align-items:center;

margin-bottom:25px;

}

.row a{

text-decoration:none;

color:#2f73ff;

font-weight:bold;

}

.btn{

width:100%;

height:50px;

border:none;

background:#2f73ff;

color:white;

font-size:18px;

border-radius:8px;

cursor:pointer;

transition:.3s;

}

.btn:hover{

background:#1457df;

}

.or{

text-align:center;

margin:25px 0;

color:#666;

position:relative;

}

.or:before{

content:"";

position:absolute;

left:0;

top:50%;

width:40%;

height:1px;

background:#ddd;

}

.or:after{

content:"";

position:absolute;

right:0;

top:50%;

width:40%;

height:1px;

background:#ddd;

}

.social{

display:flex;

justify-content:center;

gap:20px;

margin-bottom:25px;

}

.social button{

width:70px;

height:55px;

border:1px solid #ddd;

border-radius:10px;

background:white;

font-size:28px;

cursor:pointer;

transition:.3s;

}

.social button:hover{

transform:translateY(-3px);

box-shadow:0 5px 10px rgba(0,0,0,.15);

}

.register{

text-align:center;

font-size:16px;

}

.register a{

text-decoration:none;

color:#2f73ff;

font-weight:bold;

}

</style>

</head>

<body>

<header>

<div class="logo">
    <a href="index.jsp" style="color:white;text-decoration:none;">
        HAKY TECH
    </a>
</div>

</header>

<div class="container">

<div class="left">

<h1>CÔNG NGHỆ ĐỈNH CAO</h1>

<h2>NÂNG TẦM TRẢI NGHIỆM</h2>

<div class="line"></div>

<p>

Hàng chính hãng - Giá tốt mỗi ngày<br>

Bảo hành uy tín - Hỗ trợ tận tâm

</p>

<div class="feature">

<div>

<i class="fa-solid fa-shield"></i>

<p>HÀNG CHÍNH HÃNG</p>

</div>

<div>

<i class="fa-solid fa-award"></i>

<p>BẢO HÀNH UY TÍN</p>

</div>

<div>

<i class="fa-solid fa-truck-fast"></i>

<p>GIAO HÀNG TOÀN QUỐC</p>

</div>

</div>

</div>

<div class="right-panel">

<div class="login-box">

<h1>ĐĂNG NHẬP</h1>

<div class="sub">

Chào mừng bạn quay trở lại với HAKY TECH

</div>

<%
String msg=(String)request.getAttribute("message");
if(msg!=null){
%>

<div class="error">

<%=msg%>

</div>

<%
}
%>

<form action="${pageContext.request.contextPath}/LoginServlet"
      method="post">
<div class="form-group">

<label>Email hoặc số điện thoại</label>

<div class="input">

<i class="fa-regular fa-user"></i>

<input type="text"

name="username"

placeholder="Nhập email hoặc số điện thoại"

required>

</div>

</div>

<div class="form-group">

<label>Mật khẩu</label>

<div class="input">

<i class="fa-solid fa-lock"></i>

<input

type="password"

name="password"

placeholder="Nhập mật khẩu"

required>

</div>

</div>

<div class="row">

<label>

<input type="checkbox" name="remember">

Ghi nhớ đăng nhập

</label>

<a href="${pageContext.request.contextPath}/forgot-password.jsp">
    Quên mật khẩu?
</a>

</div>

<button class="btn">

Đăng nhập

</button>

</form>

<div class="or">

Hoặc liên hệ với chúng tôi qua

</div>

<div class="social">

<button>

<i class="fab fa-google"></i>

</button>

<button>

<i class="fab fa-facebook"></i>

</button>

<button>

<i class="fab fa-apple"></i>

</button>

</div>

<div class="register">

Chưa có tài khoản?

<a href="register.jsp">

Đăng ký ngay

</a>

</div>

</div>

</div>

</div>

</body>
</html>