package com.sustech.regency.db.po;

import lombok.Data;

import java.util.Date;

@Data
public class Order {
    private Integer id;
    private Integer roomId;
    private Date dateStart;
    private Date dateEnd;
    private Integer payerId;
    private Integer fee;
    private Integer status;
    private String comment;
}
