package org.zerock.myapp.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.myapp.domain.CourseDTO;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor

@RequestMapping("/instructor/*") // Base URI
@RestController

public class InstructorController { // 강사 관리

	
	@PostMapping("/list")
	String instructorList() {
		
		return "instructor List return";
	} // instructorList
	
	@PostMapping("/list")
	String instructorSearchedList() {
		
		return "instructorSearched List return";
	} // instructorSearchedList

} // end class
