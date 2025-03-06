package org.zerock.myapp.domain;

import java.util.Date;

import lombok.Value;

@Value
public class MemberVO {
	private final String member_code;
	private final String member_type;
	private final String member_id;
	private final String member_password;
	private final String member_name;
	private final String member_phone;
	
	private final Date crt_date;
	private final Date udt_date;
} // end class
