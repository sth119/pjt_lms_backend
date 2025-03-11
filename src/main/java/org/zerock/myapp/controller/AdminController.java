package org.zerock.myapp.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.myapp.domain.CourseDTO;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor

@RequestMapping("/admin/*") // Base URI

@RestController
public class AdminController {  // 관리자 관리

	@PostMapping("/list")
	String adminList() {
		
		return "admin List return";
	} // adminList
	
	@PostMapping("/list")
	String adminSearchedList() {
		
		return "admin Searched List return";
	} // adminSearchedList
	
	

} // end class
