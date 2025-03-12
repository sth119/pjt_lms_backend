package org.zerock.myapp.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.generator.EventType;
import org.zerock.myapp.util.BooleanToIntegerConverter;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;


@Data

@Entity
@Table(name="T_INSTRUCTORS")
public class Instructor implements Serializable {
	@Serial private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID", unique=true, nullable=false)
	private Long instructorId;			//아이디

	@Column(nullable=false, length=500)
	private String name;				//이름

	@Column(nullable=false, length=50)
	private String tel;

	@Column(nullable=false)
	private Integer status;				//

	@Convert(converter = BooleanToIntegerConverter.class)
	@Column(nullable=false)
	private Boolean enabled;			//삭제여부(1=유효,0=삭제)

	@CurrentTimestamp(event = EventType.INSERT, source = SourceType.DB)
	@Column(name="INSERT_TS", nullable=false)
	private Date crtDate;

	@CurrentTimestamp(event = EventType.UPDATE, source = SourceType.DB)
	@Column(name="UPDATE_TS")
	private Date udtDate;
	
	//bi-directional one-to-one association to TCours
	@OneToOne
	@JoinColumn(name="CRS_ID")
	private Course course;		//담당과정번호(FK)
	
	//bi-directional many-to-one association to TUpfile
	@JsonManagedReference
	@ToString.Exclude
	@OneToMany(mappedBy="instructor")
	private List<Upfile> upfiles = new Vector<>();


	public Upfile addTUpfile(Upfile upfile) {
		getUpfiles().add(upfile);
		upfile.setInstructor(this);

		return upfile;
	}

	public Upfile removeTUpfile(Upfile upfile) {
		getUpfiles().remove(upfile);
		upfile.setInstructor(null);

		return upfile;
	}
}