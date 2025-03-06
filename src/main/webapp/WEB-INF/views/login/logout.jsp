<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>logout.jsp</title>
</head>
<body>
	<h3><%= request.getRequestURI() %></h3>
	<hr>
	 
	 <form action="/login/login">
	 	<p>로그아웃 완료(나중엔 이 화면은 안 나옴)</p>
	 	<p>로그인 /login/login 으로 이동</p>
		<button>
			로그아웃
		</button>
	</form>
	
</body>
</html>