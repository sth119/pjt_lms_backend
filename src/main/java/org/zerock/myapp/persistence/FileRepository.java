package org.zerock.myapp.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zerock.myapp.entity.Files;

@Repository
public interface FileRepository extends JpaRepository<Files, Integer> {
	;;
}//end interface
