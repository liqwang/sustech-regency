package com.sustech.regency.db.po;

import lombok.Data;

import java.util.Date;

@Data
public class LoginLog {
	private Integer userId;
	private Date time;
}
