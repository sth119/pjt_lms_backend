package org.zerock.myapp.persistence.spec;

import lombok.Data;

@Data
public class InstructorCriteria {

	private Boolean enabled = true;
	
	private String name;
	private Integer status;
	
	
}
