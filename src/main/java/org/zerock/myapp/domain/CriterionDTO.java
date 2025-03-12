package org.zerock.myapp.domain;

import lombok.Data;

@Data
public class CriterionDTO {
	private Long id;							//과정번호

	private String type;						//과정구분
	private String name;						//과정명
	private Integer capacity;					//수강정원
} // end class
