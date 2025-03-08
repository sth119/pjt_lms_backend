package org.zerock.myapp.controller;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor

@RequestMapping("/file/*") // Base URI

//파일 URI 컨트롤러
@RestController
//@Controller
public class FileController {
	@Resource(name = "jdbcTemplate", type=JdbcTemplate.class) // 의존성 주입
	private JdbcTemplate jdbcTemplate;

	@PostConstruct
	void postConstruct() { // 전처리
		log.debug("postConstruct() invoked.");
		log.info("\t+ this.jdbcTemplate: {}",this.jdbcTemplate);
	} // postConstruct
	
	// 여긴 어떻게 해야 할지 잘 모르겠다
	// 사진을 골라서 불러온다는 메소드?라고 생각하고 진행
	@GetMapping("/choose") // 메인화면
	void choose() {
		log.debug("choose() invoked.");
		
		// return 이미지데이터
	} // choose
	

} // end class
