package org.zerock.myapp.persistence;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.SQLException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

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
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.myapp.entity.Course;
import org.zerock.myapp.entity.Instructor;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)

@Transactional

@SpringBootTest
public class InstructorRepositoryTests {
	@Autowired private InstructorRepository repo;
	@Autowired private CourseRepository crsRepo;
	
	@BeforeAll
	void beforeAll() throws SQLException {
		log.debug("InstructorRepositoryTests -- beforeAll() invoked.");
		
		assertNotNull(this.repo);
		log.info("\t + this.repo: {}", this.repo);		
	}//beforeAll
	
	
	
//	@Disabled
	@Tag("Instructor-Repository-Test")
	@Order(1)
	@Test
//	@RepeatedTest(1)
	@DisplayName("1. create")
	@Timeout(value = 1L, unit = TimeUnit.SECONDS)
	@Rollback(false)
	void create() {
		log.debug("create() invoked");
		
		Optional<Course> course = this.crsRepo.findById(40L);
		course.ifPresent(foundCourse -> {
			Instructor instructor = new Instructor();
			instructor.setName("홍길동3333");
			instructor.setTel("01012343333");
			instructor.setCourse(foundCourse);
			instructor.setStatus(1);
//			instructor.setEnabled(true);
			
			log.info("\t+ Before: {}", instructor);		// PK 속성값이 null			
			instructor = this.repo.save(instructor);			
			log.info("\t+ After: {}", instructor);		// 자동생성된 PK 속성값이 들어있다!!
		});
		
	}//create
	
	
//	@Disabled
	@Tag("Instructor-Repository-Test")
	@Order(3)
	@Test
//	@RepeatedTest(1)
	@DisplayName("3. read")
	@Timeout(value = 1L, unit = TimeUnit.SECONDS)
	void read() {
		log.debug("read() invoked");
		
		Optional<Instructor> instructor = this.repo.findByEnabledAndInstructorId(true, 104L);
		instructor.ifPresent(foundInstructor -> {
			log.info("\t+ read data: {}", instructor);
		});		
		
	}//read
	
////	@Disabled
//	@Tag("Instructor-Repository-Test")
//	@Order(4)
//	@Test
////	@RepeatedTest(1)
//	@DisplayName("4. update")
//	@Timeout(value = 1L, unit = TimeUnit.SECONDS)
//	@Rollback(false)
//	void update() {
//		log.debug("update() invoked");
//		
//		Optional<Trainee> trainee = this.repo.findById(143L);
//		trainee.ifPresent(foundTrainee -> {
//			log.info("\t+ read data: {}", foundTrainee);
//
//			foundTrainee.setName("김태영");
//			foundTrainee.setTel("01099997777");
//
//			log.info("\t+ after end: {}", foundTrainee);
//		});	
//		
//	}//update
//	
////	@Disabled
//	@Tag("Instructor-Repository-Test")
//	@Order(5)
//	@Test
////	@RepeatedTest(1)
//	@DisplayName("5. delete")
//	@Timeout(value = 1L, unit = TimeUnit.SECONDS)
//	@Rollback(false)
//	void delete() {
//		log.debug("delete() invoked");
//		
//		this.repo.deleteById(143L);
//		
//		log.info("\t+ delete end!");
//		
//	}//delete
//	
//	
////	@Disabled
//	@Tag("Instructor-Repository-Test")
//	@Order(6)
//	@Test
////	@RepeatedTest(1)
//	@DisplayName("6. findByEnabledAndTraineeId")
//	@Timeout(value = 1L, unit = TimeUnit.SECONDS)
//	void findByEnabledAndTraineeId() {
//		log.debug("read() invoked -- 단건 조회");
//		
//		Optional<Trainee> trainee = this.repo.findByEnabledAndTraineeId(true, 1963L);
//		trainee.ifPresent(foundTrainee -> {
//			log.info("\t+ findByEnabledAndTraineeId data: {}", trainee);
//		});		
//		
//	}//findByEnabledAndTraineeId
//	
//	
//	
//	
//	
//	
//	
//	
////	@Disabled
//	@Tag("Trainee-Repository-Test")
//	@Order(10)
//	@Test
////	@RepeatedTest(1)
//	@DisplayName("10. list-findByEnabled")
//	@Timeout(value = 1L, unit = TimeUnit.SECONDS)
//	void findByEnabled() {
//		log.debug("findByEnabled() invoked");
//		
//		int pageNo = 1;
//		int pageSize = 10;		
//		Pageable paging = PageRequest.of(pageNo-1, pageSize, Sort.by("crtDate").descending());
//		Boolean eanbled = true;
//		
//		Page<Trainee> list = this.repo.findByEnabled(eanbled, paging);
//		list.forEach(d -> log.info(d.toString()));
//		
////		log.info("=".repeat(100));
////		
////		pageNo = 2;
////		paging = PageRequest.of(pageNo-1, pageSize, Sort.by("crtDate").descending());
////		list = this.repo.findByEnabled(eanbled, paging);
////		list.forEach(d -> log.info(d.toString()));
//		
//	}//findByEnabled
//	
////	@Disabled
//	@Tag("Trainee-Repository-Test")
//	@Order(11)
//	@Test
////	@RepeatedTest(1)
//	@DisplayName("11. list-findByEnabledAndNameContaining")
//	@Timeout(value = 1L*5, unit = TimeUnit.SECONDS)
//	void findByEnabledAndNameContaining() {
//		log.debug("findByEnabledAndNameContaining() invoked");
//		
//		int pageNo = 1;
//		int pageSize = 100;		
//		Pageable paging = PageRequest.of(pageNo-1, pageSize, Sort.by("crtDate").descending());
//		Boolean eanbled = true;
//		String name = "011";
//		
//		Page<Trainee> list = this.repo.findByEnabledAndNameContaining(eanbled, name, paging);
//		list.forEach(d -> log.info(d.toString()));
//		
//		
//		
//		
//	}//findByEnabledAndNameContaining
//	
//	@Disabled
//	@Tag("Trainee-Repository-Test")
//	@Order(12)
//	@Test
////	@RepeatedTest(1)
//	@DisplayName("12. list-findByEnabledAndTelContaining")
//	@Timeout(value = 1L, unit = TimeUnit.SECONDS)
//	void findByEnabledAndTelContaining() {
//		log.debug("findByEnabledAndTelContaining() invoked");
//	}//findByEnabledAndTelContaining
//	
////	@Disabled
////	@Tag("Trainee-Repository-Test")
////	@Order(12)
////	@Test
//////	@RepeatedTest(1)
////	@DisplayName("12. list-findByEnabledAndTelContaining")
////	@Timeout(value = 1L, unit = TimeUnit.SECONDS)
////	void findByEnabledAndTelContaining() {
////		log.debug("findByEnabledAndTelContaining() invoked");
////	}//findByEnabledAndTelContaining
//	
//	
//	
//	
//	
	
	
	
	//== contextLoads ========================
	@Disabled
	@Tag("Trainee-Repository-Test")
	@Order(0)
	@Test
//	@RepeatedTest(1)
	@DisplayName("ContextLoads")
	@Timeout(value = 1L, unit = TimeUnit.SECONDS)
	void contextLoads() {
		log.debug("contextLoads() invoked");
	}//contextLoads
	
	
	
	
	
	
}//end class