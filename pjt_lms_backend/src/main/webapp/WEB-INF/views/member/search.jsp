<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>search.jsp</title>
</head>
<body>
	<h3><%= request.getRequestURI() %></h3>
	<hr>
	
	<form action="/member/student">
		<p>검색끝 /member/student 으로 이동</p>
		<p>이전화면으로 돌아가는 기능 필요</p>
		<button>
			검색완료
		</button>
	</form>
	
</body>
</html>