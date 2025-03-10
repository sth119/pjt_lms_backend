package org.zerock.myapp.domain;

import java.util.Date;

import lombok.NoArgsConstructor;
import lombok.Value;

@Value
public class CourseVO {
	// PK
	private final String crsCode; // 과정번호
	
	private final String crsType; // 과정구분
	private final String crsName; // 과정이름
	private final Integer crsCapacity; // 수강정원
	private final String startDate; // 시작일
	private final String endDate; // 종료일
	private final String crsDetail; // 내용
	
	// FK
	private final Integer crsImage; // 이미지 파일 시퀀스(과정)
	
	private final Date crtDate; // 등록일
	private final Date udtDate; // 수정일
} // end class
