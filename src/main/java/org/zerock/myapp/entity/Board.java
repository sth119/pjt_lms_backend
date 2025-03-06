package org.zerock.myapp.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.generator.EventType;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

// 예시

@Data

@Entity(name = "Board")
@Table(name = "tbl_board") // Mapped to the 'tbl_board' table.
public class Board implements Serializable{// Entity Class
	@Serial private static final long serialVersionUID = 1L;
	
	// 1. Set PK.
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) // Identity 자동생성방식
	@Column(name = "bno") // 실제 bno 컬럼으로 매핑
	private Long seq;
	
	// 2. General Properties.
	@Basic(optional = false) // Not Null Constraint
	private String title;
	@Column(nullable = false, length = 1000)
	private String content;
	@Basic(optional = false) // Not Null Constraint
	private String writer;
	 
	// Auditing Properties; // 감사(Audit)용, 컬럼들(정보통신망법에서 요구하는 필수항목)
	@CurrentTimestamp(event = EventType.INSERT, source = SourceType.DB)
	@Basic(optional = false)
	private Date insertTs;
	@UpdateTimestamp(source = SourceType.DB)
	private Date updateTs;
	
	// 3. Association Mapping
	
	
} // end class
