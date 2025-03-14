package org.zerock.myapp.controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

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


   //RESTfull   
   @PostMapping // 리스트 
   Page<Instructor> list(@RequestBody CriteriaDTO dto, Pageable paging){
      log.info("list({}, {}) invoked.", dto, paging);
      
      //page는 기본 0부터 시작
      Integer page = (dto.getPage()!=null && dto.getPage() >= 0) ? dto.getPage() : 0;
      Integer pageSize = (dto.getPageSize()!=null && dto.getPageSize() >= 0) ? dto.getPageSize() : 10;
      String condition = dto.getCondition();
      String q = dto.getQ();
      
      paging = PageRequest.of(page, pageSize, Sort.by("status").ascending().and(Sort.by("crtDate").descending()));
      
      // 기본적으로 모든 데이터를 조회
      Page<Instructor> list = this.repo.findByEnabled(true, paging);
      

      return list;
   } // list // 성공
   

   
   @PutMapping // 등록
   Instructor register(
         InstructorDTO dto,
         @RequestParam("upfiles") MultipartFile file
      )throws Exception, IOException {
      
      log.info("register({}) invoked.",dto);
      
      Instructor instructor = new Instructor();
      
      instructor.setName(dto.getName()); // 이름
      instructor.setTel(dto.getTel()); // 전화번호
      
      if(dto.getCourseId() != null && dto.getCourseId() > 0) // 값이 있으면
         instructor.setCourse(this.crsRepo.findById(dto.getCourseId()).orElse(new Course()));  // 담당과정
      
      instructor.setStatus(1);   // 기본값 등록(1), 상태(등록=1,강의중=2,퇴사=3)
      instructor.setEnabled(true);// 기본값 true, 삭제여부(1=유효,0=삭제)
      log.info("before success?");
            
      Instructor result = this.repo.save(instructor);
      log.info("result:{}",result);
      log.info("Regist success");
      

		Upfile upfile = new Upfile();  // 1. 파일 객체 생성
		upfile.setOriginal(file.getOriginalFilename()); // DTO에서 파일 이름 가져오기
		upfile.setUuid(UUID.randomUUID().toString()); // 고유 식별자 생성
		upfile.setPath(InstructorFileDirectory); // 주소
		upfile.setEnabled(true); // 기본값
		
		upfile.setInstructor(result); // 2. 연관 관계 설정, 자식이 부모객체 저장(set)
		
		log.info("upfile:{}",upfile);
		this.fileRepo.save(upfile); // 파일 엔티티 저장
		
		
		   // 파일 저장 처리
	    if (file != null && !file.isEmpty()) {
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
	    } // if
		
		
		// 4. Course에 Upfile 추가
		result.addUpfile(upfile); // 3. 연관 관계 설정, 부모에 자식객체 저장(add)
		

      return result;
   } // register // 성공
   
 
   @GetMapping("/{id}") // 단일 조회 화면
   Instructor read(@PathVariable("id") Long instructorId){ // error fix -> ("id")로 명확히 표시
      log.info("read({}) invoked.",instructorId);
      
//      Optional<Instructor> optional = this.repo.findById(instructorId); // error fix ->  주석처리
      
      Instructor read = this.repo.findById(instructorId)
              .orElseThrow(() -> new RuntimeException("해당 ID의 강사를 찾을 수 없습니다: " + instructorId));
      
      log.info("Read success");
      return read;
   } // read  // 성공
   
   

   @PostMapping(value = "/{id}") 
   Instructor update(@PathVariable("id") Long instructorId, 
		   			 InstructorDTO dto) {
//      ResponseEntity<Object> update(@PathVariable("id") Long instructorId, @RequestBody InstructorDTO dto) {
      log.info("update({},{}) invoked.",instructorId,dto);
      
      // 1. 기존 Instructor 조회 (없으면 예외 발생)
      Instructor instructor = this.repo.findById(instructorId).orElseThrow(() -> new RuntimeException("해당 ID의 강사를 찾을 수 없습니다: " + instructorId));
      
      // 2. DTO에서 받은 값으로 업데이트
      instructor.setName(dto.getName());
      instructor.setTel(dto.getTel());
      
      // 3. Course 설정 (register와 동일한 방식)
      if(dto.getCourseId() != null)
         instructor.setCourse(this.crsRepo.findById(dto.getCourseId()).orElse(new Course()));  // 담당과정
            
      // 4. 상태값 유지 (register에서는 status=1로 고정, update에서는 DTO에서 받음)
      if(dto.getStatus() != null) {
         instructor.setStatus(dto.getStatus());
      }
      
      // 5. 저장 및 반환
      Instructor update = this.repo.save(instructor);
      log.info("Update success: {}" , update);
      
      return update;
      
    } // update // 성공
   

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
