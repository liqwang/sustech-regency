package com.sustech.regency.db.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginLog {
	private Integer userId;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date time;
	private String ipAddress;
	private Integer port;
}
