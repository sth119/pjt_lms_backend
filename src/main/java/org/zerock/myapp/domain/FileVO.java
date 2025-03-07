package org.zerock.myapp.domain;

import java.util.Date;

import lombok.Value;

@Value
public class FileVO {
	// PK
	private final Integer file_id; // sequence 
	
	private final String file_original; // 실제파일명.확장자
	private final String file_uuid; // uuid(저장될 파일명)
	private final String file_menu; // 파일경로
	
	private final Date crt_date; // 등록일
	private final Date udt_date; // 수정일
} // end class
