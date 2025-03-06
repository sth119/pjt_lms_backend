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
	private Integer file_id;
	
	@Column(nullable = false)
	private String file_original;
	@Column(nullable = false)
	private String file_uuid;
	@Column(nullable = false)
	private String file_menu;
	
	@CurrentTimestamp(event = EventType.INSERT, source = SourceType.DB)
	@Column(nullable = false)
	private Date crt_date;
	@UpdateTimestamp(source = SourceType.DB)
	private Date udt_date;
} // end class
