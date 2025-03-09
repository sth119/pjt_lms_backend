package org.zerock.myapp.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.generator.EventType;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

@Entity(name = "File")


//final pjt 고려하여, N:1 매핑
//file + course 매핑
//file + member 매핑


public class File implements Serializable{
	@Serial private static final long serialVersionUID = 1L; 
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "FileGenerator")
	@SequenceGenerator(name="FileGenerator", sequenceName = "seq_file", initialValue = 1, allocationSize = 1)
//	@Column(name = "file_file_id")
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
	
	
	
	
} // end class
