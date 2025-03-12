package org.zerock.myapp.domain;

import java.util.Date;

import org.zerock.myapp.entity.Course;

import lombok.Data;

@Data
public class TraineeDTO {
	private Long tranineeId;				//아이디

	private String name;					//이름
	private String tel;						//전화번호
	private Integer status;					//상태(훈련중=1,중도탈락=2,중도포기=3,취업완료=4)
	private Boolean enabled;				//삭제여부(1=유효,0=삭제된데이터)
	
	private Date crtDate;
	private Date udtDate;
	
	private Course course;				//소속과정번호(FK)
} // end class
