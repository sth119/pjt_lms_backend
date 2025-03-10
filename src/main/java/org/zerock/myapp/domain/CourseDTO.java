package org.zerock.myapp.domain;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CourseDTO {
	// PK
	private Integer crsCode; // 과정번호
	
	private String crsType; // 과정구분, search
	private String crsName; // 과정이름
	private Integer crsCapacity; // 수강정원
	private String startDate; // 시작일
	private String endDate; // 종료일
	private String crsDetail; // 내용
	
	// FK
	private Integer crsImage; // 이미지 파일 시퀀스(과정)
	
	private Date crtDate; // 등록일
	private Date udtDate; // 수정일
	
	private String searchWord;
	private String searchText;
	private String runType;	//진행구분, search
} // end class
