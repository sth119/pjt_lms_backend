package org.zerock.myapp.domain;

import java.util.Date;

import lombok.NoArgsConstructor;
import lombok.Value;

@Value
public class CourseVO {
	private final String crs_code;
	private final String crs_type;
	private final String crs_name;
	private final Integer crs_capacity;
	private final String start_date;
	private final String end_date;
	private final Integer crs_image;
	
	private final Date crt_date;
	private final Date udt_date;
} // end class
