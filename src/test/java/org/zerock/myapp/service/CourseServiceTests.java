package org.zerock.myapp.service;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
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
import org.zerock.myapp.entity.Course;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)

@Transactional

@SpringBootTest
public class CourseServiceTests {
	@Resource private CourseServiceImpl service;
	
	@PostConstruct
	void postConstruct() {
		log.debug("CourseServiceTests -- postConstruct() invoked.");
		log.info("\t+ this.service: {}, type: {}",this.service, this.service.getClass().getSimpleName());
	} // postConstruct
	

	@Disabled
	@Tag("CourseService-Test")
	@Order(1)
	@Test
//	@RepeatedTest(0)
	@Rollback(false)
	@DisplayName("1. testCreate")
	@Timeout(value = 1L,unit = TimeUnit.SECONDS)
	void testCreate() {
		log.debug("testCreate() invoked.");
		
		// 1
		Course dto = new Course();
		dto.setCrsCode("A2025001-001");
		dto.setCrsType("A");
		dto.setCrsType("과정 A 001");
		dto.setCrsCapacity(20);
		dto.setStartDate("20250125");
		dto.setEndDate("20250724");
		
		log.info("\t+ Course dto: {}", dto); // PK 속성값이 null
		
		// 2
		Course data = this.service.create(dto);
		log.info("\t+ After: {}", data); // PK 속성값이 들어감
	} // testCreate
	
	
	
	
	
	

	
	@Disabled
	@Tag("CourseService-Test")
	@Order(0)
	@Test
//	@RepeatedTest(0)
	@DisplayName("")
	@Timeout(value = 1L,unit = TimeUnit.SECONDS)
	void context() {
		log.debug("context() invoked.");
		
		// 
		
	} // context

}
