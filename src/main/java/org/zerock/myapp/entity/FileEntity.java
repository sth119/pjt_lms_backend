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
public class FileEntity implements Serializable{// file + course 매핑, file + student 매핑
	@Serial private static final long serialVersionUID = 1L; 
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "file_file_id")
	private Integer file_id; // sequence 
	
	@Column(nullable = false)
	private String file_original; // 실제파일명.확장자
	@Column(nullable = false)
	private String file_uuid; // uuid(저장될 파일명)
	@Column(nullable = false)
	private String file_menu; // 파일경로
	
	@CurrentTimestamp(event = EventType.INSERT, source = SourceType.DB)
	@Column(nullable = false)
	private Date crt_date; // 등록일
	@UpdateTimestamp(source = SourceType.DB)
	private Date udt_date; // 수정일
} // end class
