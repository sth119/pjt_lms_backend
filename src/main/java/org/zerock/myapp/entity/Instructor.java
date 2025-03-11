package org.zerock.myapp.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;


@Data

@Entity
@Table(name="T_INSTRUCTORS")
public class Instructor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="INS_CODE", unique=true, nullable=false, precision=38)
	private long insCode;

	@Temporal(TemporalType.DATE)
	@Column(name="CRT_DATE", nullable=false)
	private Date crtDate;

	@Column(nullable=false, precision=38)
	private BigDecimal enabled;

	@Column(name="INS_NAME", nullable=false, length=500)
	private String insName;

	@Temporal(TemporalType.DATE)
	@Column(name="UDT_DATE")
	private Date udtDate;

	//bi-directional many-to-one association to TCours
	@ManyToOne
	@JoinColumn(name="CRS_CODE")
	private Course course;


}