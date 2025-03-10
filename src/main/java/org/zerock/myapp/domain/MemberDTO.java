package org.zerock.myapp.domain;

import java.util.Date;
import java.util.List;

import org.zerock.myapp.entity.Course;
import org.zerock.myapp.entity.File;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class MemberDTO {
	// PK
	private Integer memberCode; // 회원번호
	
	private String memberId; // 아이디
	private String memberPassword; // 비밀번호
	private String memberName; // 이름
	private String memberPhone; // 전화번호(하이픈 제외 11자리)
	
	private Integer memberType; // 회원구분(강사{instructor}, 관리자{manager}, 훈련생{student})
	
	// FK
	private Course requestCrsCode; // 과정코드(강사 = 담당과정 , 훈련생 = 신청과정)
	
	private List<File> studentImage; // 이미지 파일 시퀀스(학생)
	
	private Date crtDate; // 등록일
	private Date udtDate; // 수정일
	
	private String searchWord;
	private String searchText;
} // end class
