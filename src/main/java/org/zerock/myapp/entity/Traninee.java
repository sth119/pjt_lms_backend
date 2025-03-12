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
@Table(name="T_TRANINEES")
public class Traninee implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID", unique=true, nullable=false, precision=38)
	private long tranineeId;				//아이디

	@Column(nullable=false, length=100)
	private String name;					//이름

	@Column(nullable=false, length=50)
	private String tel;						//전화번호

	@Column(nullable=false)
	private Integer status;					//상태(훈련중=1,중도탈락=2,중도포기=3,취업완료=4)
	
	
	@Column(nullable=false, precision=38)
	private BigDecimal enabled;				//삭제여부(1=유효,0=삭제된데이터)
	
	@Temporal(TemporalType.DATE)
	@Column(name="INSERT_TS", nullable=false)
	private Date crtDate;

	@Temporal(TemporalType.DATE)
	@Column(name="UPDATE_TS")
	private Date udtDate;

	//bi-directional many-to-one association to TCours
	@ManyToOne
	@JoinColumn(name="CRS_CODE")
	private Course course;				//소속과정번호(FK)



}//class