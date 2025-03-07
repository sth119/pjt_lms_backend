package org.zerock.myapp.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.generator.EventType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

@Entity
//(instructor, manager, student) ISA 매핑
public class MemberEntity implements Serializable{ 
	@Serial private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_member_code")
	private String member_code;	// 회원번호
	
	@Column(nullable = true)
	private String member_id;	// 아이디
	@Column(nullable = false)
	private String member_password;// 비밀번호
	@Column(nullable = false)
	private String member_name; // 이름
	@Column(nullable = true)
	private String member_phone;// 전화번호(하이픈 제외 11자리)
	
	
	@Column(nullable = true)
	private String member_type;	// 회원구분(강사{instructor}, 관리자{manager}, 훈련생{student})
	
	// FK
	@Column(nullable = false)
	private String in_charge_crs_code;	// 과정번호(담당{강사})
	@Column(nullable = false)
	private String request_crs_code;	// 과정번호(신청{훈련생})
	@Column(nullable = false)
	private Integer student_image;		// 이미지 파일 시퀀스(학생)
	
	
	@CurrentTimestamp(event = EventType.INSERT, source = SourceType.DB)
	@Column(nullable = false)
	private Date crt_date; // 등록일
	@UpdateTimestamp(source = SourceType.DB)
	private Date udt_date; // 수정일
} // end class
