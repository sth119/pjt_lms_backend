package org.zerock.myapp.persistence;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.myapp.entity.Upfile;
import org.zerock.myapp.entity.User;


public interface UpFileRepository extends JpaRepository<Upfile, Long> {
	
//	//과정이미지
//	public abstract Slice<Upfile> findByCourseId(Long crsId, Pageable paging);
//	//강사이미지
//	public abstract Slice<Upfile> findByInstructorId(Long insId, Pageable paging);
//	//훈련생이미지
//	public abstract Slice<Upfile> findByTranineeId(Long trnId, Pageable paging);
//
//	
//	public abstract Boolean insertUpfile(UpfileDTO dto);		// C 삽입
	public abstract User findByFileId(Long fileId); 			// R 단건 조회 
//	public abstract Boolean updateUpfile(UpfileDTO dto);		// U 수정
//	public abstract Boolean deleteByFileId(Long fileId);		// D 삭제
	

	
	
	
	
	//로그인 처리?
//	public abstract Optional<User> findByUserIdAndUserPassword(String userId, String userPassword);
	
	
	
	//아이디 중복확인
//	public abstract boolean existsByUserId(String userId); //기본제공, 인터페이스 필요?? 

//	public Optional<User> login(String memberId, String password);

//	public Boolean checkPassword(String rawPassword, String encodedPassword); //성태 확인 필요 , 기존 메소드
	
	
	
} // end class