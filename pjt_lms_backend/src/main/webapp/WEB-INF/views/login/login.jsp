<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>login.jsp</title>
</head>
<body>
	<h3><%= request.getRequestURI() %></h3>
	<hr>
	
	<form action="/course/main">
		<p>/course/main 으로 이동</p>
		<button>
			로그인
		</button>
	</form>
	
</body>
</html>