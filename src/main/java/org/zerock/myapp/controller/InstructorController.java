package org.zerock.myapp.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
import org.zerock.myapp.domain.CriteriaDTO;
import org.zerock.myapp.domain.InstructorDTO;
import org.zerock.myapp.entity.Course;
import org.zerock.myapp.entity.Instructor;
import org.zerock.myapp.entity.Upfile;
import org.zerock.myapp.persistence.CourseRepository;
import org.zerock.myapp.persistence.InstructorRepository;
import org.zerock.myapp.persistence.UpFileRepository;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor

@RequestMapping("/instructor") // Base URI
@RestController
public class InstructorController { // 강사 관리
   @Autowired InstructorRepository repo;
   @Autowired CourseRepository crsRepo; 
   @Autowired UpFileRepository fileRepo;
   String InstructorFileDirectory = "C:/temp/instructor/";
//   String InstructorFileDirectory = "/Users/host/workspaces/tmep";
   


   //RESTfull   
   @PostMapping // 리스트 
   
   Page<Instructor> list(
		   @ModelAttribute CriteriaDTO dto, Pageable paging){
	   // @ RequestParam : 단일 값 바인딩 할때 사용. (String , Integer 등)
	   // @ ModelAttribute : 복합객체(DTO) 처리할 때 사용.
      log.info("list({}, {}) invoked.", dto, paging);
      
      //page는 기본 0부터 시작
      Integer page = (dto.getPage()!=null && dto.getPage() >= 0) ? dto.getPage() : 0;
      Integer pageSize = (dto.getPageSize()!=null && dto.getPageSize() >= 0) ? dto.getPageSize() : 10;
      String condition = dto.getCondition();
      String q = dto.getQ();
      
      paging = PageRequest.of(page, pageSize, Sort.by("status").ascending().and(Sort.by("crtDate").descending()));
      
      // 기본적으로 모든 데이터를 조회
      Page<Instructor> list = this.repo.findByEnabled(true, paging);
      
      //검색 리스트: 활성화상태(1) + status 
      Page<Instructor> list2 = this.repo.findByEnabledAndStatus(true, pageSize, paging);
      
      //검색 리스트: 활성화상태(1) + status + 이름 
      Page<Instructor> list3 = this.repo.findByEnabledAndStatusAndNameContaining(true, pageSize, q, paging);
      
      //검색 리스트: 활성화상태(1) + 이름
      Page<Instructor> list4 = this.repo.findByEnabledAndNameContaining(true, q, paging);
      
      //검색 리스트: 활성화상태(1) + status + 전화번호
      Page<Instructor> list5 = this.repo.findByEnabledAndStatusAndTelContaining(true, pageSize, q, paging);
      
      //검색 리스트: 활성화상태(1) + 전화번호
      Page<Instructor> list6 = this.repo.findByEnabledAndTelContaining(true, q, paging);
      
      return list;
   } // list // 성공
   

   
   @PutMapping // 등록
   Instructor register(
         @ModelAttribute InstructorDTO dto,
         @RequestParam(value = "upfiles", required = false) MultipartFile file
      )throws Exception, IOException {
      
      log.info("register({}) invoked.",dto);
      
      Instructor instructor = new Instructor();
      
      instructor.setName(dto.getName()); // 이름
      instructor.setTel(dto.getTel()); // 전화번호
      instructor.setStatus(1);   // 기본값 등록(1), 상태(등록=1,강의중=2,퇴사=3)
      instructor.setEnabled(true);// 기본값 true, 삭제여부(1=유효,0=삭제)
      
      log.info("before success?");
      Instructor result = this.repo.save(instructor);
      
      // 중복값 체크가 필요하다
      if (dto.getCourseId() != null && dto.getCourseId() > 0) {
    	    Course course = this.crsRepo.findById(dto.getCourseId()).orElse(null);
    	    if (course != null) {
    	        instructor.setCourse(course); // 담당과정
    	        course.setInstructor(instructor); // 양방향 참조를 위해 필요함
    	        this.crsRepo.save(course); // course도 저장해야 함
    	    } // if
    	} // if
      
      log.info("result:{}",result);
      log.info("Regist success");

      if(file != null && !file.isEmpty()) {
		Upfile upfile = new Upfile();  // 1. 파일 객체 생성
		upfile.setOriginal(file.getOriginalFilename()); // DTO에서 파일 이름 가져오기
		upfile.setUuid(UUID.randomUUID().toString()); // 고유 식별자 생성
		upfile.setPath(InstructorFileDirectory); // 주소
		upfile.setEnabled(true); // 기본값
		
		upfile.setInstructor(result); // 2. 연관 관계 설정, 자식이 부모객체 저장(set)
		
		log.info("upfile:{}",upfile);
		this.fileRepo.save(upfile); // 파일 엔티티 저장
		

	
		   // 파일 저장 처리
//	    if (file != null && !file.isEmpty()) {
	        // 파일 저장 경로 생성
	        String uploadDir = upfile.getPath();
	        File targetDir = new File(uploadDir);
	        if (!targetDir.exists()) {
	            targetDir.mkdirs(); // 디렉토리가 없는 경우 생성
	        } // if
	    
	        // 파일 저장 경로 및 이름 설정
	        
	        // error 수정 중.
	        String extension = upfile.getOriginal().substring(upfile.getOriginal().lastIndexOf('.') + 1);
	        String filePath = upfile.getPath() + upfile.getUuid() + "." + extension;
	        
	        
//	        String filePath = upfile.getPath() + upfile.getUuid() + "." + upfile.getOriginal().substring(upfile.getOriginal().lastIndexOf('.') + 1);
	        File savedFile = new File(filePath);
	        // 이후에 파일 받을때는 uuid에서 확장자를 뺴는 과정이 필요함.

	        // 파일 저장
	        file.transferTo(savedFile);
	        log.info("File saved at: {}", filePath);
	    //} // if
		
		// 4. Instructor에 Upfile 추가
		result.addUpfile(upfile); // 3. 연관 관계 설정, 부모에 자식객체 저장(add)
		
//      return result; 
      } else {
    	  log.info("File is not uploaded.");
//    	  return null;
    	  
      }
      
      return result;
   } // register // 성공
   
 
   
