package org.zerock.myapp.domain;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class FileDTO {
	// PK
	private Integer fileId; // sequence 
	
	private String fileOriginal; // 실제파일명.확장자
	private String fileUuid; // uuid(저장될 파일명)
	private String fileMenu; // 파일경로
	
	private Date crtDate; // 등록일
	private Date udtDate; // 수정일
} // end class
