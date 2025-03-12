package org.zerock.myapp.entity;

import java.io.Serial;
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
	@Serial private static final long serialVersionUID = 1L;

	@Id
	@Column(name="USER_ID", unique=true, nullable=false, length=500)
	private String userId;

	@Column(nullable=false, length=500)
	private String passwd;

	@Column(nullable=false, length=500)
	private String name;

	@Column(nullable=false)
	private Boolean enabled;

	@Temporal(TemporalType.DATE)
	@Column(name="INSERT_TS", nullable=false)
	private Date crtDate;

	@Temporal(TemporalType.DATE)
	@Column(name="UPDATE_TS")
	private Date udtDate;
	
} // end class