   @GetMapping("/{id}") // 단일 조회 화면
   InstructorDTO read(@PathVariable("id") Long instructorId){ // error fix -> ("id")로 명확히 표시
      log.info("read({}) invoked.",instructorId);
      
      Instructor Instructor = this.repo.findById(instructorId)
              .orElseThrow(() -> new RuntimeException("해당 ID의 강사를 찾을 수 없습니다: " + instructorId));
      
      InstructorDTO dto = new InstructorDTO();
  	  dto.setInstructorId(Instructor.getInstructorId());			// 아이디

  	  dto.setName(Instructor.getName());				// 이름
  	  dto.setTel(Instructor.getTel());					// 전화번호
  	  dto.setStatus(Instructor.getStatus());				//	상태(등록=1,강의중=2,퇴사=3)
  	  dto.setEnabled(Instructor.getEnabled());			//	삭제여부(1=유효,0=삭제)
  	  dto.setCrtDate(Instructor.getCrtDate());
  	  dto.setUdtDate(Instructor.getUdtDate());
  	  
  	  // 조인 객체들
  	  dto.setCourse(Instructor.getCourse());
  	  dto.setUpfile(Instructor.getUpfiles());
      
      log.info("Read success");
      return dto;
   } // read  // 성공
   

   
   @PostMapping(value = "/{id}") 
   Instructor update(@PathVariable("id") Long instructorId, 
		   			 InstructorDTO dto,
		   			@RequestParam("upfiles") MultipartFile file) throws IllegalStateException, IOException {
      log.info("update({},{}) invoked.",instructorId,dto);
      
      // 1. 기존 Instructor 조회 (없으면 예외 발생)
      Instructor instructor = this.repo.findById(instructorId).orElseThrow(() -> new RuntimeException("해당 ID의 강사를 찾을 수 없습니다: " + instructorId));
      
      // 2. DTO에서 받은 값으로 업데이트
      instructor.setName(dto.getName());
      instructor.setTel(dto.getTel());
      
      if(dto.getStatus() != null) { // 상태값 유지 (register에서는 status=1로 고정, update에서는 DTO에서 받음)
         instructor.setStatus(dto.getStatus());
      } // if
      
      // 3. Course 설정 (register와 동일한 방식)
      if(dto.getCourseId() != null)
         instructor.setCourse(this.crsRepo.findById(dto.getCourseId()).orElse(new Course()));  // 담당과정
            

      if(file != null && !file.isEmpty()) { // 사진이 없는경우 대비를 위한 if-else 문.
      
      
      //  기존 파일 처리
      if (!instructor.getUpfiles().isEmpty()) {
          Upfile existingFile = instructor.getUpfiles().get(0); // 첫 번째 파일 가져오기
          String existingFileName = existingFile.getOriginal(); // 기존 파일명 가져오기

          // 새로운 파일명과 비교
          if (!existingFileName.equals(file.getOriginalFilename())) {
              // 기존 파일 비활성화 및 연관 관계 제거
        	  instructor.removeUpfile(existingFile);
              log.info("Existing file removed: {}", existingFile);
              this.fileRepo.save(existingFile); // 변경된 상태 저장
          } else {
              log.info("Same file detected, skipping removal.");
              return instructor; // 동일한 경우 업데이트 중단
          } // if-else
      } 
      
     
		 
		 Upfile upfile = new Upfile();  // 1. 파일 객체 생성
		 
		upfile.setOriginal(file.getOriginalFilename()); // DTO에서 파일 이름 가져오기
		upfile.setUuid(UUID.randomUUID().toString()); // 고유 식별자 생성
		upfile.setPath(InstructorFileDirectory); // 주소
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
      instructor.addUpfile(upfile);
      
      this.fileRepo.save(upfile); // // 새 파일 엔티티 저장
      
      } else {
    	  log.info("사진이 수정되지 않았습니다.");
      }
          
      
      // 4. 저장 및 반환
      Instructor update = this.repo.save(instructor);
      log.info("Update success: {}" , update);
      
      return update;
      
    } // update // 성공
   

