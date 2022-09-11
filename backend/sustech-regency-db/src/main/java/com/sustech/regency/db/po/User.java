package com.sustech.regency.db.po;

import lombok.Data;

import java.util.Date;

@Data
public class User {
	private Integer id;
	private String name;
	private String password;
	private Date date;
	private String phone;
	private String email;
	private String idNumber;
}
