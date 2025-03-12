package org.zerock.myapp.persistence;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.myapp.entity.Course;
import org.zerock.myapp.entity.Trainee;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)

@Transactional

@SpringBootTest
public class CourseRepositoryTests {
	@Autowired private CourseRepository repo;
	
	@BeforeAll
	void beforeAll() throws SQLException {
		log.debug("CourseRepositoryTests -- beforeAll() invoked.");
		
		assertNotNull(this.repo);
		log.info("\t + this.repo: {}", this.repo);		
	}//beforeAll
	
	
	
//	@Disabled
	@Tag("Course-Repository-Test")
	@Order(1)
	@Test
//	@RepeatedTest(1)
	@DisplayName("1. create")
	@Timeout(value = 1L, unit = TimeUnit.SECONDS)
	@Rollback(false)
	void create() {
		log.debug("create() invoked");
		
		Course data = new Course();
		data.setName("AI를 활용한 JAVA 풀스택 과정");
		data.setType("fullstack");
		data.setCapacity(30);
		data.setDetail("열씨미 합시다!!!!");
		data.setStartDate("2024-10-18");
		data.setEndDate("2025-04-17");
		data.setStatus(1);
		data.setEnabled(true);
//		data.setCrtDate(new Date());		
		
		log.info("\t+ Before: {}", data);		// PK 속성값이 null
		
		data = this.repo.save(data);
		
		log.info("\t+ After: {}", data);		// 자동생성된 PK 속성값이 들어있다!!
		
	}//create
	
//	@Disabled
	@Tag("Course-Repository-Test")
	@Order(2)
	@Test
//	@RepeatedTest(1)
	@DisplayName("2. createList")
	@Timeout(value = 1L, unit = TimeUnit.SECONDS)
	@Rollback(false)
	void createList() {
		log.debug("createList() invoked");
		
		IntStream.rangeClosed(1, 10).forEach(seq -> {
			int random = new Random().nextInt(1, 5);
			
			Course course = new Course();			
			switch(random) {
				case 1 -> course.setType("frontend");
				case 2 -> course.setType("backend");
				case 3 -> course.setType("fullstack");
				case 4 -> course.setType("fullstack");					
			}
			course.setName("과정Name_" + String.format("%02d", seq));
			course.setCapacity(15+seq);
			course.setDetail("과정에 대한 설명은 아래와 같습니다.호호호호호호호호"+seq);
			course.setStartDate("2024-10-18");
			course.setEndDate("2025-04-17");
			course.setStatus(random);
			course.setEnabled(true);
//			course.setCrtDate(new Date());
			this.repo.save(course);
			log.info("\t+ seq: {}, {}", seq, course);
		});
		
	}//createList
	
	
//	@Disabled
	@Tag("Course-Repository-Test")
	@Order(3)
	@Test
//	@RepeatedTest(1)
	@DisplayName("3. read")
	@Timeout(value = 1L, unit = TimeUnit.SECONDS)
	void read() {
		log.debug("read() invoked");
		
		Optional<Course> optional = this.repo.findById(1L);
		optional.ifPresent(foundCourse -> {
			log.info("\t+ read data: {}", foundCourse);
		});	
		
		
	}//read
	
//	@Disabled
	@Tag("Course-Repository-Test")
	@Order(4)
	@Test
//	@RepeatedTest(1)
	@DisplayName("4. update")
	@Timeout(value = 1L, unit = TimeUnit.SECONDS)
	@Rollback(false)
	void update() {
		log.debug("update() invoked");
		

		Optional<Course> optional = this.repo.findById(1L);
		optional.ifPresent(foundCourse -> {
			log.info("\t+ read data: {}", foundCourse);
			
			foundCourse.setName("TEST");
			foundCourse.setType("front");
			
//			this.repo.save(foundCourse);	//안해도 됨
			
			log.info("\t+ update end: {}", foundCourse);
		});		
		
	}//update
	
//	@Disabled
	@Tag("Course-Repository-Test")
	@Order(5)
	@Test
//	@RepeatedTest(1)
	@DisplayName("5. delete")
	@Timeout(value = 1L, unit = TimeUnit.SECONDS)
	@Rollback(false)
	void delete() {
		log.debug("delete() invoked");
		
//		this.repo.deleteByCourseId(11L);
		
		this.repo.deleteById(11L);
		
		log.info("\t+ delete end!");
		
	}//delete
	
	
	
//	@Disabled
	@Tag("Course-Repository-Test")
	@Order(10)
	@Test
//	@RepeatedTest(1)
	@DisplayName("10. list-findByEnabled")
	@Timeout(value = 1L*10, unit = TimeUnit.SECONDS)
	void findByEnabled() {
		log.debug("findByEnabled() invoked");
		
		int pageNo = 1;
		int pageSize = 1000;		
		Pageable paging = PageRequest.of(pageNo-1, pageSize, Sort.by("crtDate").descending());
		Boolean eanbled = true;
		
		Slice<Course> list = this.repo.findByEnabled(eanbled, paging);
		list.forEach(d -> log.info(d.toString()));
		
//		log.info("=".repeat(100));
//		
//		pageNo = 2;
//		paging = PageRequest.of(pageNo-1, pageSize, Sort.by("crtDate").descending());
//		list = this.repo.findByEnabled(eanbled, paging);
//		list.forEach(d -> log.info(d.toString()));
		
	}//findByEnabled
//	
//	@Disabled
//	@Tag("Course-Repository-Test")
//	@Order(11)
//	@Test
////	@RepeatedTest(1)
//	@DisplayName("11. list-findByEnabledAndNameContaining")
//	@Timeout(value = 1L, unit = TimeUnit.SECONDS)
//	void findByEnabledAndNameContaining() {
//		log.debug("findByEnabledAndNameContaining() invoked");
//	}//findByEnabledAndNameContaining
//	
//	@Disabled
//	@Tag("Course-Repository-Test")
//	@Order(12)
//	@Test
////	@RepeatedTest(1)
//	@DisplayName("12. list-findByEnabledAndTelContaining")
//	@Timeout(value = 1L, unit = TimeUnit.SECONDS)
//	void findByEnabledAndTelContaining() {
//		log.debug("findByEnabledAndTelContaining() invoked");
//	}//findByEnabledAndTelContaining
//	
//	
	
	
	
	//== contextLoads ========================
	@Disabled
	@Tag("Course-Repository-Test")
	@Order(0)
	@Test
//	@RepeatedTest(1)
	@DisplayName("ContextLoads")
	@Timeout(value = 1L, unit = TimeUnit.SECONDS)
	void contextLoads() {
		log.debug("contextLoads() invoked");
	}//contextLoads
	
	
	
	
	
	
}//end class