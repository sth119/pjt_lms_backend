package org.zerock.myapp.controller;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor

@RequestMapping("/login/*") // Base URI

//@RestController
@Controller
public class LoginController {
	@Resource(name = "jdbcTemplate", type=JdbcTemplate.class)
	private JdbcTemplate jdbcTemplate;
	
	@PostConstruct
	void postConstruct() {
		log.debug("postConstruct() invoked.");
		log.info("\t+ this.jdbcTemplate: {}",this.jdbcTemplate);
	} // postConstruct
	
	
	@GetMapping("/login")
	void login() {
		log.debug("login() invoked.");
		
	} // login
	
	@GetMapping("/logout")
	void logout() {
		log.debug("logout() invoked.");
		
	} // logout

} // end class
