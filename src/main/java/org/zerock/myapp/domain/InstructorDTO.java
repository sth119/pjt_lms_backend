package org.zerock.myapp.domain;

import java.util.Date;

import org.zerock.myapp.entity.Course;

import lombok.Data;

@Data
public class InstructorDTO {
	private Long instructorId;			// 아이디

	private String name;				// 이름
	private String tel;					// 전화번호
	private Integer status;				//	상태(등록=1,강의중=2,퇴사=3)
	private Boolean enabled;			//	삭제여부(1=유효,0=삭제)

	private Date crtDate;
	private Date udtDate;
	
	private Course course;		//담당과정번호(FK)
} // end class
