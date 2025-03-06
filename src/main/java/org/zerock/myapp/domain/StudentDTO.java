package org.zerock.myapp.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class StudentDTO {
	private String member_code;
	private String crs_code;
	private Integer student_image;
} // end class
