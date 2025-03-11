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
@Table(name="T_USERS")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="USER_ID", unique=true, nullable=false, length=500)
	private String userId;

	@Temporal(TemporalType.DATE)
	@Column(name="CRT_DATE", nullable=false)
	private Date crtDate;

	@Column(nullable=false, precision=38)
	private BigDecimal enabled;

	@Temporal(TemporalType.DATE)
	@Column(name="UDT_DATE")
	private Date udtDate;

	@Column(name="USER_NAME", nullable=false, length=500)
	private String userName;

	@Column(name="USER_PASSWORD", nullable=false, length=500)
	private String userPassword;
	
} // end class
