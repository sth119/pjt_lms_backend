package org.zerock.myapp.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;


@Data

@Entity
@Table(name="T_UPFILES")
public class Upfile implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID", unique=true, nullable=false, precision=38)
	private long fileId;				//

	@Column(nullable=false, length=1000)
	private String original;			//원본파일명(확장자포함)

	@Column(nullable=false, length=1000)
	private String uuid;				//저장파일명

	@Column(nullable=false, length=4000)
	private String path;				//저장경로

	@Column(name="CRS_ID", precision=38)
	private long crsId;					//소속과정아이디(FK)

	@Column(name="INS_ID", precision=38)
	private long insId;					//소속훈련생아이디(FK)

	@Column(name="TRN_ID", precision=38)
	private long trnId;					//소속강사아이디(FK)

	@Column(nullable=false)
	private Boolean enabled;			//

	@Temporal(TemporalType.DATE)
	@Column(name="INSERT_TS", nullable=false)
	private Date crtDate;				//

	@Temporal(TemporalType.DATE)
	@Column(name="UPDATE_TS")
	private Date udtDate;				//

}