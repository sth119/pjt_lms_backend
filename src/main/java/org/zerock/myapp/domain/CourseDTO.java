package org.zerock.myapp.domain;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CourseDTO {
	// PK
	private String crs_code; // 과정번호
	
	private String crs_type; // 과정구분
	private String crs_name; // 과정이름
	private Integer crs_capacity; // 수강정원
	private String start_date; // 시작일
	private String end_date; // 종료일
	
	// FK
	private Integer crs_image; // 이미지 파일 시퀀스(과정)
	
	private Date crt_date; // 등록일
	private Date udt_date; // 수정일
} // end class
