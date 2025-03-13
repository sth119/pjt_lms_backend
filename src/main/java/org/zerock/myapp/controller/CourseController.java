package org.zerock.myapp.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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
import org.zerock.myapp.persistence.TraineeRepository;
import org.zerock.myapp.persistence.UpFileRepository;

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
	@Autowired TraineeRepository trnRepo;
	@Autowired UpFileRepository fileRepo;
	
	//RESTfull	
	//@GetMapping // DTO로 받기 위해서는 Post(json) 방식으로 줘야 한다
	@PostMapping // 리스트 
	Page<Course> list(@RequestBody CriteriaDTO dto, Pageable paging){
//	List<Course> list(@RequestBody CriteriaDTO dto, Pageable paging){ // Pageable paging는 아직 실험중
		log.info("list({}, {}) invoked.", dto, paging);
		
		Integer page = dto.getPage();
		Integer pageSize = dto.getPageSize();
		String condition = dto.getCondition();
		String q = dto.getQ();
		
		paging = PageRequest.of(page, pageSize, Sort.by("crtDate").descending());
		
		
		Page<Course> list = this.repo.findByEnabled(true, paging);
		list.forEach(s -> {
			s.setCurrCount(this.trnRepo.countByEnabledAndCourse(true, s));
			log.info(s.toString());
		}); // forEach
		

		return list;
	} // list
	
	
	
	
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
		

		Upfile upfile = new Upfile();  // 파일 객체 생성
		upfile.setOriginal(file.getOriginalFilename()); // DTO에서 파일 이름 가져오기
		upfile.setUuid(UUID.randomUUID().toString()); // 고유 식별자 생성
		upfile.setPath("C:/temp/course/"); // 주소
		upfile.setEnabled(true); // 기본값
		
		upfile.setCourse(result); // 연관 관계 설정, 자식이 부모객체 저장(set)
		
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
		result.addUpfile(upfile); // 연관 관계 설정, 부모에 자식객체 저장(add)
		
		log.info("result:{}",result);
		log.info("getFileCourse success");
		
		return result;
	} // register
	
	@GetMapping("/{id}") // 단일 조회 화면
	Course read(@PathVariable Long id){
		log.info("read({}) invoked.",id);
		
		Course course = this.repo.findById(id)
		        .orElseThrow(() -> new RuntimeException("해당 ID의 코스를 찾을 수 없습니다: " + id));
		
		log.info("Read success");
		return course;
	} // read
	
	@PostMapping("/{id}")
	Course update(@PathVariable Long id, @RequestBody CourseDTO dto) {
		log.info("update({},{}) invoked.",id,dto);
		
		Optional<Course> optionalCourse  = this.repo.findById(id);
		
		 if (optionalCourse.isPresent()) {
			 Course course = optionalCourse.get();
			 
			 course.setType(dto.getType());
			 course.setName(dto.getName());
			 course.setCapacity(dto.getCapacity());
			 course.setDetail(dto.getDetail());
			 course.setStartDate(dto.getStartDate());
			 course.setEndDate(dto.getEndDate());
			 
			 Course result =  this.repo.save(course);
			 
			 log.info("Update success");
			 return result;
		 } // if
		 log.info("Update fail");
		return null; // 값이 없으면 NULL반환
	} // update
	
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
	public List<Course> selectCourseListTrainee(){
		log.info("selectCourseList() invoked.");
		
		List<Course> list = this.repo.findByEnabledAndStatusInOrderByStartDate(true, List.of(1, 2));
		
		// 수강 인원이 정원보다 작은 과정만 필터링
		list.stream()
		.peek(c -> c.setCurrCount(this.trnRepo.countByEnabledAndCourse(true, c))) // peek: currCount를 설정하기 위해 사용. forEach 대신 중간 연산으로 처리.
		.filter(c -> c.getCurrCount() < c.getCapacity()) // 정원 미만인 경우만 포함
		.collect(Collectors.toList());

		return list;
	} // 훈련생 수강정원 필터

	
	// 강사 등록 화면: 담당과정 선택 리스트
	@GetMapping("/selectCourseIns") 
	public List<Course> selectCourseListInstructor(){
		log.info("selectCourseListInstructor() invoked.");
		
		List<Course> list = this.repo.findByEnabledAndStatusInOrderByStartDate(true, List.of(1, 2));
		
		return list;
		
	} // 강사 수강정원 필터.
	
	
	 
} // end class
