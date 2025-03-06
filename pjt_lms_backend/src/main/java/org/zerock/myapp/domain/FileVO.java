package org.zerock.myapp.domain;

import java.util.Date;

import lombok.Value;

@Value
public class FileVO {
	private final Integer file_id;
	private final String file_original;
	private final String file_uuid;
	private final String file_menu;
	
	private final Date crt_date;
	private final Date udt_date;
} // end class
