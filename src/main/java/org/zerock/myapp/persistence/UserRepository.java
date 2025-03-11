package org.zerock.myapp.persistence;

import java.awt.print.Pageable;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.myapp.entity.User;


public interface UserRepository extends JpaRepository<User, String> {
	
	//전체 리스트
	public abstract Page<User> findAll(Pageable paging);
	
	//userName 검색
	public abstract Page<User> findByUserNameContaining(String userName, Pageable paging);
	
	
	
//	public abstract Boolean insertUser(UserDTO dto);		// C 삽입
	
	public abstract User findByUserId(Integer userId); 	// R 단건 조회, 수강생 수는 member에서....
	
//	public abstract Boolean updateUser(UserDTO dto);		// U 수정
	
	public abstract Boolean deleteByUserId(Integer userId);	// D 삭제
	
	

	//로그인 처리
	public abstract Optional<User> findByUserIdAndUserPassword(String userId, String userPassword);
	
	//아이디 중복확인
	public abstract boolean existsByUserId(String userId);
	
	
} // end class