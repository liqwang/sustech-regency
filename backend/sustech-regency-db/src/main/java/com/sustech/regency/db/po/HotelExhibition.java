package com.sustech.regency.db.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 酒店的图片或视频
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class HotelExhibition {
	private Integer hotelId;
	private String fileId;
}
