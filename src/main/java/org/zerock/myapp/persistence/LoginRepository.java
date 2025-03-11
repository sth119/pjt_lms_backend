package org.zerock.myapp.persistence;

import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.zerock.myapp.entity.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class LoginRepository {
	@PersistenceContext
    private EntityManager entityManager;
	
	public Optional<User> findByMemberId(String memberId);
	public Boolean checkPassword(String rawPassword, String encodedPassword);
	public Optional<User> login(String memberId, String password);
	
	
}//end interface
