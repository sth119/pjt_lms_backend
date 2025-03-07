package org.zerock.myapp.controller;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor

@RequestMapping("/course") // Base URI

// 과정 URI 컨트롤러
//@RestController
@Controller
public class CourseController {
	@Resource(name = "jdbcTemplate", type=JdbcTemplate.class) // 의존성 주입
	private JdbcTemplate jdbcTemplate; 

	@PostConstruct
	void postConstruct() { // 전처리
		log.debug("postConstruct() invoked.");
		log.info("\t+ this.jdbcTemplate: {}",this.jdbcTemplate);
	} // postConstruct
	
	

	@GetMapping("/list") // 과정 조회, LIST, 화면
	void searchList() {
		log.debug("list() invoked.");
		
		// search 하는 기능 추가해야함
		// 진행예정,진행중,종료도 여기 추가
		// ?검색항목=검색어
		
		// return List<VO>
	} // searchList
	
	
	@GetMapping("/registration") // 과정 등록/수정, 화면 이동
	void registration() {
		log.debug("registration() invoked.");
		
		// 매개변수가 PK값.
		// 매개변수가 없을 경우 = 등록
		// 매개변수가 있을 경우 = 수정, 데이터 SELECT해서 가져옴
		
		// return null or VO
	} // registration
	
	@GetMapping("/registrationSave") // 과정 등록/수정 처리 결과
	void registrationSave() {
		log.debug("registrationSave() invoked.");
		
		// 매개변수 DTO
		
		// return true/false
	} // registration
	
	@GetMapping("/detail") // 과정 세부조회, R , 화면 이동
	void detail() {
		log.debug("detail() invoked.");
		
		// PK받아서 세부조회 화면으로 넘어감.
		
		// return VO
	} // detail
	
	@GetMapping("/delete") // 과정 삭제, D
	void delete() {
		log.debug("delete() invoked.");
		
		// 매개변수 DTO
		
		// return true/false
	} // delete

} // end class
