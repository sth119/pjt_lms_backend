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
	private String member_code;	//회원 고유번호
	
	@Column(nullable = true)
	private String member_id;	//로그인 id
	@Column(nullable = false)
	private String member_password;//pw
	@Column(nullable = false)
	private String member_name;
	@Column(nullable = true)
	private String member_phone;//휴대폰
	
	
	@Column(nullable = true)
	private String member_type;	//회원타입
	
	//강사
	@Column(nullable = false)
	private String in_charge_crs_code;	//담당과정

	//훈련생
	@Column(nullable = false)
	private String request_crs_code;	//신청과정
	@Column(nullable = false)
	private Integer student_image;		//학생사진
	
	
	@CurrentTimestamp(event = EventType.INSERT, source = SourceType.DB)
	@Column(nullable = false)
	private Date crt_date;
	@UpdateTimestamp(source = SourceType.DB)
	private Date udt_date;
} // end class
