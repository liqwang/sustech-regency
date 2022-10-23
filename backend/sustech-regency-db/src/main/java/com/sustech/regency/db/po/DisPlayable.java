package com.sustech.regency.db.po;


/**
 * 展示物(具有封面的对象)，如酒店、房型...
 */
public interface DisPlayable {
	/**
	 * 获取封面对应图片的UUID
	 */
	String getCoverId();

	/**
	 * 设置封面对应图片的UUID
	 */
	void setCoverId(String coverId);
}
