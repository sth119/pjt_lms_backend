package org.zerock.myapp.domain;

import java.util.Date;

import lombok.Data;

@Data
public class UserDTO {
	
	
	private String userId;
	private String passwd;
	private String name;
	private Boolean enabled;

	
	private Date crtDate;
	private Date udtDate;
} // end class
