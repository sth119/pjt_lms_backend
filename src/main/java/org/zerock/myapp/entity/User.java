package org.zerock.myapp.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.generator.EventType;
import org.zerock.myapp.util.BooleanToIntegerConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
	@Column(name="ID", unique=true, nullable=false)
	private String userId;

	@Column(nullable=false)
	private String passwd;

	@Column(nullable=false)
	private String name;

	@Convert(converter = BooleanToIntegerConverter.class)
	@Column(nullable=false)
	private Boolean enabled;

	@CurrentTimestamp(event = EventType.INSERT, source = SourceType.DB)
	@Column(name="INSERT_TS", nullable=false)
	private Date crtDate;

	@CurrentTimestamp(event = EventType.UPDATE, source = SourceType.DB)
	@Column(name="UPDATE_TS")
	private Date udtDate;
	
} // end class
