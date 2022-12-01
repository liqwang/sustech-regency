package com.sustech.regency.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ReverseNotifyInfo {
	private Long orderId;
	private String hotelName;
	private Integer roomNum;
	private String roomTypeName;
	private Date startDate;
	private Date endDate;
	private Integer money;
	private String merchantEmail;
	private String payerName;
}
