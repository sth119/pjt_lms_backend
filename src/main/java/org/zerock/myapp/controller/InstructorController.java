package org.zerock.myapp.controller;



import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

import jakarta.servlet.ServletContext;
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
    	  
    	  log.info("기본적으로 모든 데이터를 조회");
     list = this.repo.findByEnabled( true, paging);
      }
      else if(dto.getStatus() != null && dto.getSearchText() == null) {
         //검색 리스트: 활성화상태(1) + status 
    	  log.info("활성화상태 + status");
         list = this.repo.findByEnabledAndStatus(true, dto.getStatus(), paging);
      }
      else if(dto.getStatus() == null && dto.getSearchText() != null ) {
    	  
    	  if(dto.getSearchWord().equals("name")) {
         //검색 리스트: 활성화상태(1) + 이름
    		  log.info("활성화상태(1) + 이름");  
         list = this.repo.findByEnabledAndNameContaining(true, dto.getSearchText(),  paging);
    	  } 
    	  else if (dto.getSearchWord().equals("tel")) {
    		  log.info("활성화상태(1) + 전화."); 
    		  list = this.repo.findByEnabledAndTelContaining(true, dto.getSearchText(), paging);
    	  }
    	  
      }
      else if(dto.getStatus() != null && dto.getSearchText() != null) {
    	  
    	 if(dto.getSearchWord().equals("name")) {
    		 //검색 리스트: 활성화상태(1) + status + 이름 
    		 log.info("활성화상태(1) + status + 이름" );
    		 list = this.repo.findByEnabledAndStatusAndNameContaining(true, dto.getStatus(), dto.getSearchText(), paging);
    	 } else if (dto.getSearchWord().equals("tel")) {
    		 //검색 리스트: 활성화상태(1) + status + 전화번호
    		 log.info("활성화상태(1) + status + 전화번호" );
    		 list = this.repo.findByEnabledAndStatusAndTelContaining(true, dto.getStatus(), dto.getSearchText(), paging);  
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
      instructor.setCourse(this.crsRepo.findById(dto.getCourseId()).orElse(null));
      
      log.info("before success?");
      Instructor result = this.repo.save(instructor);

      log.info("result:{}",result);
      log.info("Regist success");
      
      // "path": "C:\\Users\\chltj\\Desktop\\프로젝트\\깃버전\\pjt_lms_backend/src/main/resources/static/instructorFile/" 로 전송
      String fileDirectory = System.getProperty("user.dir") + "/src/main/resources/static/instructorFile/"; // 백에서 저장할 주소
      String useDirectory = "/instructorFile/"; // 프론트로 보낼 주소
      

      if(file != null && !file.isEmpty()) {
	      Upfile upfiles = new Upfile();  // 1. 파일 객체 생성
	      upfiles.setOriginal(file.getOriginalFilename()); // DTO에서 파일 이름 가져오기
	      
	      String extension = upfiles.getOriginal().substring(upfiles.getOriginal().lastIndexOf('.') + 1);
	      
	      upfiles.setUuid(UUID.randomUUID().toString()+"."+extension); // 고유 식별자 생성
	      upfiles.setPath(useDirectory); // 주소
	      upfiles.setEnabled(true); // 기본값
	      
	      upfiles.setInstructor(result); // 2. 연관 관계 설정, 자식이 부모객체 저장(set)
	      
	      log.info("upfile:{}",upfiles);
	      this.fileRepo.save(upfiles); // 파일 엔티티 저장

	      // 파일 저장 처리
           // 파일 저장 경로 생성
           File targetDir = new File(fileDirectory);
           if (!targetDir.exists()) {
               targetDir.mkdirs(); // 디렉토리가 없는 경우 생성
           } // if
       
           // 파일 저장 경로 및 이름 설정
           
           String filePath = fileDirectory + upfiles.getOriginal();
           File savedFile = new File(filePath);
           
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
      } // if-else
      return result;
   } // register // 성공
   
 
   
   @GetMapping("/{id}") // 단일 조회 화면
   InstructorDTO read(@PathVariable("id") Long instructorId){ // error fix -> ("id")로 명확히 표시
      log.info("read({}) invoked.",instructorId);
      
      Instructor Instructor = this.repo.findById(instructorId)
              .orElseThrow(() -> new RuntimeException("해당 ID의 강사를 찾을 수 없습니다: " + instructorId));

      InstructorDTO dto = new InstructorDTO();
       dto.setInstructorId(Instructor.getInstructorId());         // 아이디

       dto.setName(Instructor.getName());            // 이름
       dto.setTel(Instructor.getTel());               // 전화번호
       dto.setStatus(Instructor.getStatus());            //   상태(등록=1,강의중=2,퇴사=3)
       dto.setEnabled(Instructor.getEnabled());         //   삭제여부(1=유효,0=삭제)
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
                  @RequestParam(value = "upfiles" , required = false) MultipartFile file) 
                throws IllegalStateException, IOException {
      log.info("update({},{}) invoked.",instructorId,dto);
      
      // 1. 기존 Instructor 조회 (없으면 예외 발생)
      Instructor instructor = this.repo.findById(instructorId).orElseThrow(() -> new RuntimeException("해당 ID의 강사를 찾을 수 없습니다: " + instructorId));
      
      // 2. DTO에서 받은 값으로 업데이트
      instructor.setName(dto.getName());
      instructor.setTel(dto.getTel());
      instructor.setStatus(dto.getStatus());
      instructor.setCourse(this.crsRepo.findById(dto.getCourseId()).orElse(null));
      
      String fileDirectory = System.getProperty("user.dir") + "/src/main/resources/static/instructorFile/"; // 백에서 저장할 주소
      String useDirectory = "/instructorFile/"; // 프론트로 보낼 주소
      
      // 4. 파일 처리 // fix16
      if (file != null && !file.isEmpty()) { 
	      //  기존 파일 처리
    	  if (instructor.getUpfiles() != null && !instructor.getUpfiles().isEmpty()) {
              Upfile existingFile = instructor.getUpfiles().get(0);
              instructor.removeUpfile(existingFile);
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
	
	      
		   // 임시 파일을 실제 경로로 복사
		   try {
		       file.transferTo(savedFile);
		       log.info("File saved at: {}", filePath);
		   } catch (IOException e) {
		       log.error("Failed to save file", e);
		   } // try-catch
	      
		  // Course 엔티티에 새로운 파일 추가
	      instructor.addUpfile(upfile);
	      this.fileRepo.save(upfile); // // 새 파일 엔티티 저장
      } else {
          log.info("파일이 수정되지 않았습니다.");
       } // if-else
       
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
