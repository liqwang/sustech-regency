package com.sustech.regency.db.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 房间种类的图片和视频
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RoomTypeExhibition {
	private String fileId;
	private Integer roomTypeId;
}
