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

@RequestMapping("/course") // Base URI

// 과정 URI 컨트롤러
@RestController
public class CourseController {
	// service 객체 주입 필요

	
	@PostMapping(path = "/list") // 과정 조회, LIST, 화면
	List<String> list(String id) { // 이후 Slice<> 로 바꾸기
		log.debug("list({}) invoked.",id);
		
		// search 하는 기능 추가해야함
		// 진행예정,진행중,종료도 여기 추가
		 return null;
	} // list
	
	@PostMapping(path = {"/registrationPage", "/modifyPage"}) // 과정 등록/수정 화면
	String registrationPage(String crsCode) {
		log.debug("registrationPage({}) invoked.",crsCode);
		
		// crsCode가 null 이면 /regist , 
		// null 이 아니면 /modify 로 이동한다
		
		return null;
	} // registrationPage
	
	@PostMapping(path = {"/regist", "/modify"}, params = "dto") // 과정 등록/수정 처리 실행
	Boolean regist(CourseDTO dto) {
		log.debug("regist({}) invoked.",dto);
		
		 return true;
	} // regist
	
	@PostMapping(path = "/detail", params = "crsCode") // 과정 세부조회, R , 화면 이동
	String detail(String crsCode) { // PK받아서 세부조회 화면으로 넘어감.
		log.debug("detail({}) invoked.",crsCode);
		
		return null;
	} // detail
	
	@PostMapping(path = "/delete", params = "crsCode") // 과정 삭제, D , List로 이동
	Boolean delete(String crsCode) {
		log.debug("delete({}) invoked.",crsCode);
		
		 return true;
	} // delete

} // end class
