package org.zerock.myapp.persistence;


import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
	//검색 리스트: 활성화상태(true) 
	public abstract Page<Course> findByEnabled(
			Boolean enabled, Pageable paging
		);
	//검색 리스트: 활성화상태(true) => 페이징 X
	public abstract List<Course> findByEnabled(Boolean enabled);
	
	//검색 리스트: 활성화상태(true) + 진행여부 
	public abstract Page<Course> findByEnabledAndStatus(
			Boolean enabled, Integer status, Pageable paging
		);
		
	//검색 리스트: 활성화상태(true) + 진행여부 + 과정구분
	public abstract Page<Course> findByEnabledAndStatusAndType(
			Boolean enabled, Integer status, Integer type, Pageable paging
		);
	
	//검색 리스트: 활성화상태(true) + 진행상태 + 과정명
	public abstract Page<Course> findByEnabledAndStatusAndNameContaining(
			Boolean enabled, Integer status, String name, Pageable paging
		);
	
	//검색 리스트: 활성화상태(true) + 진행여부 + 과정구분 + 과정명
	public abstract Page<Course> findByEnabledAndStatusAndTypeAndNameContaining(
			Boolean enabled, Integer status, Integer type, String name, Pageable paging
		);
	
	//검색 리스트: 활성화상태(true) + 진행여부 + 과정구분 + 강사명
	final String nativeSQL_TypeAndInsName = """
			SELECT c.* 
			FROM t_courses c 
				JOIN t_instructors i ON c.id = i.crs_id 
			WHERE c.enabled = :enabled AND c.status = :status AND c.type = :type AND i.name LIKE '%' || :instructorName || '%'
		""";		
	@Query(value = nativeSQL_TypeAndInsName, nativeQuery = true)
	Page<Course> findCoursesByTypeAndInstructorName(
			@Param("enabled") Boolean enabled, 
			@Param("status") Integer status, 
			@Param("type") Integer type, 
			@Param("instructorName") String instructorName, 
			Pageable paging
		);
	
	//검색 리스트: 활성화상태(true) + 진행여부 + 강사명
	final String nativeSQL_InsName = """
			SELECT c.* 
			FROM t_courses c 
				JOIN t_instructors i ON c.id = i.crs_id 
			WHERE c.enabled = :enabled AND c.status = :status AND i.name LIKE '%' || :instructorName || '%'
		""";
	@Query(value = nativeSQL_InsName, nativeQuery = true)
	Page<Course> findCoursesByInstructorName(
			@Param("enabled") Boolean enabled, 
			@Param("status") Integer status, 
			@Param("instructorName") String instructorName, 
			Pageable paging
		);
	
	

	
	//훈련생 등록 화면: 신청과정 선택 리스트
	public abstract List<Course> findByEnabledAndStatusInOrderByStartDate(Boolean enabled, List<Integer> statuses);

	
	//강사 등록 화면: 담당과정 선택 리스트
	final String nativeSQL_InsRegCourseList = """
			SELECT c.* 
			FROM t_courses c 
				LEFT JOIN t_instructors i ON c.id = i.crs_id 
			WHERE c.enabled = :enabled AND c.status in (1, 2) AND i.name is null	--상태(등록=1,진행중=2,폐지=3,종료=4)
			--ORDER BY c.INSERT_TS DESC
			
		""";
	@Query(value = nativeSQL_InsRegCourseList, nativeQuery = true)
	List<Course> findCoursesByNotInstructorName(
			@Param("enabled") Boolean enabled
		);
	
	
	//강사 수정 화면: 담당과정 선택 리스트
	final String nativeSQL_InsRegCourseListAndInsId = """
			SELECT c.* 
			FROM t_courses c 
				LEFT JOIN t_instructors i ON c.id = i.crs_id 
			WHERE c.enabled = 1 AND c.status in (1, 2) --상태(등록=1,진행중=2,폐지=3,종료=4)
                AND (i.id = :instructorId or i.name is null) 	
			ORDER BY c.INSERT_TS DESC
			
		""";
	@Query(value = nativeSQL_InsRegCourseListAndInsId, nativeQuery = true)
	List<Course> findCoursesByNotInstructorNameAndInsId(
			@Param("enabled") Boolean enabled,
			@Param("instructorId") Integer instructorId
		);
	

	
	//단건 조회 :
	//	삭제를 Enabled false로 지정하였기 때문에 조회도 Enabled를 기본 조건으로 검색해야한다.
	public abstract Optional<Course> findByEnabledAndCourseId(Boolean enabled, Long courseId);
	

}//end interface
