package com.sustech.regency.db.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements DisPlayable{
	@TableId(type=IdType.AUTO)
	private Integer id;
	private String name;
	private String password;
	private String phone;
	private String email;
	private String idNumber;
	private String headshotId; //用户的头像就是封面

	@Override
	public String getCoverId() {
		return headshotId;
	}

	@Override
	public void setCoverId(String coverId) {
		headshotId=coverId;
	}
}
