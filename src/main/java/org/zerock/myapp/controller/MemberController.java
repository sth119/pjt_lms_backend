package org.zerock.myapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.myapp.domain.MemberDTO;
import org.zerock.myapp.service.MemberServiceImpl;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j


@RequestMapping("/member/*") // Base URI

// 회원 URI 컨트롤러
@RestController
//@Controller
@RequiredArgsConstructor
public class MemberController {
	private final MemberServiceImpl memberServiceimpl;
	
	
	@Resource(name = "jdbcTemplate", type=JdbcTemplate.class) // 의존성 주입
	private JdbcTemplate jdbcTemplate;
	
	@PostConstruct
	void postConstruct() { // 전처리
		log.debug("postConstruct() invoked.");
		log.info("\t+ this.jdbcTemplate: {}",this.jdbcTemplate);
	} // postConstruct
	
	// 멤버도 main 을 만들어야 할 것 같다
	
	// 훈련생 강사 관리자 버튼은, 멤버 조회(/search)에 조건 추가한 방식으로 만들면 될 듯
	@GetMapping("/student") // 훈련생
	void student() {
		log.debug("student() invoked.");
		
	} // student
	
	@GetMapping("/instructor") // 강사
	void instructor() {
		log.debug("instructor() invoked.");
		
	} // instructor
	
	@GetMapping("/manager") // 관리자
	void manager() {
		log.debug("manager() invoked.");
		
	} // manager
	
	//=============================
	
    @PostMapping("/registration")
    public ResponseEntity<?> register(@RequestBody MemberDTO dto) {
        
    	try {
    		
    	memberServiceimpl.registerMember(dto);
    	return ResponseEntity.ok("회원가입이 완료되었습니다!");
    	
    	} catch (IllegalArgumentException e) {
    		
    		   // 유효성 검사 실패 등 비즈니스 로직 예외 처리
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원가입 실패: " + e.getMessage());
            
        } catch (Exception e) {
        	
            // 예상치 못한 서버 오류 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류로 인해 회원가입에 실패했습니다.");
            
        }
    } // 회원가입 
	
    @GetMapping("/check-id")
    public ResponseEntity<String> checkIdDuplicate(@RequestParam String memberId) {
        String resultMessage = memberServiceimpl.checkIdDuplicate(memberId);
        
        if("이미 사용 중인 아이디입니다.".equals(resultMessage)) {
        	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resultMessage);
        }
        
        return ResponseEntity.ok(resultMessage);
    } // 아이디 중복 검증. 
    
    
	
	
	@GetMapping("/registration") // 멤버 등록, C
	void registration() {
		log.debug("registration() invoked.");
		
	} // registration
	
	@GetMapping("/search") // 멤버 조회, R, LIST?
	void search() {
		log.debug("search() invoked.");
		
	} // search
	
	@GetMapping("/modify") // 멤버 수정, U
	void modify() {
		log.debug("modify() invoked.");
		
	} // modify
	
	@GetMapping("/delete") // 멤버 삭제, D
	void delete() {
		log.debug("delete() invoked.");
		
	} // delete
	
} // end class
