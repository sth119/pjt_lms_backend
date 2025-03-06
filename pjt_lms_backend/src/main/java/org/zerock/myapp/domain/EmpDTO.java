package org.zerock.myapp.domain;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

// 관계 데이터 모델로 최종 테이블이 만들어지면,
// 각 테이블마다, 아래와 같이 DTO, VO를 만들어야한다
// 각 테이블 데이터의 CRUD+LIST 기능 구현을 위함이다.

@NoArgsConstructor
@Data
public class EmpDTO { // 수정 가능한 성질을 가진다.(Mutable Object)
	private Integer empno;
	private String ename;
	private String job;
	private Integer mgr;
	private Date hiredate;
	private Double sal;
	private Double comm;
	private Integer deptno;

} // end class
