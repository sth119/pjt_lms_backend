package org.zerock.myapp.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
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
	Slice<Instructor> list(@RequestBody CriteriaDTO dto, Pageable paging){
//		List<Instructor> list(@RequestBody CriteriaDTO dto, Pageable paging){ // Pageable paging는 아직 실험중
		log.info("list({}) invoked.",dto);
		
		Integer page = dto.getPage();
		Integer pageSize = dto.getPageSize();
		String condition = dto.getCondition();
		String q = dto.getQ();
		//Integer type = dto.getType();
		//log.info("DTO list: {},{},{},{},{}",page,pageSize,condition,q,type);
		
		//paging = PageRequest.of(page, pageSize);
		
		// 기본적으로 모든 데이터를 조회
	    //List<Instructor> list = this.repo.findByEnabled(true, paging);
		Slice<Instructor> slice = this.repo.findByEnabled(true, paging);
		

		return slice;
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
		
		instructor.setName(dto.getName()); // 이름
		instructor.setTel(dto.getTel()); // 전화번호
//		instructor.setCourse(dto.getCourse());  // 담당과정
		instructor.setCourse((Course) dto.getCourseId());  // 담당과정
		
		
				
		
		Instructor register = this.repo.save(instructor);
		log.info("result:{}",register);
		log.info("Regist success");
		
		return register;
	} // register
	

	
	@GetMapping("/{id}") // 단일 조회 화면
	Instructor read(@PathVariable("id") Long instructorId){ // error fix -> ("id")로 명확히 표시
		log.info("read({}) invoked.",instructorId);
		
//		Optional<Instructor> optional = this.repo.findById(instructorId); // error fix ->  주석처리
		
		Instructor read = this.repo.findById(instructorId)
		        .orElseThrow(() -> new RuntimeException("해당 ID의 강사를 찾을 수 없습니다: " + instructorId));
		
		log.info("Read success");
		return read;
	} // read  
	
	

	@PostMapping(value = "/{id}", consumes = "application/json") // error fix? -> consumes  추가
//	Instructor update(@PathVariable("id") Long instructorId, @RequestBody InstructorDTO dto) {
		ResponseEntity<Object> update(@PathVariable("id") Long instructorId, @RequestBody InstructorDTO dto) {
		log.info("update({},{}) invoked.",instructorId,dto);
		
		Optional<Instructor> optionalInstructor  = this.repo.findById(instructorId);
//	
//		 if (optionalInstructor.isPresent()) {
//			 Instructor instructor = optionalInstructor.get();
//			 
//			 instructor.setName(dto.getName());      // 강사 이름
//			 instructor.setTel(dto.getTel());        // 전화번호
////			 instructor.setCourse(dto.getCourse());  // 담당과정
//			 instructor.setCourse(dto.getCourse());  // 담당과정  // fix?
//			 instructor.setStatus(dto.getStatus());  // 상태
//			 
//			 if (dto.getCourse() != null) {
////		            Optional<Course> optionalCourse = course.findById(dto.getCourseId()); // courseRepo는 Course 엔티티용 리포지토리
//		            Optional<Course> optionalCourse = this.crsRepo.findById(dto.getCourse().getCourseId()); // fix?
//		            if (optionalCourse.isPresent()) {
////		                instructor.setCourseId(optionalCourse.get());
//		                instructor.setCourseId(instructorId); // fix?
//		            } else {
//		                log.warn("Course with ID {} not found", dto.getCourse());
//		            }
//		        }
//			 
//			 
//			 
//			 Instructor update =  this.repo.save(instructor);
//			 
//			 log.info("Update success");
//			 return update;
//		 } // if
		
//		log.info("Update fail");
//		return null; // 값이 없으면 NULL반환
//		// update  // 성공
//	}// 1차 방법	
	
	
		if (!optionalInstructor.isPresent()) {
            log.warn("Instructor with ID {} not found", instructorId);
            return ResponseEntity.notFound().build(); // 404 Not Found
        }

        // 2. Instructor 업데이트
        Instructor instructor = optionalInstructor.get();
        instructor.setName(dto.getName());      // 강사 이름
        instructor.setTel(dto.getTel());        // 전화번호
        instructor.setStatus(dto.getStatus());  // 상태
        instructor.setEnabled(dto.getEnabled()); // 활성화 상태 (DTO에 enabled 필드가 있다고 가정)

        // 3. courseId를 기반으로 Course 조회 및 설정
        if (dto.getCourseId() != null) {
//            Optional<Course> optionalCourse = this.crsRepo.findById(dto.getCourseId());
            Optional<Course> optionalCourse = this.crsRepo.findById((Long) dto.getCourseId());
            if (optionalCourse.isPresent()) {
                instructor.setCourse(optionalCourse.get()); // Course 객체 설정
            } else {
                log.warn("Course with ID {} not found", dto.getCourseId());
                return ResponseEntity.badRequest().body(null); // 400 Bad Request
            }
        } else {
            instructor.setCourse(null); // courseId가 없으면 Course를 null로 설정
        }

        // 4. 업데이트된 Instructor 저장
        Instructor updatedInstructor = this.repo.save(instructor);
        log.info("Update success for Instructor ID: {}", updatedInstructor.getInstructorId());
        return ResponseEntity.ok(updatedInstructor); // 200 OK
    } // update // 성공
	
	
	// TEST 방법
//	{
//	    "name": "김개똥",
//	    "tel": "0102322232",
//	    "course": 100,
//	    "status":1,
//	    "enabled": true
//	}
		
		
		
	
	

	@DeleteMapping("/{id}")
	Instructor delete(@PathVariable Long id) {
		log.info("delete({})",id);
		
		Optional<Instructor> optionalInstructor  = this.repo.findById(id);
		
		if (optionalInstructor.isPresent()) {
			Instructor instructor = optionalInstructor.get();
			 
			instructor.setStatus(3);    // 상태(등록=1,강의중=2,퇴사=3)
			instructor.setEnabled(false);   // 삭제여부(1=유효,0=삭제)
			 
			Instructor delete =  this.repo.save(instructor);
			 
			 log.info("Delete success");
			 return delete;
		 } // if
		log.info("Delete fail");
		return null;
	} // delete
	

	
} // end class
