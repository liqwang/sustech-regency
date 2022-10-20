package com.sustech.regency.db.po;

/**
 * 实现该接口的类表示数据库中的&lt;display&gt;-file关系表，将一个图片或视频关联到一个展示物<p>
 * 例如酒店展示、评论附带图片、房型展示...
 * @param <Display> 要展示的类，如Hotel、RoomType...
 */
@SuppressWarnings("SpellCheckingInspection")
public interface Exhibitable<Display> {

	void setMediaId(String mediaId);

	void setDisplayId(Integer displayId);
}
