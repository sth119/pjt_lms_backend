package org.zerock.myapp.persistence;


import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
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

	
	
	//단건 조회 :
	//	삭제를 Enabled false로 지정하였기 때문에 조회도 Enabled를 기본 조건으로 검색해야한다.
	public abstract Instructor findByEnabledAndInstructorId(Boolean enabled, Long instructorId);
	
	
}//end interface
