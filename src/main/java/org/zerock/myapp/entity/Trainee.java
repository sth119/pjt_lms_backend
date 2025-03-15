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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;



@Data

//JSON 으로 변환해서 보낼때, 제외 할 항목
@JsonIgnoreProperties({
//"crtDate",
"udtDate",

//"course",
"upfiles"
})

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
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	@CurrentTimestamp(event = EventType.INSERT, source = SourceType.DB)
	@Column(name="INSERT_TS", nullable=false)
	private Date crtDate;						//등록일

	@CurrentTimestamp(event = EventType.UPDATE, source = SourceType.DB)
	@Column(name="UPDATE_TS")
	private Date udtDate;						//수정일
	
	@JsonBackReference("course-trainees")
	// 3. Bi-directional Many-To-One Association
	@ManyToOne
	@JoinColumn(name="CRS_ID")
	private Course course;				//소속과정번호(FK)
	
	
	//4. Bi-directional One-To-Many Association
	@JsonManagedReference("trainee-upfiles")
	@ToString.Exclude
	@OneToMany(mappedBy="trainee")
	private List<Upfile> upfiles = new Vector<>();


	public Upfile addUpfile(Upfile upfile) {
		// 1. 기존 부모와의 연관 관계 제거
        if (getUpfiles().contains(upfile)) {
            getUpfiles().remove(upfile); // 부모 컬렉션에서 제거
        } // if

        // 2. 자식 엔티티에 새로운 부모 참조 설정
        upfile.setTrainee(this);

        // 3. 부모 컬렉션에 자식 엔티티 추가
        getUpfiles().add(upfile);

		return upfile;
	} // addTUpfile

	public Upfile removeUpfile(Upfile upfile) {
		if (upfile != null) {
	        // 1. 자식 엔티티의 활성 상태를 비활성화
	        upfile.setEnabled(false);
	     // 2. 자식 엔티티에서 부모 참조 제거
	        upfile.setTrainee(null);
	        // 3. 부모 컬렉션에서 자식 엔티티 제거
	        getUpfiles().remove(upfile);
	    } // if
		return upfile;
	} // removeUpfile

}//class