package org.zerock.myapp.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
import org.zerock.myapp.domain.TraineeDTO;
import org.zerock.myapp.entity.Trainee;
import org.zerock.myapp.persistence.CourseRepository;
import org.zerock.myapp.persistence.TraineeRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor

@RequestMapping("/trainee") // Base URI
@RestController
public class TraineeController {  // 훈련생 관리
	@Autowired TraineeRepository repo;
	@Autowired CourseRepository crsRepo;  // fix

	
	@PostMapping // 리스트 
	Slice<Trainee> list(@RequestBody CriteriaDTO dto, Pageable paging){
//		List<Instructor> list(@RequestBody CriteriaDTO dto, Pageable paging){ // Pageable paging는 아직 실험중
		log.info("list({}) invoked.",dto);
		
		Integer page = dto.getPage();
		Integer pageSize = dto.getPageSize();
		String condition = dto.getCondition();
		String q = dto.getQ();
		//Integer type = dto.getType();
		//log.info("DTO list: {},{},{},{},{}",page,pageSize,condition,q,type);
		
		paging = PageRequest.of(page, pageSize);
		
		// 기본적으로 모든 데이터를 조회
	    //List<Instructor> list = this.repo.findByEnabled(true, paging);
		Slice<Trainee> slice = this.repo.findByEnabled(true, paging);
		

		return slice;
		
	} // list
	
	
	  @PutMapping //값을 수정할때
//	  @PostMapping //객체(값)를 만들때  
	  Trainee register(@RequestBody TraineeDTO dto) {
	      log.info("register({}) invoked.", dto);

	      // DTO -> Entity 변환
	      Trainee trn = new Trainee();

	      trn.setName(dto.getName());
	      trn.setTel(dto.getTel());	  
	      trn.setCourse(dto.getCourse());
	      trn.setStatus(dto.getStatus());


	      
	      Trainee register =repo.save(trn); // 새로운 훈련생 저장!
	      log.info("result:{}",register);
	      log.info("Regist success");
		
	      return register;
	  } // register
	 
	  /*성공 put https://localhost/trainee
	    {
	   
		    "name":"SSS3"
		    ,"tel":"0101112333"
		    ,"course":{
		        "courseId":5
		    }
		    ,"status":1

		}
	*/
	  
	  
		@GetMapping("/{id}") // 단일 조회 화면
		Trainee read(@PathVariable("id") Long traineeId){ // error fix -> ("id")로 명확히 표시
			log.info("read({}) invoked.",traineeId);
			
			Optional<Trainee> optional = this.repo.findByEnabledAndTraineeId(true,traineeId); // error fix ->  주석처리
			
			Trainee read = optional.get();
//					.orElseThrow(() -> new RuntimeException("해당 ID의 강사를 찾을 수 없습니다: " + traineeId));
			
			log.info("Read success");
			return read;
		} // read
		
		// https://localhost/trainee/1
		
		
		@PostMapping(value = "/{id}", consumes = "application/json")
		public ResponseEntity<Trainee> update(@PathVariable("id") Long traineeId, @RequestBody TraineeDTO dto) {
		    log.info("update({},{}) invoked.", traineeId, dto);
		    
		    return this.repo.findByEnabledAndTraineeId(true, traineeId)
		        .map(trn -> {
		            trn.setName(dto.getName());
		            trn.setTel(dto.getTel());
		            trn.setStatus(dto.getStatus());
		            
		            if (dto.getCourse() != null && dto.getCourse().getCourseId() != null) {
		                return this.crsRepo.findById(dto.getCourse().getCourseId())
		                    .map(course -> {
		                        trn.setCourse(course);
		                        return this.repo.save(trn);
		                    })
		                    .orElseThrow(() -> new EntityNotFoundException("Course not found with ID: " + dto.getCourse().getCourseId()));
		           //결과가 존재하면 map을 통해 처리하고, 존재하지 않으면 EntityNotFoundException을 던집니다.
		            } else {
		                trn.setCourse(null);
		                return this.repo.save(trn);
		            }
		           
		        })
		        
		        .map(updatedTrainee -> {
		            log.info("Update success");
		            return ResponseEntity.ok(updatedTrainee);
//		           훈련생이 성공적으로 업데이트되면 ResponseEntity.ok(updatedTrainee)를 반환하여 업데이트된 훈련생을 응답으로 보냅니다.
		        })
		        .orElseThrow(() -> new EntityNotFoundException("Trainee not found with ID: " + traineeId));
		   
		}//update

	// Post https://localhost/trainee/2385
		/**
		 {
    "name": "홍길동사사"
    ,"tel": "0101234444"
    ,"course": {
        "courseId": 4
    }
    ,"status":4
}
		 */


		@DeleteMapping("/{id}")
		Trainee delete(@PathVariable("id") Long traineeId) {
			log.info("delete({})",traineeId);
			
			Optional<Trainee> optionalTrainee  = this.repo.findByEnabledAndTraineeId(true,traineeId);
			
			if (optionalTrainee.isPresent()) {
				Trainee trn = optionalTrainee.get();
				 
				trn.setStatus(3);    // 상태(등록=1,강의중=2,퇴사=3)
				trn.setEnabled(false);   // 삭제여부(1=유효,0=삭제)
				 
				Trainee delete =  this.repo.save(trn);
				 
				 log.info("Delete success");
				 return delete;
			 } // if
			log.info("Delete fail");
			return null;
		}//delete
		
		//https://localhost/trainee/id
} // end class
