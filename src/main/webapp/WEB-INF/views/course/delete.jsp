<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>delete.jsp</title>
</head>
<body>
	<h3><%= request.getRequestURI() %></h3>
	<hr>
	
	<p>팝업</p>
	<form action="/course/list">
		<p>삭제완료 /course/main 으로 이동</p>
		<button>
			삭제완료
		</button>
	</form>
	
</body>
</html>