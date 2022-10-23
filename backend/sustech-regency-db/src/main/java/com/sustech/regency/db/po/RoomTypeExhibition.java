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
public class RoomTypeExhibition implements Exhibitable<RoomType> {
	private String fileId;
	private Integer roomTypeId;

	@Override
	public void setMediaId(String mediaId) {
		fileId=mediaId;
	}

	@Override
	public String getMediaId() {
		return fileId;
	}

	@Override
	public void setDisplayId(Integer displayId) {
		roomTypeId=displayId;
	}

	@Override
	public Integer getDisplayId() {
		return roomTypeId;
	}
}
