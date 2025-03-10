package org.zerock.myapp.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.zerock.myapp.domain.MemberDTO;
import org.zerock.myapp.entity.Member;

@Service
public interface MemberService {
	
	public abstract List<Member> getAllList();     			// 전체 조회
	public abstract List<Member> getSearchList(MemberDTO dto); // 전체 조회(검색)

	public abstract Member create(MemberDTO dto);    	// 생성 처리
	public abstract Member getById(String courseId);    // 단일 조회
	public abstract Boolean update(MemberDTO dto);    	// 수정 처리
	public abstract Boolean deleteById(String courseId);// 삭제 처리
	
}//end interface