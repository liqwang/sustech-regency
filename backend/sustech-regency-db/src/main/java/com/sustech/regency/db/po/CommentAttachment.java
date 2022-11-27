package com.sustech.regency.db.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 评论附带的图片或视频
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentAttachment implements Exhibitable<Order>{
    private String fileId;
    private Long orderId;

    @Override
    public void setMediaId(String mediaId) {
        fileId=mediaId;
    }

    @Override
    public String getMediaId() {
        return fileId;
    }

    @Override
    public void setDisplayId(Long displayId) {
        orderId=displayId;
    }

    @Override
    public Long getDisplayId() {
        return orderId;
    }
}
