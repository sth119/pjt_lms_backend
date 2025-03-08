<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>list.jsp</title>
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
		<p>진행예정,진행중,종료 /course/list(main) 으로 이동</p>
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
	
	<form action="/course/registration">
		<p>과정등록 /course/registration 으로 이동</p>
		<button>
			과정등록
		</button>
	</form>
	
	<form action="/course/search">
		<p>과정검색 /course/search 으로 이동</p>
		<button>
			과정검색
		</button>
	</form>
	
	<form action="/course/detail">
		<p>세부조회페이지 /course/detail 으로 이동</p>
		<button>
			세부조회페이지
		</button>
	</form>


	
</body>
</html>