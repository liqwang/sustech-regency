package com.sustech.regency.db.po;

import com.sustech.regency.db.annotation.DateTimeField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginLog {
	private Integer userId;
	@DateTimeField
	private Date time;
	private String ipAddress;
	private Integer port;
}
