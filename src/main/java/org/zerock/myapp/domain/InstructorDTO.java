package org.zerock.myapp.domain;

import java.util.Date;
import java.util.List;

import org.zerock.myapp.entity.Course;
import org.zerock.myapp.entity.Upfile;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
//입력시 course 필드를 무시하고, 출력 시에는 getter를 허용
@JsonIgnoreProperties(value = {"course"}, allowGetters = true)
public class InstructorDTO {
	private Long instructorId;			// 아이디

	private String name;				// 이름
	private String tel;					// 전화번호
	private Integer status;				//	상태(등록=1,강의중=2,퇴사=3)
	private Boolean enabled;			//	삭제여부(1=유효,0=삭제)

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private Date crtDate;
	private Date udtDate;
	
	// 받을 CourseId
	private Long courseId;

	// 돌려줄 객체 DTO
	private Course course;
	private List<Upfile> upfile;

	private String searchWord;
	private String searchText; // 검색

} // end class
