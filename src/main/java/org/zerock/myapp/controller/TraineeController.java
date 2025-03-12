package org.zerock.myapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

	  @GetMapping
	    public Slice<Trainee> list(@ModelAttribute CriteriaDTO dto) {
	        log.info("list({}) invoked.", dto);

	        // 기본값 설정
	        Integer page = (dto.getPage() != null) ? dto.getPage() - 1 : 0;
	        Integer pageSize = (dto.getPageSize() != null) ? dto.getPageSize() : 10;
	        Pageable paging = PageRequest.of(page, pageSize);

	        // 검색 조건 적용
	        Slice<Trainee> result = null;

	        if (dto.getQ() != null && dto.getCondition() != null) {
	            switch (dto.getCondition()) {
	                case "name":
	                    result = repo.findByEnabledAndNameContaining(true, dto.getQ(), paging);
	                    break;
	                case "tel":
	                    result = repo.findByEnabledAndTelContaining(true, dto.getQ(), paging);
	                    break;
	                default:
	                    result = repo.findByEnabled(true, paging);
	                    break;
	            }
	        } else {
	            result = repo.findByEnabled(true, paging);
	        }

	        // 결과가 null이면 빈 Slice 반환
	        result = (result != null) ? result : new SliceImpl<>(List.of(), paging, false);

	        result.forEach(seq -> log.info(seq.toString()));
	        return result;
	    }//list
	
	
	
//	@PutMapping //값을 수정할때
	  @PostMapping //객체(값)를 만들때  
	  public Trainee register(@RequestBody TraineeDTO dto) {
	      log.info("register({}) invoked.", dto);

	      // DTO -> Entity 변환
	      Trainee trn = new Trainee();
	      trn.setTraineeId(dto.getTranineeId());
	      trn.setName(dto.getName());
	      trn.setTel(dto.getTel());
	      trn.setStatus(dto.getStatus());
	      trn.setEnabled(dto.getEnabled() != null ? dto.getEnabled() : true); // 기본값 설정
	      trn.setCrtDate(dto.getCrtDate());
	      trn.setUdtDate(dto.getUdtDate());
	      trn.setCourse(dto.getCourse());
	      trn.setUpfiles(null);

	      return repo.save(trn); // 새로운 훈련생 저장!
	  }
	
	@GetMapping("/{id}")
	Trainee read(@PathVariable Long id){
		log.info("read({}) invoked.",id);
		
		Trainee trn= repo.findById(id).orElse(null);
		
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
	

} // end class
