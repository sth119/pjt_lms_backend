package org.zerock.myapp.persistence;


import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zerock.myapp.entity.Course;
import org.zerock.myapp.entity.Instructor;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long> {
	
	/* 검색
	 * ** 기본 조건 => 활성화상태(enabled)
	 * 2. 검색어 
	 * 		- 이름
	 *  	- 전화번호
	 *  	- 담당과정
	 * */
	//검색 리스트: 활성화상태(1) 
	public abstract Slice<Instructor> findByEnabled(
			Boolean enabled, Pageable paging
		);
	
	//검색 리스트: 활성화상태(1) + 이름 
	public abstract Slice<Instructor> findByEnabledAndNameContaining(
			Boolean enabled, String name, Pageable paging
		);
		
	//검색 리스트: 활성화상태(1) + 전화번호
	public abstract Slice<Instructor> findByEnabledAndTelContaining(
			Boolean enabled, String tel, Pageable paging
		);
	
	

//	public abstract Boolean insertInstructor(InstructorDTO dto);	// C 삽입
	
//	public abstract Course findByInstructorId(Long instructorId); 	// R 단건 조회
	
//	public abstract Boolean updateInstructor(InstructorDTO dto);	// U 수정
	
//	public abstract Boolean deleteByInstructorId(Long instructorId);	// D 삭제
	
	
}//end interface
