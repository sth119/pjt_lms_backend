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
	String userRegistration() {
		
		return "userRegistration return";
	} // adminList
	
	
	@PostMapping("/modification")
	String userModification() {
		
		return "user Modification return";
	} // adminSearchedList
	
	
	@PostMapping("/deletion")
	String userDeletion() {
		
		return "user Deletion return";
	} // userDeletion

} // end class
