package org.zerock.myapp.controller;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.myapp.domain.TraineeDTO;
import org.zerock.myapp.entity.Course;
import org.zerock.myapp.entity.Trainee;
import org.zerock.myapp.persistence.CourseRepository;
import org.zerock.myapp.persistence.TraineeRepository;
import org.zerock.myapp.persistence.UpFileRepository;

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
	@Autowired UpFileRepository fileRepo;
	String CourseFileDirectory = "C:/temp/course/";

	
	
	@PostMapping
	public Page<Trainee> list(@ModelAttribute TraineeDTO dto 
//			,Integer page, Integer PageSize
			 ,@RequestParam(value = "page", defaultValue = "0") Integer page
			 ,@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
			) {
		
	    log.info("list({}) invoked.", dto);

	    // 기본값 설정
//	    Integer page = (dto.getPage() != null) ? dto.getPage() - 1 : 0;  // 페이지 시작 값은 0부터
//	    Integer pageSize = (dto.getPageSize() != null) ? dto.getPageSize() : 10; // 기본 페이지 사이즈 10
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
				                result = repo.findByEnabledAndStatusAndNameContaining(true,dto.getStatus(),dto.getSearchText(), paging);
				                break;
				            case "tel":
				                result = repo.findByEnabledAndStatusAndTelContaining(true,dto.getStatus(), dto.getSearchText(), paging);
				                break;
				            default:
				                result = repo.findByEnabledAndStatus(true,dto.getStatus(), paging);
				                break;
				        }
	        	   } else {
				        result = repo.findByEnabledAndStatus(true,dto.getStatus(), paging);
				    }
	        } else {
	            // 유효하지 않은 status 값일 경우 기본적으로 모든 status를 받아들임// 넣는다면 1~4뿐이긴 하지만
	            result = repo.findByEnabled(true, paging);
	        }
	        
	    } else { //status를 선택 안하고 검색했을때
			    if (dto.getSearchText() != null && dto.getSearchWord() != null) {
			        switch (dto.getSearchWord()) {
			            case "name":
			                result = repo.findByEnabledAndNameContaining(true, dto.getSearchText(), paging);
			                break;
			            case "tel":
			                result = repo.findByEnabledAndTelContaining(true, dto.getSearchText(), paging);
			                break;
			            default:
			                result = repo.findByEnabled(true, paging);
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
	ResponseEntity<Trainee> register(
//			@ModelAttribute TraineeDTO dto,	// String 으로 받아서 변환해야 한다
			@RequestParam("name") String name, // String으로 받기
		    @RequestParam("tel") String tel,   // String으로 받기
			@RequestParam("upfiles") MultipartFile file) throws Exception, IOException {
//		log.info("register({}) invoked.",dto);
		log.info("register(name={}, tel={}, file={}) invoked.", name, tel, file.getOriginalFilename());
		
		TraineeDTO dto=new TraineeDTO();
		dto.setName(name);
		dto.setTel(tel);
		
		Trainee trn = new Trainee(); // 코스 객체 생성
		
	    trn.setName(dto.getName());     
	    trn.setTel(dto.getTel());	  
//	    trn.setCourse(dto.getCourse()); //신청과정 (courseId)
		

	    
//	    trn.setStatus(dto.getStatus()); //1.훈련중2.중도탈락3.중도포기4.취업완료
//	  	trn.setEnabled(dto.getEnabled()); //1=true 0=false
	    
		
		
//	    trn.setCourse(dto.getCourse());
//	    trn.setStatus(2);
//	    trn.setEnabled(true);
	  
//	    if(dto.getCourseId() != null) {
//	    	Course course= crsRepo.findById(dto.getCourseId())
//	    			.orElseThrow(()-> new RuntimeException("Course not found"));
//	    	trn.setCourse(course);
//	    }
	    
	    Trainee savedTrn= repo.save(trn);
	    return ResponseEntity.ok(savedTrn);
		
	    
//		Trainee result = this.repo.save(trn);
//		log.info("result:{}",result);
//		log.info("Regist success");
		
		
//		Upfile upfile = new Upfile();  // 1. 파일 객체 생성
//		upfile.setOriginal(file.getOriginalFilename()); // DTO에서 파일 이름 가져오기
//		upfile.setUuid(UUID.randomUUID().toString()); // 고유 식별자 생성
//		upfile.setPath(CourseFileDirectory); // 주소
//		upfile.setEnabled(true); // 기본값
//		
//		upfile.setTrainee(result); // 2. 연관 관계 설정, 자식이 부모객체 저장(set)
//		
//		log.info("upfile:{}",upfile);
//		this.fileRepo.save(upfile); // 파일 엔티티 저장
//		
//		
//		   // 파일 저장 처리
//	    if (file != null && !file.isEmpty()) {
//	        // 파일 저장 경로 생성
//	        String uploadDir = upfile.getPath();
//	        File targetDir = new File(uploadDir);
//	        if (!targetDir.exists()) {
//	            targetDir.mkdirs(); // 디렉토리가 없는 경우 생성
//	        } // if
//	    
//	        // 파일 저장 경로 및 이름 설정
//	        String filePath = upfile.getPath() + upfile.getUuid() + "." + upfile.getOriginal().substring(upfile.getOriginal().lastIndexOf('.') + 1);
//	        File savedFile = new File(filePath);
//	        // 이후에 파일 받을때는 uuid에서 확장자를 뺴는 과정이 필요함.
//
//	        // 파일 저장
//	        file.transferTo(savedFile);
//	        log.info("File saved at: {}", filePath);
//	    } // if
//		
//		
//		// 4. Course에 Upfile 추가
//		result.addUpfile(upfile); // 3. 연관 관계 설정, 부모에 자식객체 저장(add)
//		
//		log.info("result:{}",result);
//		log.info("getFileCourse success");
		
//		return result;
	    
	} // register
	
	
	
//	  @PutMapping 
//	  Trainee register(@RequestBody TraineeDTO dto) {
//	      log.info("register({}) invoked.", dto);
//
//	      // DTO -> Entity 변환
//	      Trainee trn = new Trainee();
//
//	      trn.setName(dto.getName());
//	      trn.setTel(dto.getTel());	  
//	      trn.setCourse(dto.getCourse());
//
//
//	      
//	      Trainee register =repo.save(trn); // 새로운 훈련생 저장!
//	      log.info("result:{}",register);
//	      log.info("Regist success");
//		
//	      return register;
//	  } // register
	 
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
		
		
		//update
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
