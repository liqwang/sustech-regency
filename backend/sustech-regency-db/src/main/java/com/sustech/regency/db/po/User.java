package com.sustech.regency.db.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
	@TableId(type=IdType.AUTO)
	private Integer id;
	private String name;
	private String password;
	private Date createTime;
	private String phone;
	private String email;
	private String idNumber;
}
