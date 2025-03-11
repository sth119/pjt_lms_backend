package org.zerock.myapp.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
import lombok.Data;


@Data

@Entity
@Table(name="T_COURSES")
public class Course implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CRS_CODE", unique=true, nullable=false, precision=38)
	private long crsCode;

	@Column(name="CRS_CAPACITY", precision=38)
	private BigDecimal crsCapacity;

	@Column(name="CRS_DETAIL", length=500)
	private String crsDetail;

	@Column(name="CRS_NAME", length=500)
	private String crsName;

	@Column(name="CRS_TYPE", length=500)
	private String crsType;

	@Temporal(TemporalType.DATE)
	@Column(name="CRT_DATE", nullable=false)
	private Date crtDate;

	@Column(nullable=false, precision=38)
	private BigDecimal enabled;

	@Column(name="END_DATE", length=500)
	private String endDate;

	@Column(name="START_DATE", length=500)
	private String startDate;

	@Column(nullable=false, precision=38)
	private BigDecimal status;

	@Temporal(TemporalType.DATE)
	@Column(name="UDT_DATE")
	private Date udtDate;

	//bi-directional many-to-one association to TInstructor
	@OneToMany(mappedBy="TCours", fetch=FetchType.EAGER)
	private List<Instructor> instructors;

	//bi-directional many-to-one association to TTraninee
	@OneToMany(mappedBy="TCours", fetch=FetchType.EAGER)
	private List<Traninee> traninees;

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

	public Traninee addTraninee(Traninee traninee) {
		getTraninees().add(traninee);
		traninee.setCourse(this);

		return traninee;
	}

	public Traninee removeTTraninee(Traninee traninee) {
		getTraninees().remove(traninee);
		traninee.setCourse(null);

		return traninee;
	}
} // end class
