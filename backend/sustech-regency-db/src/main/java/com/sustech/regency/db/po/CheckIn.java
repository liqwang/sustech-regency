package com.sustech.regency.db.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class CheckIn {
    @TableId(type = IdType.AUTO)
    private Integer userId;
    private Integer orderId;
}
