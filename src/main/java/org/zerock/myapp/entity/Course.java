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
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j

@Data
@NoArgsConstructor

@Entity(name = "Course")

public class Course implements Serializable{ // course + file 매핑
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
	
	
	// FK
	@OneToMany(targetEntity = File.class)
	//@JoinColumn(name = "fileId", referencedColumnName = "fileId")
	@ToString.Exclude
	private List<File> crsImage = new Vector<>(); // 이미지 파일 시퀀스(과정)
	
	@OneToMany(mappedBy = "inChargeCrsCode")
	//@JoinColumn(name = "inChargeCrsCode", referencedColumnName = "inChargeCrsCode")
	@ToString.Exclude
	private List<Member> inChargeCrsCode = new Vector<>(); // 과정번호(담당{강사})
	
	@OneToMany(mappedBy = "requestCrsCode")
	//@JoinColumn(name = "requestCrsCode", referencedColumnName = "requestCrsCode")
	@ToString.Exclude
	private List<Member> requestCrsCode = new Vector<>(); // 과정번호(신청{훈련생})
	
	
	public void addRequestCrsCode(Member newMember) { // 편의메소드
		log.debug("addRequestCrsCode({}) invoked.",newMember);
		// Step1. 이전 팀에서 소속 제거(FK 필드가 있어야 함)
		// List는 중복 허용: 즉, 같은 회원이 2번 이상 추가될 수 있음.
		this.requestCrsCode.remove(newMember);
		// Step2. 새로운 팀으로 소속 시킴
		this.requestCrsCode.add(newMember);
		// Step3. 자식(팀원)의 FK필드에 현재 팀 설정(FK 필드가 있어야 함)
		// 새로운 팀원(Member)가 생성되어, 팀에 소속될 때, 자기의 팀(FK필드)을
		// 아래와 같이 설정해줘야, 이 팀원을 em에 persist(저장)할 때, 소속팀 설정을
		// 다시 할 필요가 없다.
		newMember.setRequestCrsCode(this);
	} // addRequestCrsCode
	
	public void addInChargeCrsCode(Member newMember) { // 편의메소드
		log.debug("addMember({}) invoked.",newMember);
		this.inChargeCrsCode.remove(newMember);
		this.inChargeCrsCode.add(newMember);
		newMember.setInChargeCrsCode(this);
	} // addInChargeCrsCode

} // end class
