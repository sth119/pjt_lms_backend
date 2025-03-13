package org.zerock.myapp.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.myapp.domain.CourseDTO;
import org.zerock.myapp.domain.CriteriaDTO;
import org.zerock.myapp.entity.Course;
import org.zerock.myapp.persistence.CourseRepository;
import org.zerock.myapp.persistence.TraineeRepository;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@NoArgsConstructor

@RequestMapping("/course")
@RestController
// 과정 URI 컨트롤러
public class CourseController {
	@Autowired CourseRepository repo;
	@Autowired TraineeRepository trnRepo;
	
	//RESTfull	
	//@GetMapping // DTO로 받기 위해서는 Post(json) 방식으로 줘야 한다
	@PostMapping // 리스트 
//	Page<Course> list(@RequestBody CriteriaDTO dto, Pageable paging){
		List<Course> list(@RequestBody CriteriaDTO dto, Pageable paging){ // Pageable paging는 아직 실험중
		log.info("list({}) invoked.",dto);
		
		Integer page = dto.getPage();
		Integer pageSize = dto.getPageSize();
		String condition = dto.getCondition();
		String q = dto.getQ();
		//Integer type = dto.getType();
		//log.info("DTO list: {},{},{},{},{}",page,pageSize,condition,q,type);
		
		//paging = PageRequest.of(page, pageSize);
		
		// 기본적으로 모든 데이터를 조회
	    //Page<Course> slice = this.repo.findByEnabled(true, paging);
		List<Course> list = this.repo.findByEnabled(true);
		
		// 아직 값을 굉장히 많이 찍는 문제가 있다.
		//list.forEach(s -> log.info(s.toString()));
	    
		//temp
		list.forEach(s -> {
			s.setCurrCount(this.trnRepo.countByEnabledAndCourse(true, s));
		});

		return list;
	} // list
	
	@PutMapping // 등록
	Course register(@RequestBody CourseDTO dto) {
		log.info("register({}) invoked.",dto);
		Course course = new Course();
		
		course.setType(dto.getType()); // 과정구분
		course.setName(dto.getName()); // 과정명
		course.setCapacity(dto.getCapacity()); // 수강정원
		course.setDetail(dto.getDetail()); // 내용
		course.setStartDate(dto.getStartDate()); // 시작일
		course.setEndDate(dto.getEndDate()); // 종료일
		
		// 담당강사명, 여기서 넣는게 아니다. 강사나 훈련생을 등록할 때 넣어야 한다.
		//course.setInstructor(dto.getInstructor().getName()); 
		
		course.setCurrCount(0); // 현재수강인원
		
		// enabled 는 기본적으로 1이 자동으로 들어감
		// currCount는 기본값 0
		
		Course result = this.repo.save(course);
		log.info("result:{}",result);
		log.info("Regist success");
		
		return result;
	} // register
	
	@GetMapping("/{id}") // 단일 조회 화면
	Course read(@PathVariable Long id){
		log.info("read({}) invoked.",id);
		
		Course course = this.repo.findById(id)
		        .orElseThrow(() -> new RuntimeException("해당 ID의 코스를 찾을 수 없습니다: " + id));
		
		log.info("Read success");
		return course;
	} // read
	
	@PostMapping("/{id}")
	Course update(@PathVariable Long id, @RequestBody CourseDTO dto) {
		log.info("update({},{}) invoked.",id,dto);
		
		Optional<Course> optionalCourse  = this.repo.findById(id);
		
		 if (optionalCourse.isPresent()) {
			 Course course = optionalCourse.get();
			 
			 course.setType(dto.getType());
			 course.setName(dto.getName());
			 course.setCapacity(dto.getCapacity());
			 course.setDetail(dto.getDetail());
			 course.setStartDate(dto.getStartDate());
			 course.setEndDate(dto.getEndDate());
			 
			 Course result =  this.repo.save(course);
			 
			 log.info("Update success");
			 return result;
		 } // if
		 log.info("Update fail");
		return null; // 값이 없으면 NULL반환
	} // update
	
	@DeleteMapping("/{id}")
	Course delete(@PathVariable Long id) {
		log.info("delete({})",id);
		
		Optional<Course> optionalCourse  = this.repo.findById(id);
		
		if (optionalCourse.isPresent()) {
			 Course course = optionalCourse.get();
			 
			 course.setStatus(4);
			 course.setEnabled(false);
			 
			 Course result =  this.repo.save(course);
			 
			 log.info("Delete success");
			 return result;
		 } // if
		log.info("Delete fail");
		return null;
	} // delete
	
	

	//RESTfull
	// 훈련생 등록 화면: 담당과정 선택 리스트
	@GetMapping("/selectCourseTrn") 
	public List<Course> selectCourseListTrainee(){
		log.info("selectCourseList() invoked.");
		
		List<Course> list = this.repo.findByEnabledAndStatusInOrderByStartDate(true, List.of(1, 2));
		
		// 수강 인원이 정원보다 작은 과정만 필터링
		list.stream()
		.peek(c -> c.setCurrCount(this.trnRepo.countByEnabledAndCourse(true, c))) // peek: currCount를 설정하기 위해 사용. forEach 대신 중간 연산으로 처리.
		.filter(c -> c.getCurrCount() < c.getCapacity()) // 정원 미만인 경우만 포함
		.collect(Collectors.toList());

		return list;
	} // 훈련생 수강정원 필터

	
	// 강사 등록 화면: 담당과정 선택 리스트
	@GetMapping("/selectCourseIns") 
	public List<Course> selectCourseListInstructor(){
		log.info("selectCourseListInstructor() invoked.");
		
		List<Course> list = this.repo.findByEnabledAndStatusInOrderByStartDate(true, List.of(1, 2));
		
		return list;
		
	} // 강사 수강정원 필터.
	
	
	 
} // end class
