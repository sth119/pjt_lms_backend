package org.zerock.myapp.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.generator.EventType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j

@Data
@NoArgsConstructor

@Entity(name = "Course")

@Table(name = "t_courses")

public class Course implements Serializable{ 
	@Serial private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "CourseGenerator")
	@SequenceGenerator(name="CourseGenerator", sequenceName = "seq_course", initialValue = 1, allocationSize = 1)
	private Integer crsCode; // 과정번호
	
	
	@Column(nullable = false)	//not null 제약
	private String crsType; 	// 과정구분

	@Column(nullable = false)	//not null 제약
	private String crsName; 	// 과정이름

	@Column(nullable = false)	//not null 제약
	private Integer crsCapacity;// 수강정원
	
	@Column(nullable = false)	//not null 제약
	private String startDate; 	// 과정 시작일

	@Column(nullable = false)	//not null 제약
	private String endDate; 	// 과정 종료일
	
	@Column(nullable = true)	//not null 제약
	private String crsDetail; 	// 내용
	
	//정보통신망법 관련 2가지
	
	@CurrentTimestamp(event = EventType.INSERT, source = SourceType.DB)
	@Column(nullable = false)
	private Date crtDate; // 등록일
	
	@UpdateTimestamp(source = SourceType.DB)	//update 시 자동으로 DB에서 현재시시간 가져와 설정
	private Date udtDate; // 수정일
	
	@Column(nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0")
	private Boolean del;	//삭제여부
	
	
	@Transient	// DB 컬럼으로 매핑되지 않음
	private Integer currCount;	//현재 수강 인원

	
	
	
	
//	// FK
//	@OneToMany(targetEntity = Files.class)
//	//@JoinColumn(name = "fileId", referencedColumnName = "fileId")
//	@ToString.Exclude
	private List<Files> crsImage = new Vector<>(); // 이미지 파일 시퀀스(과정)
//	
//	@OneToMany(mappedBy = "requestCrsCode")
//	//@JoinColumn(name = "memberList", referencedColumnName = "memberList")
//	@ToString.Exclude
//	private List<Member> requestCrsCode = new Vector<>(); // 과정번호(담당{강사})
//	
//
//	public void addRequestCrsCode(Member newMember) { // 편의메소드
//		log.debug("addRequestCrsCode({}) invoked.",newMember);
//		// Step1. 이전 팀에서 소속 제거(FK 필드가 있어야 함)
//		// List는 중복 허용: 즉, 같은 회원이 2번 이상 추가될 수 있음.
//		this.requestCrsCode.remove(newMember);
//		// Step2. 새로운 팀으로 소속 시킴
//		this.requestCrsCode.add(newMember);
//		// Step3. 자식(팀원)의 FK필드에 현재 팀 설정(FK 필드가 있어야 함)
//		// 새로운 팀원(Member)가 생성되어, 팀에 소속될 때, 자기의 팀(FK필드)을
//		// 아래와 같이 설정해줘야, 이 팀원을 em에 persist(저장)할 때, 소속팀 설정을
//		// 다시 할 필요가 없다.
//		newMember.setRequestCrsCode(this);
//	} // addRequestCrsCode
//	
//	
//
//	
	
} // end class
