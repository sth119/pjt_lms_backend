package org.zerock.myapp.service;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface CommonService<T, ID> {
	
	public abstract List<T> getAllList();     		// 전체 조회
	public abstract List<T> getSearchList(T dto);   // 전체 조회(검색)
	public abstract T getById(ID id);         	// 단일 조회
	public abstract T create(T dto);    	// 생성 처리
	public abstract Boolean update(T dto);    	// 수정 처리
	public abstract Boolean deleteById(ID id); 	// 삭제 처리
	
}//end interface