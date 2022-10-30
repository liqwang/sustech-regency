package com.sustech.regency.db.po;

import com.sustech.regency.db.annotation.DateTimeField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class File {
	private String id;
	@DateTimeField
	private Date uploadTime;
	@DateTimeField
	private Date deleteTime;
	private String suffix;
}
