package org.zerock.myapp.service;

import java.util.List;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.myapp.domain.CourseDTO;
import org.zerock.myapp.entity.Course;
import org.zerock.myapp.persistence.CourseRepository;

import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@NoArgsConstructor

@Service
public class CourseServiceImpl implements CourseService {	
	@Autowired CourseRepository dao;
	
	@PostConstruct
    void postConstruct(){
        log.debug("CourseServiceImpl -- postConstruct() invoked");
        log.debug("dao: {}", dao);
    }//postConstruct


	@Override
	public List<Course> getAllList() {	//검색 없는 전체 리스트
		log.debug("CourseServiceImpl -- getAllList() invoked");
		
		List<Course> list = dao.findAll();
		
		return list;
	}
	
	@Override
	public List<Course> getSearchList(CourseDTO dto) {	//검색 있는 전체 리스트
		log.debug("CourseServiceImpl -- getSearchList(()) invoked", dto);

		List<Course> list = new Vector<Course>();
		log.debug("리포지토리 미 생성");
		
		return list;
	}
	
	@Override
	public Course create(CourseDTO dto) {	//등록 처리
		log.debug("CourseServiceImpl -- create({}) invoked", dto);
		
		Course data = new Course();//dao.save(dto);
		log.debug("create data: {}", data);
		
		return data;
	}
	
	@Override
	public Course getById(String id) {	// 단일 조회
		log.debug("CourseServiceImpl -- getById({}) invoked", id);
		
		//값이 존재하면 반환하고, 없으면 new Course()와 같은 기본값을 반환합니다.
		Course data = new Course();//dao.findById(id).orElse(new Course());
		
		return data;
	}
	
	@Override
	public Boolean update(CourseDTO dto) {//수정 처리
		log.debug("CourseServiceImpl -- update({}) invoked", dto);
		
//		Course data = dao.save(dto);
//		log.debug("create data: {}", data);
		Boolean isUpdate = true;
		return isUpdate;
	}

	@Override
	public Boolean deleteById(String id) { // 삭제 처리
		log.debug("CourseServiceImpl -- deleteById({}) invoked", id);
		
		//dao.deleteById(id);
		
		return true;
	}
	
}//end class
