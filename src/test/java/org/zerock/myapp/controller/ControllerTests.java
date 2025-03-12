package org.zerock.myapp.controller;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeAll;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.myapp.entity.Course;
import org.zerock.myapp.persistence.CourseRepository;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@NoArgsConstructor

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)

@SpringBootTest

@AutoConfigureMockMvc
public class ControllerTests {

//	@Autowired
//	private MockMvc mockMvc;
	
	@Autowired
	private CourseRepository coursRepo;
	
//	@Autowired 
//	private ObjectMapper objectMapper;
	
	private Course testCourse; //테스트용 더미.
	
	@BeforeAll //한번만
	void setup() {
		coursRepo.deleteAll(); //기존데이터 삭제
		
		//테스트용 더미 데이터 만들기
		testCourse=new Course();
		testCourse.setId(1L);
		testCourse.setType("ING");
		testCourse.setName("Test");
		testCourse.setCapacity(5);
		testCourse.setDetail("Testing...");
		testCourse.setStartDate("2025/03");
		testCourse.setEndDate("2025/10");
		testCourse.setStatus(1);
		testCourse.setEnabled(null); //리포 수정전 불린 값이라
//		testCourse.setInsert_ts();//데이터 등록일
//		testCourse.setUpdate_ts();//업데이트 날 
		testCourse.setCrtDate(null);//이건 삭제될 예정
		testCourse.setUdtDate(null);//이건 삭제될 예정
		
	}//@BeforeAll
	
	
//	@Disabled
	@Tag("Controller-Test")
	@Order(1)
	@Test
//	@RepeatedTest(1)
	@DisplayName("1. testFindAllList")
	@Timeout(value=3L, unit =TimeUnit.SECONDS)
	void testFindAllList() {
		log.debug("testFindAllList() invoked");
		
		long total=this.coursRepo.count();
		log.info("\t+ total:{}",total);
		
		Iterable<Course> iter=this.coursRepo.findAll();
		for(Course course :iter) {
			log.info(course.toString());
		}
				
	}//testFindAllList
	
	
//	@Disabled
	@Tag("Controller-Test")
	@Order(2)
	@Test
//	@RepeatedTest(1)
	@DisplayName("1. testCreate")
	@Timeout(value=3L, unit =TimeUnit.SECONDS)
	void testCreate() {
		log.debug("testCreate() invoked");
		
//		Course course
				
	}//testFindAllList
	
	
	
	@Disabled
	@Tag("Controller-Test")
	@Order(0)
	@Test
//	@RepeatedTest(1)
	@DisplayName("1. test")
	@Timeout(value=3L, unit =TimeUnit.SECONDS)
	void context() {
		log.debug("conText() invoked");
		
	}//context
	
}//class
