package com.sustech.regency.db.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 评论附带的图片或视频
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentAttachment {
    private String id;
    private Integer orderId;
    private Date uploadTime;
    private String suffix;
    private Boolean isPicture;
}
