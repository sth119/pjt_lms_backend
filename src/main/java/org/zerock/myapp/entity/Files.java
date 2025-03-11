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
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

@Entity(name = "UpFile")
@Table(name = "t_upfiles")
public class Files implements Serializable{
	@Serial private static final long serialVersionUID = 1L; 
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "FileGenerator")
	@SequenceGenerator(name="FileGenerator", sequenceName = "seq_file", initialValue = 1, allocationSize = 1)
	private Integer fileId; // sequence 
	
	
	@Column(nullable = false)	//not null 제약
	private String fileOriginal; // 실제파일명.확장자

	@Column(nullable = false)	//not null 제약
	private String fileUuid; // uuid(저장될 파일명)

	@Column(nullable = false)	//not null 제약
	private String fileMenu; // 파일경로 => course, member
	
	
	//정보통신망법 관련 2가지
	@CurrentTimestamp(event = EventType.INSERT, source = SourceType.DB)
	@Column(nullable = false)
	private Date crt_date; // 등록일
	@UpdateTimestamp(source = SourceType.DB)
	private Date udt_date; // 수정일
	
//	// FK
//	 @ManyToOne // File 엔티티에 Member를 참조하는 외래 키 설정
//	 @JoinColumn(name = "memberId", referencedColumnName = "memberCode") // Member의 기본 키 참조
//	 private User member;
	
	
	
} // end class
