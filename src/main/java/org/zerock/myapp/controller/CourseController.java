package org.zerock.myapp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
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

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@NoArgsConstructor

@RequestMapping("/course")
@RestController
// 과정 URI 컨트롤러
public class CourseController {
	@Autowired CourseRepository repo;
	
	//RESTfull	
	//@GetMapping // DTO로 받기 위해서는 Post(json) 방식으로 줘야 한다
	@PostMapping
	Slice<Course> list(@RequestBody CriteriaDTO dto, Pageable paging){
		log.info("list({}) invoked.",dto);
		
		Integer page = dto.getPage();
		Integer pageSize = dto.getPageSize();
		String condition = dto.getCondition();
		String q = dto.getQ();
		//Integer type = dto.getType();
		//log.info("DTO list: {},{},{},{},{}",page,pageSize,condition,q,type);
		
		
		paging = PageRequest.of(page, pageSize);
		
		// 기본적으로 모든 데이터를 조회
	    Slice<Course> slice = this.repo.findByEnabled(true, paging);
	    
	    
	    
	    // 조건에 맞는 데이터 필터링, 필터링 필요한 양이 늘어나면 조건을 추가해야함
//	    List<Course> filteredList = slice.getContent().stream()
//	        .filter(s -> {
//	        	
//	        	if (q != null) {
//	                if (condition != null && condition.equals("id") && !String.valueOf(s.getCourseId()).equals(q)) return false;
//	                if (condition != null && condition.equals("name") && !s.getName().contains(q)) return false;
//	                if (condition == null || !condition.equals("id") && !condition.equals("name")) {
//	                    if (!s.getName().contains(q)) return false; // 기본적으로 이름으로 검색
//	                } // if
//	            } // if
//	        	
//	            return true;
//	        }) // filteredList
//	        .toList();
//	    
//	    // Slice를 새로 생성하여 반환
//	    Slice<Course> result = new SliceImpl<>(filteredList, paging, false);
//	    
//	    result.forEach(seq -> log.info(seq.toString()));
	    
		return slice;
	} // list
	
	@PutMapping
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
		
		return result;
	} // register
	
	@GetMapping("/{id}")
	Course read(@PathVariable Long id){
		log.info("read({}) invoked.",id);
		
		Optional<Course> optional = this.repo.findById(1L);
		
		optional.ifPresent(foundCourse -> {
			log.info("\t+ read data: {}", foundCourse);
		});	// ifPresent
		
		Course course = optional.get();
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
			 return result;
		 } // if
		 
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
			 return result;
		 } // if
		
		return null;
	} // delete
	 
} // end class
