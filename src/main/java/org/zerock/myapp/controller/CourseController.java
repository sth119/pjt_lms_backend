package org.zerock.myapp.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@NoArgsConstructor

@RequestMapping("/course/*") // Base URI
@RestController
// 과정 URI 컨트롤러

public class CourseController {
	
	
	@PostMapping("/registration")
	String registration() { // 과정 등록
		log.debug("Course registration() invoked.");
		
		return "registration return";
	}
	
	@PostMapping("/searched_list ")
	String searched_list() { // 과정 검색
		log.debug("Course searched_list() invoked.");
		
		return "searched_list return";
	} // list
	
	
	@PostMapping("/upcoming ")
	String upcoming() { // 과정 진행예정 리스트
		log.debug("Course upcoming() invoked.");
		
		return "upcoming return";
	} // list
	
	
	@PostMapping("/ongoing")
	String ongoing() { // 과정 진행중 리스트
		log.debug("Course ongoing() invoked.");
		
		return "ongoing return";
	} // list
	
	
	@PostMapping("/finished")
	String finished() { // 과정 종료 리스트
		log.debug("Course finished() invoked.");
		
		return "finished return";
	} // list
	
	
	@PostMapping("/deletion")
	String deletion() { // 과정 삭제
		log.debug("Course deletion() invoked.");
		
		return "deletion return";
	} // list
	
	
	@PostMapping("/modification")
	String modification() { // 과정 수정
		log.debug("Course modification() invoked.");
		
		return "modification return";
	} // list

} // end class
