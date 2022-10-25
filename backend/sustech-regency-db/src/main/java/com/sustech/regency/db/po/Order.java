package com.sustech.regency.db.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("`order`")
public class Order {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer roomId;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date dateStart;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date dateEnd;
    private Integer payerId;
    private Float fee;
    private Integer status; //需要设置不同的status数字对应说明状态 0代表订单异常 1代表已付款 2代表订单进行中 3代表订单已完成
    private String comment;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date commentTime;
    private Float stars;
}
