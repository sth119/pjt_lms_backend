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

	@PostMapping("/list")
	String list() {
		
		return "list return";
	} // list
	
	
	@PostMapping("/registration")
	String registration() {
		
		return "registration return";
	}
	
	@PostMapping("/searched_list ")
	String searched_list() {
		
		return "searched_list return";
	} // list
	
	
	@PostMapping("/upcoming ")
	String upcoming() {
		
		return "upcoming return";
	} // list
	
	
	@PostMapping("/ongoing")
	String ongoing() {
		
		return "ongoing return";
	} // list
	
	
	@PostMapping("/finished")
	String finished() {
		
		return "finished return";
	} // list
	
	
	@PostMapping("/deletion")
	String deletion() {
		
		return "deletion return";
	} // list
	
	
	@PostMapping("/modification")
	String modification() {
		
		return "modification return";
	} // list

} // end class
