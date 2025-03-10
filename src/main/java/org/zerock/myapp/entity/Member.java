package org.zerock.myapp.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.generator.EventType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

@Entity(name = "Member")
//(instructor, manager, student) ISA 매핑
public class Member implements Serializable{ 
	@Serial private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name = "member_member_code")
	private Integer memberCode;	// 회원번호
	
	@Column(nullable = false)
	private String memberId;	// 아이디
	@Column(nullable = false)
	private String memberPassword;// 비밀번호
	@Column(nullable = false)
	private String memberName; // 이름
//	@Column(nullable = true)
	private String memberPhone;// 전화번호(하이픈 제외 11자리)
	
	
	@Column(nullable = false)
	private String memberType;	// 회원구분(강사{instructor}, 관리자{manager}, 훈련생{student})
	
	// FK
	@ManyToOne(optional = true, targetEntity = Course.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "inChargeCrsCode", referencedColumnName = "crsCode", insertable = false,
			updatable = false)
	private Course inChargeCrsCode;	// 과정번호(담당{강사})
	
	@ManyToOne(optional = true, targetEntity = Course.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "requestCrsCode", referencedColumnName = "crsCode", insertable = false,
			updatable = false)
	private Course requestCrsCode;	// 과정번호(신청{훈련생})
	
	@OneToMany
	//@JoinColumn(name = "fileId", referencedColumnName = "fileId", insertable = false, updatable = false)
	@JoinColumn(name = "memberId", insertable = false, updatable = false)
	private List<File> studentImage;		// 이미지 파일 시퀀스(학생)
	
	
	@CurrentTimestamp(event = EventType.INSERT, source = SourceType.DB)
	@Column(nullable = false)
	private Date crtDate; // 등록일
	@UpdateTimestamp(source = SourceType.DB)
	private Date udtDate; // 수정일
	
} // end class
