package org.zerock.myapp.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zerock.myapp.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {

	
}//end interface
