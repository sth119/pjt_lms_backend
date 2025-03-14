package org.zerock.myapp.persistence;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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
import org.zerock.myapp.entity.Trainee;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)

@Transactional

@SpringBootTest
public class TraineeRepositoryTests {
	@Autowired private TraineeRepository repo;
	@Autowired private CourseRepository crsRepo;
	
	@BeforeAll
	void beforeAll() throws SQLException {
		log.debug("TraineeRepositoryTests -- beforeAll() invoked.");
		
		assertNotNull(this.repo);
		log.info("\t + this.repo: {}", this.repo);		
	}//beforeAll
	
	
	
//	@Disabled
	@Tag("Trainee-Repository-Test")
	@Order(1)
	@Test
//	@RepeatedTest(1)
	@DisplayName("1. create")
	@Timeout(value = 1L, unit = TimeUnit.SECONDS)
	@Rollback(false)
	void create() {
		log.debug("create() invoked");
		
		Optional<Course> course = this.crsRepo.findById(39L);
		course.ifPresent(foundCourse -> {
			Trainee trainee = new Trainee();
			trainee.setName("최성락");
			trainee.setTel("01012341234");
			trainee.setCourse(foundCourse);
			trainee.setStatus(1);
			trainee.setEnabled(true);
			
			log.info("\t+ Before: {}", trainee);		// PK 속성값이 null			
			trainee = this.repo.save(trainee);			
			log.info("\t+ After: {}", trainee);		// 자동생성된 PK 속성값이 들어있다!!
		});
		
	}//create
	
