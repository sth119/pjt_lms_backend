package org.zerock.myapp.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import lombok.Data;


@Data

@Entity
@Table(name="T_COURSES")
public class Course implements Serializable {
	@Serial private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "CRS_ID", unique=true, nullable=false, precision=38)
	private Long id;							//과정번호

	@Column(length=500)
	private String type;						//과정구분

	@Column(length=500)
	private String name;						//과정명

	@Column(precision=3)
	private Integer capacity;					//수강정원

	@Column(name="DETAIL", length=4000)
	private String detail;						//내용

	@Column(name="START_DATE", length=500)
	private String startDate;					//시작일: '2024-10-18'

	@Column(name="END_DATE", length=500)
	private String endDate;						//종료일: '2025-04-17'

	@Column(nullable=false)
	private Integer status;						//진행여부(연기=3,예정=2,진행중=1,종료=0)

	@Column(nullable=false)
	private Boolean enabled;					//활성화상태(1=유효,0=삭제)
	
	@Temporal(TemporalType.DATE)
	@Column(name="INSERT_TS", nullable=false)
	private Date crtDate;						//등록일

	@Temporal(TemporalType.DATE)
	@Column(name="UPDATE_TS")
	private Date udtDate;						//수정일
	
	
	@Transient	// DB 컬럼으로 매핑되지 않음
	private Integer currCount;	//현재 수강 인원


	
	//bi-directional many-to-one association to Instructor
	@OneToMany(mappedBy="course", fetch=FetchType.EAGER)
	private List<Instructor> instructors;				//강사

	//bi-directional many-to-one association to Traninee
	@OneToMany(mappedBy="course", fetch=FetchType.EAGER)
	private List<Trainee> traninees;					//훈련생

	//bi-directional many-to-one association to Upfile
	@OneToMany(mappedBy="course", fetch=FetchType.EAGER)
	private List<Upfile> upfiles;

	
	
	public Instructor addInstructor(Instructor instructor) {
		this.instructors.add(instructor);
		instructor.setCourse(this);

		return instructor;
	}

	public Instructor removeInstructor(Instructor instructor) {
		this.instructors.remove(instructor);
		instructor.setCourse(null);

		return instructor;
	}

	public Trainee addTraninee(Trainee traninee) {
		getTraninees().add(traninee);
		traninee.setCourse(this);

		return traninee;
	}

	public Trainee removeTraninee(Trainee traninee) {
		getTraninees().remove(traninee);
		traninee.setCourse(null);

		return traninee;
	}

	public Upfile addUpfile(Upfile upfile) {
		getUpfiles().add(upfile);
		upfile.setCourse(this);

		return upfile;
	}

	public Upfile removeTUpfile(Upfile upfile) {
		getUpfiles().remove(upfile);
		upfile.setCourse(null);

		return upfile;
	}
	
	
	
	
} // end class
