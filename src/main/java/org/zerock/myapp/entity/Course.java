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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j

@Data
@NoArgsConstructor

@Entity(name = "Course")

public class Course implements Serializable{ // course + file 매핑
	@Serial private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "CourseGenerator")
	@SequenceGenerator(name="CourseGenerator", sequenceName = "seq_course", initialValue = 1, allocationSize = 1)
//	@Column(name = "course_crs_code")
	private String crsCode; // 과정번호

	
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
	@ManyToOne
	@JoinColumn(name = "fileId")
	private File crsImage; // 이미지 파일 시퀀스(과정)
	
	// 이거 맞는 모델링일까???????
	
	
	
//	//연관관계 편의 메소드 선언
//	public void setCrsImage(FileEntity file) {
//		log.info("setCrsImage({}) invoked.", file);
//		
//		//1. 현재 이미지를 얻는다.
//		FileEntity oldImg = this.getCrsImage();
//		
//		//2. 이미 img가 있으면..
//		if(oldImg != null) {	
//			//기존 파일 에서 img 제거
//			boolean isRemoved = oldImg.getFileId().remove(this); //이전 팀에서 현 회원 삭제
//			log.info("isRemoved from oldImg ? {}", isRemoved);
//
//			log.info("oldImg members: {}", oldImg.getMembers());			
//		}
//		
//		//3. 새로운 팀으로 설정
//		this.myTeam = newTeam;
//		
//		//4. 새로운 팀 멤버(회원)으로 현 회훤 추가
//		this.myTeam.getMembers().add(this);  	
//		log.info("newTeam members: {}", myTeam.getMembers());
//	}
	
	
	
} // end class
