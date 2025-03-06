package org.zerock.myapp.persistence.data.jpa;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.myapp.entity.Board;
import org.zerock.myapp.persistence.BoardRepository;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;



//예시

@Slf4j
@NoArgsConstructor


@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)

@Transactional

@SpringBootTest
public class BoardRepositoryTests {
	@Resource private BoardRepository dao;
	
	@PostConstruct
	void postConstruct() {
		log.debug("postConstruct() invoked.");
		log.info("\t+ this.dao: {}, type: {}",this.dao, this.dao.getClass().getSimpleName());
	} // postConstruct
	
	@Disabled
	@Tag("BoardRepository-Test")
	@Order(0)
	@Test
//	@RepeatedTest(0)
	@DisplayName("")
	@Timeout(value = 1L,unit = TimeUnit.SECONDS)
	void context() {
		log.debug("context() invoked.");
		
		// 
		
	} // context
	
	
	
	@Disabled
	@Tag("BoardRepository-Test")
	@Order(1)
	@Test
//	@RepeatedTest(0)
	@Rollback(false)
	@DisplayName("1. testCreate")
	@Timeout(value = 1L,unit = TimeUnit.SECONDS)
	void testCreate() {
		log.debug("testCreate() invoked.");
		
		// 1
		Board board = new Board();
		board.setContent("JPA Title");
		board.setTitle("Studying Spring Data JPA");
		board.setWriter("성락");
		
		log.info("\t+ Before: {}",board); // PK 속성값이 null
		
		// 2
		this.dao.save(board);
		log.info("\t+ After: {}",board); // PK 속성값이 들어감
	} // testCreate
	
	@Disabled
	@Tag("BoardRepository-Test")
	@Order(1)
	@Test
//	@RepeatedTest(0)
	@Rollback(false)
	@DisplayName("2. testRead") // 게시글 하나 상세조회 != 목록조회
	@Timeout(value = 1L,unit = TimeUnit.SECONDS)
	void testRead() {
		log.debug("testRead() invoked.");
		
		// 1
		final Long seq = 159L;
		Optional<Board> valueObj = this.dao.findById(seq);
		
		// 2
		valueObj.ifPresent(b -> log.info(b.toString()));
		//log.info("\t+ opt: {}",valueObj);
		
		
	} // testRead
	
	@Disabled
	@Tag("BoardRepository-Test")
	@Order(1)
	@Test
//	@RepeatedTest(0)
	@Rollback(false)
	@DisplayName("3. testUpdate") // 게시글 하나 상세조회 != 목록조회
	@Timeout(value = 1L,unit = TimeUnit.SECONDS)
	void testUpdate() {
		log.debug("testUpdate() invoked.");
		
		// 1
		final Long seq = 159L;
		Optional<Board> valueObj = this.dao.findById(seq);
		
		// 2
		valueObj.ifPresent(b -> {
			b.setTitle("JPA Modified");
			b.setContent(b.getContent() + " - Modified");
			b.setWriter("바뀐성락");
		});
	} // testUpdate
	
	@Disabled
	@Tag("BoardRepository-Test")
	@Order(1)
	@Test
//	@RepeatedTest(0)
	@Rollback(false)
	@DisplayName("4. testDelete") // 게시글 하나 상세조회 != 목록조회
	@Timeout(value = 1L,unit = TimeUnit.SECONDS)
	void testDelete() {
		log.debug("testDelete() invoked.");
		
		// 1
		final Long seq = 159L;
		this.dao.deleteById(seq);
	} // testDelete
	
//	@Disabled
	@Tag("BoardRepository-Test")
	@Order(1)
	@Test
//	@RepeatedTest(0)
	@Rollback(false)
	@DisplayName("5. testLIST") // 게시글 하나 상세조회 != 목록조회
	@Timeout(value = 1L,unit = TimeUnit.SECONDS)
	void testLIST() {
		log.debug("testLIST() invoked.");
		
		// 1
		List<Board> list = this.dao.findAll();
		
		// 2
		list.forEach(p -> log.info(p.toString()));
		
	} // testLIST
	
} // end class
