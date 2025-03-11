package org.zerock.myapp.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.generator.EventType;
import org.zerock.myapp.domain.MemberType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

@Entity(name = "User")

@Table(name = "t_users")

public class User implements Serializable{ 
	@Serial private static final long serialVersionUID = 1L;
	
	@Id 
	private String userId;		// 아이디
	
	@Column(nullable = false)
	private String userPassword;	// 비밀번호
	
	@Column(nullable = false)
	private String userName; 		// 이름

	//정보통신망법 관련 2가지
	
	@CurrentTimestamp(event = EventType.INSERT, source = SourceType.DB)
	@Column(nullable = false)
	private Date crtDate; // 등록일
	
	@UpdateTimestamp(source = SourceType.DB)
	private Date udtDate; // 수정일
	

	@Column(nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0")
	private Boolean del;	//삭제여부
	
} // end class
