package org.zerock.myapp.domain;

import lombok.Value;

@Value
public class StudentVO {
	private final String member_code;
	private final String crs_code;
	private final Integer student_image;
} // end class
