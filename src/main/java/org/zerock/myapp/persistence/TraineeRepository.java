package org.zerock.myapp.persistence;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zerock.myapp.entity.Course;
import org.zerock.myapp.entity.Trainee;

@Repository
public interface TraineeRepository extends JpaRepository<Trainee, Long> {
	
	/* 검색
	 * ** 기본 조건 => 활성화상태(enabled)
	 * 2. 검색어 
	 * 		- 이름
	 *  	- 전화번호
	 *  	- 소속과정
	 * */
	//검색 리스트: 활성화상태(1) 
	public abstract Slice<Trainee> findByEnabled(
			Boolean enabled, Pageable paging
		);
	
	//검색 리스트: 활성화상태(1) + 이름 
	public abstract Slice<Trainee> findByEnabledAndNameContaining(
			Boolean enabled, String name, Pageable paging
		);
		
	//검색 리스트: 활성화상태(1) + 전화번호
	public abstract Slice<Trainee> findByEnabledAndTelContaining(
			Boolean enabled, String tel, Pageable paging
		);
	
	
	
	
	

//	public abstract Boolean insertTrainee(TraineeDTO dto);	// C 삽입
	
//	public abstract Course findByTraineeId(Long traineeId); 	// R 단건 조회, 수강생 수는 member에서....
	
//	public abstract Boolean updateTrainee(TraineeDTO dto);	// U 수정
	
//	public abstract Boolean deleteByTraineeId(Long tranineeId);	// D 삭제
	
	
}//end interface
