package com.sustech.regency.db.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.sustech.regency.db.annotation.DateField;
import com.sustech.regency.db.annotation.DateTimeField;
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
}
