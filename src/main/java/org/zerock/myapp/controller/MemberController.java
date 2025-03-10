package org.zerock.myapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.myapp.domain.CourseDTO;
import org.zerock.myapp.domain.MemberDTO;
import org.zerock.myapp.service.MemberService;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor

@RequestMapping("/member/*") // Base URI

// 회원 URI 컨트롤러
@RestController
public class MemberController {
	@Autowired private MemberService service;

    
	@PostMapping("/list") // 멤버 리스트 LIST
	List<String> list(String memberCode) {
		log.debug("list({}) invoked.",memberCode);
		
		return null;
	} // list
	
    @PostMapping("/registration") // 회원등록
    ResponseEntity<?> register(@RequestBody MemberDTO dto) {
    	log.debug("register({}) invoked.",dto);
        
    	try {
    	this.service.registerMember(dto);
    	
    	return ResponseEntity.ok("회원가입이 완료되었습니다!");
    	} catch (IllegalArgumentException e) {
    		// 유효성 검사 실패 등 비즈니스 로직 예외 처리
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원가입 실패: " + e.getMessage());
        } catch (Exception e) {
            // 예상치 못한 서버 오류 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류로 인해 회원가입에 실패했습니다.");
        } // try-catch
    } // 회원가입 
	
    @PostMapping(path = "/check-id") // Login 으로 옮길 것
    ResponseEntity<String> checkIdDuplicate(@RequestParam String memberId) {// 아이디 중복 검증
    	log.debug("checkIdDuplicate({}) invoked.",memberId);
    	
        String resultMessage = this.service.checkIdDuplicate(memberId);
        
        if("이미 사용 중인 아이디입니다.".equals(resultMessage)) {
        	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resultMessage);
        } // if
        return ResponseEntity.ok(resultMessage);
    } // checkIdDuplicate
    
	// 위의 회원가입 로직과 중복되면 삭제할 것
	@PostMapping(path = {"/registrationPage", "/modifyPage"}) // 맴버 등록/수정 화면
	String registrationPage(String memberCode) {
		log.debug("registrationPage({}) invoked.",memberCode);
		
		// memberCode가 null 이면 /regist , 
		// null 이 아니면 /modify 로 이동한다
		
		return null;
	} // registrationPage
	
	@PostMapping(path = {"/registration", "/modify"}, params = "dto") // 맴버 등록/수정 처리 실행
	Boolean registration(MemberDTO dto) {
		log.debug("registration({}) invoked.",dto);
		
		 return true;
	} // registration
	
	@PostMapping(path = "/delete", params = "memberCode") // 맴버 삭제, D , List로 이동
	Boolean delete(String memberCode) {
		log.debug("delete({}) invoked.",memberCode);
		
		 return true;
	} // delete
	
} // end class
