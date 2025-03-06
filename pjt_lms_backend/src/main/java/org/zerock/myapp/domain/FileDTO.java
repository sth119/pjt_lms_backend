package org.zerock.myapp.domain;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class FileDTO {
	private Integer file_id;
	private String file_original;
	private String file_uuid;
	private String file_menu;
	
	private Date crt_date;
	private Date udt_date;
} // end class
