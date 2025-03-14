package org.zerock.myapp.persistence.spec;

import org.springframework.data.jpa.domain.Specification;
import org.zerock.myapp.entity.Instructor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InstructorSpecifications {

	public static Specification<Instructor> isEnabled(Boolean enabled) {
		log.debug(">>>> isEnabled({}) invoked. <<<< ", enabled);
		
		return (root, query, builder) -> builder.equal(root.get("enabled"), enabled);
	} // isEnabled
	
	
	public static Specification<Instructor> hasName(String name) {
		log.debug(">>>> hasName({}) invoked. <<<< ", name);
		
		return (root, query, builder) -> builder.like(root.get("name"), '%' + name + '%');
	} // hasName
	
	
	public static Specification<Instructor> hasStatus(Integer status) {
		log.debug(">>>> hasStatus({}) invoked. <<<< ", status);
		
		return (root, query, builder) -> builder.equal(root.get("status"), status);
	} // hasStatus
	
	
} // end class
