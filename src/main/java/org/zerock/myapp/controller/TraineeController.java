package org.zerock.myapp.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor

@RequestMapping("/trainee/*") // Base URI

@RestController
public class TraineeController {  // 훈련생 관리

	@PostMapping("/list")
	String list() {  // 훈련생 리스트
		log.debug("list() invoked.");
		
		return "/list";
	} // list
	
	
	@PostMapping("/register")
	String register() { // 검색 기능
		log.debug("register() invoked.");
		
		return "/register";
	} // register
	
	@PostMapping("/read")
	String read() { // 검색 기능
		log.debug("read() invoked.");
		
		return "/read";
	} // read

	@PostMapping("/update")
	String update() { // 검색 기능
		log.debug("update() invoked.");
		
		return "/update";
	}
	
	@PostMapping("/delete")
	String delete() { // 검색 기능
		log.debug("delete() invoked.");
		
		return "/delete";
	} // delete
	
} // end class
