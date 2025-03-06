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
import jakarta.persistence.SequenceGenerator;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

@Entity
public class CourseEntity implements Serializable{ // course + file 매핑
	@Serial private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "course_crs_code")
	private String crs_code;
	
	@Column(nullable = true)
	private String crs_type;
	@Column(nullable = true)
	private String crs_name;
	@Column(nullable = true)
	private Integer crs_capacity;
	@Column(nullable = true)
	private String start_date;
	@Column(nullable = true)
	private String end_date;
	@Column(nullable = false)
	private Integer crs_image;
	
	@CurrentTimestamp(event = EventType.INSERT, source = SourceType.DB)
	@Column(nullable = false)
	private Date crt_date;
	@UpdateTimestamp(source = SourceType.DB)
	private Date udt_date;
} // end class
