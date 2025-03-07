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


// 로그인 URI 컨트롤러
//@RestController
@Controller
public class LoginController {
	@Resource(name = "jdbcTemplate", type=JdbcTemplate.class) // 의존성 주입
	private JdbcTemplate jdbcTemplate;
	
	@PostConstruct
	void postConstruct() { // 전처리
		log.debug("postConstruct() invoked.");
		log.info("\t+ this.jdbcTemplate: {}",this.jdbcTemplate);
	} // postConstruct
	
	
	// 로그인 화면
	// 이후에 인증 성공과 인증 실패도 만들어야 하는가?
	@GetMapping("/login")
	void login() {
		log.debug("login() invoked.");
		
	} // login
	
	// 로그인 처리, /course/main 으로 이동
	@GetMapping("/loginCheck")
	String loginCheck() {
		log.debug("loginCheck() invoked.");
		
		return "redirect:/course/main";
	} // loginCheck
	
	// 로그아웃 처리, 바로 /login 으로 넘어간다.
	@GetMapping("/logout")
	String logout() {
		log.debug("logout() invoked.");
		
		 // 세션 무효화, 이렇게 적으면 바로 세션이 끝난다고 함(GPT)
//	    request.getSession().invalidate();
		
		return "redirect:/login/login";
	} // logout

} // end class
