package org.zerock.myapp.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zerock.myapp.entity.File;

@Repository
public interface FileRepository extends JpaRepository<File, Integer> {

//	//공통을 사용 가능한 LIST + CRUD 기능 수행 추상메소드 선언
//	
//	//검색은 어찌???/
//	public abstract List<File> findAll();
//	
//	public abstract Optional<File> findById(Integer id);		//R
//	
//	public abstract  File save(File dto); 						//C, U
//	
//	public abstract void deleteById(Integer id); 				//D	
	

}//end interface
