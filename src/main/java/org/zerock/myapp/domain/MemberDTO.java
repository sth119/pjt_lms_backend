package org.zerock.myapp.domain;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class MemberDTO {
	// PK
	private String member_code; // 회원번호
	
	private String member_id; // 아이디
	private String member_password; // 비밀번호
	private String member_name; // 이름
	private String member_phone; // 전화번호(하이픈 제외 11자리)
	
	private String member_type; // 회원구분(강사{instructor}, 관리자{manager}, 훈련생{student})
	
	// FK
	private String in_charge_crs_code; // 과정번호(담당{강사})
	private String request_crs_code; // 과정번호(신청{훈련생})
	private Integer student_image; // 이미지 파일 시퀀스(학생)
	
	private Date crt_date; // 등록일
	private Date udt_date; // 수정일
} // end class
