package org.zerock.myapp.entity;

import java.io.Serial;
import java.io.Serializable;

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
// instructor + member ISA 매핑, instructor + course 매핑
// PK가 없는 경우엔 어떻게 해야 하는가 잘 모르겠음
public class InstructorEntity implements Serializable{ 
	@Serial private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "instructor_member_code")
	private String member_code;
	
	@Column(nullable = false)
	private String crs_code;
} // end class
