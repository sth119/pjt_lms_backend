package org.zerock.myapp.domain;

import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;
import org.zerock.myapp.entity.Instructor;
import org.zerock.myapp.entity.Trainee;
import org.zerock.myapp.entity.Upfile;

import lombok.Data;

@Data
public class CourseDTO {
	private Long courseId;							//과정번호

	private Integer type;						//과정구분(1=NCS, 2=KDT, 3=산대특, 4=미정)
	private String name;						//과정명
	private Integer capacity;					//수강정원
	private String detail;						//내용
	private String startDate;					//시작일: '2024-10-18'
	private String endDate;						//종료일: '2025-04-17'
	private Integer status;						//진행여부(연기=3,예정=2,진행중=1,종료=0)
	private Boolean enabled;					//활성화상태(1=유효,0=삭제)
	
	private Date crtDate;						//등록일
	private Date udtDate;						//수정일
	
	private MultipartFile upfiles; // 받을 파일 객체
	
	private List<Upfile> upfile;
	private Instructor instructor;
	private List<Trainee> trainees;
	
	private Integer currCount;	//현재 수강 인원

	private String searchWord;
	private String searchText;
} // end class
