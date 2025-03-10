package org.zerock.myapp.domain;


import lombok.Getter;

@Getter
public enum MemberType {
	MANAGER(1, "관리자"),
	TEACHER(2, "강사"),
	STUDENT(3, "훈련생");
	
	// memberCode  도 이런 형식으로 간단하게 M~~ , T~~ , S~~ 이런식으로 만들수 있나?
	
	// Enum 이 가질 값 // MemberEntity 참조 하는거 X, 애플리케이션 내부에서 사용될 숫자 코드를 명시적으로 관리하기 위한 것.
	private final Integer code;
	private final String description;
	
	// Enum 생성자는 반드시 private 또는 package-private 이어야 함.
	private MemberType(Integer code, String description) {
		this.code = code;
		this.description = description;
	} // MemberType
	
	// 멤버타입을 코드로 비교 
	public static MemberType fromCode(Integer code) {
		for(MemberType type: MemberType.values()) {
			if(type.getCode().equals(code)) {
				return type;
			} // if
		} // for
		throw new IllegalArgumentException("잘못된 회원코드 : " +code );
	} // fromCode
	
	
	
} // end enum
