package org.zerock.myapp.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.myapp.domain.TraineeDTO;
import org.zerock.myapp.entity.Course;
import org.zerock.myapp.entity.Trainee;
import org.zerock.myapp.entity.Upfile;
import org.zerock.myapp.persistence.CourseRepository;
import org.zerock.myapp.persistence.TraineeRepository;
import org.zerock.myapp.persistence.UpFileRepository;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor

@RequestMapping("/trainee") // Base URI
@RestController
public class TraineeController {  // 훈련생 관리
	@Autowired TraineeRepository repo;
	@Autowired CourseRepository crsRepo;  // fix
	@Autowired UpFileRepository fileRepo;
	String traineeFileDirectory = "C:/temp/trainee/";

	
	
	@PostMapping
	public Page<Trainee> list(@ModelAttribute TraineeDTO dto 
			 ,@RequestParam(value = "page", defaultValue = "0") Integer page // 페이지 시작 값은 0부터
			 ,@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize // 기본 페이지 사이즈 10
			) {
		
	    log.info("list({}) invoked.", dto);

	    Pageable paging = PageRequest.of(page, pageSize, Sort.by(Sort.Order.asc("traineeId"))); // Pageable 설정

	    // 검색 조건 적용
	    Page<Trainee> result = null;

	    // status가 null이 아니면 status로 필터링
	    if (dto.getStatus() != null) {
	        // status 값이 1, 2, 3, 4 중 하나라면
	        if (dto.getStatus() >= 1 && dto.getStatus() <= 4) {
	            // status 조건을 추가한 검색
	    
	        	 if (dto.getSearchText() != null && dto.getSearchWord() != null) {
				        switch (dto.getSearchWord()) {
				            case "name":
				                result = this.repo.findByEnabledAndStatusAndNameContaining(true,dto.getStatus(),dto.getSearchText(), paging);
				                break;
				            case "tel":
				                result = this.repo.findByEnabledAndStatusAndTelContaining(true,dto.getStatus(), dto.getSearchText(), paging);
				                break;
				            default:
				                result = this.repo.findByEnabledAndStatus(true,dto.getStatus(), paging);
				                break;
				        }
	        	   } else {
				        result = this.repo.findByEnabledAndStatus(true,dto.getStatus(), paging);
				    }
	        } else {
	            // 유효하지 않은 status 값일 경우 기본적으로 모든 status를 받아들임// 넣는다면 1~4뿐이긴 하지만
	            result = this.repo.findByEnabled(true, paging);
	        }
	        
	    } else { //status를 선택 안하고 검색했을때
			    if (dto.getSearchText() != null && dto.getSearchWord() != null) {
			        switch (dto.getSearchWord()) {
			            case "name":
			                result = this.repo.findByEnabledAndNameContaining(true, dto.getSearchText(), paging);
			                break;
			            case "tel":
			                result = this.repo.findByEnabledAndTelContaining(true, dto.getSearchText(), paging);
			                break;
			            default:
			                result = this.repo.findByEnabled(true, paging);
			                break;
			        }           
			        
			    } else {
			        result = repo.findByEnabled(true, paging);
			    }
	    }

	    // 결과가 null이면 빈 Page 반환
	    result = (result != null) ? result : Page.empty(paging);

	    result.forEach(seq -> log.info(seq.toString())); // 로그 출력
	    return result;
	}//list
		//test get https://localhost/trainee?searchWord=name&searchText=0
	//https://localhost/trainee?searchWord=name&searchText=0&tel=00
	//https://localhost/trainee?searchWord=tel&searchText=000

	
	@PutMapping // 등록
	Trainee register(
			@ModelAttribute TraineeDTO dto,	
			@RequestParam("upfiles") MultipartFile file) throws Exception, IOException {
		log.info("register(dto={}, file={}) invoked.", dto, file.getOriginalFilename());
		
		Trainee trainee =new Trainee();
		trainee.setName(dto.getName()); // 훈련생이름
		trainee.setTel(dto.getTel()); // 전화번호
		trainee.setStatus(1);   // 기본값 등록(1), 상태(등록=1,강의중=2,퇴사=3)
		trainee.setEnabled(true);// 기본값 true, 삭제여부(1=유효,0=삭제)
		
		log.info("before success?");
	      Trainee result = this.repo.save(trainee);
	      
	      // 중복값 체크가 필요하다
	      if (dto.getCourseId() != null && dto.getCourseId() > 0) {
	    	    Course course = this.crsRepo.findById(dto.getCourseId()).orElse(null);
	    	    if (course != null) {
	    	        trainee.setCourse(course); // 담당과정
	    	        course.addTraninee(trainee); // course 개체에 trainee 저장
	    	        this.crsRepo.save(course); // course도 저장해야 함
	    	    } // if
	    	} // if
	      
	      log.info("result:{}",result);
	      log.info("Regist success");
	      if(file != null && !file.isEmpty()) {
				Upfile upfile = new Upfile();  // 1. 파일 객체 생성
				upfile.setOriginal(file.getOriginalFilename()); // DTO에서 파일 이름 가져오기
				upfile.setUuid(UUID.randomUUID().toString()); // 고유 식별자 생성
				upfile.setPath(traineeFileDirectory); // 주소
				upfile.setEnabled(true); // 기본값
				
				upfile.setTrainee(trainee); // 2. 연관 관계 설정, 자식이 부모객체 저장(set)
				
				log.info("upfile:{}",upfile);
				this.fileRepo.save(upfile); // 파일 엔티티 저장
				
				   // 파일 저장 처리
//			    if (file != null && !file.isEmpty()) {
			        // 파일 저장 경로 생성
			        String uploadDir = upfile.getPath();
			        File targetDir = new File(uploadDir);
			        if (!targetDir.exists()) {
			            targetDir.mkdirs(); // 디렉토리가 없는 경우 생성
			        } // if
			    
			        // 파일 저장 경로 및 이름 설정
			        String filePath = upfile.getPath() + upfile.getUuid() + "." + upfile.getOriginal().substring(upfile.getOriginal().lastIndexOf('.') + 1);
			        File savedFile = new File(filePath);
			        // 이후에 파일 받을때는 uuid에서 확장자를 뺴는 과정이 필요함.

			        // 파일 저장
			        file.transferTo(savedFile);
			        log.info("File saved at: {}", filePath);
			    //} // if
				
				// 4. Instructor에 Upfile 추가
				result.addUpfile(upfile); // 3. 연관 관계 설정, 부모에 자식객체 저장(add)
				
		      return result; 
		      } else {
		    	return null;
		      } // if-else
		} // register
	
	
	@GetMapping("/{id}") // 단일 조회 화면
	TraineeDTO read(@PathVariable("id") Long traineeId){ // error fix -> ("id")로 명확히 표시
		log.info("read({}) invoked.",traineeId);
		
		Optional<Trainee> optional = this.repo.findByEnabledAndTraineeId(true,traineeId); // error fix ->  주석처리
		Trainee trainee = optional.get();
		
		TraineeDTO dto = new TraineeDTO();
	  	dto.setTraineeId(trainee.getTraineeId());			// 아이디
	  	
	  	dto.setName(trainee.getName());				// 이름
	  	dto.setTel(trainee.getTel());					// 전화번호
	  	dto.setStatus(trainee.getStatus());				//	상태(등록=1,강의중=2,퇴사=3)
	  	dto.setEnabled(trainee.getEnabled());			//	삭제여부(1=유효,0=삭제)
	  	dto.setCrtDate(trainee.getCrtDate());
	  	dto.setUdtDate(trainee.getUdtDate());
	  	  
	  	// 조인 객체들
	  	dto.setCourse(trainee.getCourse());
	  	dto.setUpfile(trainee.getUpfiles());
		
		log.info("Read success");
		return dto;
	} // read
		
