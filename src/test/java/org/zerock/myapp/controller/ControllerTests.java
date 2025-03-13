package org.zerock.myapp.controller;

import java.util.Date;
import java.util.Optional;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.zerock.myapp.entity.Course;
import org.zerock.myapp.persistence.CourseRepository;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@NoArgsConstructor

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)

@SpringBootTest

@TestConfiguration


public class ControllerTests {

	
	@Autowired
	private CourseRepository crsRepo;
	

	
	private Course course; //테스트용 더미.
	
	@BeforeAll //한번만
	void setup() {
		crsRepo.deleteAll(); //기존데이터 삭제
		
		//테스트용 더미 데이터 만들기
		course=new Course();
//		course.setCourseId(1L);
		course.setType(1);
		course.setName("Test");
		course.setCapacity(5);
		course.setDetail("Testing...");
		course.setStartDate("2025/03");
		course.setEndDate("2025/10");
		course.setStatus(1);
		course.setEnabled(true); //리포 수정전 불린 값이라
//		testCourse.setInsert_ts();//데이터 등록일
//		testCourse.setUpdate_ts();//업데이트 날 
		course.setCrtDate(new Date());//이건 삭제될 예정
		course.setUdtDate(null);//이건 삭제될 예정
		
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
		
		long total=this.crsRepo.count();
		log.info("\t+ total:{}",total);
		
		Iterable<Course> iter=this.crsRepo.findAll();
		for(Course testcourse :iter) {
			log.info(testcourse.toString());
		}
				
	}//testFindAllList
	
	
//	@Disabled
	@Tag("Controller-Test")
	@Order(2)
	@Test
//	@RepeatedTest(1)
	@DisplayName("2. testCreate")
	@Timeout(value=3L, unit =TimeUnit.SECONDS)
	void testCreate() {
		log.debug("testCreate() invoked");
		
		Course testCourse=new Course();
		testCourse.setType(2);
		testCourse.setName("Test2");
		testCourse.setCapacity(6);
		testCourse.setDetail("Testing2...");
		testCourse.setStartDate("2025/04");
		testCourse.setEndDate("2025/11");
		testCourse.setStatus(2);
		testCourse.setEnabled(true); //리포 수정전 불린 값이라
//		testCourse.setInsert_ts();//데이터 등록일
//		testCourse.setUpdate_ts();//업데이트 날 
		testCourse.setCrtDate(new Date());//이건 삭제될 예정
		testCourse.setUdtDate(null);//이건 삭제될 예정
		
		log.info("\t Before:{}",testCourse);
		this.crsRepo.save(testCourse);
		
		log.info("\t After:{}",testCourse);
		
	}//testCreate
			
	
//	@Disabled
	@Tag("Controller-Test")
	@Order(3)
	@Test
//	@RepeatedTest(1)
	@DisplayName("3. testRead")
	@Timeout(value=3L, unit =TimeUnit.SECONDS)
	void testRead() {
		log.debug("testRead() invoked");
		
		final Long courseId=2L;
		Optional<Course> otn=this.crsRepo.findById(courseId);
		if(! otn.isEmpty()) {
			Course foundCrs=otn.get();
			log.info("\t_ foundCrs:{}",foundCrs);
				
		}
		
		otn.ifPresent(p-> log.info("\t+ foundCrs:{}",p));
				
	}//testRead
	
	
//	@Disabled
	@Tag("Controller-Test")
	@Order(4)
	@Test
//	@RepeatedTest(1)
	@DisplayName("4. testUpdate")
	@Timeout(value=3L, unit =TimeUnit.SECONDS)
	void testUpdate() {
		log.debug("testUpdate() invoked");
		
		final Long courseId=1L;
		
		Optional<Course> otn=this.crsRepo.findById(courseId);
		otn.ifPresent(foundCrs->{
			log.info("Before:{}",foundCrs);
			
//			foundCrs.setCourseId(2L);
			foundCrs.setType(3);
			foundCrs.setName("Test222");
			foundCrs.setCapacity(60);
			foundCrs.setDetail("Testing2222...");
			foundCrs.setStartDate("2025/04");
			foundCrs.setEndDate("2025/11");
			foundCrs.setStatus(1);
			foundCrs.setEnabled(false); //리포 수정전 불린 값이라
//			testCourse.setInsert_ts();//데이터 등록일
//			testCourse.setUpdate_ts();//업데이트 날 
			foundCrs.setCrtDate(new Date());//이건 삭제될 예정
			foundCrs.setUdtDate(new Date());//이건 삭제될 예정
			
			Course modifiedCrs=this.crsRepo.save(foundCrs);
			log.info("After:{}",modifiedCrs);
			
		});
	
	}//testUpdate
	

//	@Disabled
	@Tag("Controller-Test")
	@Order(5)
	@Test
//	@RepeatedTest(1)
	@DisplayName("5. testDelete")
	@Timeout(value=3L, unit =TimeUnit.SECONDS)
	void testDelete() {
		log.debug("testDelete() invoked");
		
		final Long courseId=2L;
		
		Optional<Course> otn=this.crsRepo.findById(courseId);
		otn.ifPresent(foundCrs -> {
			this.crsRepo.delete(foundCrs);
		});//.ifPresent
	

		
	}//testDelete
	
	
	
	@Disabled
	@Tag("Controller-Test")
	@Order(0)
	@Test
//	@RepeatedTest(1)
	@DisplayName("0. test")
	@Timeout(value=3L, unit =TimeUnit.SECONDS)
	void context() {
		log.debug("conText() invoked");
		
	}//context
	
}//class
