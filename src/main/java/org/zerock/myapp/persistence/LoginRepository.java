package org.zerock.myapp.persistence;

import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.zerock.myapp.entity.Member;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public interface LoginRepository {
	public static final EntityManager entityManager = null;
	
	public Optional<Member> findByMemberId(String memberId);
	public Boolean checkPassword(String rawPassword, String encodedPassword);
	public Optional<Member> login(String memberId, String password);
	
	
}//end interface
