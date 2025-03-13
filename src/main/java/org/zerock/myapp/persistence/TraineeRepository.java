package org.zerock.myapp.persistence;

import java.util.List;
import java.util.Optional;

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
	
	//과정리스트 화면 -> 과정별 현재 수강생 Count
	public abstract Integer countByEnabledAndCourse(
			Boolean enabled, Course course
		);
	
	//과정조회 화면 -> 해당 과정 수강생 정보 리스트
	public abstract List<Trainee> findByEnabledAndCourse(
			Boolean enabled, Course course
		);
	
	
	
	
	//단건 조회 :
	//	삭제를 Enabled false로 지정하였기 때문에 조회도 Enabled를 기본 조건으로 검색해야한다.
	public abstract Optional<Trainee> findByEnabledAndTraineeId(Boolean enabled, Long traineeId);
	
	
	
	
	
	
}//end interface
