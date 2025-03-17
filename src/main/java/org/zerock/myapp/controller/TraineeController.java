package org.zerock.myapp.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
import org.springframework.web.bind.annotation.RequestPart;
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


	@PostMapping
	public Page<TraineeDTO> list(
	        @ModelAttribute TraineeDTO dto,
			@RequestParam(name = "currPage", required = false, defaultValue = "0") Integer currPage, // 페이지 시작 값은 0부터
			@RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize // 기본 페이지 사이즈 10
	) {
		log.info("list({}, {}, {}) invoked.", dto, currPage, pageSize);

		Pageable paging = PageRequest.of(currPage, pageSize, Sort.by("crtDate").descending());	// Pageable 설정
		
		Page<Trainee> list = Page.empty(); // 기본 값 설정

		if	   ( dto.getStatus() == null && dto.getSearchText() == null ) {
			//검색 리스트: 활성화상태(true) 
			list = this.repo.findByEnabled(true, paging);
		}
		else if( dto.getStatus() != null && dto.getSearchText() == null ) {
			//검색 리스트: 활성화상태(true) + status
			list = this.repo.findByEnabledAndStatus(true, dto.getStatus(), paging);
		}
		else if( dto.getStatus() == null && dto.getSearchText() != null ) {
			switch (dto.getSearchWord()) {
	            case "name":
	            	list = this.repo.findByEnabledAndNameContaining(true, dto.getSearchText(), paging);
	                break;
	            case "tel":
	            	list = this.repo.findByEnabledAndTelContaining(true, dto.getSearchText(), paging);
	                break;
			}
		}
		else if( dto.getStatus() != null && dto.getSearchText() != null ) {
			switch (dto.getSearchWord()) {
	            case "name":
	            	list = this.repo.findByEnabledAndStatusAndNameContaining(true, dto.getStatus(), dto.getSearchText(), paging);
	                break;
	            case "tel":
	            	list = this.repo.findByEnabledAndStatusAndTelContaining(true, dto.getStatus(), dto.getSearchText(), paging);
	                break;
			}
		}
		
		List<TraineeDTO> resultList = new Vector<>();
		list.forEach(t -> {
			log.debug(t.toString());
			
			TraineeDTO tDto = new TraineeDTO();
			tDto.setTraineeId(t.getTraineeId());	// 아이디
		  	
			tDto.setName(t.getName());				// 이름
			tDto.setTel(t.getTel());					// 전화번호
			tDto.setStatus(t.getStatus());			//	상태(등록=1,강의중=2,퇴사=3)
			tDto.setEnabled(t.getEnabled());			//	삭제여부(1=유효,0=삭제)
			tDto.setCrtDate(t.getCrtDate());
			tDto.setUdtDate(t.getUdtDate());
		  	  
		  	// 조인 객체들
			tDto.setCourse(t.getCourse());
			tDto.setUpfile(t.getUpfiles());
			
			resultList.add(tDto);
		});
		
		// 위에서 DTO로 담은걸 Page로 다시 담음
	    Page<TraineeDTO> result = new PageImpl<>(resultList, list.getPageable(), list.getTotalElements());
	    
	    return result;
	}//list
	

	
	@PutMapping // 등록
	Trainee register(
			@ModelAttribute TraineeDTO dto,	
			@RequestParam(value = "upfiles", required = false) MultipartFile file
		) throws Exception, IOException {
		log.info("register(dto={}) invoked.", dto);
		
		Trainee trainee =new Trainee();
		trainee.setName(dto.getName()); // 훈련생이름
		trainee.setTel(dto.getTel()); // 전화번호
		trainee.setStatus(1);   // 기본값 등록(1), 상태(등록=1,강의중=2,퇴사=3)
		trainee.setEnabled(true);// 기본값 true, 삭제여부(1=유효,0=삭제)
		trainee.setCourse(this.crsRepo.findById(dto.getCourseId()).orElse(null));
		
		log.info("before success?");
	    Trainee result = this.repo.save(trainee);
	    
	    log.info("Regist success!!!  result:{}",result);
	    
	    // "path": "C:\\Users\\chltj\\Desktop\\프로젝트\\깃버전\\pjt_lms_backend/src/main/resources/static/traineeFile/" 로 전송
	    String fileDirectory = System.getProperty("user.dir") + "/src/main/resources/static/traineeFile/"; // 백에서 저장할 주소
		String useDirectory = "/traineeFile/"; // 프론트로 보낼 주소, 테스트중
	    
	    if(file != null && !file.isEmpty()) {
			Upfile upfile = new Upfile();  // 1. 파일 객체 생성
			upfile.setOriginal(file.getOriginalFilename()); // DTO에서 파일 이름 가져오기
			
			String extension = upfile.getOriginal().substring(upfile.getOriginal().lastIndexOf('.') + 1);
		      
		    upfile.setUuid(UUID.randomUUID().toString()+"."+extension); // 고유 식별자 생성
			upfile.setPath(useDirectory); // 주소
			upfile.setEnabled(true); // 기본값
			
			upfile.setTrainee(trainee); // 2. 연관 관계 설정, 자식이 부모객체 저장(set)
			
			log.info("upfile:{}",upfile);
			this.fileRepo.save(upfile); // 파일 엔티티 저장
			

	        File targetDir = new File(fileDirectory);
	        if (!targetDir.exists()) {
	            targetDir.mkdirs(); // 디렉토리가 없는 경우 생성
	        } // if
	    
	        // 파일 저장 경로 및 이름 설정
	        String filePath = fileDirectory + upfile.getOriginal();
	        File savedFile = new File(filePath);

	        // 파일 저장
	        file.transferTo(savedFile);
	        log.info("File saved at: {}", filePath);
		    //} // if
			
			// 4. Instructor에 Upfile 추가
			result.addUpfile(upfile); // 3. 연관 관계 설정, 부모에 자식객체 저장(add)
			this.repo.save(result); // fix16
	      } else {
	      } // if-else	
	    	return result; 
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
		Trainee update(
					@PathVariable("id") Long traineeId,
					TraineeDTO dto,
					@RequestPart(value = "upfiles", required = false) MultipartFile file
				) throws IllegalStateException, IOException {
		    log.info("update({},{}) invoked.", traineeId, dto);

		 // 1. 기존 Instructor 조회 (없으면 예외 발생)
		      Trainee trainee = this.repo.findById(traineeId).orElseThrow(() -> new RuntimeException("해당 ID의 강사를 찾을 수 없습니다: " + traineeId));
		      
		      // 2. DTO에서 받은 값으로 업데이트
		      trainee.setName(dto.getName());
		      trainee.setTel(dto.getTel());
		      trainee.setStatus(dto.getStatus());
		    	  
		      // 3. Course 설정 (register와 동일한 방식)
		      trainee.setCourse(this.crsRepo.findById(dto.getCourseId()).orElse(null));	// 담당과정
		      
		      String fileDirectory = System.getProperty("user.dir") + "/src/main/resources/static/traineeFile/"; // 백에서 저장할 주소
			  String useDirectory = "/traineeFile/"; // 프론트로 보낼 주소, 테스트중
		      
		      // 사진이 없는경우 대비를 위한 if-else 문.
		      if(file != null && !file.isEmpty()) { 
		      //  기존 파일 처리
		    	  if (trainee.getUpfiles() != null && !trainee.getUpfiles().isEmpty()) {
		              Upfile existingFile = trainee.getUpfiles().get(0);
		              trainee.removeUpfile(existingFile);
		              log.info("Existing file removed: {}", existingFile);
		              this.fileRepo.save(existingFile);
		          } // if
	              
	          Upfile upfile = new Upfile();  // 1. 파일 객체 생성
	          
	          upfile.setOriginal(file.getOriginalFilename()); // DTO에서 파일 이름 가져오기
	          
	          String extension = upfile.getOriginal().substring(upfile.getOriginal().lastIndexOf('.') + 1);
		      
			  upfile.setUuid(UUID.randomUUID().toString()+"."+extension); // 고유 식별자 생성
	          upfile.setPath(useDirectory); // 주소
	          upfile.setEnabled(true); // 기본값
				
	          log.info("New upfile created: {}", upfile);

			  // 파일 저장 처리
		      // 파일 저장 경로 생성
		      File targetDir = new File(fileDirectory);
		      if (!targetDir.exists()) {
		          targetDir.mkdirs(); // 디렉토리가 없는 경우 생성
		      } // if
		  
		      // 파일 저장 경로 및 이름 설정
		      String filePath = fileDirectory + upfile.getOriginal();
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
