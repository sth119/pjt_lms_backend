package org.zerock.myapp.persistence;

import java.awt.print.Pageable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zerock.myapp.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
	
	/* 검색
	 * ** 상태 => 기본 조건, 삭제여부 => 기본 조건
	 * 1. 과정구분 : 선택 X / O
	 * 2. 검색어 입력 : X / O
	 * 		- 과정명
	 *  	- 강사명
	 * */
	//검색 리스트: 활성화상태(1) 
	public abstract Slice<Course> findByEnabled(
			Boolean enabled, Pageable paging
		);
	
	//검색 리스트: 활성화상태(1) + 진행여부 
	public abstract Slice<Course> findByEnabledAndStatus(
			Boolean enabled, Integer status, Pageable paging
		);
		
	//검색 리스트: 활성화상태(1) + 진행여부 + 과정구분
	public abstract Slice<Course> findByEnabledAndStatusAndCrsType(
			Boolean enabled, Integer status, String crsType, Pageable paging
		);
	
	//검색 리스트: 활성화상태(1) + 진행여부 + 과정구분 + 과정명
	public abstract Slice<Course> findByEnabledAndStatusAndCrsTypeAndCrsNameContaining(
			Boolean enabled, Integer status, String crsType, String crsName, Pageable paging
		);
	
	//검색 리스트: 활성화상태(1) + 진행여부 + 과정구분 + 강사명  => ????
//	public abstract Slice<Course> findByEnabledAndStatusAndCrsType(
//			Boolean enabled, Integer state, String crsType, Pageable paging
//		);
	
	//검색 리스트: 진행상태 + 과정명
	public abstract Slice<Course> findByEnabledAndStatusAndCrsNameContaining(
			Boolean enabled, Integer status, String crsName, Pageable paging
		);
	
	//검색 리스트: 진행상태 + 강사명  => ????
//	public abstract Slice<Course> findByEnabledAndStatus(
//			Boolean enabled, Integer status, Pageable paging
//		);
	
	
	

//	public abstract Boolean insertCourse(CourseDTO dto);	// C 삽입
	
	public abstract Course findByCourseId(String courseId); // R 단건 조회
	
//	public abstract Boolean updateCourse(CourseDTO dto);	// U 수정
	
//	public abstract Boolean deleteByCourseId(String courseId);	// D 삭제
	
	
}//end interface
