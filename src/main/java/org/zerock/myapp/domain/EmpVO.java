package org.zerock.myapp.domain;

import java.util.Date;

import lombok.extern.slf4j.Slf4j;

@Slf4j
// 자동으로 private final이 붙고, Setter는 없다
public record EmpVO(
		Integer empno, String ename, String job, Integer mgr,
		Date hiredate, Double sal, Double comm, Integer deptno
		) {
	
	public EmpVO {
		// 주의사항 : 필드 초기화 / 변경하면 안 된다
		// 필드는 모두 private final 이 되어버리기 때문에, 
		// 필드값 변경 자체가 문법상 허용되지 않음
		log.debug("Compact Constructor invoked.");
		log.info("\t+ empno: {}",empno);
		log.info("\t+ ename: {}",ename);
		log.info("\t+ job: {}",job);
		log.info("\t+ mgr: {}",mgr);
		log.info("\t+ hiredate: {}",hiredate);
		log.info("\t+ sal: {}",sal);
		log.info("\t+ comm: {}",comm);
		log.info("\t+ deptno: {}",deptno);
		
		
	} // Compact Constructor EmpVO
	
} // end record
