package org.zerock.myapp.persistence;

import java.awt.print.Pageable;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zerock.myapp.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
	
	/* 검색
	 * ** 상태 => 기본 조건
	 * 1. 과정구분 : 선택 X / O
	 * 2. 검색어 입력 : X / O
	 * 		- 과정명
	 *  	- 강사명
	 * */
	//검색 리스트: 진행상태, 다른 검색 없음 
	public abstract Page<Course> findByState(Integer state, Pageable paging);
		
	//검색 리스트: 진행상태 + 과정구분
	public abstract Page<Course> findByStateAndCrsType(Integer state, String crsType, Pageable paging);
	
	//검색 리스트: 진행상태 + 과정구분 + 과정명
	public abstract Page<Course> findByStateAndCrsTypeAndCrsNameContaining(Integer state, String crsType, String crsName, Pageable paging);
	
	//검색 리스트: 진행상태 + 과정구분 + 강사명   => 강사명은 강사 테이블에......?????
//	public abstract Page<Course> findByStateAndCrsType(Integer state, String crsType, Pageable paging);
	
	//검색 리스트: 진행상태 + 과정명
	public abstract Page<Course> findByStateAndCrsNameContaining(Integer state, String crsName, Pageable paging);
	
	//검색 리스트: 진행상태 + 강사명   => 강사명은 강사 테이블에......?????
//	public abstract Page<Course> findByState(Integer state, Pageable paging);
	



	
//	public abstract Boolean insertCourse(CourseDTO dto);	// C 삽입
	
	public abstract Course findByCrsCode(Integer crsCode); 	// R 단건 조회, 수강생 수는 member에서....
	
//	public abstract Boolean updateCourse(CourseDTO dto);	// U 수정
	
	public abstract Boolean deleteByCrsCode(Integer crsCode);	// D 삭제
	
	
}//end interface
