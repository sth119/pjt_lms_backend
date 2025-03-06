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

@RequestMapping("/file/*") // Base URI

//@RestController
@Controller
public class FileController {
	@Resource(name = "jdbcTemplate", type=JdbcTemplate.class)
	private JdbcTemplate jdbcTemplate;

	@PostConstruct
	void postConstruct() {
		log.debug("postConstruct() invoked.");
		log.info("\t+ this.jdbcTemplate: {}",this.jdbcTemplate);
	} // postConstruct
	
	// 여긴 어떻게 해야 할지 잘 모르겠다
	@GetMapping("/choose") // 메인화면
	void choose() {
		log.debug("choose() invoked.");
		
	} // choose
	

} // end class
