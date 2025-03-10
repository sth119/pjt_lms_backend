<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>registration.jsp</title>
</head>
<body>
	<h3><%= request.getRequestURI() %></h3>
	<hr>
	
	<form action="/login/logout">
		<p>로그아웃 /login/logout 으로 이동</p>
		<button>
			로그아웃 버튼
		</button>
	</form>
	
	<form action="/course/list">
		<p>진행예정,진행중,종료 /course/main 으로 이동</p>
		<button>
			진행예정,진행중,종료
		</button>
	</form>
	
	<form action="/member/student">
		<p>훈련생 /member/student 으로 이동</p>
		<button>
			훈련생
		</button>
	</form>
	
	<form action="/member/instructor">
		<p>강사 /member/instructor 으로 이동</p>
		<button>
			강사
		</button>
	</form>
	
	<form action="/member/manager">
		<p>관리자 /member/manager 으로 이동</p>
		<button>
			관리자
		</button>
	</form>
	
	
	// ===========================
	
	<form action="/course/main">
		<p>메인 /course/main 으로 이동</p>
		<button>
			등록완료
		</button>
	</form>
	
	<form action="/course/main">
		<p>메인 /course/main 으로 이동</p>
		<button>
			등록취소
		</button>
	</form>
	
	<form action="/file/choose">
		<p>파일선택 /file/choose 으로 이동</p>
		<button>
			파일선택
		</button>
	</form>
	
</body>
</html>