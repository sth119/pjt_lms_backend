package org.zerock.myapp.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.generator.EventType;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.myapp.util.BooleanToIntegerConverter;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.ToString;


@Data

// JSON 으로 변환해서 보낼때, 제외 할 항목
@JsonIgnoreProperties({
//	"crtDate",
	"udtDate",
	
	"instructor",
	"traninees",
	"upfiles"
})

@Entity
@Table(name="T_COURSES")
public class Course implements Serializable {
	@Serial private static final long serialVersionUID = 1L;
	
	//1. pk
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique=true, nullable=false)
	private Long courseId;							//과정번호

	
	// 2-1. General Properties
	@Column(nullable = false)
	private Integer type = 4;						//과정구분(1=NCS, 2=KDT, 3=산대특, 4=미정)

	@Column(nullable = false)
	private String name;						//과정명

	@Column(nullable = false)
	private Integer capacity = 0;				//수강정원
	
	@Column(nullable = true)
	private String detail;						//내용

	@Column(name="START_DATE")
	private String startDate;					//시작일: '2024-10-18'

	@Column(name="END_DATE")
	private String endDate;						//종료일: '2025-04-17'

	@Column(nullable=false)
	private Integer status = 2;						//진행여부(연기=3,예정=2,진행중=1,종료=0)

	@Convert(converter = BooleanToIntegerConverter.class)
	@Column(nullable=false)
	private Boolean enabled = true;					//활성화상태(1=유효,0=삭제)
	
	
	// 2-2. Iegal Auditing Properties
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	@CurrentTimestamp(event = EventType.INSERT, source = SourceType.DB)
	@Column(name="INSERT_TS", nullable=false)
	private Date crtDate;						//등록일

	@CurrentTimestamp(event = EventType.UPDATE, source = SourceType.DB)
	@Column(name="UPDATE_TS")
	private Date udtDate;						//수정일
	
	
	@Transient	// DB 컬럼으로 매핑되지 않음
	private Integer currCount = 0;	//현재 수강 인원

	@JsonManagedReference("course-instructor")	// fix
	@ToString.Exclude
	//3. Bi-directional One-To-One Association
	@OneToOne(mappedBy="course")
	private Instructor instructor;				//강사

	//4. Bi-directional Many-To-One Association
	@JsonManagedReference("course-trainees") // fix
	@ToString.Exclude
	@OneToMany(mappedBy="course")
	private List<Trainee> traninees = new Vector<>();					//훈련생
	
	@JsonManagedReference("course-upfiles") // fix
	@ToString.Exclude
	@OneToMany(mappedBy="course")
	private List<Upfile> upfiles = new Vector<>(); // 생성해두지 않으면 null이라 오류 발생


	
	public Trainee addTraninee(Trainee traninee) { // 중복된 훈련생이 있으면 체크가 필요하다
		getTraninees().add(traninee);
		traninee.setCourse(this);

		return traninee;
	} // addTraninee

	public Trainee removeTraninee(Trainee traninee) {
		getTraninees().remove(traninee);
		traninee.setCourse(null);

		return traninee;
	} // removeTraninee

	public Upfile addUpfile(Upfile upfile) {
		 // 1. 기존 부모와의 연관 관계 제거
        if (getUpfiles().contains(upfile)) {
            getUpfiles().remove(upfile); // 부모 컬렉션에서 제거
        } // if

        // 2. 자식 엔티티에 새로운 부모 참조 설정
        upfile.setCourse(this);

        // 3. 부모 컬렉션에 자식 엔티티 추가
        getUpfiles().add(upfile);

		return upfile;
	} // addUpfile

	public Upfile removeUpfile(Upfile upfile) {
		if (upfile != null) {
	        // 1. 자식 엔티티의 활성 상태를 비활성화
	        upfile.setEnabled(false);

	        // 2. 자식 엔티티에서 부모 참조 제거
	        upfile.setCourse(null);

	        // 3. 부모 컬렉션에서 자식 엔티티 제거
	        getUpfiles().remove(upfile);
	    } // if
		return upfile;
	} // removeUpfile

	
} // end class
