package org.zerock.myapp.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.zerock.myapp.domain.CourseDTO;
import org.zerock.myapp.entity.Course;

@Service
public interface CourseService {
	
	public abstract List<Course> getAllList();     			// 전체 조회
	public abstract List<Course> getSearchList(CourseDTO dto); // 전체 조회(검색)

	public abstract Course create(CourseDTO dto);    	// 생성 처리
	public abstract Course getById(String courseId);    // 단일 조회
	public abstract Boolean update(CourseDTO dto);    	// 수정 처리
	public abstract Boolean deleteById(String courseId);// 삭제 처리
	
}//end interface