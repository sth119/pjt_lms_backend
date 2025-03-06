package org.zerock.myapp.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.zerock.myapp.domain.EmpDTO;
import org.zerock.myapp.domain.EmpVO;
import org.zerock.myapp.exception.DAOException;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
@ToString

// 2개의 기능을 수행하는 어노테이션
// (1) 이 클래스가 DAO 역할을 한다
// (2) Spring Context 에 빈으로 등록
@Repository("empDAO")
public class EmpDAOImpl implements EmpDAO, BeanNameAware {
	// 첫번쨰 방법 : 
	//@Inject
	//@Resource(name = "주입받을빈의이름",type = 주입받을빈의타입정보)
	//@Autowired
	@Setter(onMethod_ = @Autowired(required = true)) 
	private javax.sql.DataSource dataSource;
	
	@Autowired private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<EmpVO> selectAllEmps() throws DAOException {
		log.debug("selectAllEmps() invoked.");
		
		try {
			/**
			 1st. method: Using JDBC API
			final String sql = "SELECT * FROM emp ORDER BY empno DESC";
			
			Connection conn = this.dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			
			try(conn; pstmt; rs;){
				if(rs.getFetchSize() > 0) {
					List<EmpVO> list = new Vector<>();
					
					while(rs.next()) {
						final Integer empno = rs.getInt("empno");
						final String ename = rs.getString("ename");
						final String job = rs.getString("job");
						final Integer mgr = rs.getInt("mgr");
						final Date hiredate = rs.getDate("hiredate");
						final Double sal = rs.getDouble("sal");
						final Double comm = rs.getDouble("comm");
						final Integer deptno = rs.getInt("deptno");
						
						EmpVO vo = 
								new EmpVO(empno, ename, job, mgr, hiredate, sal, comm, deptno);
						
						list.add(vo);
					} // while
					
					log.info("\t+ list: {}",list);
					
					return list;
				} // if 
			} // try-with-resources  
			*/
			
			// 2nd. method: Using Spring JDBC, jdbcTemplate
			
			final String sql = "SELECT * FROM emp ORDER BY empno DESC";
			List<EmpVO> list = this.jdbcTemplate.<EmpVO>query(sql, (rs,i)->{
				log.debug("mapRow({},{}) invoked.",rs,i);
				
				final Integer empno = rs.getInt("empno");
				final String ename = rs.getString("ename");
				final String job = rs.getString("job");
				final Integer mgr = rs.getInt("mgr");
				final Date hiredate = rs.getDate("hiredate");
				final Double sal = rs.getDouble("sal");
				final Double comm = rs.getDouble("comm");
				final Integer deptno = rs.getInt("deptno");
				
				EmpVO vo = 
						new EmpVO(empno, ename, job, mgr, hiredate, sal, comm, deptno);
				
				return vo;
			}); // .query
			
			return list;
		} catch(Exception _original) {
			throw new DAOException(_original);
		} // try-catch
	} // selectAllEmps

	@Override
	public Boolean insertEmp(EmpDTO dto) throws DAOException {
		log.debug("insertEmp({}) invoked.",dto);
		
		try {
			/**
			final String sql = "INSERT INTO emp (empno, ename, job, mgr, hiredate, sal, comm, deptno) VALUES (?,?,?,?,?,?,?,?)";
			
			Connection conn = this.dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			try(conn; pstmt;){
			pstmt.setInt(1, dto.getEmpno());
			pstmt.setString(2, dto.getEname());
			pstmt.setString(3, dto.getJob());
			pstmt.setInt(4, dto.getMgr());
			
			// java.util.Date -> java.sql.Date 로 형변환이 필요하다
			Date hiredate = Objects.requireNonNullElse(dto.getHiredate(), new Date());
			if(hiredate != null) pstmt.setDate(5, new java.sql.Date(dto.getHiredate().getTime()));
			else pstmt.setNull(5, Types.DATE); // Set NULL data as each type.
			
			pstmt.setDouble(6, dto.getSal());
			pstmt.setDouble(7, dto.getComm());
			pstmt.setInt(8, dto.getDeptno());
			
			int affectedRows = pstmt.executeUpdate();
			
			return affectedRows == 1;
			} // try-with-resources 
			*/
		
		// 2nd. method: Using Spring JDBC's JdbcTemplate Helper
			
			
			final String sql = "INSERT INTO emp VALUES (?,?,?,?,?,?,?,?)";
			
			
			int affectedRows = this.jdbcTemplate.update(sql,
					dto.getEmpno(),
					dto.getEname(),
					dto.getJob(),
					dto.getMgr(),
					
					new java.sql.Date(dto.getHiredate().getTime()),
					//new java.sql.Date((Date) null),
					//dto.getHiredate().setNull(5, Types.DATE),
					//null,
					
					dto.getSal(),
					dto.getComm(),
					dto.getDeptno());
					
					return affectedRows == 1;	
		} catch (Exception _original) {
			throw new DAOException(_original);
		} // try-catch
	} // insertEmp
	
