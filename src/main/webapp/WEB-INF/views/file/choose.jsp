<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>choose.jsp</title>
</head>
<body>
	<h3><%= request.getRequestURI() %></h3>
	<hr>
	
	<form action="/course/list">
		<p>과정메인 /course/main 으로 이동</p>
		<p>파일을 가져오는 곳은 /course/detail 이랑 /member/registration , /member/modify 다</p>
		<p>이후에 수정 필요</p>
		<button>
			파일선택완료
		</button>
	</form>
	
</body>
</html>