   @DeleteMapping("/{id}")
   Instructor delete(@PathVariable("id") Long id) {
      log.info("delete({})",id);
      
      Instructor instructor  = this.repo.findById(id).orElseThrow(() -> new RuntimeException("해당 ID의 강사를 찾을 수 없습니다: " + id));
 
      instructor.setStatus(3);    // 상태(등록=1,강의중=2,퇴사=3)
      instructor.setEnabled(false);   // 삭제여부(1=유효,0=삭제)
          
   // 연관된 Instructor 처리
      if (instructor.getCourse() != null) {
    	  Course course = instructor.getCourse();
    	  course.setInstructor(null); // Course의 Instructor 목록에서 해당 Instructor 제거
    	  this.crsRepo.save(course); // 변경된 Course 저장
    	    
    	  instructor.setCourse(null); // Instructor의 Course 참조를 null로 설정
      } // if

      // 연관된 Upfile 처리
      if (!instructor.getUpfiles().isEmpty()) { // 파일이 비어있지 않으면
          for (Upfile upfile : new ArrayList<>(instructor.getUpfiles())) {
        	  instructor.removeUpfile(upfile); // Upfile의 상태를 비활성화하고 Course 참조를 null로 설정
          } // for
      } // if
      
      Instructor delete =  this.repo.save(instructor);
      
      log.info("Delete success");
      return delete;
   } // delete // 성공
   

} // end class
