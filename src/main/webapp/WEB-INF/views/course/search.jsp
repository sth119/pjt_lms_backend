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
	
	<form action="/course/main">
		<p>검색끝 /course/main 으로 이동</p>
		<button>
			검색완료
		</button>
	</form>
	
</body>
</html>