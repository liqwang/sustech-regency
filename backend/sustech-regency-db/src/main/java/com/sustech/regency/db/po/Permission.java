package com.sustech.regency.db.po;

import lombok.Data;

@Data
public class Permission {
	private Integer id;
	private Integer parentId;
	private String name;
}
