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
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.myapp.domain.CourseDTO;
import org.zerock.myapp.domain.CriteriaDTO;
import org.zerock.myapp.entity.Course;
import org.zerock.myapp.entity.Upfile;
import org.zerock.myapp.persistence.CourseRepository;
import org.zerock.myapp.persistence.InstructorRepository;
import org.zerock.myapp.persistence.TraineeRepository;
import org.zerock.myapp.persistence.UpFileRepository;
import org.zerock.myapp.persistence.spec.CourseSearchCriteria;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@NoArgsConstructor

@RequestMapping("/course")
@RestController
// 과정 URI 컨트롤러
public class CourseController {
	@Autowired CourseRepository repo;
	@Autowired InstructorRepository InsRepo;
	@Autowired TraineeRepository trnRepo;
	@Autowired UpFileRepository fileRepo;
	String CourseFileDirectory = "C:/temp/course/";
	
	//RESTfull
	
	
	//== 리스트 =============================== 
	//RequestParameter 확인 필요
	@PostMapping 
	Page<Course> list(
			CourseSearchCriteria criteria,	//동적 검색을 위한 conditions
			@RequestBody CriteriaDTO dto
		){
		log.info("list({}, {}) invoked.", criteria, dto);
		
		Integer currPage = (dto.getPage()!=null && dto.getPage() >= 0) ? dto.getPage() : 0;	//page는 기본 0부터 시작
		Integer pageSize = (dto.getPageSize()!=null && dto.getPageSize() >= 0) ? dto.getPageSize() : 10;
		String condition = dto.getCondition();
		String q = dto.getQ();
		
		//log.info("DTO list: {},{},{},{},{}",page,pageSize,condition,q,type);
		
//		Pageable paging = PageRequest.of(currPage - 1, pageSize, Sort.Direction.DESC, "id");	// 강사님 코드
		Pageable paging = PageRequest.of(currPage, pageSize, Sort.by("crtDate").descending().and(Sort.by("status").ascending()));
				
		
		
		Page<Course> list = this.repo.findByEnabled(true, paging);
		list.forEach(s -> {
			s.setCurrCount(this.trnRepo.countByEnabledAndCourse(true, s));
			log.info(s.toString());
		}); // forEach
		

		return list;
	} // list
	
	
	
	
	
	
	
	
	
//	//@GetMapping // DTO로 받기 위해서는 Post(json) 방식으로 줘야 한다
//	@PostMapping // 리스트 
//	Page<Course> list(@RequestBody CriteriaDTO dto, Pageable paging){
//		log.info("list({}, {}) invoked.", dto, paging);
//		
//		//page는 기본 0부터 시작
//		Integer page = (dto.getPage()!=null && dto.getPage() >= 0) ? dto.getPage() : 0;
//		Integer pageSize = (dto.getPageSize()!=null && dto.getPageSize() >= 0) ? dto.getPageSize() : 10;
//		String condition = dto.getCondition();
//		String q = dto.getQ();
//		
//		//log.info("DTO list: {},{},{},{},{}",page,pageSize,condition,q,type);
//		
//		paging = PageRequest.of(page, pageSize, Sort.by("crtDate").descending().and(Sort.by("status").ascending()));
//		
//		
//		Page<Course> list = this.repo.findByEnabled(true, paging);
//		list.forEach(s -> {
//			s.setCurrCount(this.trnRepo.countByEnabledAndCourse(true, s));
//			log.info(s.toString());
//		}); // forEach
//		
//
//		return list;
//	} // list
	
	
	
	
	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE) // 등록
	Course register(
			@RequestPart("dto") String dtoString, // String 으로 받아서 변환해야 한다
			@RequestPart("upfiles") MultipartFile file) throws Exception, IOException {
		log.info("register({}) invoked.",dtoString);
		
		// JSON 데이터를 DTO로 변환
	    ObjectMapper objectMapper = new ObjectMapper();
	    CourseDTO dto = objectMapper.readValue(dtoString, CourseDTO.class);
		
		
		Course course = new Course();
		
		course.setType(dto.getType()); // 과정구분
		course.setName(dto.getName()); // 과정명
		course.setCapacity(dto.getCapacity()); // 수강정원
		course.setDetail(dto.getDetail()); // 내용
		course.setStartDate(dto.getStartDate()); // 시작일
		course.setEndDate(dto.getEndDate()); // 종료일
		
		course.setCurrCount(0); // 현재수강인원
		// currCount는 기본값 0
		// enabled 는 기본적으로 1이 자동으로 들어감
		
		Course result = this.repo.save(course);
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
	
	
	
	//RequestParameter 확인 완료
	//read
	@GetMapping("/{id}") // 단일 조회 화면
	CourseDTO read(@PathVariable Long id){
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
	
	
	//RequestParameter 확인 필요
	//update
	@PostMapping(path = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	Course update(@PathVariable Long id, 
				@RequestPart("dto") String dtoString,
				@RequestPart("upfiles") MultipartFile file) throws IllegalStateException, IOException {
		log.info("update({},{}) invoked.",id,dtoString);
		
		ObjectMapper objectMapper = new ObjectMapper();
	    CourseDTO dto = objectMapper.readValue(dtoString, CourseDTO.class);
	    
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
			 
			// NullPointerException 방지를 위한 컬렉션 초기화
	        if (course.getUpfiles() == null) {
	            course.setUpfiles(new ArrayList<>()); // upfiles가 null이면 빈 리스트로 초기화
	        } // if
			 
			// Course에 파일이 있다면, 파일명을 가져온다.
			 String originFileName = course.getUpfiles().isEmpty() ? 
					 null : course.getUpfiles().get(0).getOriginal(); 
			 
			 Upfile upfile = new Upfile();  // 1. 파일 객체 생성
			 
			 // 기존에 있던 파일명과, 방금 업데이트한 파일 명을 비교한다.
			 if(originFileName == null ||
				!originFileName.equals(file.getOriginalFilename())) {
				 
				upfile.setOriginal(file.getOriginalFilename()); // DTO에서 파일 이름 가져오기
				upfile.setUuid(UUID.randomUUID().toString()); // 고유 식별자 생성
				upfile.setPath(CourseFileDirectory); // 주소
				upfile.setEnabled(true); // 기본값
				
				upfile.setCourse(course); // 2. 연관 관계 설정, 자식이 부모객체 저장(set)
				
				log.info("upfile:{}",upfile);
				this.fileRepo.save(upfile); // 파일 엔티티 저장
				
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
		        // 이후에 파일 받을때는 uuid에서 확장자를 뺴는 과정이 필요함.

		        // 파일 저장
		        file.transferTo(savedFile);
		        log.info("File saved at: {}", filePath); 
		        
			    // Course 엔티티에 새로운 파일 추가
                course.addUpfile(upfile);
			 } else{
				log.info("같은 파일입니다");
			 }// if-else
			 
			 Course result =  this.repo.save(course);
			 log.info("Update success");
			 
			 return result;
		 } // if
		 log.info("Update fail");
		return null; // 값이 없으면 NULL반환
	} // update
	
	
	//RequestParameter 확인 OK
	//Delete
	@DeleteMapping("/{id}")
	Course delete(@PathVariable Long id) {
		log.info("delete({})",id);
		
		Optional<Course> optionalCourse  = this.repo.findById(id);
		
		if (optionalCourse.isPresent()) {
			 Course course = optionalCourse.get();
			 
			 course.setStatus(4);
			 course.setEnabled(false);
			 
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
	public List<CourseDTO> selectCourseListTrainee(){
		log.info("selectCourseList() invoked.");
		
		List<CourseDTO> dtoList = new Vector<>(); // 값을 담을 DTO객체 생성
		
		List<Course> list = this.repo.findByEnabledAndStatusInOrderByStartDate(true, List.of(1, 2));
		
		// 수강 인원이 정원보다 작은 과정만 필터링
		list.stream()
		.peek(c -> c.setCurrCount(this.trnRepo.countByEnabledAndCourse(true, c))) // peek: currCount를 설정하기 위해 사용. forEach 대신 중간 연산으로 처리.
		.filter(c -> c.getCurrCount() < c.getCapacity()) // 정원 미만인 경우만 포함
		.collect(Collectors.toList());
		
		list.forEach(course -> { // 순회해서 하나씩 넣기
			CourseDTO dto = new CourseDTO(); // DTO객체 생성해서 원하는 값만 전달하기 + 조인한 객체들
			
			// 기존 Course 엔티티 값을 DTO에 셋팅
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
			dto.setCurrCount(course.getCurrCount());
			
			// 조인 객체들
			dto.setInstructor(course.getInstructor());
			
			dtoList.add(dto); // DTOList에 객체 하나씩 넣기
		}); // list.forEach

		return dtoList;
	} // 훈련생 수강정원 필터

	
	// 강사 등록 화면: 담당과정 선택 리스트, 강사 수강정원 필터.
	@GetMapping("/selectCourseIns") 
	public List<CourseDTO> selectCourseListInstructor(){
		log.info("selectCourseListInstructor() invoked.");
		
		List<CourseDTO> dtoList = new Vector<>(); // 값을 담을 DTO객체 생성
		
		List<Course> list = this.repo.findByEnabledAndStatusInOrderByStartDate(true, List.of(1, 2)); // enabled + status가 1,2인 course를 가져옴
		list.forEach(course -> { // 순회해서 하나씩 넣기
			CourseDTO dto = new CourseDTO(); // DTO객체 생성해서 원하는 값만 전달하기 + 조인한 객체들
			
			// 기존 Course 엔티티 값을 DTO에 셋팅
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
			
			Integer currCount = this.trnRepo.countByEnabledAndCourse(true, course);
			dto.setCurrCount(currCount);
			
			// 조인 객체들
			dto.setInstructor(course.getInstructor());
			
			dtoList.add(dto); // DTOList에 객체 하나씩 넣기
		}); // list.forEach
		
		// 이거 그냥 /list에 넣어서 조건부 조회하게 하면 안 되는건가요?
		
		return dtoList;
	} // 강사 수강정원 필터.
	
	
	 
} // end class
