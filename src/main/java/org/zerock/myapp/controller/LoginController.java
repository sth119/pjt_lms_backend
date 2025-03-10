package org.zerock.myapp.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.myapp.domain.MemberDTO;
import org.zerock.myapp.entity.Member;
import org.zerock.myapp.service.MemberServiceImpl;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j


@RequestMapping("/login/*") // Base URI
@RestController
//@Controller
@RequiredArgsConstructor
public class LoginController {
	
	private final MemberServiceImpl memberServiceimpl;
	
	
	@Resource(name = "jdbcTemplate", type=JdbcTemplate.class) // 의존성 주입
	private JdbcTemplate jdbcTemplate;
	
	@PostConstruct
	void postConstruct() { // 전처리
		log.debug("postConstruct() invoked.");
		log.info("\t+ this.jdbcTemplate: {}",this.jdbcTemplate);
	} // postConstruct
	
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody MemberDTO dto){ // <MemberEntity> , 서로 다른 타입을 반환할 경우 <?> 를 사용.
			
	     try {
	    	 
	            Optional<Member> member = memberServiceimpl.login(dto.getMemberId(), dto.getMemberPassword());
	            return ResponseEntity.ok(member); // 로그인 성공 시 회원 정보 반환 (회원 정보 수정때 사용할 정보들 )
//	            return ResponseEntity
//	            		.status(HttpStatus.FOUND)
//	            		.location(URI.create("/home")) // redirect 주소
//	            		.build(); // 백엔드 만 있을시 redirect 설정 
	            
	        } catch (IllegalArgumentException e) {
	        	
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage()); // 예외 메시지 반환
		}

	} // 로그인 
	
	// 로그인 처리, /course/main 으로 이동
	@GetMapping("/loginCheck")
	String loginCheck() {
		log.debug("loginCheck() invoked.");
		
		return "redirect:/course/list";
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
