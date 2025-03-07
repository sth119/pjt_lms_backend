package org.zerock.myapp.domain;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class FileDTO {
	// PK
	private Integer file_id; // sequence 
	
	private String file_original; // 실제파일명.확장자
	private String file_uuid; // uuid(저장될 파일명)
	private String file_menu; // 파일경로
	
	private Date crt_date; // 등록일
	private Date udt_date; // 수정일
} // end class
