package org.zerock.myapp.domain;

import java.util.Date;

import lombok.Value;

@Value
public class MemberVO {
	// PK
	private final String member_code; // 회원번호
	
	private final String member_id; // 아이디
	private final String member_password; // 비밀번호
	private final String member_name; // 이름
	private final String member_phone; // 전화번호(하이픈 제외 11자리)
	
	private final String member_type; // 회원구분(강사{instructor}, 관리자{manager}, 훈련생{student})
	
	// FK
	private final String in_charge_crs_code; // 과정번호(담당{강사})
	private final String request_crs_code; // 과정번호(신청{훈련생})
	private final Integer student_image; // 이미지 파일 시퀀스(학생)
	
	private final Date crt_date; // 등록일
	private final Date udt_date; // 수정일
} // end class
