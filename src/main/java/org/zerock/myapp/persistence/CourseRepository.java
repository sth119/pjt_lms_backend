package org.zerock.myapp.persistence;


import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
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
	//검색 리스트: 활성화상태(1) => 페이징 X
	public abstract List<Course> findByEnabled(Boolean enabled);
	
	//검색 리스트: 활성화상태(1) + 진행여부 
	public abstract Slice<Course> findByEnabledAndStatus(
			Boolean enabled, Integer status, Pageable paging
		);
		
	//검색 리스트: 활성화상태(1) + 진행여부 + 과정구분
	public abstract Slice<Course> findByEnabledAndStatusAndType(
			Boolean enabled, Integer status, String type, Pageable paging
		);
	
	//검색 리스트: 활성화상태(1) + 진행상태 + 과정명
	public abstract Slice<Course> findByEnabledAndStatusAndNameContaining(
			Boolean enabled, Integer status, String name, Pageable paging
		);
	
	//검색 리스트: 활성화상태(1) + 진행여부 + 과정구분 + 과정명
	public abstract Slice<Course> findByEnabledAndStatusAndTypeAndNameContaining(
			Boolean enabled, Integer status, String type, String name, Pageable paging
		);
	
	public abstract Course findByEnabledAndId(Boolean enabled, Long id);
	

}//end interface
