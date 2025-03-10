<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>detail.jsp</title>
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
	
	// ==========================
	
	<form action="/course/list">
		<p>목록페이지 /course/main 으로 이동</p>
		<button>
			목록페이지
		</button>
	</form>
	
	<form action="/course/modify">
		<p>수정페이지 /course/modify 으로 이동</p>
		<button>
			수정페이지
		</button>
	</form>

	<form action="/course/delete">
		<p>삭제페이지 /course/delete 으로 이동</p>
		<button>
			삭제페이지
		</button>
	</form>
	
	<p>훈련생 펼치기/접기 추가하기</p>
	
	<form action="/member/student">
		<p>과정에서 멤버 등록 취소 /member/student 으로 이동</p>
		<p>이후에 삭제되는 링크로 옮겨야함</p>
		<button>
			등록 취소
		</button>
	</form>
	
</body>
</html>