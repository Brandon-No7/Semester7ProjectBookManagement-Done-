<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Insert title here</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css"
	rel="stylesheet">
</head>
<body style="background: url('image/背景图片4.jpg'); background-size:100% 100% ; background-attachment: fixed">
	<!-- 引入jquery -->
	<script type="text/javascript"
		src="https://cdn.jsdelivr.net/npm/jquery@1.12.4/dist/jquery.min.js"></script>
	<!-- java script -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js"></script>
	
	<!-- 导航栏 -->
	<nav class="navbar navbar-default">
		<div class="container-fluid">
			<div class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
					<li><a href="${pageContext.request.contextPath }/login.jsp">Login/Register<span class="sr-only">(current)</span></a></li>
					<li><a href="${pageContext.request.contextPath }/home.do">Home</a></li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li style="margin-right: 20px; color: #777777; margin-top: 15px"><B>Hello,${username }</B></li>
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-haspopup="true"
						aria-expanded="false">Personal Center<span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="${pageContext.request.contextPath }/userorderbook.do">Order</a></li>
							<li><a href="${pageContext.request.contextPath }/userrentbook.do">Rent</a></li>
							<li><a href="${pageContext.request.contextPath }/userovertime.do">Overtime</a></li>
							<li><a href="${pageContext.request.contextPath }/userreturnback.do">Return</a></li>
							<li role="separator" class="divider"></li>
							<li><a href="${pageContext.request.contextPath }/changePassword.do">Change Password</a></li>
						</ul></li>
				</ul>
			</div>
		</div>
	</nav>
	<!-- 导航栏结束 -->
	<input type="text" value="${overtime }" id="overtime" hidden="hidden">
	<input type="text" value="${hasrent }" id="hasrent" hidden="hidden">
	<input type="text" value="${rentonly }" id="rentonly" hidden="hidden">
	<form action="${pageContext.request.contextPath }/rentbookInformation.do" onsubmit="return check()" class="form-horizontal" style="margin-top: 100px; margin-right:400px; margin-left:300px">
		<div style="margin-bottom: 30px; font-size: 20px" align="center">
			<label>Borrowing information</label>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label">Book ID</label>
			<div class="col-sm-10">
				<c:forEach items="${booklist }" var="book">
					<input type="text" class="form-control" name="bookid" value="${book.bookid }" readonly="readonly">
				</c:forEach>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label">Book Name</label>
			<div class="col-sm-10">
				<c:forEach items="${booklist }" var="book">
					<input type="text" class="form-control" name="bookname" value="${book.bookname }" readonly="readonly">
				</c:forEach>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label">publisher</label>
			<div class="col-sm-10">
				<c:forEach items="${booklist }" var="book">
					<input type="text" class="form-control" name="publisher" value="${book.publisher }" readonly="readonly">
				</c:forEach>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label">User Name</label>
			<div class="col-sm-10">
				<input type="text" class="form-control" name="username" value="${username }" readonly="readonly">
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label">Borrowing Time</label>
			<div class="col-sm-10">
				<div class="radio-inline">
					<input type="radio" name="time" value="15" required="required">15 days
					<input type="radio" name="time" value="30" required="required" style="margin-left:20px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;30 days
				</div>
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<div class="checkbox">
					<label> <input type="checkbox" required="required">Remember me
					</label>
				</div>
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<button type="submit" class="btn btn-default">Submit</button>
			</div>
		</div>
	</form>
<script type="text/javascript">
function check(){
	var overtime = document.getElementById('overtime').value;
	var hasrent = document.getElementById('hasrent').value;
	var rentonly = document.getElementById('rentonly').value;
	if(parseInt(overtime) > 0){
		alert("You need to pay off your overtime books!");
		return false;
	}
	if(parseInt(hasrent) >= 3){
		alert("You have borrowed three books!");
		return false;
	}
	if(parseInt(rentonly)){
		alert("You can only borrow this book once!");
		return false;
	}
}
</script>
</body>
</html>