package com.sustech.regency.db.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sustech.regency.db.annotation.DateField;
import com.sustech.regency.db.annotation.DateTimeField;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 入住时间认为是 dateStart中午-dateEnd中午
 */
@Data
@Accessors(chain = true)
@TableName("`order`")
public class Order {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private Integer roomId;
    @DateField
    private Date dateStart;
    @DateField
    private Date dateEnd;
    private Integer payerId;
    private Float fee;
    private OrderStatus status;
    private String comment;
    @DateTimeField
    private Date commentTime;
    private Float stars;
    @DateTimeField
    private Date payTime;
    @DateTimeField
    private Date createTime;
    @DateTimeField
    private Date refundTime;
}
