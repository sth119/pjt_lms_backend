package org.zerock.myapp.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.Vector;
import java.util.stream.Collectors;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.myapp.domain.CourseDTO;
import org.zerock.myapp.entity.Course;
import org.zerock.myapp.entity.Trainee;
import org.zerock.myapp.entity.Upfile;
import org.zerock.myapp.persistence.CourseRepository;
import org.zerock.myapp.persistence.InstructorRepository;
import org.zerock.myapp.persistence.TraineeRepository;
import org.zerock.myapp.persistence.UpFileRepository;

import jakarta.inject.Named;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@NoArgsConstructor

@RequestMapping("/course")
@RestController
// 과정 URI 컨트롤러
public class CourseController {
	@Autowired CourseRepository repo;
	@Autowired InstructorRepository insRepo;
	@Autowired TraineeRepository trnRepo;
	@Autowired UpFileRepository fileRepo;
	String CourseFileDirectory = "C:/temp/course/";
	
	//RESTfull
	
	
	//== 리스트 =============================== 
	//RequestParameter 확인 필요
	@PostMapping(path = "/list/{status1}")
	Page<Course> list(
			CourseDTO dto,
			@PathVariable Integer status1,
			@RequestParam(defaultValue = "0") Integer currPage, 
			@RequestParam(defaultValue = "10") Integer pageSize
		){
			log.debug("Course - list({}, {}, {}, {}) invoked.", dto, status1, currPage, pageSize);
			
			dto.setStatus(status1);
			
			//쿼리메소드 사용 시
			Pageable paging = PageRequest.of(currPage, pageSize, Sort.by("crtDate").descending().and(Sort.by("status").ascending()));
			//nativeSQL  사용 시
			Pageable paging2 = PageRequest.of(currPage, pageSize, Sort.Direction.DESC, "INSERT_TS");
					
			/* 리스트 검색 조건
			 * 1. dto.getEnabled();	// null, true 기본으로 셋팅
			 * 2. dto.getStatus();	// 네브바 검색으로 필수로 들어옴
			 * 
			 * 3. dto.getType(); //null 확인
			 * 
			 * 4. dto.getSearchWord();
			 * 	  dto.getSearchText();	//null 확인
			 * */
			
			Page<Course> list = Page.empty(); // 기본 값 설정
			
			if	   ( dto.getType() == null && dto.getSearchText() == null ) {
				//기본 검색 : 활성화상태(true) + 진행여부 
				list = this.repo.findByEnabledAndStatus(true, dto.getStatus(), paging);
			}
			else if( dto.getType() != null && dto.getSearchText() == null ) {
				//검색 리스트: 활성화상태(true) + 진행여부 + 과정구분
				list = this.repo.findByEnabledAndStatusAndType(true, dto.getStatus(), dto.getType(), paging);
			}
			else if( dto.getType() == null && dto.getSearchText() != null ) {
				if(dto.getSearchWord().equals("name")) {
					//검색 리스트: 활성화상태(true) + 진행상태 + 과정명
					list = this.repo.findByEnabledAndStatusAndNameContaining(true, dto.getStatus(), dto.getSearchText(), paging);
				}
				else if(dto.getSearchWord().equals("instructorName")) {	
					list = this.repo.findCoursesByInstructorName(true, dto.getStatus(), dto.getSearchText(), paging2);
				}
			}
			else if( dto.getType() != null && dto.getSearchText() != null ) {
				if(dto.getSearchWord().equals("name")) {
					//검색 리스트: 활성화상태(true) + 진행여부 + 과정구분 + 과정명
					list = this.repo.findByEnabledAndStatusAndTypeAndNameContaining(true, dto.getStatus(), dto.getType(), dto.getSearchText(), paging);
				}
				else if(dto.getSearchWord().equals("instructorName")) {
					list = this.repo.findCoursesByTypeAndInstructorName(true, dto.getStatus(), dto.getType(), dto.getSearchText(), paging2);
					
				}
			}
			
			//과정 리스트에 현재 수강생 수 담기 
			list.forEach(s -> {
				s.setCurrCount(this.trnRepo.countByEnabledAndCourse(true, s));
				log.info(s.toString());
			}); // forEach
		

		return list;
	} // list
	
