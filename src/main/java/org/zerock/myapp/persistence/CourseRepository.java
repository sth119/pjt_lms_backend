package org.zerock.myapp.persistence;


import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.zerock.myapp.entity.Course;


//JpaSpecificationExecutor: 동적 검색을 위한 상속

@Repository
public interface CourseRepository extends JpaRepository<Course, Long>, JpaSpecificationExecutor<Course> {
	
	/* 검색
	 * ** 상태 => 기본 조건, 삭제여부 => 기본 조건
	 * 1. 과정구분 : 선택 X / O
	 * 2. 검색어 입력 : X / O
	 * 		- 과정명
	 *  	- 강사명
	 * */
	//검색 리스트: 활성화상태(1) 
	public abstract Page<Course> findByEnabled(
			Boolean enabled, Pageable paging
		);
	//검색 리스트: 활성화상태(1) => 페이징 X
	public abstract List<Course> findByEnabled(Boolean enabled);
	
	//검색 리스트: 활성화상태(1) + 진행여부 
	public abstract Page<Course> findByEnabledAndStatus(
			Boolean enabled, Integer status, Pageable paging
		);
		
	//검색 리스트: 활성화상태(1) + 진행여부 + 과정구분
	public abstract Page<Course> findByEnabledAndStatusAndType(
			Boolean enabled, Integer status, String type, Pageable paging
		);
	
	//검색 리스트: 활성화상태(1) + 진행상태 + 과정명
	public abstract Page<Course> findByEnabledAndStatusAndNameContaining(
			Boolean enabled, Integer status, String name, Pageable paging
		);
	
	//검색 리스트: 활성화상태(1) + 진행여부 + 과정구분 + 과정명
	public abstract Page<Course> findByEnabledAndStatusAndTypeAndNameContaining(
			Boolean enabled, Integer status, String type, String name, Pageable paging
		);
	
	//강사 & 훈련생 등록 화면: 담당과정 선택 리스트
	public abstract List<Course> findByEnabledAndStatusInOrderByStartDate(Boolean enabled, List<Integer> statuses);
	
	
	//Native SQL
	final String nativeSQL = """
			SELECT c.* 
			FROM t_courses c 
				JOIN t_instructors i ON c.id = i.crs_id 
			WHERE c.enabled = :enabled AND c.status = :status AND i.name LIKE '%' || :instructorName || '%'
		""";
	
	
	
	//단건 조회 :
	//	삭제를 Enabled false로 지정하였기 때문에 조회도 Enabled를 기본 조건으로 검색해야한다.
	public abstract Optional<Course> findByEnabledAndCourseId(Boolean enabled, Long courseId);
	

}//end interface