	@Disabled
	@Tag("Trainee-Repository-Test")
	@Order(2)
	@Test
//	@RepeatedTest(1)
	@DisplayName("2. createList")
	@Timeout(value = 1L, unit = TimeUnit.SECONDS)
	@Rollback(false)
	void createList() {
		log.debug("createList() invoked");
		
		IntStream.rangeClosed(1, 50).forEach(seq -> {
			Trainee data = new Trainee();
			data.setName("NAME_" + seq);
			data.setTel("010123412" + String.format("%02d", seq));
			data.setStatus(1);
			data.setEnabled(true);
			data.setCrtDate(new Date());
			this.repo.save(data);
			log.info("\t+ seq: {}, {}", seq, data);
		});
		
	}//createList
	
	
//	@Disabled
	@Tag("Trainee-Repository-Test")
	@Order(3)
	@Test
//	@RepeatedTest(1)
	@DisplayName("3. read")
	@Timeout(value = 1L, unit = TimeUnit.SECONDS)
	void read() {
		log.debug("read() invoked");
		
		Optional<Trainee> trainee = this.repo.findByEnabledAndTraineeId(true, 2L);
		trainee.ifPresent(foundTrainee -> {
			log.info("\t+ read data: {}", trainee);
		});		
		
	}//read
	
//	@Disabled
	@Tag("Trainee-Repository-Test")
	@Order(4)
	@Test
//	@RepeatedTest(1)
	@DisplayName("4. update")
	@Timeout(value = 1L, unit = TimeUnit.SECONDS)
	@Rollback(false)
	void update() {
		log.debug("update() invoked");
		
		Optional<Trainee> trainee = this.repo.findById(143L);
		trainee.ifPresent(foundTrainee -> {
			log.info("\t+ read data: {}", foundTrainee);

			foundTrainee.setName("김태영");
			foundTrainee.setTel("01099997777");

			log.info("\t+ after end: {}", foundTrainee);
		});	
		
	}//update
	
//	@Disabled
	@Tag("Trainee-Repository-Test")
	@Order(5)
	@Test
//	@RepeatedTest(1)
	@DisplayName("5. delete")
	@Timeout(value = 1L, unit = TimeUnit.SECONDS)
	@Rollback(false)
	void delete() {
		log.debug("delete() invoked");
		
		this.repo.deleteById(143L);
		
		log.info("\t+ delete end!");
		
	}//delete
	
	
//	@Disabled
	@Tag("Trainee-Repository-Test")
	@Order(6)
	@Test
//	@RepeatedTest(1)
	@DisplayName("6. findByEnabledAndTraineeId")
	@Timeout(value = 1L, unit = TimeUnit.SECONDS)
	void findByEnabledAndTraineeId() {
		log.debug("read() invoked -- 단건 조회");
		
		Optional<Trainee> trainee = this.repo.findByEnabledAndTraineeId(true, 1963L);
		trainee.ifPresent(foundTrainee -> {
			log.info("\t+ findByEnabledAndTraineeId data: {}", trainee);
		});		
		
	}//findByEnabledAndTraineeId
	
	
//	@Disabled
	@Tag("Trainee-Repository-Test")
	@Order(7)
	@Test
//	@RepeatedTest(1)
	@DisplayName("7. countByEnabledAndCourse")
	@Timeout(value = 1L, unit = TimeUnit.SECONDS)
	void countByEnabledAndCourse() {
		log.debug("countByEnabledAndCourse() invoked");
		
		Optional<Course> course = this.crsRepo.findById(39L);
		course.ifPresent(foundCourse -> {
			Integer count = this.repo.countByEnabledAndCourse(true, foundCourse);
			log.info("\t+ 과정별 현재 수강생 Count: {}", count);
		});
		
	}//countByEnabledAndCourse
	
//	@Disabled
	@Tag("Trainee-Repository-Test")
	@Order(8)
	@Test
//	@RepeatedTest(1)
	@DisplayName("8. findByEnabledAndCourse")
	@Timeout(value = 1L, unit = TimeUnit.SECONDS)
	void findByEnabledAndCourse() {
		log.debug("findByEnabledAndCourse() invoked");

		Optional<Course> course = this.crsRepo.findById(39L);
		course.ifPresent(foundCourse -> {
			List<Trainee> list = this.repo.findByEnabledAndCourse(true, foundCourse);
			log.info("\t+ 해당 과정 수강생 정보 list");
			list.forEach(t -> log.info(t.toString()));
		});
		
	}//findByEnabledAndCourse
	
	
	
	
	
	
	
	
	
//	@Disabled
	@Tag("Trainee-Repository-Test")
	@Order(10)
	@Test
//	@RepeatedTest(1)
	@DisplayName("10. list-findByEnabled")
	@Timeout(value = 1L, unit = TimeUnit.SECONDS)
	void findByEnabled() {
		log.debug("findByEnabled() invoked");
		
		int pageNo = 1;
		int pageSize = 10;		
		Pageable paging = PageRequest.of(pageNo-1, pageSize, Sort.by("crtDate").descending());
		Boolean eanbled = true;
		
		Page<Trainee> list = this.repo.findByEnabled(eanbled, paging);
		list.forEach(d -> log.info(d.toString()));
		
//		log.info("=".repeat(100));
//		
//		pageNo = 2;
//		paging = PageRequest.of(pageNo-1, pageSize, Sort.by("crtDate").descending());
//		list = this.repo.findByEnabled(eanbled, paging);
//		list.forEach(d -> log.info(d.toString()));
		
	}//findByEnabled
	
//	@Disabled
	@Tag("Trainee-Repository-Test")
	@Order(11)
	@Test
//	@RepeatedTest(1)
	@DisplayName("11. list-findByEnabledAndNameContaining")
	@Timeout(value = 1L*5, unit = TimeUnit.SECONDS)
	void findByEnabledAndNameContaining() {
		log.debug("findByEnabledAndNameContaining() invoked");
		
		int pageNo = 1;
		int pageSize = 100;		
		Pageable paging = PageRequest.of(pageNo-1, pageSize, Sort.by("crtDate").descending());
		Boolean eanbled = true;
		String name = "011";
		
		Page<Trainee> list = this.repo.findByEnabledAndNameContaining(eanbled, name, paging);
		list.forEach(d -> log.info(d.toString()));
		
		
		
		
	}//findByEnabledAndNameContaining
	
	@Disabled
	@Tag("Trainee-Repository-Test")
	@Order(12)
	@Test
//	@RepeatedTest(1)
	@DisplayName("12. list-findByEnabledAndTelContaining")
	@Timeout(value = 1L, unit = TimeUnit.SECONDS)
	void findByEnabledAndTelContaining() {
		log.debug("findByEnabledAndTelContaining() invoked");
	}//findByEnabledAndTelContaining
	
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