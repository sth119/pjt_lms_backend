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

@RequestMapping("/course/*") // Base URI

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
	
	
	// 진행예정, 진행중, 종료를 다 여기 적어야 하는가?
	// 일단 임시로 진행예정,진행중,종료를 묶어서 /course/main 으로 반환하겠다
	@GetMapping("/main") // 메인화면
	void main() {
		log.debug("main() invoked.");
		
	} // main
	
	
	@GetMapping("/registration") // 과정 등록, C
	void registration() {
		log.debug("registration() invoked.");
		
	} // registration
	
	@GetMapping("/search") // 과정 조회, R, LIST?
	void search() {
		log.debug("search() invoked.");
		
	} // search
	
	@GetMapping("/detail") // 과정 세부조회, R
	void detail() {
		log.debug("detail() invoked.");
		
	} // detail
	
	@GetMapping("/modify") // 과정 수정, U
	void modify() {
		log.debug("modify() invoked.");
		
	} // modify
	
	@GetMapping("/delete") // 과정 삭제, D
	void delete() {
		log.debug("delete() invoked.");
		
	} // delete

} // end class
