package org.zerock.myapp.domain;

import java.io.Serial;
import java.io.Serializable;

import lombok.Data;

@Data
public class CriteriaDTO implements Serializable{
	@Serial private static final long serialVersionUID = 1L;

	private Integer page;
	private Integer pageSize;
	
	private String condition; // selected 검색 항목 번호
	private String q; // selected 검색 데이터
	
	//구분명 과정1 강사2 훈련생3
	//private Integer type;
	
	//강사
	
	//훈련생
					
} // end class
