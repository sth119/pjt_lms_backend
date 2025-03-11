package org.zerock.myapp.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor

@RequestMapping("/instructor/*") // Base URI
@RestController

public class InstructorController { // 강사 관리

	
	@PostMapping("/list")
	String instructorList() { // 관리자 리스트
		
		log.debug("instructorList() invoked.");
		
		return "instructor List return";
	} // instructorList
	
	@PostMapping("/list")
	String instructorSearchedList() { // 관리자 검색
		log.debug("instructorSearchedList() invoked.");
		
		return "instructorSearched List return";
	} // instructorSearchedList

} // end class
