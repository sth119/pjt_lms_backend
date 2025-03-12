package org.zerock.myapp.persistence;

import java.awt.print.Pageable;

import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.myapp.entity.User;


public interface UserRepository extends JpaRepository<User, String> {

	//userName 검색
	public abstract Slice<User> findByNameContaining(String name, Pageable paging);

	
//	public abstract Boolean insertUser(UserDTO dto);		// C 삽입
	public abstract User findByUserId(String userId); 		// R 단건 조회 
//	public abstract Boolean updateUser(UserDTO dto);		// U 수정
//	public abstract Boolean deleteByUserId(String userId);	// D 삭제
	

	
	
	
	
	//로그인 처리?
//	public abstract Optional<User> findByUserIdAndUserPassword(String userId, String userPassword);
	
	
	
	//아이디 중복확인
//	public abstract boolean existsByUserId(String userId); //기본제공, 인터페이스 필요?? 

//	public Optional<User> login(String memberId, String password);

//	public Boolean checkPassword(String rawPassword, String encodedPassword); //성태 확인 필요 , 기존 메소드
	
	
	
} // end class