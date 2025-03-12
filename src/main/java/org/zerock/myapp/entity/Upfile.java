package org.zerock.myapp.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.generator.EventType;
import org.zerock.myapp.util.BooleanToIntegerConverter;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;


@Data

@Entity
@Table(name="T_UPFILES")
public class Upfile implements Serializable {
	@Serial private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID", unique=true, nullable=false)
	private Long fileId;				//

	@Column(nullable=false)
	private String original;			//원본파일명(확장자포함)

	@Column(nullable=false)
	private String uuid;				//저장파일명

	@Column(nullable=false)
	private String path;				//저장경로

	@Convert(converter = BooleanToIntegerConverter.class)
	@Column(nullable=false)
	private Boolean enabled = true;			//

	@CurrentTimestamp(event = EventType.INSERT, source = SourceType.DB)
	@Column(name="INSERT_TS", nullable=false)
	private Date crtDate;				//등록일

	@CurrentTimestamp(event = EventType.UPDATE, source = SourceType.DB)
	@Column(name="UPDATE_TS")
	private Date udtDate;				//수정일
	

<<<<<<< Updated upstream
	@JsonBackReference("course-upfile")
	@ToString.Exclude
=======
//	@JsonBackReference
//	@ToString.Exclude
>>>>>>> Stashed changes
	//bi-directional many-to-one association to Cours
	@ManyToOne
	@JoinColumn(name="CRS_ID")
	private Course course;

<<<<<<< Updated upstream
	@JsonBackReference("instructor-upfile")
	@ToString.Exclude
=======
//	@JsonBackReference
//	@ToString.Exclude
>>>>>>> Stashed changes
	//bi-directional many-to-one association to Instructor
	@ManyToOne
	@JoinColumn(name="INS_ID")
	private Instructor instructor;
<<<<<<< Updated upstream
	
	@JsonBackReference("trainee-upfile")
	@ToString.Exclude
=======

//	@JsonBackReference
//	@ToString.Exclude
>>>>>>> Stashed changes
	//bi-directional many-to-one association to Trainee
	@ManyToOne
	@JoinColumn(name="TRN_ID")
	private Trainee trainee;

}