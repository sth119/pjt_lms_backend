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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
//JSON 으로 변환해서 보낼때, 제외 할 항목
@JsonIgnoreProperties({
//"crtDate",
"udtDate",
//"course", fix -> 강사 리스트에서 과정목록 보이도록 활성화.
//"upfiles" //fix16
})
@Entity
@Table(name="T_INSTRUCTORS")
public class Instructor implements Serializable {
	@Serial private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID", unique=true, nullable=false)
	private Long instructorId;			// 아이디

	@Column(nullable=false, length=500)
	private String name;				// 이름

	@Column(nullable=false, length=50)
	private String tel;					// 전화번호

	@Column(nullable=false) //상태(등록=1,강의중=2,퇴사=3)
	private Integer status = 1;				

	@Convert(converter = BooleanToIntegerConverter.class)
	@Column(nullable=false)
	private Boolean enabled = true;			//삭제여부(1=유효,0=삭제)

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	@CurrentTimestamp(event = EventType.INSERT, source = SourceType.DB)
	@Column(name="INSERT_TS", nullable=false)
	private Date crtDate;

	@CurrentTimestamp(event = EventType.UPDATE, source = SourceType.DB)
	@Column(name="UPDATE_TS")
	private Date udtDate;
	
	@JsonBackReference("course-instructor")
	//bi-directional one-to-one association to TCours
	@OneToOne
	@JoinColumn(name="CRS_ID", unique=true)
//	private Course course;		//담당과정번호(FK)
	private Course course;
	
	//bi-directional many-to-one association to TUpfile
	@JsonManagedReference("instructor-upfiles")
	@ToString.Exclude
	@OneToMany(mappedBy="instructor")
	private List<Upfile> upfiles = new Vector<>();


	public Upfile addUpfile(Upfile upfile) {
		// 1. 기존 부모와의 연관 관계 제거
        if (getUpfiles().contains(upfile)) {
            getUpfiles().remove(upfile); // 부모 컬렉션에서 제거
        } // if

        // 2. 자식 엔티티에 새로운 부모 참조 설정
        upfile.setInstructor(this);

        // 3. 부모 컬렉션에 자식 엔티티 추가
        getUpfiles().add(upfile);

		return upfile;
	} // addTUpfile

	public Upfile removeUpfile(Upfile upfile) {
		if (upfile != null) {
	        // 1. 자식 엔티티의 활성 상태를 비활성화
	        upfile.setEnabled(false);

	        // 2. 자식 엔티티에서 부모 참조 제거
	        upfile.setInstructor(null);

	        // 3. 부모 컬렉션에서 자식 엔티티 제거
	        getUpfiles().remove(upfile);
	    } // if
		return upfile;
	}



} // end class