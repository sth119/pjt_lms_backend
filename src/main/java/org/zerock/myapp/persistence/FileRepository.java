package org.zerock.myapp.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zerock.myapp.entity.Upfile;

@Repository
public interface FileRepository extends JpaRepository<Upfile, Integer> {
	;;
}//end interface