	@Override
	public EmpVO selectEMP(EmpDTO dto) throws DAOException, SQLException{
		log.debug("selectEMP({}) invoked.",dto);
		
		//final String sql = "SELECT * FROM emp WHERE empno = ?";
		
		//Connection conn = this.dataSource.getConnection();
		//PreparedStatement pstmt = conn.prepareStatement(sql);
		
		//boolean autoCommit = conn.getAutoCommit();
		//log.debug("\t+ Is autoCommit enabled: {}", autoCommit);
		
		//pstmt.setInt(1, dto.getEmpno());
		
		//ResultSet rs = pstmt.executeQuery();

		/**
		try(conn; pstmt; rs;){				
			while(rs.next()) {
				final Integer empno = rs.getInt("empno");
				final String ename = rs.getString("ename");
				final String job = rs.getString("job");
				final Integer mgr = rs.getInt("mgr");
				final Date hiredate = rs.getDate("hiredate");
				final Double sal = rs.getDouble("sal");
				final Double comm = rs.getDouble("comm");
				final Integer deptno = rs.getInt("deptno");
				
				EmpVO vo = 
						new EmpVO(empno, ename, job, mgr, hiredate, sal, comm, deptno);
				return vo;
			} // while
		} // try-with-resources  
		*/
		
		// 2nd. method: Using Spring JDBC's jdbcTemplate JDBC Helper
		
		final String sql = "SELECT * FROM emp WHERE empno = %s".formatted(dto.getEmpno());
		
		List<EmpVO> list = this.jdbcTemplate.<EmpVO>query(sql, (rs,i)->{
			log.debug("query({},{}) invoked.",rs,i);
			
			final Integer empno = rs.getInt("empno");
			final String ename = rs.getString("ename");
			final String job = rs.getString("job");
			final Integer mgr = rs.getInt("mgr");
			final Date hiredate = rs.getDate("hiredate");
			final Double sal = rs.getDouble("sal");
			final Double comm = rs.getDouble("comm");
			final Integer deptno = rs.getInt("deptno");
			
			EmpVO vo = 
					new EmpVO(empno, ename, job, mgr, hiredate, sal, comm, deptno);
			
			return vo;
		}); // 
		log.info("\t+ vo: {}",list);
		
	return list.getFirst();
	} // selectEMP

	@Override
	public Boolean updateEmp(EmpDTO dto) throws DAOException {
		log.debug("updateEmp({}) invoked.",dto);
		
		try {
			
			/**
			final String sql = 
					"UPDATE emp SET ename = ?, job = ?, mgr = ?, hiredate = ?, sal = ?, comm = ?, deptno = ? WHERE empno = ?";
			
			Connection conn = this.dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			try(conn; pstmt;){
			
			pstmt.setString(1, dto.getEname());
			pstmt.setString(2, dto.getJob());
			pstmt.setInt(3, dto.getMgr());
			
			// java.util.Date -> java.sql.Date 로 형변환이 필요하다
			Date hiredate = Objects.requireNonNullElse(dto.getHiredate(), null);
			if(hiredate != null) pstmt.setDate(4, new java.sql.Date(dto.getHiredate().getTime()));
			else pstmt.setNull(4, Types.DATE);
			
			pstmt.setDouble(5, dto.getSal());
			pstmt.setDouble(6, dto.getComm());
			pstmt.setInt(7, dto.getDeptno());
			
			pstmt.setInt(8, dto.getEmpno());
			
			int affectedRows = pstmt.executeUpdate();
			
			return affectedRows == 1;
			} // try-with-resources 
			*/
			final String sql = 
					"UPDATE emp SET ename = ?, job = ?, mgr = ?, hiredate = ?, sal = ?, comm = ?, deptno = ? WHERE empno = ?";
			int affectedRows = this.jdbcTemplate.update(sql,
					dto.getEname(),
					dto.getJob(),
					dto.getMgr(),
					
					new java.sql.Date(dto.getHiredate().getTime()),
					//new java.sql.Date((Date) null),
					//dto.getHiredate().setNull(5, Types.DATE),
					//null,
					
					dto.getSal(),
					dto.getComm(),
					dto.getDeptno(),
					dto.getEmpno()
					);
					
					return affectedRows == 1;
		} catch (Exception _original) {
			throw new DAOException(_original);
		} // try-catch
	} // updateEmp

	@Override
	public Boolean deleteEmp(EmpDTO dto) throws DAOException {
		log.debug("deleteEmp({}) invoked.",dto);
		
		try {
			/**
			final String sql = "DELETE FROM emp WHERE empno = ?";
			
			Connection conn = this.dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			try(conn; pstmt;){
			pstmt.setInt(1, dto.getEmpno());
			
			int affectedRows = pstmt.executeUpdate();
			
			return affectedRows == 1;
			} // try-with-resources 
			*/
			final String sql = "DELETE FROM emp WHERE empno = ?";
			int affectedRows = this.jdbcTemplate.update(sql,
					dto.getEmpno()
					);
					
					return affectedRows == 1;

		} catch (Exception _original) {
			throw new DAOException(_original);
		} // try-catch
	} // deleteEmp

	@Override
	public void setBeanName(String name) {
		log.debug("setBeanName({}) invoked.",name);
		
	} // setBeanName
	
	
} // end class
