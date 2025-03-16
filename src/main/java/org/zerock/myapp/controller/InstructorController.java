package org.zerock.myapp.controller;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
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
//   String InstructorFileDirectory = "C:/temp/instructor/";
   String InstructorFileDirectory = "/Users/host/workspaces/tmep/";
   


   //RESTfull   
   @PostMapping // 리스트 
   
   Page<InstructorDTO> list(
		   @ModelAttribute InstructorDTO dto,

		   @RequestParam(name = "currPage", required = false, defaultValue = "0") Integer currPage,
		   @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize){
	   // @ RequestParam : 단일 값 바인딩 할때 사용. (String , Integer 등)
	   // @ ModelAttribute : 복합객체(DTO) 처리할 때 사용.
      log.info("list({}, {}, {}) invoked.", dto, currPage, pageSize);


      
      Pageable paging = PageRequest.of(currPage, pageSize, Sort.by("crtDate").descending());
      

      
      Page<Instructor> list = Page.empty();
      
      if(dto.getStatus() == null && dto.getSearchText() == null) {
      // 기본적으로 모든 데이터를 조회

     list = this.repo.findByEnabled( true, paging);
      }
      else if(dto.getStatus() != null && dto.getSearchText() == null) {
    	  //검색 리스트: 활성화상태(1) + status 
    	  list = this.repo.findByEnabledAndStatus(true, dto.getStatus(), paging);
      }
      else if(dto.getStatus() == null && dto.getSearchText() != null ) {
    	  //검색 리스트: 활성화상태(1) + 이름
    	  list = this.repo.findByEnabledAndNameContaining(true, dto.getName(),  paging);
      }
      else if(dto.getStatus() != null && dto.getSearchText() != null) {
    	
    	  if(dto.getName() != null && !dto.getName().isEmpty()) {
    	  //검색 리스트: 활성화상태(1) + status + 이름 
    	  list = this.repo.findByEnabledAndStatusAndNameContaining(true, dto.getStatus(), dto.getName(), paging);
    	  } else if  (dto.getTel() != null && !dto.getTel().isEmpty()) {
    		  //검색 리스트: 활성화상태(1) + status + 전화번호
    		  list = this.repo.findByEnabledAndStatusAndTelContaining(true, dto.getStatus(), dto.getTel(), paging); 		  
    	  } else {
    		  //검색 리스트: 활성화상태(1) + status 
    		  list = this.repo.findByEnabledAndStatus(true, dto.getStatus(), paging);
    	  } 
    	  
      } // if  

      List<InstructorDTO> dtoList = new ArrayList<>();
      list.getContent().forEach(s -> {
         InstructorDTO instructorDto = new InstructorDTO();
         instructorDto.setInstructorId(s.getInstructorId());
         instructorDto.setName(s.getName());            // 이름
         instructorDto.setTel(s.getTel());               // 전화번호
         instructorDto.setStatus(s.getStatus());            //   상태(등록=1,강의중=2,퇴사=3)
         instructorDto.setEnabled(s.getEnabled());         //   삭제여부(1=유효,0=삭제)
         instructorDto.setCrtDate(s.getCrtDate());
         
           // 조인 객체들
         instructorDto.setCourse(s.getCourse());
         instructorDto.setUpfile(s.getUpfiles());
         
         dtoList.add(instructorDto);
      }); // forEach
      Page<InstructorDTO> result = new PageImpl<>(dtoList, list.getPageable(), list.getTotalElements());
      // 위에서 DTO로 담은걸 Page로 다시 담음

      return result;

   } // list // 성공
   

   
   @PutMapping // 등록
