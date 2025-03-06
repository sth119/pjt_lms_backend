package org.zerock.myapp.domain;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class MemberDTO {
	private String member_code;
	private String member_type;
	private String member_id;
	private String member_password;
	private String member_name;
	private String member_phone;
	
	private Date crt_date;
	private Date udt_date;
} // end class
