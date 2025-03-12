package org.zerock.myapp.controller;

import java.util.List;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.myapp.entity.Course;
import org.zerock.myapp.persistence.CourseRepository;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@NoArgsConstructor

@RequestMapping("/course/*") // Base URI
@RestController
// 과정 URI 컨트롤러

public class CourseController {
//	@Autowired CourseRepository repo;
	
		
	   @PostMapping("/list")
	   List<Object> list(String type) { // 리스트
	      log.debug("list() invoked.");
	      Course course = new Course();
	      
	      // 코스를 담당하는 강사 정보를 전부 찍고 싶을때 사용.
	      // course.getInstructor();
	      
	      List<Object> list = new Vector<>();
	      list.add(course.getStatus());
	      list.add(course.getType());
	      list.add(course.getName());
	      
	      list.add(course.getInstructor().getName());
	      
	      return list;
	   } // list
	   
	   @PostMapping("/register")
	   String register() { // 과정 등록
	      log.debug("register() invoked.");
	      
	      return "/register";
	   } // register
	   	   
	   @PostMapping("/read")
	   String read() { // 과정 종료 리스트
	      log.debug("read() invoked.");
	      
	      return "/read";
	   } // read
	   
	   
	   @PostMapping("/update")
	   String ongoing() { // 과정 진행중 리스트
	      log.debug("update() invoked.");
	      
	      return "/update";
	   } // update
	   
	   
	   @PostMapping("/delete ")
	   String delete() { // 과정 삭제
	      log.debug("delete() invoked.");
	      
	      return "/delete";
	   } // delete
	   
	   


} // end class
