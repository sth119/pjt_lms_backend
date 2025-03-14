package org.zerock.myapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.myapp.domain.CriteriaDTO;
import org.zerock.myapp.domain.InstructorDTO;
import org.zerock.myapp.entity.Course;
import org.zerock.myapp.entity.Instructor;
import org.zerock.myapp.persistence.CourseRepository;
import org.zerock.myapp.persistence.InstructorRepository;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor

@RequestMapping("/instructor") // Base URI
@RestController
public class InstructorController { // 강사 관리
	@Autowired InstructorRepository repo;
	@Autowired CourseRepository crsRepo;  // fix


	//RESTfull	
	//@GetMapping // DTO로 받기 위해서는 Post(json) 방식으로 줘야 한다
	@PostMapping // 리스트 
	Page<Instructor> list(@RequestBody CriteriaDTO dto, Pageable paging){
		log.info("list({}, {}) invoked.", dto, paging);
		
		//page는 기본 0부터 시작
		Integer page = (dto.getPage()!=null && dto.getPage() >= 0) ? dto.getPage() : 0;
		Integer pageSize = (dto.getPageSize()!=null && dto.getPageSize() >= 0) ? dto.getPageSize() : 10;
		String condition = dto.getCondition();
		String q = dto.getQ();
		//Integer type = dto.getType();
		//log.info("DTO list: {},{},{},{},{}",page,pageSize,condition,q,type);
		
		paging = PageRequest.of(page, pageSize, Sort.by("status").ascending().and(Sort.by("crtDate").descending()));
		
		// 기본적으로 모든 데이터를 조회
		Page<Instructor> list = this.repo.findByEnabled(true, paging);
		

		return list;
	} // list // 성공
	
	
	// TEST 용도
//	{
//	    "page": 0,
//	    "pageSize": 10,
//	    "condition": "name",
//	    "q": "Lorem2"
//	}
	
	
	

	
	@PutMapping // 등록
	Instructor register(@RequestBody InstructorDTO dto) {
		log.info("register({}) invoked.",dto);
		Instructor instructor = new Instructor();
		
		Course course = this.crsRepo.findById(dto.getCourseId()).orElse(new Course());
		instructor.setName(dto.getName()); // 이름
		instructor.setTel(dto.getTel()); // 전화번호
		instructor.setCourse(course);  // 담당과정
		instructor.setStatus(1);
//		instructor.setEnabled(true);
		log.info("before success?");
		
		
		Instructor register = this.repo.save(instructor);
		log.info("result:{}",register);
		log.info("Regist success");
		
		return register;
	} // register // 성공
	
	// test 방법
//	{
//	    "name": "gilldong",
//	    "tel": "0102351222",
//	    "courseId": 39
//	}
	

	
	@GetMapping("/{id}") // 단일 조회 화면
	Instructor read(@PathVariable("id") Long instructorId){ // error fix -> ("id")로 명확히 표시
		log.info("read({}) invoked.",instructorId);
		
//		Optional<Instructor> optional = this.repo.findById(instructorId); // error fix ->  주석처리
		
		Instructor read = this.repo.findById(instructorId)
		        .orElseThrow(() -> new RuntimeException("해당 ID의 강사를 찾을 수 없습니다: " + instructorId));
		
		log.info("Read success");
		return read;
	} // read  // 성공
	
	

	@PostMapping(value = "/{id}", consumes = "application/json") // error fix? -> consumes  추가
	Instructor update(@PathVariable("id") Long instructorId, @RequestBody InstructorDTO dto) {
//		ResponseEntity<Object> update(@PathVariable("id") Long instructorId, @RequestBody InstructorDTO dto) {
		log.info("update({},{}) invoked.",instructorId,dto);
		
		// 1. 기존 Instructor 조회 (없으면 예외 발생)
		Instructor instructor = this.repo.findById(instructorId).orElseThrow(() -> new RuntimeException("해당 ID의 강사를 찾을 수 없습니다: " + instructorId));
		
		// 2. DTO에서 받은 값으로 업데이트
		instructor.setName(dto.getName());
		instructor.setTel(dto.getTel());
		
		// 3. Course 설정 (register와 동일한 방식)
		Course course = this.crsRepo.findById(dto.getCourseId()).orElse(new Course());
		instructor.setCourse(course);
		
		// 4. 상태값 유지 (register에서는 status=1로 고정, update에서는 DTO에서 받음)
		if(dto.getStatus() != null) {
			instructor.setStatus(dto.getStatus());
		}
		
		// 5. 저장 및 반환
		Instructor update = this.repo.save(instructor);
		log.info("Update success: {}" , update);
		
		return update;
		
    } // update // 성공
	
	
	// test 방법
//	{
//	    "name": "김개삐",
//	    "tel": "0107534232",
//	    "courseId": 100,
//	    "status":1,
//	    "enabled": true
//	}
		
		


	@DeleteMapping("/{id}")
	Instructor delete(@PathVariable("id") Long id) {
		log.info("delete({})",id);
		
		Instructor DelInstructor  = this.repo.findById(id).orElseThrow(() -> new RuntimeException("해당 ID의 강사를 찾을 수 없습니다: " + id));
 
		DelInstructor.setStatus(3);    // 상태(등록=1,강의중=2,퇴사=3)
		DelInstructor.setEnabled(false);   // 삭제여부(1=유효,0=삭제)
			 
			Instructor delete =  this.repo.save(DelInstructor);
			 
			 log.info("Delete success");
			 return delete;

	} // delete // 성공
	

	
} // end class
