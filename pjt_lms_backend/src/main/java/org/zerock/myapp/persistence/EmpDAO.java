package org.zerock.myapp.persistence;

import java.sql.SQLException;
import java.util.List;

import org.zerock.myapp.domain.EmpDTO;
import org.zerock.myapp.domain.EmpVO;
import org.zerock.myapp.exception.DAOException;

// DAO는 기능이다
public interface EmpDAO {
	// 사원테이블에 대한 CRUD + LIST 5개의 기능을 수행하는 추상메소드 선언
	public abstract List<EmpVO> selectAllEmps() throws DAOException; // LIST
	
	public abstract Boolean insertEmp(EmpDTO dto) throws DAOException; // CREATE
	public abstract EmpVO selectEMP(EmpDTO dto) throws DAOException, SQLException; // READ
	public abstract Boolean updateEmp(EmpDTO dto) throws DAOException; //UPDATE
	public abstract Boolean deleteEmp(EmpDTO dto) throws DAOException; // DELETE
	
} // end interface