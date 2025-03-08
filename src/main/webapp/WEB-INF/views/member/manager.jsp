<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>manager.jsp</title>
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
		<p>진행예정,진행중,종료 /course/list 으로 이동</p>
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
	
	
	// ============================================
	
	<form action="/member/registration">
		<p>멤버등록 /member/registration 으로 이동</p>
		<button>
			멤버등록
		</button>
	</form>
	
	<form action="/member/search">
		<p>멤버검색 /member/search 으로 이동</p>
		<button>
			멤버검색
		</button>
	</form>
	
	<form action="/member/modify">
		<p>멤버수정 /member/modify 으로 이동</p>
		<button>
			멤버수정
		</button>
	</form>
	
	<form action="/member/delete">
		<p>멤버삭제 /member/delete 으로 이동</p>
		<button>
			멤버삭제
		</button>
	</form>
	
</body>
</html>