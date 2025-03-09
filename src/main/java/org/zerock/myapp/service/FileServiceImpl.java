package org.zerock.myapp.service;

import java.util.List;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.myapp.entity.File;
import org.zerock.myapp.persistence.FileRepository;

import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@NoArgsConstructor

@Service
public class FileServiceImpl implements CommonService<File, Integer> {
	@Autowired FileRepository dao;
	
	@PostConstruct
    void postConstruct(){
        log.debug("FileServiceImpl -- postConstruct() invoked");
        log.debug("dao: {}", dao);
    }//postConstruct


	@Override
	public List<File> getAllList() {	//검색 없는 전체 리스트
		log.debug("FileServiceImpl -- getAllList() invoked");
		
		List<File> list = dao.findAll();
		
		return list;
	}

	@Override
	public List<File> getSearchList(File dto) {	//검색 있는 전체 리스트
		log.debug("FileServiceImpl -- getSearchList(()) invoked", dto);

		List<File> list = new Vector<File>();
		log.debug("리포지토리 미 생성");
		
		return list;
	}

	@Override
	public File getById(Integer id) {	// 단일 조회
		log.debug("FileServiceImpl -- getById({}) invoked", id);
		
		//값이 존재하면 반환하고, 없으면 new File()와 같은 기본값을 반환합니다.
		File data = dao.findById(id).orElse(new File());
		
		return data;
	}

	@Override
	public File create(File dto) {	//등록 처리
		log.debug("FileServiceImpl -- create({}) invoked", dto);
		
		File data = dao.save(dto);
		log.debug("create data: {}", data);
		
		return data;
	}

	@Override
	public Boolean update(File dto) {	//수정 처리
		log.debug("FileServiceImpl -- update({}) invoked", dto);
		
		Boolean isUpdate = true;
		return isUpdate;
	}

	@Override
	public Boolean deleteById(Integer id) { // 삭제 처리
		log.debug("FileServiceImpl -- deleteById({}) invoked", id);
		
		dao.deleteById(id);
		
		return true;
	}
	
}
