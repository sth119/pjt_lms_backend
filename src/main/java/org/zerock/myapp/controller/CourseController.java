package org.zerock.myapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.myapp.persistence.CourseRepository;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@NoArgsConstructor

@RequestMapping("/course/*") // Base URI
@RestController
// 과정 URI 컨트롤러

public class CourseController {
	@Autowired CourseRepository repo;
	
	
	   @PostMapping("/list ")
	   String upcoming() { // 과정 진행예정 리스트
	      log.debug("list() invoked.");
	      
	      return "/list";
	   } // list
	   
	   @PostMapping("/register")
	   String registration() { // 과정 등록
	      log.debug("register() invoked.");
	      
	      return "/register";
	   } // register
	   	   
	   @PostMapping("/read")
	   String finished() { // 과정 종료 리스트
	      log.debug("read() invoked.");
	      
	      return "/read";
	   } // read
	   
	   
	   @PostMapping("/update")
	   String ongoing() { // 과정 진행중 리스트
	      log.debug("update() invoked.");
	      
	      return "/update";
	   } // update
	   
	   
	   @PostMapping("/delete ")
	   String searched_list() { // 과정 검색
	      log.debug("delete() invoked.");
	      
	      return "/delete";
	   } // delete
	   
	   


} // end class
