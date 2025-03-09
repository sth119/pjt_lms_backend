package org.zerock.myapp.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zerock.myapp.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, String> {

//	//공통을 사용 가능한 LIST + CRUD 기능 수행 추상메소드 선언
//	
//	//검색은 어찌???/
//	public abstract List<Course> findAll();
//	
//	public abstract Optional<Course> findById(String id);		//R
//	
//	public abstract  Course save(Course dto); 	//C, U
	
//	public abstract void dedeleteById(String id); 			//D	
	
}//end interface
