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
	
	<form action="/login/loginCheck">
		<p>/login/loginCheck -> /course/list(main에서) 으로 이동</p>
		<button>
			로그인
		</button>
	</form>
	
</body>
</html>