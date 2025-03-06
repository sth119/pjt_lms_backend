package org.zerock.myapp.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.myapp.entity.Board;


// 예시

//Automatically implemented by Spring Boot with auto-proxy techniques.
//               -------- <Repository> --------
//               |                                    |
//      <CrudRepository>            <PagingAndSortingRepository>
//               |                                    |
//      <ListCrudRepository>      <ListPagingAndSortingRepository>
//               |                                    |
//               ------  <JpaRepository>  -----
//
//(1) Repository                        - Spring Data JDBC
//(2) CrudRepository                     - Spring Data JDBC
//(3) PagingAndSortingRepository            - Spring Data JDBC
//(4) ListCrudRepository                  - Spring Data JDBC
//(5) ListPagingAndSortingRepository         - Spring Data JDBC
//(6) JpaRepository                     - Spring Data *JPA


public interface BoardRepository extends JpaRepository<Board, Long> {
	;;
} // end interface
