package org.zerock.myapp.entity;

import java.io.Serial;
import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

@Entity(name = "Traninee")

@Table(name = "t_traninees")
public class Traninee implements Serializable{ 
	@Serial private static final long serialVersionUID = 1L;
	
	
	
	//
	@Id
	private String tra_code;	// 회원ID FK
	
//	private String ; // 이름
	
	private String memberPhone;// 전화번호(하이픈 제외 11자리)
	
} // end class
