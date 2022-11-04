package com.sustech.regency.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfo {
	private String token;
	private Integer id;
	private String name;
	private String email;
	private String headshotUrl;
	private List<String> roles;
}
