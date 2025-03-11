package org.zerock.myapp.controller;



import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor

@RequestMapping("/User/*") // Base URI


@RestController
public class UserController { // 회원관리

	@PostMapping("/registration")
	String userRegistration() { // 회원 등록
		log.debug("userRegistration() invoked.");
		
		return "userRegistration return";
	} // adminList
	
	
	@PostMapping("/modification")
	String userModification() { // 회원 수정
		log.debug("userModification() invoked.");
		
		return "user Modification return";
	} // adminSearchedList
	
	
	@PostMapping("/deletion")
	String userDeletion() { // 회원 삭제
		log.debug("userDeletion() invoked.");
		
		return "user Deletion return";
	} // userDeletion

} // end class

