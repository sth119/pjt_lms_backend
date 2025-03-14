package org.zerock.myapp.persistence.spec;

import lombok.Data;


@Data

//과정 리스트 검색 DTO
public class CourseSearchCriteria {

	private Boolean enabled = true;	//활성화상태(1=유효,0=삭제)
	
	private Integer status;			//진행여부(연기=3,예정=2,진행중=1,종료=0)
	private Integer type;			//과정구분(1=NCS, 2=KDT, 3=산대특, 4=미정)
	private String name;			//과정명
	
	private String instructorName;	//강사명
}
