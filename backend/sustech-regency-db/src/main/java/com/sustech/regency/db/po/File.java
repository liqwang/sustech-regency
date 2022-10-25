package com.sustech.regency.db.po;

import com.fasterxml.jackson.annotation.JsonFormat;
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
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date uploadTime;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date deleteTime;
	private String suffix;
}


