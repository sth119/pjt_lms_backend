package org.zerock.myapp.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.zerock.myapp.domain.EmpDTO;
import org.zerock.myapp.domain.EmpVO;
import org.zerock.myapp.exception.DAOException;

//주의: 당연히 스프링부트 안에서 마이바티스의 Mapper Interface 선언할 때에도 스프링 MVC의 이름 규칙에 따라,
//당연히 인터페이스 이름 뒤에 "DAO"를 붙여야 하지만 기존의 구현을 삭제하지 않기 위해, 마이바티스를 단독으로 사용할 때의 
//이름 규약을 사용합니다.(즉, 인터페이스이름 + Mapper)

@Mapper
public interface EmpMapper {
	//사원 테이블에 대한 CRUD + List 5개의 기능을 수행하는 추상 메소드 선언
	
	//=================================
	// 1st. section: with annotation
	//=================================

	// 리턴타입인 EmpVO의 모든 필드와 매핑되는 각 컬럼의 값을 얻어내려고 마이바티스는 시도하기 때문에, 
	// 아래 3개 컬럼까지는 EmpVO객체에 잘 들어갔지만, 나머지 필드의 값은 ResultSet 에서 얻을 수가 없기때문에 오류발생
	// -> 이렇게 되길 원하면, Mapper XML에 등록된 태그에 ResultSet 을 우리가 원하는 VO객체로 어떻게 
	// 매핑시킬지를 직접 지정해서 사용하면 됩니다. (즉, 마이바티스의 ResultMap 속성)
//	@Select(" SELECT empno, ename, job FROM EMP ORDER BY EMPNO DESC ")	//XX
	@Select(" SELECT * FROM EMP ORDER BY EMPNO DESC ")	//ok
	public abstract List<EmpVO> selectAllEmps() throws DAOException;	//List

	
	// 아래와 같이 마이바티스는 바인드 변수명을 #{바인드변수명} 으로 지정하게 되어있습니다.
	// 바인드 변수가 1개일 때에는, 이름과 상관없이 우리가 매개변수로 준 값을 무조건 바인드변수에 바인딩(값을 넣어줌)하게 되어 있지만, 
	// 아래와 같이 바인드 변수가 2개 이상일 때에는, 개발자는 
	// (1) 바인드 변수를 키로 가지는 Map객체 또는 
	// (2) 바인드 변수명과 동일한 프로퍼티를 가지는 "자바빈"객체로 넣어주시면, 
	// Map에서는 바인드변수명으로 값을 얻어내고, "자바빈"이라면, get+바인드변수명()으로, 
	// Getter 메소드 호출해서 값을 얻어내어 같은 이름의 바인드변수에 값을 넣어주게 되어 있습니다.
	@Select(" SELECT * FROM EMP WHERE EMPNO = #{empNo} AND ENAME= #{eName} ")
//	public abstract EmpVO selectEmp(Integer empNo, String eName) throws DAOException;	//R => 가능하지만 추천하지 않음
	public abstract EmpVO selectEmp(EmpDTO dto) throws DAOException;	//R

	
	//=================================
	// 2nd. section: with Mapper XML
	//	==> porperties 파일 : mybatis.mapper-locations=classpath:mapper/**/*.xml
	//=================================
	
	//1. namespace="org.zerock.myapp.persistence.EmpMapper"
	//		이 해당 mapper interface의 fqcn이 자동으로 namesapce으로 
	//2. id="deleteEmp"
	//		이 해당 mapper interface의 추상 메소드 명이 
	//3. 실제삭제 행 수가 1이상 => true, 0 => false 
	public abstract Boolean deleteEmp(EmpDTO dto) throws DAOException;	//D
	
	public abstract Boolean insertEmp(EmpDTO dto) throws DAOException;	//C
	public abstract Boolean updateEmp(EmpDTO dto) throws DAOException;	//U	
	
	//**
	public abstract Boolean deleteWhereEmp(EmpDTO dto) throws DAOException;	//D
}
