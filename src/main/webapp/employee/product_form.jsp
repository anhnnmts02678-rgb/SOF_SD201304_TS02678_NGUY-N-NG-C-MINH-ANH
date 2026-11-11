<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="nhom3.Model.Sanpham"%>
<%@ page import="nhom3.Model.Danhmuc"%>
<%@ page import="nhom3.Model.Khuyenmai"%>

<%
if (session.getAttribute("employee") == null
        && session.getAttribute("admin") == null) {
    response.sendRedirect("../login.jsp");
    return;
}

request.setAttribute("activeMenu", "product");

Sanpham sp = (Sanpham) request.getAttribute("sp");

List<Danhmuc> danhMucList =
(List<Danhmuc>) request.getAttribute("danhMucList");

List<Khuyenmai> khuyenMaiList =
(List<Khuyenmai>) request.getAttribute("khuyenMaiList");

boolean isEdit =
(sp != null && sp.getMaSP() > 0);

String message =
(String) request.getAttribute("message");
%>

<!DOCTYPE html>

<html>

<head>

<meta charset="UTF-8">

<title>
<%= isEdit
? "Cập nhật sản phẩm"
: "Thêm sản phẩm" %>
- HAKY TECH
</title>

<link rel="stylesheet"
href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

<link rel="stylesheet"
href="css/admin.css">

<style>

.preview-image{
margin-top:15px;
}

.preview-image img{
width:220px;
height:220px;
object-fit:cover;
border-radius:10px;
border:1px solid #ddd;
background:#fff;
}

</style>

</head>

<body>

<div class="admin-wrapper">

<%@ include file="_sidebar.jsp"%>

<div class="main-content">

<div class="page-header">

<div>

<h1>

<%= isEdit
? "Cập nhật sản phẩm"
: "Thêm sản phẩm mới"%>

</h1>

<div class="welcome">

<a href="EmployeeSanphamServlet"
style="color:#2f73ff;font-weight:bold;">

<i class="fa-solid fa-arrow-left"></i>

Quay lại danh sách

</a>

</div>

</div>

</div>

<% if(message!=null){ %>

<div class="alert alert-error">

<%= message %>

</div>

<% } %>

<div class="form-card">

<form action="EmployeeSanphamServlet"
method="post">

<% if(isEdit){ %>

<input type="hidden"
name="maSP"
value="<%= sp.getMaSP()%>">

<% } %>

<div class="form-grid">

<div class="form-group full">

<label>Tên sản phẩm *</label>

<input
type="text"
name="tenSP"
required

value="<%= sp!=null && sp.getTenSP()!=null
? sp.getTenSP()
: "" %>">

</div>

<div class="form-group">

<label>Giá bán</label>

<input
type="number"
name="giaThanh"
step="1000"
min="0"
required

value="<%= sp!=null
? sp.getGiaThanh()
: "" %>">

</div>

<div class="form-group">

<label>Số lượng</label>

<input
type="number"
name="soLuong"
min="0"
required

value="<%= sp!=null
? sp.getSoLuong()
: 0 %>">

</div>

<div class="form-group">

<label>Danh mục</label>

<select name="maDM">

<option value="0">
-- Chọn danh mục --
</option>

<%

if(danhMucList!=null){

for(Danhmuc dm:danhMucList){

%>

<option
value="<%= dm.getMaDM()%>"

<%= sp!=null
&& sp.getMaDM()==dm.getMaDM()
? "selected"
: "" %>>

<%= dm.getTenDanhMuc()%>

</option>

<%

}

}

%>

</select>

</div>

<div class="form-group">

<label>Khuyến mãi</label>

<select name="maKM">

<option value="0">

Không áp dụng

</option>

<%

if(khuyenMaiList!=null){

for(Khuyenmai km:khuyenMaiList){

%>

<option
value="<%= km.getMaKM()%>"

<%= sp!=null
&& sp.getMaKM()==km.getMaKM()
? "selected"
: "" %>>

<%= km.getLoaiKM()%>

(-<%= km.getPhanTramGiam()%>%)

</option>

<%

}

}

%>

</select>

</div>

<div class="form-group">

<label>Nguồn gốc</label>

<input
type="text"
name="nguonGoc"

value="<%= sp!=null
&& sp.getNguonGoc()!=null
? sp.getNguonGoc()
: "" %>">

</div>

<div class="form-group full">

<label>Link hình ảnh</label>

<input
type="url"
id="urlHinhAnh"
name="urlHinhAnh"

placeholder="https://..."

value="<%= sp!=null
&& sp.getUrlHinhAnh()!=null
? sp.getUrlHinhAnh()
: "" %>">

<div class="preview-image">

<img
id="preview"

src="<%= sp!=null
&& sp.getUrlHinhAnh()!=null
&& !sp.getUrlHinhAnh().isEmpty()
? sp.getUrlHinhAnh()
: "https://via.placeholder.com/220x220?text=No+Image"%>">

</div>

</div>

<div class="form-group full">

<label>Mô tả</label>

<textarea
name="moTa"
rows="5"><%= sp!=null
&& sp.getMoTa()!=null
? sp.getMoTa()
: "" %></textarea>

</div>

<div class="form-group full">
    <label>Trạng thái</label>

    <div class="form-check">
        <input type="checkbox"
               id="trangThai"
               name="trangThai"
               <%= (sp == null || sp.isTrangThai()) ? "checked" : "" %>>

        <label for="trangThai"
               style="margin:0;font-weight:normal;">
            Đang bán
        </label>
    </div>
</div>

<div class="form-actions">

    <button type="submit" class="btn btn-primary">
        <i class="fa-solid fa-floppy-disk"></i>

        <%= isEdit ? "Lưu thay đổi" : "Thêm sản phẩm" %>

    </button>

    <a href="EmployeeSanphamServlet?action=list"
       class="btn btn-secondary">
        Hủy
    </a>

</div>

</form>

</div>

</div>

</div>
<script>

const urlInput = document.getElementById("urlHinhAnh");
const preview = document.getElementById("preview");

if(urlInput){

    urlInput.addEventListener("input", function(){

        if(this.value.trim()!=""){

            preview.src=this.value;

        }else{

            preview.src="https://via.placeholder.com/220x220?text=No+Image";

        }

    });

    preview.onerror=function(){

        this.src="https://via.placeholder.com/220x220?text=Image+Error";

    };

}

</script>
</body>
</html>