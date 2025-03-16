package org.zerock.myapp.persistence;


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.zerock.myapp.entity.Instructor;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long> , JpaSpecificationExecutor<Instructor> {
	
	
	/* 검색
	 * ** 기본 조건 => 활성화상태(enabled)
	 * 2. 검색어 
	 * 		- 이름
	 *  	- 전화번호
	 *  	- 담당과정
	 * */
	//검색 리스트: 활성화상태(1) 
	public abstract Page<Instructor> findByEnabled(
			Boolean enabled, Pageable paging
		);
	
	//검색 리스트: 활성화상태(1) + status 
	public abstract Page<Instructor> findByEnabledAndStatus(
			Boolean enabled, Integer status, Pageable paging
		);
	
	//검색 리스트: 활성화상태(1) + status + 이름 
	public abstract Page<Instructor> findByEnabledAndStatusAndNameContaining(
			Boolean enabled, Integer status, String name, Pageable paging
		);
	
	//검색 리스트: 활성화상태(1) + 이름 
	public abstract Page<Instructor> findByEnabledAndNameContaining(
			Boolean enabled, String name, Pageable paging
		);
		
	//검색 리스트: 활성화상태(1) + status + 전화번호
	public abstract Page<Instructor> findByEnabledAndStatusAndTelContaining(
			Boolean enabled, Integer status, String tel, Pageable paging
		);
		
	//검색 리스트: 활성화상태(1) + 전화번호
	public abstract Page<Instructor> findByEnabledAndTelContaining(
			Boolean enabled, String tel, Pageable paging
		);
	
	
	
	
	//단건 조회 :
	//	삭제를 Enabled false로 지정하였기 때문에 조회도 Enabled를 기본 조건으로 검색해야한다.
	public abstract Optional<Instructor> findByEnabledAndInstructorId(Boolean enabled, Long instructorId);
	
//  프로그램 실행시에 오류나서 잠시 주석처리
//	//과정 아이디로 강사정보 찾기
//	final String nativeSQL_findCrsId = """
//		select * 
//		from t_instructors 
//		where enabled = :enabled and crs_id = :crsId
//	""";
//	@Query(value = nativeSQL_findCrsId, nativeQuery = true)
//	public abstract Optional<Instructor> findByEnabledAndCrsId(Boolean enabled, @Param("crsId") Long crsId);

	
	// 과정을 강사명으로 찾을때 native 쿼리문 명령어
//	final String nativeSQL = """
//			SELECT c.*
//			FROM t_courses c JOIN t_instructors i ON c.id = i.crs_id
//			WHERE c.enabled = 1 AND i.name LIKE '%' || : instructorName || '%'
//			""";
//	
//	@Query(value = nativeSQL, nativeQuery = true)
//	Page<Course> findCoursesByInstructorName(@Param("instructorName") String name, Pageable paging);
	
}//end interface
