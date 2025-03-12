package org.zerock.myapp.service;

import java.util.List;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.myapp.domain.FileDTO;
import org.zerock.myapp.entity.Upfile;
import org.zerock.myapp.persistence.FileRepository;

import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@NoArgsConstructor

@Service
public class FileServiceImpl implements FileService {
	@Autowired FileRepository dao;
	
	@PostConstruct
    void postConstruct(){
        log.debug("FileServiceImpl -- postConstruct() invoked");
        log.debug("dao: {}", dao);
    }//postConstruct


	@Override
	public List<Upfile> getList(String id) {	
		log.debug("FileServiceImpl -- getAllList() invoked");
		
		List<Upfile> list = new Vector<>(); //dao.findAll();
		
		return list;
	}

	@Override
	public Upfile create(FileDTO dto) {	//등록 처리
		log.debug("FileServiceImpl -- create({}) invoked", dto);
		
		Upfile data = new Upfile();//dao.save(dto);
		log.debug("create data: {}", data);
		
		return data;
	}

	@Override
	public Upfile getById(Integer id) {	// 단일 조회
		log.debug("FileServiceImpl -- getById({}) invoked", id);
		
		//값이 존재하면 반환하고, 없으면 new File()와 같은 기본값을 반환합니다.
		Upfile data = dao.findById(id).orElse(new Upfile());
		
		return data;
	}

	@Override
	public Boolean update(FileDTO dto) {	//수정 처리
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
