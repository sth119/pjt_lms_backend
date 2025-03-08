package org.zerock.myapp.controller;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor

@RequestMapping("/file/*") // Base URI

//파일 URI 컨트롤러
@RestController
//@Controller
public class FileController {
	@Resource(name = "jdbcTemplate", type=JdbcTemplate.class) // 의존성 주입
	private JdbcTemplate jdbcTemplate;

	@PostConstruct
	void postConstruct() { // 전처리
		log.debug("postConstruct() invoked.");
		log.info("\t+ this.jdbcTemplate: {}",this.jdbcTemplate);
	} // postConstruct
	
	//사진을 불러올 자리는 /list
	//.jsp의 registration modify
	// registration은 처음엔 null이었다가
	// 사진을 올리면 등록된 사진이 choose에서 올라가도록
	
	
	// 사진을 골라서 불러온다는 메소드?라고 생각하고 진행
	// /WEB-INF/views/member/registration.jsp (팝업)
	// /file/choose 으로 이동
	// 이전화면으로 돌아가는 기능 필요파일선택

	//로긴하면 나오는 스보2 뷰(진행${})에 나오는 것들
	// 사진이미지 나오는 공간
	
	// 과정을 선택하면 스보의 2-1(과정정보조회)에서 나오는 사진들
	// 과정사진1, 훈련생사진1, 훈련생사진2.
	// 파일 선택 버튼이 아니고 사진이지미 나오는 공간 필요
	
	//2-2 과정등록수정 삭제에 과정사진1
	
	//3-1 회원등록수정 삭제에 ${구분}회원
	
	@GetMapping("/choose") // 메인화면 파일을 불러옴 처음엔 Null
	void choose() {
		log.debug("choose() invoked.");
		
		// return 이미지데이터
	} // choose
	
	//---------------------------------------------------
	//멤버등록/수정과 과정등록때 들어갈 사진을 올릴 코딩-
	//---------------------------------------------------
	// .jsp의 registration modify에 등록할 사진이 나오는 곳
	// 
	
	// 여기는 업로드 화면임.
	// 저장할때는 시퀀스 앞에
	// 과정, 훈련생, 강사, 관리자를 표시할 분을 넣어야함.
	// "과_"+seq, "훈_"+seq, "강_"+seq, "관_"+seq.
	// 시퀀스를 4개 만들어야함.
	//		IntStream.rangeClosed(1, 30).forEach(seq ->{
	//	String name="NAME_" +seq;
	//	Integer age=23+seq;
	//	log.info("\t+ name({}),age({})",name,age);
	
	
	

} // end class
