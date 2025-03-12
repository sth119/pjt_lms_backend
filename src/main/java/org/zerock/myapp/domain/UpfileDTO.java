package org.zerock.myapp.domain;

import java.util.Date;

import org.zerock.myapp.entity.Course;
import org.zerock.myapp.entity.Instructor;
import org.zerock.myapp.entity.Trainee;

import lombok.Data;

@Data
public class UpfileDTO {
	private Long fileId;				// 아이디

	private String original;			//원본파일명(확장자포함)
	private String uuid;				//저장파일명
	private String path;				//저장경로
	private Boolean enabled;			// 삭제여부(1=유효,0=삭제된데이터)
	
	private Date crtDate;				
	private Date udtDate;				
	
	// FK
	private Course course;
	private Instructor instructor;
	private Trainee trainee;
} // end class
