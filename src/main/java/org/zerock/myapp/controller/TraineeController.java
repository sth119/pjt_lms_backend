package org.zerock.myapp.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< Updated upstream
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
=======
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
>>>>>>> Stashed changes
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
//	@Autowired UpFileRepository ufRepo;
//
//	  @Value("${C:\temp}") // application.properties 또는 application.yml에 설정
//	    private String uploadPath;
//	
//	public TraineeController(TraineeRepository repo, CourseRepository crsRepo, UpFileRepository ufRepo) {
//		this.repo=repo;
//		this.crsRepo=crsRepo;
//		this.ufRepo=ufRepo;
//	}
	
	
	
	@PostMapping // 리스트 
//	Slice<Trainee> list(@RequestBody CriteriaDTO dto, Pageable paging){
	Page<Trainee> list(@RequestBody CriteriaDTO dto, Pageable paging){
//		List<Trainee> list(@RequestBody CriteriaDTO dto, Pageable paging){ 
		log.info("list({}) invoked.",dto);
		
		Integer page = (dto.getPage() != null && dto.getPage() >= 0) ? dto.getPage() : 0;
		Integer pageSize = (dto.getPageSize() != null && dto.getPageSize() >= 0) ? dto.getPageSize():10;
		String condition = dto.getCondition();
		String q = dto.getQ();
		//Integer type = dto.getType();
		//log.info("DTO list: {},{},{},{},{}",page,pageSize,condition,q,type);
		
//		paging = PageRequest.of(page, pageSize);		//방법1		 
		paging=PageRequest.of(page, pageSize, Sort.by(Sort.Order.desc("crtDate"),Sort.Order.asc("name"))); //방법2
		
		
		// 기본적으로 모든 데이터를 조회
	    //List<Trainee> list = this.repo.findByEnabled(true, paging);

//		Slice<Trainee> slice = this.repo.findByEnabled(true, paging);
		Page<Trainee> trnPage = this.repo.findByEnabled(true, paging);


		boolean hasNext = trnPage.hasNext(); // 다음 페이지 존재 여부
		boolean hasPrevious = trnPage.hasPrevious(); // 이전 페이지 존재 여부
		long totalElements = trnPage.getTotalElements(); // 전체 데이터 개수
		int totalPages = trnPage.getTotalPages(); // 전체 페이지 개수
		
	    log.info("\t hasNext: {}, hasPrevious: {}, totalElements: {}, totalPages: {}", 
	    		hasNext, hasPrevious, totalElements, totalPages);

		
		
//		return list;
		return trnPage;
		//페이지 사이즈 갯수대로 소팅
		//2페이지 20개 
	} // list
	
	
//	@PostMapping("/{id}")
//	public ResponseEntity<Trainee> updateTrainee(
//			@PathVariable("id") Long traineeId
//			,@RequestPart("trainee") TraineeDTO dto
//			,@RequestPart(value="file", required=false) MultipartFile file){
//		//파일이 사진이라 없으면 안올려도 된다.
//		
//		log.info("updateTrainee({},{},{}) invoked.", traineeId,dto,file != null ? file.getOriginalFilename() : null);
//		
//		return repo.findByEnabledAndTraineeId(true, traineeId)
//				.map(trn -> {
//					trn.setName(dto.getName());
//					trn.setTel(dto.getTel());
//					trn.setStatus(dto.getStatus());
//					
//					if (dto.getCourse() != null &&
//							dto.getCourse().getCourseId() != null) {
//						crsRepo.findById(dto.getCourse().getCourseId()).ifPresent(trn::setCourse);
//					}else {
//						trn.setCourse(null);
//					}//if
//					
//					//파일처리
//					if (file != null && !file.isEmpty()) {
//						try {
//							String uuid=UUID.randomUUID().toString();
//							String fileName=uuid+"_"+file.getOriginalFilename();
//							
//							File dest=new File(uploadPath, fileName);
//							
//							file.transferTo(dest);
//							
//							Upfile upfile = new Upfile();
//							upfile.setOriginal(file.getOriginalFilename());
//							upfile.setUuid(uuid);
//							upfile.setPath(dest.getAbsolutePath());
//							upfile.setEnabled(true);
//							upfile.setTrainee(trn);
//							
//							ufRepo.save(upfile);
//							
//							log.info("\t )
//						}catch(IOException e) {
//							log.info("IOException : {}",e.getMessage(),e);
//							
//							
//						}//try
//					}//if
//						
//				});//.mpa
//	}//updateTrainee
//	
//	//보통 파일 업로드와 JSON 데이터를 함께 전송해야 할 때 사용됩니다.
	
	
	

	  @PutMapping 
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
