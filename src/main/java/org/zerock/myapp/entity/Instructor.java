package org.zerock.myapp.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;


@Data

@Entity
@Table(name="T_INSTRUCTORS")
public class Instructor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID", unique=true, nullable=false)
	private Long instructorId;			//아이디

	@Column(nullable=false, length=500)
	private String name;				//이름

	@Column(nullable=false, length=50)
	private String tel;

	@Column(nullable=false)
	private Integer status;				//

	@Column(nullable=false)
	private Boolean enabled;			//삭제여부(1=유효,0=삭제)

	@Temporal(TemporalType.DATE)
	@Column(name="INSERT_TS", nullable=false)
	private Date crtDate;

	@Temporal(TemporalType.DATE)
	@Column(name="UPDATE_TS")
	private Date udtDate;

	//bi-directional many-to-one association to TCours
	@ManyToOne
	@JoinColumn(name="CRS_ID")
	private Course course;		//담당과정번호(FK)
	
	//bi-directional many-to-one association to TUpfile
	@OneToMany(mappedBy="Instructor", fetch=FetchType.EAGER)
	private List<Upfile> upfiles;


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