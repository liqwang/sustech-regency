package com.sustech.regency.db.po;

import lombok.Data;

import java.util.Date;

@Data
public class Order {
    private Integer id;
    private Integer room_id;
    private Date date_start;
    private Date date_end;
    private Integer payer_id;
    private Integer fee;
    private Integer status;
    private String comment;
}
