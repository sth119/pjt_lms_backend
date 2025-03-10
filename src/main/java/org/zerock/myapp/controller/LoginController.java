package org.zerock.myapp.controller;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.myapp.domain.MemberDTO;
import org.zerock.myapp.entity.Member;
import org.zerock.myapp.service.MemberServiceImpl;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor

@RequestMapping("/login/*") // Base URI

@RestController
public class LoginController {
	@Autowired private MemberServiceImpl memberServiceimpl;
	

	@GetMapping(path = "/login")
	void login(){ // 로그인 화면
	} // login 
	
	// 로그인 처리, /course/main 으로 이동
	@PostMapping(path = "/loginCheck")
	ResponseEntity<?> loginCheck(@RequestBody MemberDTO dto) { // <MemberEntity> , 서로 다른 타입을 반환할 경우 <?> 를 사용.
		log.debug("loginCheck({}) invoked.",dto);
		
		 try {
		        Optional<Member> member = memberServiceimpl.login(dto.getMemberId(), dto.getMemberPassword());

		        if (member.isPresent()) {
		            // 로그인 성공 시 회원 정보 반환
		            return ResponseEntity.ok(member.get());
		        } else {
		            // 로그인 실패 시 리다이렉트
		            return ResponseEntity
		                    .status(HttpStatus.FOUND)
		                    .location(URI.create("/course/list")) // 리다이렉트 주소
		                    .build();
		        } // if-else
		    } catch (IllegalArgumentException e) {
		        // 예외 메시지 반환
		        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		    } // try-catch
	} // loginCheck
	
	// 로그아웃 처리, 바로 /login 으로 넘어간다.
	@GetMapping(path = "/logout")
	String logout() {
		log.debug("logout() invoked.");
		
		 // 세션 무효화, 이렇게 적으면 바로 세션이 끝난다고 함(GPT)
//	    request.getSession().invalidate();
		
		return "redirect:/login/login";
	} // logout

} // end class
