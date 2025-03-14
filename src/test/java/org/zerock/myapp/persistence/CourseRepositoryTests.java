package org.zerock.myapp.persistence;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.sql.SQLException;
import java.util.List;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.myapp.entity.Course;

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
		data.setType(1);					//과정구분(1=NCS, 2=KDT, 3=산대특, 4=미정)
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
				case 1 -> course.setType(1);
				case 2 -> course.setType(2);
				case 3 -> course.setType(3);
				case 4 -> course.setType(4);				
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
			foundCourse.setType(2);
			
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
	@Order(6)
	@Test
//	@RepeatedTest(1)
	@DisplayName("6. 강사 & 훈련생 등록 화면: 담당과정 선택 리스트")
	@Timeout(value = 1L*5, unit = TimeUnit.SECONDS)
	@Rollback(false)
	void findByEnabledAndStatusIn() {
		log.debug("findByEnabledAndStatusIn() invoked");
		
		List<Course> list = this.repo.findByEnabledAndStatusInOrderByStartDate(true, List.of(1, 2));
		
		list.forEach(c -> log.info(c.toString()));
		
	}//findByEnabledAndStatusIn
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	@Disabled
	@Tag("Course-Repository-Test")
	@Order(10)
	@Test
//	@RepeatedTest(1)
	@DisplayName("10. list-findByEnabled")
	@Timeout(value = 1L*10, unit = TimeUnit.SECONDS)
	void findByEnabled() {
		log.debug("findByEnabled() invoked");
		
//		List<Course> list = this.repo.findAll();
//		list.forEach(d -> log.info(d.toString()));
		
		
		int pageNo = 1;
		int pageSize = 100;		
		Pageable paging = PageRequest.of(pageNo-1, pageSize, Sort.by("crtDate").descending());
		Boolean eanbled = true;
		
		Page<Course> list = this.repo.findByEnabled(eanbled, paging);
		list.forEach(d -> log.info(d.toString()));
		
		log.info("=".repeat(100));
//		
//		pageNo = 2;
//		paging = PageRequest.of(pageNo-1, pageSize, Sort.by("crtDate").descending());
//		list = this.repo.findByEnabled(eanbled, paging);
//		list.forEach(d -> log.info(d.toString()));
		
	}//findByEnabled

	
	@Disabled
	@Tag("Course-Repository-Test")
	@Order(11)
	@Test
//	@RepeatedTest(1)
	@DisplayName("11. list-findByEnabledAndStatus")
	@Timeout(value = 1L*10, unit = TimeUnit.SECONDS)
	void findByEnabledAndStatus() {
		log.debug("findByEnabledAndStatus() invoked");
		
		int pageNo = 1;
		int pageSize = 50;		
		Pageable paging = PageRequest.of(pageNo-1, pageSize, Sort.by("crtDate").descending());
		
		Page<Course> list = this.repo.findByEnabledAndStatus(true, 1, paging);
		list.forEach(d -> log.info(d.toString()));
		
		log.info("=".repeat(100));
		
		Page<Course> list2 = this.repo.findByEnabledAndStatus(true, 2, paging);
		list2.forEach(d -> log.info(d.toString()));
		
		
	}//findByEnabledAndStatus
	
	
	@Disabled
	@Tag("Course-Repository-Test")
	@Order(12)
	@Test
//	@RepeatedTest(1)
	@DisplayName("12. list-nativeSQL_TypeAndInsName")
	@Timeout(value = 1L*10, unit = TimeUnit.SECONDS)
	void findCoursesByTypeAndInstructorName() {
		log.debug("findCoursesByTypeAndInstructorName() invoked");
		
		int pageNo = 1;
		int pageSize = 50;		
		Pageable paging = PageRequest.of(pageNo-1, pageSize, Sort.by("INSERT_TS").descending());
		
		Boolean enabled = true;
		Integer status = 1;	//진행여부(연기=3,예정=2,진행중=1,종료=0)
		Integer type = 1;		//과정구분(1=NCS, 2=KDT, 3=산대특, 4=미정)
		String instructorName = "dong";
		
		Page<Course> list = this.repo.findCoursesByTypeAndInstructorName(enabled, status, type, instructorName, paging);
		list.forEach(d -> log.info(d.toString()));
		
		log.info("=".repeat(100));
		
		status = 2;
		type = 2;
		instructorName = "dong";
		
		Page<Course> list2 = this.repo.findCoursesByTypeAndInstructorName(true, status, type, instructorName, paging);
		list2.forEach(d -> log.info(d.toString()));
		
	}//findCoursesByTypeAndInstructorName
	
//	@Disabled
	@Tag("Course-Repository-Test")
	@Order(13)
	@Test
//	@RepeatedTest(1)
	@DisplayName("13. list-findCoursesByInstructorName")
	@Timeout(value = 1L*10, unit = TimeUnit.SECONDS)
	void nativeSQL_InsName() {
		log.debug("findCoursesByInstructorName() invoked");
		
		int pageNo = 1;
		int pageSize = 50;		
		Pageable paging = PageRequest.of(pageNo-1, pageSize, Sort.by("INSERT_TS").descending());
		
		Boolean enabled = true;
		Integer status = 1;	//진행여부(연기=3,예정=2,진행중=1,종료=0)
		String instructorName = "22";
		
		Page<Course> list = this.repo.findCoursesByInstructorName(enabled, status, instructorName, paging);
		list.forEach(d -> log.info(d.toString()));
		
		log.info("=".repeat(100));
		
		status = 2;
		instructorName = "33";
		
		Page<Course> list2 = this.repo.findCoursesByInstructorName(true, status, instructorName, paging);
		list2.forEach(d -> log.info(d.toString()));
		
	}//findCoursesByInstructorName
	
	
	
	
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