//   @PostMapping
   Instructor register(
         @ModelAttribute InstructorDTO dto,
         @RequestParam(value = "upfiles", required = false) MultipartFile file // fix -> required = false 
      )throws Exception, IOException {
      
      log.info("register({}) invoked.",dto);
      
      
      
      Instructor instructor = new Instructor();
      
      instructor.setName(dto.getName()); // 이름
      instructor.setTel(dto.getTel()); // 전화번호
      instructor.setStatus(1);   // 기본값 등록(1), 상태(등록=1,강의중=2,퇴사=3)
      instructor.setEnabled(true);// 기본값 true, 삭제여부(1=유효,0=삭제)
      
      log.info("before success?");
      
      // 중복값 체크가 필요하다
      if (dto.getCourseId() != null && dto.getCourseId() > 0) {
    	    Course course = this.crsRepo.findById(dto.getCourseId()).orElse(null);
    	    if (course != null) {
    	        instructor.setCourse(course); // 담당과정
    	        course.setInstructor(instructor); // 양방향 참조를 위해 필요함
    	        this.crsRepo.save(course); // course도 저장해야 함
    	    } // if
    	} // if
      
      Instructor result = this.repo.save(instructor);
      log.info("Regist success");
      log.info("result:{}",result);

      if(file != null && !file.isEmpty()) {
		Upfile upfiles = new Upfile();  // 1. 파일 객체 생성
		upfiles.setOriginal(file.getOriginalFilename()); // DTO에서 파일 이름 가져오기
		upfiles.setUuid(UUID.randomUUID().toString()); // 고유 식별자 생성
		upfiles.setPath(InstructorFileDirectory); // 주소
		upfiles.setEnabled(true); // 기본값
		
		upfiles.setInstructor(result); // 2. 연관 관계 설정, 자식이 부모객체 저장(set)
		
		log.info("upfile:{}",upfiles);
		this.fileRepo.save(upfiles); // 파일 엔티티 저장
		

	
		   // 파일 저장 처리
//	    if (file != null && !file.isEmpty()) {
	        // 파일 저장 경로 생성
	        String uploadDir = upfiles.getPath();
	        File targetDir = new File(uploadDir);
	        if (!targetDir.exists()) {
	            targetDir.mkdirs(); // 디렉토리가 없는 경우 생성
	        } // if
	    
	        // 파일 저장 경로 및 이름 설정
	        
	        // error 수정 중.
	        String extension = upfiles.getOriginal().substring(upfiles.getOriginal().lastIndexOf('.') + 1);
	        String filePath = upfiles.getPath() + upfiles.getUuid() + "." + extension;
	        
	        
//	        String filePath = upfile.getPath() + upfile.getUuid() + "." + upfile.getOriginal().substring(upfile.getOriginal().lastIndexOf('.') + 1);
	        File savedFile = new File(filePath);
	        // 이후에 파일 받을때는 uuid에서 확장자를 뺴는 과정이 필요함.

	        // 파일 저장
	        file.transferTo(savedFile);
	        log.info("File saved at: {}", filePath);
	    //} // if
		
		// 4. Instructor에 Upfile 추가
		result.addUpfile(upfiles); // 3. 연관 관계 설정, 부모에 자식객체 저장(add)
		this.repo.save(result); // fix16
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
      
      
   // 연관 객체 강제 로드. 프록시 객체가 아닌 실제 데이터를 가져오게. // fix 16
      Hibernate.initialize(Instructor.getCourse());
      Hibernate.initialize(Instructor.getUpfiles());
      
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
//   @PutMapping(value = "/{id}")
   Instructor update(@PathVariable("id") Long instructorId, 
		   			 InstructorDTO dto,
		   			@RequestParam(value = "upfiles" , required = false) MultipartFile file) throws IllegalStateException, IOException {
      log.info("update({},{}) invoked.",instructorId,dto);
      
      // 1. 기존 Instructor 조회 (없으면 예외 발생)
      Instructor instructor = this.repo.findById(instructorId).orElseThrow(() -> new RuntimeException("해당 ID의 강사를 찾을 수 없습니다: " + instructorId));
      
   // 연관 객체 강제 로드 // fix 16
      Hibernate.initialize(instructor.getCourse());
      Hibernate.initialize(instructor.getUpfiles());
      
      // 2. DTO에서 받은 값으로 업데이트
      instructor.setName(dto.getName());
      instructor.setTel(dto.getTel());
      
      if(dto.getStatus() != null) { // 상태값 유지 (register에서는 status=1로 고정, update에서는 DTO에서 받음)
         instructor.setStatus(dto.getStatus());
      } // if
      
      // 3. Course 설정 (register와 동일한 방식)
//      if(dto.getCourseId() != null)
//         instructor.setCourse(this.crsRepo.findById(dto.getCourseId()).orElse(new Course()));  // 담당과정
            
//      if (dto.getCourseId() != null) {
//          Course course = this.crsRepo.findById(dto.getCourseId()).orElse(new Course());
//          instructor.setCourse(course);
//       }
      
      
      if (dto.getCourseId() != null && dto.getCourseId() > 0) {
    	  Course course = this.crsRepo.findById(dto.getCourseId()).orElse(null); // fix 16 course() ->null courseId 가 null 값이면 기존 값 유
    	  if(course != null) {
    		  instructor.setCourse(course);
    		  course.setInstructor(instructor);
    		  this.crsRepo.save(course);
    	  }
      }

      
   // 4. 파일 처리 // fix16
      if (file != null && !file.isEmpty()) {
          // 기존 파일이 있으면 제거
          if (instructor.getUpfiles() != null && !instructor.getUpfiles().isEmpty()) {
              Upfile existingFile = instructor.getUpfiles().get(0);
              if (!existingFile.getOriginal().equals(file.getOriginalFilename())) {
                  instructor.removeUpfile(existingFile);
                  log.info("Existing file removed: {}", existingFile);
                  this.fileRepo.save(existingFile);
              } else {
                  log.info("Same file detected, skipping file update.");
                  return instructor;
              }
          }

      
//      if (file != null && !file.isEmpty()) {
//          // 기존 파일이 null이 아닌지 먼저 확인
//          if (instructor.getUpfiles() != null && !instructor.getUpfiles().isEmpty()) {
//             Upfile existingFile = instructor.getUpfiles().get(0);
//             String existingFileName = existingFile.getOriginal();
//             // 새로운 파일과 기존 파일명이 다를 경우에만 기존 파일 제거
//             if (!existingFileName.equals(file.getOriginalFilename())) {
//                instructor.removeUpfile(existingFile);
//                log.info("Existing file removed: {}", existingFile);
//                this.fileRepo.save(existingFile);
//             } else {
//                log.info("Same file detected, skipping file update.");
//                return instructor;
//             }
//          }
          
          // 새 파일 저장
          Upfile upfiles = new Upfile();
          upfiles.setOriginal(file.getOriginalFilename());
          upfiles.setUuid(UUID.randomUUID().toString());
          upfiles.setPath(InstructorFileDirectory);
          upfiles.setEnabled(true);
          
          log.info("New upfile created: {}", upfiles);
          
          File targetDir = new File(InstructorFileDirectory);
          if (!targetDir.exists()) {
             targetDir.mkdirs();
          }
          
          String extension = upfiles.getOriginal().substring(upfiles.getOriginal().lastIndexOf('.') + 1);
          String filePath = upfiles.getPath() + upfiles.getUuid() + "." + extension;
          File savedFile = new File(filePath);
          file.transferTo(savedFile);
          log.info("File saved at: {}", filePath);
          
          upfiles.setInstructor(instructor);   // fix16      
          instructor.addUpfile(upfiles);
          this.fileRepo.save(upfiles);
       } else {
          log.info("파일이 수정되지 않았습니다.");
       }
       
       Instructor updated = this.repo.save(instructor);
       log.info("Update success: {}", updated);
       return updated;
      
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
          for (Upfile upfiles : new ArrayList<>(instructor.getUpfiles())) {
        	  instructor.removeUpfile(upfiles); // Upfile의 상태를 비활성화하고 Course 참조를 null로 설정
          } // for
      } // if
      
      Instructor delete =  this.repo.save(instructor);
      
      log.info("Delete success");
      return delete;
   } // delete // 성공
   
   
   @InitBinder // 특정 필드를 바인딩에서 제외하도록 설정.
   // 바인딩 : 클라이언트로부터 들어온 http 요청 데이터를 서버의 객체 (DTO, 모델)에 자동으로 매핑해주는 기능.
   public void initBinder(WebDataBinder binder) {
       // InstructorDTO의 course 필드는 바인딩하지 않음
       binder.setDisallowedFields("course");
   }

} // end class
