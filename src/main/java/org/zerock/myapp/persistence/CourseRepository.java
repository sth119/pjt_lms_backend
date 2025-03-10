package org.zerock.myapp.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zerock.myapp.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
	
	
	public abstract List<Course> findAllOrderByStartDateDescEndDateDesc();
	
	//1. 예정, 중, 완료 를 한번에 구현 가능한지?
	//2. 검색항목 word, 검색어 text 를 이용하여 동적 조회 가능한지?
	//3. 검색 항목을 선택하지 않은 조건 빈값을 경우 처리 여부
	
	//진행예정
	//검색항목 미 구현
	public abstract List<Course> findByStartDateGreaterThanAndCrsTypeOrderByStartDateDescEndDateDesc(
			  String today		//'2025-03-11'
			, String crsType	//'java'
		);
	
	//진행중
	//검색항목 미 구현
	public abstract List<Course> findByStartDateLessThanEqualAndEndDateGreaterThanEqualAndCrsTypeOrderByStartDateDescEndDateDesc(
			  String today		//'2025-03-11'
			, String crsType	//'java'
		);
	
	//진행완료
	//검색항목 미 구현
	public abstract List<Course> findByEndDateLessThanAndCrsTypeOrderByStartDateDescEndDateDesc(
			  String today		//'2025-03-11'
			, String crsType	//'java'
		);
	
	public abstract Course findByCrsCode(Integer id);
	
//	public abstract	
	
	public abstract void deleteByCrsCode(Integer id);
	
}//end interface
