package org.zerock.myapp.domain;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CourseDTO {
	private String crs_code;
	private String crs_type;
	private String crs_name;
	private Integer crs_capacity;
	private String start_date;
	private String end_date;
	private Integer crs_image;
	
	private Date crt_date;
	private Date udt_date;
} // end class
