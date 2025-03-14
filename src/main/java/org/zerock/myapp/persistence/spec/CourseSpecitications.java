package org.zerock.myapp.persistence.spec;

import org.springframework.data.jpa.domain.Specification;
import org.zerock.myapp.entity.Course;

import lombok.extern.slf4j.Slf4j;


@Slf4j

/** 강사님 설명
 * =======================================
 * org.springframework.data.jpa.domain.Specification:
 * 		Predicate toPredicate(Root<T> root, @Nullable CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder);
 * =======================================
 * Specification<Course> spec = (root, query, builder) -> {
 * 			// root: 검색해야할 Course 엔티티 객체
 * 			// root.get("엔티티속성명");		// 엔티티객체(root)에서, 지정된 속성값을 얻어냄
 * 	
 * 			// 조건식의 구성 : <기준컬럼: root.get(name)> <비교연산자: builder.비교메소드> <비교값: 매개변수값>
 * 			return builder.equal(root.get("enabled"), enabled);
 * 		};
 */

//검색 DTO  항목에 따른 검색 조건 설정
public class CourseSpecitications {

	public static Specification<Course> isEnabled(Boolean enabled) {
		log.debug(">>> isEnabled({}) invoked.", enabled);
		return (root, query, builder) -> builder.equal(root.get("enabled"), enabled);
	}
	
	public static Specification<Course> hasStatus(Integer status) {
		log.debug(">>> hasStatus({}) invoked.", status);
		return (root, query, builder) -> builder.equal(root.get("status"), status);
	}
	
	public static Specification<Course> hasType(Integer type) {
		log.debug(">>> hasType({}) invoked.", type);
		return (root, query, builder) -> builder.equal(root.get("type"), type);
	}
	
	public static Specification<Course> hasName(String name) {
		log.debug(">>> hasName({}) invoked.", name);
		return (root, query, builder) -> builder.equal(root.get("name"), name);
	}
	
	
	//instructorName는 불가, 관련 entity에 있는 항목만 가능
	
}
