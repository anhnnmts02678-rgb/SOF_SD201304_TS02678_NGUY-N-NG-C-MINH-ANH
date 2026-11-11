<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>

<%
request.setAttribute("activePage", "checkout");
%>

<%@ include file="_header.jsp"%>

<link rel="stylesheet"
      href="${pageContext.request.contextPath}/checkoutcss/checkout.css">

<link rel="stylesheet" href="css/site-header.css">
      
<div class="checkout-page">

<div class="checkout-container">

    <!-- TIÊU ĐỀ -->
    <div class="checkout-title">
        <h1>XÁC NHẬN ĐẶT HÀNG</h1>
        <p>Kiểm tra thông tin đơn hàng trước khi thanh toán</p>
    </div>


    <div class="checkout-layout">


        <!-- ============================= -->
        <!-- CỘT TRÁI -->
        <!-- ============================= -->

        <div class="checkout-left">


            <!-- THÔNG TIN KHÁCH HÀNG -->
            <div class="checkout-card">

                <div class="card-title">
                    <i class="fa-solid fa-user"></i>
                    Thông tin khách hàng
                </div>

                <div class="customer-info">

                    <div class="info-row">
                        <span>Họ và tên</span>
                        <strong>${user.fullName}</strong>
                    </div>

                    <div class="info-row">
                        <span>Số điện thoại</span>
                        <strong>${user.sdt}</strong>
                    </div>

                    <div class="info-row">
                        <span>Email</span>
                        <strong>${user.email}</strong>
                    </div>

                    <div class="info-row">
                        <span>Địa chỉ</span>
                        <strong>${user.address}</strong>
                    </div>

                </div>

            </div>



            <!-- SẢN PHẨM -->
            <div class="checkout-card">

                <div class="card-title">
                    <i class="fa-solid fa-cart-shopping"></i>
                    Sản phẩm đặt mua
                </div>


                <div class="product-list">

                    <c:forEach items="${cartItems}" var="item">

                        <div class="checkout-product">

                            <div class="product-info">

                                <div class="product-name">
                                    Sản phẩm #${item.maSP}
                                </div>

                                <div class="product-quantity">
                                    Số lượng: ${item.soLuong}
                                </div>

                            </div>

                            <div class="product-price">

                                <fmt:formatNumber
                                    value="${item.tongGia}"
                                    pattern="#,##0"/> đ

                            </div>

                        </div>

                    </c:forEach>

                </div>

            </div>



            <!-- KHUYẾN MÃI -->
            <div class="checkout-card">

                <div class="card-title">
                    <i class="fa-solid fa-ticket"></i>
                    Khuyến mãi
                </div>


                <div class="promotion-list">

                    <c:choose>

                        <c:when test="${not empty kmList}">

                            <c:forEach items="${kmList}" var="km">

                                <form action="CheckoutServlet"
									      method="post"
									      class="promotion-item">
									
									    <input type="hidden"
									           name="action"
									           value="applyKM">
									
									    <input type="hidden"
									           name="maKM"
									           value="${km.maKM}">
									
									    <div class="promotion-info">
									
									        <strong>
									            ${km.loaiKM}
									        </strong>
									
									        <span>
									            Giảm ${km.phanTramGiam}%
									        </span>
									
									        <small>
									            ${km.moTa}
									        </small>
									
									    </div>
									
									    <button type="submit"
									            class="apply-promotion">
									
									        Áp dụng
									
									    </button>
									
									</form>

                            </c:forEach>

                        </c:when>


                        <c:otherwise>

                            <div class="no-promotion">

                                <i class="fa-solid fa-ticket"></i>

                                Hiện tại không có khuyến mãi nào.

                            </div>

                        </c:otherwise>

                    </c:choose>

                </div>

            </div>



<!-- THANH TOÁN -->
<div class="checkout-card">

    <div class="card-title">
        <i class="fa-solid fa-credit-card"></i>
        Phương thức thanh toán
    </div>

    <form action="CheckoutServlet" method="post">

        <input type="hidden"
               name="action"
               value="confirmOrder">

        <input type="hidden"
               name="maKM"
               value="${selectedKM != null ? selectedKM.maKM : 0}">


        <!-- COD -->
        <label class="payment-option">

            <input type="radio"
                   name="phuongThuc"
                   value="COD"
                   checked
                   onchange="changePayment('COD')">

            <div class="payment-content">

                <strong>
                    Thanh toán khi nhận hàng (COD)
                </strong>

                <p>
                    Thanh toán trực tiếp khi nhận được sản phẩm.
                </p>

            </div>

        </label>




        <!-- NÚT DUY NHẤT -->
        <button type="submit"
                class="order-btn">

            <i class="fa-solid fa-check"></i>

            Xác nhận đặt hàng

        </button>

    </form>


    <div class="secure-note">

        <i class="fa-solid fa-shield-halved"></i>

        Thông tin của bạn được bảo mật an toàn.

    </div>

</div>
        <!-- ============================= -->
        <!-- CỘT PHẢI -->
        <!-- ============================= -->

        <div class="checkout-right">

            <div class="order-summary">

                <h2>
                    Tóm tắt đơn hàng
                </h2>


                <div class="summary-row">

                    <span>Tạm tính</span>

                    <strong>
                        <fmt:formatNumber
                            value="${tongTienGoc}"
                            pattern="#,##0"/> đ
                    </strong>

                </div>


                <div class="summary-row">

                    <span>Giảm giá</span>

                    <strong class="discount">

                        -
                        <fmt:formatNumber
                            value="${giamGia}"
                            pattern="#,##0"/> đ

                    </strong>

                </div>


                <div class="summary-line"></div>


                <div class="summary-total">

                    <span>
                        Tổng thanh toán
                    </span>

                    <strong>

                        <fmt:formatNumber
                            value="${tongTien}"
                            pattern="#,##0"/> đ

                    </strong>

                </div>
                <div class="secure-note">

                    <i class="fa-solid fa-shield-halved"></i>

                    Thông tin của bạn được bảo mật an toàn.

                </div>
            </div>
        </div>
        </div>
    </div>
</div>

<%@ include file="_footer.jsp"%>