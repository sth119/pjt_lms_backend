package org.zerock.myapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
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
import org.zerock.myapp.entity.Course;
import org.zerock.myapp.entity.Trainee;
import org.zerock.myapp.persistence.TraineeRepository;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor

@RequestMapping("/trainee") // Base URI
@RestController
public class TraineeController {  // 훈련생 관리
	@Autowired TraineeRepository repo;

	//RESTfull	
	@PostMapping
	Slice<Trainee> list(@RequestBody CriteriaDTO dto, Pageable paging){
		log.info("list({},{}) invoked.",dto,paging);
		Trainee trainee = new Trainee();
		
		Integer page = dto.getPage();
		Integer pageSize = dto.getPageSize();
		String condition = dto.getCondition();
		String q = dto.getQ();
		//Integer type = dto.getType();
		//log.info("DTO list: {},{},{},{},{}",page,pageSize,condition,q,type);
		
		
		paging = PageRequest.of(page, pageSize);
		
		  // 기본적으로 모든 데이터를 조회
	    Slice<Trainee> slice = this.repo.findByEnabled(true, paging);
	    
	    // 조건에 맞는 데이터 필터링
	    List<Trainee> filteredList = slice.getContent().stream()
	        .filter(s -> {
	        	
	        	if (q != null) {
	                if (condition != null && condition.equals("id") && !String.valueOf(s.getTraineeId()).equals(q)) return false;
	                if (condition != null && condition.equals("name") && !s.getName().contains(q)) return false;
	                if (condition == null || !condition.equals("id") && !condition.equals("name")) {
	                    if (!s.getName().contains(q)) return false; // 기본적으로 이름으로 검색
	                } // if
	            } // if
	        	
	            //if (condition != null && dto.getCondition() != condition) return false;
	            //if (q != null && !s.getName().contains(q)) return false;
	            //if (type != null && s.getType() != type) return false;
	            
	            return true;
	        }) // filteredList
	        .toList();
	    
	    // Slice를 새로 생성하여 반환
	    Slice<Trainee> result = new SliceImpl<>(filteredList, paging, false);
	    
	    result.forEach(seq -> log.info(seq.toString()));
		
		return result;
	} // list
	
	@PutMapping
	Course register(TraineeDTO dto) {
		log.info("register({}) invoked.",dto);
		
		return null;
	} // register
	
	@GetMapping("/{id}")
	Course read(@PathVariable Integer id){
		log.info("read({}) invoked.",id);
		
		return null;
	} // read
	
	@PostMapping("/{id}")
	Course update(@PathVariable Integer id, TraineeDTO dto) {
		log.info("update({},{}) invoked.",id,dto);
		
		return null;
	} // update
	
	@DeleteMapping("/{id}")
	Course delete(@PathVariable Integer id) {
		log.info("delete({})",id);
		
		return null;
	} // delete
	
	
//	@PostMapping("/list")
//	String list() {  // 훈련생 리스트
//		log.debug("list() invoked.");
//		
//		return "/list";
//	} // list
//	
//	
//	@PostMapping("/register")
//	String register() { // 검색 기능
//		log.debug("register() invoked.");
//		
//		return "/register";
//	} // register
//	
//	@PostMapping("/read")
//	String read() { // 검색 기능
//		log.debug("read() invoked.");
//		
//		return "/read";
//	} // read
//
//	@PostMapping("/update")
//	String update() { // 검색 기능
//		log.debug("update() invoked.");
//		
//		return "/update";
//	}
//	
//	@PostMapping("/delete")
//	String delete() { // 검색 기능
//		log.debug("delete() invoked.");
//		
//		return "/delete";
//	} // delete
	
} // end class
