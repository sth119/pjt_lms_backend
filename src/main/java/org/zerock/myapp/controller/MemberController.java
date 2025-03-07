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

@RequestMapping("/member/*") // Base URI

// 회원 URI 컨트롤러
//@RestController
@Controller
public class MemberController {
	@Resource(name = "jdbcTemplate", type=JdbcTemplate.class) // 의존성 주입
	private JdbcTemplate jdbcTemplate;
	
	@PostConstruct
	void postConstruct() { // 전처리
		log.debug("postConstruct() invoked.");
		log.info("\t+ this.jdbcTemplate: {}",this.jdbcTemplate);
	} // postConstruct
	
	// 멤버도 main 을 만들어야 할 것 같다
	
	// 훈련생 강사 관리자 버튼은, 멤버 조회(/search)에 조건 추가한 방식으로 만들면 될 듯
	@GetMapping("/student") // 훈련생
	void student() {
		log.debug("student() invoked.");
		
	} // student
	
	@GetMapping("/instructor") // 강사
	void instructor() {
		log.debug("instructor() invoked.");
		
	} // instructor
	
	@GetMapping("/manager") // 관리자
	void manager() {
		log.debug("manager() invoked.");
		
	} // manager
	
	//=============================
	
	@GetMapping("/registration") // 멤버 등록, C
	void registration() {
		log.debug("registration() invoked.");
		
	} // registration
	
	@GetMapping("/search") // 멤버 조회, R, LIST?
	void search() {
		log.debug("search() invoked.");
		
	} // search
	
	@GetMapping("/modify") // 멤버 수정, U
	void modify() {
		log.debug("modify() invoked.");
		
	} // modify
	
	@GetMapping("/delete") // 멤버 삭제, D
	void delete() {
		log.debug("delete() invoked.");
		
	} // delete
	
} // end class
