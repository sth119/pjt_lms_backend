package org.zerock.myapp.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.myapp.entity.Member;


public interface MemberRepository extends JpaRepository<Member, Integer> {
	Optional<Member> findByMemberId(String memberId);
	boolean existsByMemberId(String memberId);
} // end class

// MemberEntity 대신 Optional<MemberEntity> 를 사용한 이유?
// > 안전성 측면. 꼭 사용할 필요는 없으나 후에 발생하는 오류를 줄이기 위해서 쓰는것
//만약 DB에 memberId 가 존재하지 않으면 null 반환할 수 있다. → nullpointerException 이 발생할 가능성이 높아짐.
//Optional<Entity> 를 사용하면 데이터가 없을 경우에도 안전하게 처리 가능.