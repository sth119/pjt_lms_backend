package org.zerock.myapp.domain;

import java.util.Date;
import java.util.List;

import org.zerock.myapp.entity.Course;
import org.zerock.myapp.entity.Upfile;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class TraineeDTO {
	private Long traineeId;				//아이디
	
	private String name;					//이름
	private String tel;						//전화번호
	private Integer status;					//상태(훈련중=1,중도탈락=2,중도포기=3,취업완료=4)
	private Boolean enabled;				//삭제여부(1=유효,0=삭제된데이터)
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private Date crtDate;
	private Date udtDate;
	
	// 받을 CourseId
	private Long courseId;
	
	// DTO로 담아서 프론트로 보낼 데이터
	private Course course;				//소속과정번호
	private List<Upfile> upfile;
	
	private String searchWord;    //이름 전화번호
	private String searchText;	//검색어
	

} // end class
