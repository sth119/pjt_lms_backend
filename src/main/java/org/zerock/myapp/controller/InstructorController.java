package org.zerock.myapp.controller;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.myapp.domain.CourseDTO;
import org.zerock.myapp.domain.CriterionDTO;
import org.zerock.myapp.entity.Course;
import org.zerock.myapp.entity.Instructor;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor

@RequestMapping("/instructor/*") // Base URI
@RestController

public class InstructorController { // 강사 관리

	@GetMapping
	Page<Instructor> list(CriterionDTO dto){
		log.info("list: {}",dto);
		
		return null;
	}
	
	@PutMapping("{id}")
	Course register(@PathVariable Integer id) {
		log.info("register: {}",id);
		
		return null;
	}
	
	@PostMapping("{id}")
	Course update(@PathVariable Integer id, CourseDTO dto) {
		log.info("update: {},{}",id,dto);
		
		return null;
	}
	
	@DeleteMapping("{id}")
	Course delete(@PathVariable Integer id) {
		log.info("delete: {}",id);
		
		return null;
	}
	
//	@PostMapping("/list")
//	String list() { // 관리자 리스트
//		
//		log.debug("list() invoked.");
//		
//		return "/list";
//	} // list
//	
//	@PostMapping("/register")
//	String register() {
//		log.debug("register() invoked.");
//		
//		return "/register";
//	}//register
//	
//	@PostMapping("/read")
//	String read() { // 관리자 검색
//		log.debug("read() invoked.");
//		
//		return "/read";
//	} // read
//
//	@PostMapping("/update")
//	String update() {
//		log.debug("update() invoked.");
//		
//		return "/update";
//	}//update
//	
//	@PostMapping("/delete")
//	String delete() {
//		log.debug("delete() invoked.");
//		
//		return "/delete";
//	}//delete
	
} // end class
