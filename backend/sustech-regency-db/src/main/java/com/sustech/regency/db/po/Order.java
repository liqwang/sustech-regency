package com.sustech.regency.db.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer roomId;
    private Date dateStart;
    private Date dateEnd;
    private Integer payerId;
    private Integer fee;
    private Integer status;
    private String comment;
}
