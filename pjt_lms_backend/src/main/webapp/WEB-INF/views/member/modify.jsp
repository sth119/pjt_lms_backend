<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>modify.jsp</title>
</head>
<body>
	<h3><%= request.getRequestURI() %></h3>
	<hr>
	
	<p>팝업</p>
	<form action="/member/student">
		<p>세부정보 /member/student 으로 이동</p>
		<p>이전화면으로 돌아가는 기능 필요</p>
		<button>
			수정완료
		</button>
	</form>
	
	<form action="/member/student">
		<p>세부정보 /member/student 으로 이동</p>
		<p>이전화면으로 돌아가는 기능 필요</p>
		<button>
			수정취소
		</button>
	</form>
	
	<form action="/file/choose">
		<p>파일선택 /file/choose 으로 이동</p>
		<p>이전화면으로 돌아가는 기능 필요</p>
		<button>
			파일선택
		</button>
	</form>
	
</body>
</html>