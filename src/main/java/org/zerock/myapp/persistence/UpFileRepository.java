package org.zerock.myapp.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.myapp.entity.Course;
import org.zerock.myapp.entity.Instructor;
import org.zerock.myapp.entity.Trainee;
import org.zerock.myapp.entity.Upfile;


public interface UpFileRepository extends JpaRepository<Upfile, Long> {
	
	//과정이미지
	public abstract List<Upfile> findByEnabledAndCourse(Boolean enabled, Course course);
	//강사이미지
	public abstract List<Upfile> findByEnabledAndInstructor(Boolean enabled, Instructor instructor);
	//훈련생이미지
	public abstract List<Upfile> findByEnabledAndTrainee(Boolean enabled, Trainee trainee);
	
	//단건 조회 :
	//	삭제를 Enabled false로 지정하였기 때문에 조회도 Enabled를 기본 조건으로 검색해야한다.
	public abstract Optional<Upfile> findByEnabledAndFileId(Boolean enabled, Long fileId);

} // end class