		// https://localhost/trainee/1

		//update 
		@PostMapping(value = "/{id}")
		Trainee update(@PathVariable("id") Long traineeId,
				TraineeDTO dto,
				@RequestParam("upfiles") MultipartFile file
				) throws IllegalStateException, IOException {
		    log.info("update({},{}) invoked.", traineeId, dto);

		 // 1. 기존 Instructor 조회 (없으면 예외 발생)
		      Trainee trainee = this.repo.findById(traineeId).orElseThrow(() -> new RuntimeException("해당 ID의 강사를 찾을 수 없습니다: " + traineeId));
		      
		      // 2. DTO에서 받은 값으로 업데이트
		      trainee.setName(dto.getName());
		      trainee.setTel(dto.getTel());
		      
		      if(dto.getStatus() != null) { // 상태값 유지 (register에서는 status=1로 고정, update에서는 DTO에서 받음)
		    	  trainee.setStatus(dto.getStatus());
		      } // if
		      
		      // 3. Course 설정 (register와 동일한 방식)
		      if(dto.getCourseId() != null)
		    	  trainee.setCourse(this.crsRepo.findById(dto.getCourseId()).orElse(new Course()));  // 담당과정
		      if(file != null && !file.isEmpty()) { // 사진이 없는경우 대비를 위한 if-else 문.
		      
		      //  기존 파일 처리
		      if (!trainee.getUpfiles().isEmpty()) {
		          Upfile existingFile = trainee.getUpfiles().get(0); // 첫 번째 파일 가져오기
		          String existingFileName = existingFile.getOriginal(); // 기존 파일명 가져오기

		          // 새로운 파일명과 비교
		          if (!existingFileName.equals(file.getOriginalFilename())) {
		              // 기존 파일 비활성화 및 연관 관계 제거
		        	  trainee.removeUpfile(existingFile);
		              log.info("Existing file removed: {}", existingFile);
		              this.fileRepo.save(existingFile); // 변경된 상태 저장
		          } else {
		              log.info("Same file detected, skipping removal.");
		              return trainee; // 동일한 경우 업데이트 중단
		          } // if-else
		      } // if
		      
				Upfile upfile = new Upfile();  // 1. 파일 객체 생성
				 
				upfile.setOriginal(file.getOriginalFilename()); // DTO에서 파일 이름 가져오기
				upfile.setUuid(UUID.randomUUID().toString()); // 고유 식별자 생성
				upfile.setPath(traineeFileDirectory); // 주소
				upfile.setEnabled(true); // 기본값
				
				log.info("New upfile created: {}", upfile);

				// 파일 저장 처리
				
		      // 파일 저장 경로 생성
		      String uploadDir = upfile.getPath();
		      File targetDir = new File(uploadDir);
		      if (!targetDir.exists()) {
		          targetDir.mkdirs(); // 디렉토리가 없는 경우 생성
		      } // if
		  
		      // 파일 저장 경로 및 이름 설정
		      String filePath = upfile.getPath() + upfile.getUuid() + "." + upfile.getOriginal().substring(upfile.getOriginal().lastIndexOf('.') + 1);
		      File savedFile = new File(filePath);

		      // 파일 저장
		      file.transferTo(savedFile);
		      log.info("File saved at: {}", filePath); 
		      
			  // Course 엔티티에 새로운 파일 추가
		      trainee.addUpfile(upfile);
		      this.fileRepo.save(upfile); // // 새 파일 엔티티 저장
		      
		      } else {
		    	  log.info("사진이 수정되지 않았습니다.");
		      } // if-else
		          
		      
		      // 4. 저장 및 반환
		      Trainee update = this.repo.save(trainee);
		      log.info("Update success: {}" , update);
		      
		      return update; 
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
				Trainee trainee = optionalTrainee.get();
				 
				trainee.setStatus(3);    // 상태(훈련중=1,중도탈락=2,중도포기=3,취업완료=4)
				trainee.setEnabled(false);   // 삭제여부(true=유효,false=삭제)
				 
				// 연관된 Instructor 처리
			      if (trainee.getCourse() != null) {
			    	  Course course = trainee.getCourse();
			    	  course.removeTraninee(trainee); // Course의 Instructor 목록에서 해당 Instructor 제거
			    	  this.crsRepo.save(course); // 변경된 Course 저장
			      } // if

			      // 연관된 Upfile 처리
			      if (!trainee.getUpfiles().isEmpty()) { // 파일이 비어있지 않으면
			          for (Upfile upfile : new ArrayList<>(trainee.getUpfiles())) {
			        	  trainee.removeUpfile(upfile); // Upfile의 상태를 비활성화하고 Course 참조를 null로 설정
			          } // for
			      } // if
				
				Trainee delete =  this.repo.save(trainee);
				 
				 log.info("Delete success");
				 return delete;
			 } // if
			log.info("Delete fail");
			return null;
		}//delete
		
		//https://localhost/trainee/id
} // end class
