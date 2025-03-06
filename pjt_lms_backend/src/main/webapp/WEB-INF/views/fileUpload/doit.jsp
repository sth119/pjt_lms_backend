<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>doit.jsp</title>
</head>
<body>
	<h3><%= request.getRequestURI() %></h3>
	<hr>
	<p>업로드가 완료되었습니다.</p>
	<p>&nbsp;</p>
	<form action="/fileUpload/upload">
		<button>이전으로 돌아가기</button>
	</form>
	
</body>
</html>