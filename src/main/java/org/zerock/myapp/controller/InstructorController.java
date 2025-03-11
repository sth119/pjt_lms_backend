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
	String list() { // 관리자 리스트
		
		log.debug("list() invoked.");
		
		return "/list";
	} // list
	
	@PostMapping("/register")
	String register() {
		log.debug("register() invoked.");
		
		return "/register";
	}//register
	
	@PostMapping("/read")
	String read() { // 관리자 검색
		log.debug("read() invoked.");
		
		return "/read";
	} // read

	@PostMapping("/update")
	String update() {
		log.debug("update() invoked.");
		
		return "/update";
	}//update
	
	@PostMapping("/delete")
	String delete() {
		log.debug("delete() invoked.");
		
		return "/delete";
	}//delete
	
} // end class
