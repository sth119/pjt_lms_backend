package org.zerock.myapp.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.generator.EventType;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;



@Data

@Entity
@Table(name="T_TRAINEES")
public class Trainee implements Serializable {
	@Serial private static final long serialVersionUID = 1L;

	// 1. Set PK
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID", unique=true, nullable=false, precision=38)
	private Long traineeId;				//아이디

	// 2-1. General Properties
	@Column(nullable=false)
	private String name;					//이름

	@Column(nullable=false)
	private String tel;						//전화번호

	@Column(nullable=false)
	private Integer status = 1;					//상태(훈련중=1,중도탈락=2,중도포기=3,취업완료=4)
	
	@Convert(converter = BooleanToIntegerConverter.class)
	@Column(nullable=false)
	private Boolean enabled = true;				//삭제여부(1=유효,0=삭제된데이터)
	
	
	// 2-2. Iegal Auditing Properties
	@CurrentTimestamp(event = EventType.INSERT, source = SourceType.DB)
	@Column(name="INSERT_TS", nullable=false)
	private Date crtDate;						//등록일

	@CurrentTimestamp(event = EventType.UPDATE, source = SourceType.DB)
	@Column(name="UPDATE_TS")
	private Date udtDate;						//수정일
	

	// 3. Bi-directional Many-To-One Association
	@ManyToOne
	@JoinColumn(name="CRS_ID")
	private Course course;				//소속과정번호(FK)
	
	//4. Bi-directional One-To-Many Association
	@ToString.Exclude
	@OneToMany(mappedBy="trainee")
	private List<Upfile> upfiles = new Vector<>();


	public Upfile addTUpfile(Upfile upfiles) {
		getUpfiles().add(upfiles);
		upfiles.setTrainee(this);

		return upfiles;
	}

	public Upfile removeUpfile(Upfile upfiles) {
		getUpfiles().remove(upfiles);
		upfiles.setTrainee(null);

		return upfiles;
	}


}//class