	//RequestParameter 확인 완료
	@PutMapping // 등록
	Course register(
			CourseDTO dto, // String 으로 받아서 변환해야 한다
			@RequestParam("upfiles") MultipartFile file) throws Exception, IOException {
		log.info("register({}) invoked.",dto);
		
		Course course = new Course(); // 코스 객체 생성
		
		course.setType(dto.getType()); // 과정구분
		course.setName(dto.getName()); // 과정명
		course.setCapacity(dto.getCapacity()); // 수강정원
		course.setDetail(dto.getDetail()); // 내용
		course.setStartDate(dto.getStartDate()); // 시작일
		course.setEndDate(dto.getEndDate()); // 종료일
		
		course.setCurrCount(0); // 현재수강인원
		// currCount는 기본값 0
		// enabled 는 기본적으로 1이 자동으로 들어감
		
		Course result = this.repo.save(course); // DTO로 받아온 값 저장해서 DB에 올림
		log.info("result:{}",result);
		log.info("Regist success");
		
		
		Upfile upfile = new Upfile();  // 1. 파일 객체 생성
		upfile.setOriginal(file.getOriginalFilename()); // DTO에서 파일 이름 가져오기
		upfile.setUuid(UUID.randomUUID().toString()); // 고유 식별자 생성
		upfile.setPath(CourseFileDirectory); // 주소
		upfile.setEnabled(true); // 기본값
		
		upfile.setCourse(result); // 2. 연관 관계 설정, 자식이 부모객체 저장(set)
		
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
		
		log.info("result:{}",result);
		log.info("getFileCourse success");
		
		return result;
	} // register
	
	
	//read
	@GetMapping("/{id}") // 단일 조회 화면
	CourseDTO read(@PathVariable(name="id") Long id){
		log.info("read({}) invoked.",id);
		
		Course course = this.repo.findById(id) // id값 받아서 course 객체 단일 조회
		        .orElseThrow(() -> new RuntimeException("해당 ID의 코스를 찾을 수 없습니다: " + id));
		
		CourseDTO dto = new CourseDTO(); // DTO객체 생성해서 원하는 값만 전달하기 + 조인한 객체들
		dto.setCourseId(course.getCourseId());
		dto.setType(course.getType());
		dto.setName(course.getName());
		dto.setCapacity(course.getCapacity());
		dto.setDetail(course.getDetail());
		dto.setStartDate(course.getStartDate());
		dto.setEndDate(course.getEndDate());
		dto.setStatus(course.getStatus());
		dto.setEnabled(course.getEnabled());
		dto.setCrtDate(course.getCrtDate());
		
		// 테이블엔 없고, 직접 계산해야 하는 값
		Integer currCount = this.trnRepo.countByEnabledAndCourse(true, course);
		dto.setCurrCount(currCount);
		
		// 조인 객체들
		dto.setUpfile(course.getUpfiles());
		dto.setInstructor(course.getInstructor());
		dto.setTrainees(course.getTraninees());
		
		log.info("Read success");
		return dto;
	} // read
	
	
	//update
	@PostMapping(path = "/{id}")
	Course update(@PathVariable(name="id") Long id, 
				CourseDTO dto,
				@RequestParam("upfiles") MultipartFile file) throws IllegalStateException, IOException {
		log.info("update({},{}) invoked.",id,dto);
		
		Optional<Course> optionalCourse  = this.repo.findById(id);
		
		 if (optionalCourse.isPresent()) {
			 Course course = optionalCourse.get();
			 
			 course.setType(dto.getType());
			 course.setName(dto.getName());
			 course.setDetail(dto.getDetail());
			 course.setStartDate(dto.getStartDate());
			 course.setEndDate(dto.getEndDate());
			 
			 Integer currCount = this.trnRepo.countByEnabledAndCourse(true, course);
			 course.setCurrCount(currCount);
			 
			 course.setInstructor(course.getInstructor());
			 course.setTraninees(course.getTraninees());

	        // 3. 기존 파일 처리
	        if (!course.getUpfiles().isEmpty()) {
	            Upfile existingFile = course.getUpfiles().get(0); // 첫 번째 파일 가져오기
	            String existingFileName = existingFile.getOriginal(); // 기존 파일명 가져오기

	            // 새로운 파일명과 비교
	            if (!existingFileName.equals(file.getOriginalFilename())) {
	                // 기존 파일 비활성화 및 연관 관계 제거
	                course.removeUpfile(existingFile);
	                log.info("Existing file removed: {}", existingFile);
	                this.fileRepo.save(existingFile); // 변경된 상태 저장
	            } else {
	                log.info("Same file detected, skipping removal.");
	                return course; // 동일한 경우 업데이트 중단
	            } // if-else
	        } // if
			 
			 Upfile upfile = new Upfile();  // 1. 파일 객체 생성
			 
			upfile.setOriginal(file.getOriginalFilename()); // DTO에서 파일 이름 가져오기
			upfile.setUuid(UUID.randomUUID().toString()); // 고유 식별자 생성
			upfile.setPath(CourseFileDirectory); // 주소
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
            course.addUpfile(upfile);
            
            this.fileRepo.save(upfile); // // 새 파일 엔티티 저장
                
         // 7. Course 엔티티 저장
         Course result =  this.repo.save(course);
         
		 log.info("Update success");
		 
		 return result;
		 } else{
			log.info("같은 파일입니다");
		 }// if-else
			 
		 log.info("Update fail");
		return null; // 값이 없으면 NULL반환
	} // update
	
	
	//RequestParameter 확인 OK
	//Delete
	@DeleteMapping("/{id}")
	Course delete(@PathVariable(name="id") Long id) {
		log.info("delete({})",id);
		
		Optional<Course> optionalCourse  = this.repo.findById(id);
		
		if (optionalCourse.isPresent()) {
			 Course course = optionalCourse.get();
			 
			 course.setStatus(4);
			 course.setEnabled(false);
			 
			// 연관된 Instructor 처리
	        if (course.getInstructor() != null) {
	            course.getInstructor().setCourse(null); // Instructor의 Course 참조를 null로 설정
	            this.insRepo.save(course.getInstructor()); // 변경된 상태 저장
	        } // if

	        // 연관된 Trainee 처리
	        if (!course.getTraninees().isEmpty()) {
	            for (Trainee trainee : new ArrayList<>(course.getTraninees())) {
	            	course.removeTraninee(trainee); // Trainee의 Course 참조를 null로 설정
	            } // for
	        } // if

	        // 연관된 Upfile 처리
	        if (!course.getUpfiles().isEmpty()) {
	            for (Upfile upfile : new ArrayList<>(course.getUpfiles())) {
	            	course.removeUpfile(upfile); // Upfile의 상태를 비활성화하고 Course 참조를 null로 설정
	            } // for
	        } // if
			 
			 Course result =  this.repo.save(course);
			 
			 log.info("Delete success");
			 return result;
		 } // if
		log.info("Delete fail");
		return null;
	} // delete
	
	
	//RESTfull
	// 훈련생 등록 화면: 담당과정 선택 리스트
	@GetMapping("/selectCourseTrn") 
	public List<Course> selectCourseListTrainee(){
		log.info("selectCourseList() invoked.");
		
		List<CourseDTO> dtoList = new Vector<>(); // 값을 담을 DTO객체 생성
		
		List<Course> list = this.repo.findByEnabledAndStatusInOrderByStartDate(true, List.of(1, 2));
		
		// 수강 인원이 정원보다 작은 과정만 필터링
		list.stream()
		.peek(c -> c.setCurrCount(this.trnRepo.countByEnabledAndCourse(true, c))) // peek: currCount를 설정하기 위해 사용. forEach 대신 중간 연산으로 처리.
		.filter(c -> c.getCurrCount() < c.getCapacity()) // 정원 미만인 경우만 포함
		.collect(Collectors.toList());
		
//		list.forEach(course -> { // 순회해서 하나씩 넣기
//			CourseDTO dto = new CourseDTO(); // DTO객체 생성해서 원하는 값만 전달하기 + 조인한 객체들
//			
//			// 기존 Course 엔티티 값을 DTO에 셋팅
//			dto.setCourseId(course.getCourseId());
//			dto.setType(course.getType());
//			dto.setName(course.getName());
//			dto.setCapacity(course.getCapacity());
//			dto.setDetail(course.getDetail());
//			dto.setStartDate(course.getStartDate());
//			dto.setEndDate(course.getEndDate());
//			dto.setStatus(course.getStatus());
//			dto.setEnabled(course.getEnabled());
//			dto.setCrtDate(course.getCrtDate());
//			dto.setCurrCount(course.getCurrCount());
//			
//			// 조인 객체들
//			dto.setInstructor(course.getInstructor());
//			
//			dtoList.add(dto); // DTOList에 객체 하나씩 넣기
//		}); // list.forEach

		return list;
	} // 훈련생 수강정원 필터

	
	// 강사 등록 화면: 담당과정 선택 리스트, 강사 수강정원 필터.
	@GetMapping("/selectCourseIns") 
	public List<Course> selectCourseListInstructor(){
		log.info("selectCourseListInstructor() invoked.");
		
//		List<CourseDTO> dtoList = new Vector<>(); // 값을 담을 DTO객체 생성
		
		List<Course> list = this.repo.findCoursesByNotInstructorName(true); // enabled + status가 1,2인 course를 가져옴
//		list.forEach(course -> { // 순회해서 하나씩 넣기
//			CourseDTO dto = new CourseDTO(); // DTO객체 생성해서 원하는 값만 전달하기 + 조인한 객체들
//			
//			// 기존 Course 엔티티 값을 DTO에 셋팅
//			dto.setCourseId(course.getCourseId());
//			dto.setType(course.getType());
//			dto.setName(course.getName());
//			dto.setCapacity(course.getCapacity());
//			dto.setDetail(course.getDetail());
//			dto.setStartDate(course.getStartDate());
//			dto.setEndDate(course.getEndDate());
//			dto.setStatus(course.getStatus());
//			dto.setEnabled(course.getEnabled());
//			dto.setCrtDate(course.getCrtDate());
//			
//			Integer currCount = this.trnRepo.countByEnabledAndCourse(true, course);
//			dto.setCurrCount(currCount);
//			
//			// 조인 객체들
////			dto.setInstructor(course.getInstructor());
//			
//			dtoList.add(dto); // DTOList에 객체 하나씩 넣기
//		}); // list.forEach
		
		// 이거 그냥 /list에 넣어서 조건부 조회하게 하면 안 되는건가요?
		
		return list;
	} // 강사 수강정원 필터.
	
	
	 
} // end class
