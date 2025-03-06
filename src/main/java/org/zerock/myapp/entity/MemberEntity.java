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
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

@Entity
//(instructor, manager, student) ISA 매핑
public class MemberEntity implements Serializable{ 
	@Serial private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_member_code")
	private String member_code;
	
	@Column(nullable = true)
	private String member_type;
	@Column(nullable = true)
	private String member_id;
	@Column(nullable = false)
	private String member_password;
	@Column(nullable = false)
	private String member_name;
	@Column(nullable = true)
	private String member_phone;
	
	@CurrentTimestamp(event = EventType.INSERT, source = SourceType.DB)
	@Column(nullable = false)
	private Date crt_date;
	@UpdateTimestamp(source = SourceType.DB)
	private Date udt_date;
} // end class
