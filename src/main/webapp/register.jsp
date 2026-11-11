<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Đăng kí tài khoản</title>

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

/*================ HEADER ================*/

header{
    width:100%;
    height:75px;
    background:#081224;
    display:flex;
    justify-content:space-between;
    align-items:center;
    padding:0 60px;
        position:sticky;
}

.logo{
    color:white;
    font-size:34px;
    font-weight:bold;
}

.logo span{
    color:#246BFF;
}

.right{
    display:flex;
    align-items:center;
    gap:20px;
}

.search{
    width:340px;
    height:42px;
    border:none;
    outline:none;
    background:#1b263b;
    color:white;
    border-radius:8px;
    padding:0 15px;
}

.search::placeholder{
    color:#b8b8b8;
}

.icon{
    color:white;
    font-size:24px;
    cursor:pointer;
}

.login-btn{
    background:#246BFF;
    color:white;
    text-decoration:none;
    padding:12px 25px;
    border-radius:8px;
    font-weight:bold;
    transition:.3s;
}

.login-btn:hover{
    background:#1654d9;
}

/*================ CONTAINER ================*/

.container{
    width:1250px;
    margin:35px auto;
    display:flex;
    gap:40px;
}

/*================ LEFT ================*/

.left{
    width:50%;
    padding:30px;
}

.left h1{
    font-size:42px;
    color:#111;
    margin-top:20px;
}

.left h2{
    font-size:48px;
    color:#246BFF;
    margin-top:10px;
}

.line{
    width:70px;
    height:4px;
    background:#246BFF;
    margin:25px 0;
    border-radius:5px;
}

.left p{
    color:#555;
    line-height:34px;
    font-size:18px;
}

.left img{
    width:520px;
    display:block;
    margin:auto;
    margin-top:25px;
}

.feature{
    display:flex;
    justify-content:space-around;
    margin-top:35px;
}

.feature div{
    text-align:center;
}

.feature i{
    font-size:34px;
    color:#246BFF;
    margin-bottom:12px;
}

.feature p{
    font-size:14px;
    font-weight:bold;
    line-height:22px;
}

/*================ RIGHT ================*/

.right-panel{
    width:50%;
    display:flex;
    justify-content:center;
    align-items:center;
}

.login-box{
    width:520px;
    background:white;
    padding:40px;
    border-radius:20px;
    box-shadow:0 0 25px rgba(0,0,0,.08);
}

.login-box h1{
    text-align:center;
    font-size:38px;
    margin-bottom:10px;
}

.sub{
    text-align:center;
    color:#666;
    margin-bottom:30px;
}

.form-group{
    margin-bottom:18px;
}

.form-group label{
    display:block;
    font-weight:bold;
    margin-bottom:8px;
}

.input{
    display:flex;
    align-items:center;
    border:1px solid #ddd;
    border-radius:8px;
    padding:12px;
}

.input i{
    width:30px;
    color:#888;
}

.input input{
    width:100%;
    border:none;
    outline:none;
    font-size:15px;
}

/*================ BUTTON ================*/

.btn{
    width:100%;
    height:48px;
    border:none;
    border-radius:8px;
    background:#246BFF;
    color:white;
    font-size:18px;
    font-weight:bold;
    cursor:pointer;
    margin-top:10px;
    transition:.3s;
}

.btn:hover{
    background:#1654d9;
}

/*================ SOCIAL ================*/

.or{
    text-align:center;
    margin:28px 0 18px;
    color:#666;
}

.social{
    display:flex;
    justify-content:center;
    gap:18px;
}

.social button{
    width:70px;
    height:50px;
    border:1px solid #ddd;
    border-radius:10px;
    background:white;
    cursor:pointer;
    font-size:24px;
    transition:.3s;
}

.social button:hover{
    background:#f5f5f5;
}

.register{
    text-align:center;
    margin-top:28px;
}

.register a{
    text-decoration:none;
    color:#246BFF;
    font-weight:bold;
}

.register a:hover{
    text-decoration:underline;
}

</style>

</head>

<body>

<header>

    <div class="logo">
        HAKY <span>TECH</span>
    </div>
    <div class="right">
        <input class="search" placeholder="Tìm kiếm sản phẩm...">

        <i class="fa-solid fa-cart-shopping icon"></i>

        <a href="login.jsp" class="login-btn">
            Đăng nhập
        </a>
    </div>

</header>

<div class="container">

    <div class="left">

        <h1>CÔNG NGHỆ ĐỈNH CAO</h1>

        <h2>NÂNG TẦM TRẢI NGHIỆM</h2>

        <div class="line"></div>

        <p>
            Đăng ký tài khoản để mua sắm nhanh hơn.<br>
            Theo dõi đơn hàng, nhận nhiều ưu đãi và
            tích điểm thành viên.
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
                <i class="fa-solid fa-truck"></i>
                <p>GIAO HÀNG TOÀN QUỐC</p>
            </div>

        </div>

    </div>

    <div class="right-panel">

        <div class="login-box">

            <h1>ĐĂNG KÝ</h1>

            <div class="sub">
                Tạo tài khoản HAKY TECH
            </div>

<form action="RegisterServlet" method="post">

    <div class="form-group">
        <label>Tên đăng nhập</label>

        <div class="input">
            <i class="fa-solid fa-user"></i>

            <input
            type="text"
            name="username"
            placeholder="Nhập tên đăng nhập"
            required>
        </div>
    </div>

    <div class="form-group">

        <label>Email</label>

        <div class="input">
            <i class="fa-solid fa-envelope"></i>

            <input
            type="email"
            name="email"
            placeholder="Nhập email"
            required>
        </div>

    </div>
<div class="form-group">
    <label>Họ và tên</label>

    <div class="input">
        <i class="fa-solid fa-user"></i>

        <input
            type="text"
            name="fullName"
            placeholder="Nhập họ và tên"
            required>
    </div>
</div>

<div class="form-group">
    <label>Số điện thoại</label>

    <div class="input">
        <i class="fa-solid fa-phone"></i>

        <input
            type="text"
            name="sdt"
            placeholder="Nhập số điện thoại"
            required>
    </div>
</div>

<label>
Địa chỉ nhận hàng *
</label>

<textarea name="address"
required
placeholder="VD:123 Lê Trọng Tấn,Phường Tây Thạnh,Quận Tân Phú,TP.HCM">
</textarea>
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

<div class="form-group">

    <label>Nhập lại mật khẩu</label>

    <div class="input">

        <i class="fa-solid fa-lock"></i>

        <input
            type="password"
            name="confirmPassword"
            placeholder="Nhập lại mật khẩu"
            required>

    </div>

</div>
    <% if(request.getAttribute("message") != null){ %>
        <div style="
            color:red;
            text-align:center;
            margin-bottom:15px;
            font-weight:bold;">
            <%= request.getAttribute("message") %>
        </div>
    <% } %>

    <button class="btn">
        Đăng ký
    </button>

</form>

            <div class="or">
                Hoặc đăng ký với
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

                Đã có tài khoản?

                <a href="login.jsp">
                    Đăng nhập ngay
                </a>

            </div>

        </div>

    </div>

</div>

</body>
</html>