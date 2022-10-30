package com.sustech.regency.db.po;

import com.sustech.regency.db.annotation.DateTimeField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserWithRole {
	private Integer userId;
	private Integer roleId;
	@DateTimeField
	private Date createTime;
}
