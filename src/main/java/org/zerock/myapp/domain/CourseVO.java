package org.zerock.myapp.domain;

import java.util.Date;

import lombok.NoArgsConstructor;
import lombok.Value;

@Value
public class CourseVO {
	// PK
	private final String crs_code; // 과정번호
	
	private final String crs_type; // 과정구분
	private final String crs_name; // 과정이름
	private final Integer crs_capacity; // 수강정원
	private final String start_date; // 시작일
	private final String end_date; // 종료일
	
	// FK
	private final Integer crs_image; // 이미지 파일 시퀀스(과정)
	
	private final Date crt_date; // 등록일
	private final Date udt_date; // 수정일
} // end class
