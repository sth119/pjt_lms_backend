package org.zerock.myapp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.zerock.myapp.domain.MemberType;
import org.zerock.myapp.entity.Member;
import org.zerock.myapp.persistence.LoginRepository;

import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@NoArgsConstructor

@Service
public class LoginServiceImpl implements LoginService {
    private PasswordEncoder passwordEncoder;
    @Autowired LoginRepository dao;
	
	
	@PostConstruct
    void postConstruct(){
        log.debug("MemberServiceImpl -- postConstruct() invoked");
        log.debug("dao: {}", dao);
    }//postConstruct

  // ================= 로그인 로직 =======================
    
    public Optional<Member> findByMemberId(String memberId) {
    	return this.dao.findByMemberId(memberId);
    }  // 사용자의 id 가 db 에 저장이 되어 있는지 검색

    public Boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    } // 비번이 일치하는지 검증.

    public Optional<Member> login(String memberId, String password) {
    	Optional<Member> memberOptional = this.dao.findByMemberId(memberId);
    	
    	if (memberOptional.isEmpty()) {
    		throw new IllegalArgumentException("아이디 또는 패스워드를 입력해주세요.");
    	} // 아이디 & 비밀번호 창이 비어있는 경우 검증
    	
    	Member member = memberOptional.get();
    	
    	if (!checkPassword(password, member.getMemberPassword())) {
    		throw new IllegalArgumentException("아이디 또는 패스워드가 틀립니다.");
    	} // 비밀번호 검증.
    	
    	if (member.getMemberTypeCode() != MemberType.MANAGER) {
    		throw new IllegalArgumentException("현재 관리자만 로그인 가능합니다.");
    	} // 관리자만 로그인 가능 
    	
    	return memberOptional;
    } // end Optional<MemberEntity> login
    
}//end class
