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

@RequestMapping("/trainee/*") // Base URI

@RestController
public class TraineeController {  // 훈련생 관리

	@PostMapping("/list")
	String traineeList() {  // 훈련생 리스트
		log.debug("Trainee list() invoked.");
		
		return "trainee List return";
	} // traineeList
	
	
	@PostMapping("/searched_list")
	String traineeSearchedList() { // 검색 기능
		log.debug("searched_list() invoked.");
		
		return "trainee SearchedList return";
	} // traineeSearchedList

} // end class
