package org.zerock.myapp.service;

import java.util.Optional;

import org.zerock.myapp.entity.Member;

public interface LoginService {
	// 로그인 로직
	public abstract Optional<Member> findByMemberId(String memberId);
	public abstract Boolean checkPassword(String rawPassword, String encodedPassword);
	public abstract Optional<Member> login(String memberId, String password);
} //end interface