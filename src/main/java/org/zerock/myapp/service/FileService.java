package org.zerock.myapp.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.zerock.myapp.domain.FileDTO;
import org.zerock.myapp.entity.Upfile;

@Service
public interface FileService {
	
	public abstract List<Upfile> getList(String id); 		// 과정 or 회원 id에 따른 리스트

	public abstract Upfile create(FileDTO dto);    		// 생성 처리
	public abstract Upfile getById(Integer fileId);      	// 단일 조회
	public abstract Boolean update(FileDTO dto);    	// 수정 처리
	public abstract Boolean deleteById(Integer fileId); // 삭제 처리
	
}//end interface