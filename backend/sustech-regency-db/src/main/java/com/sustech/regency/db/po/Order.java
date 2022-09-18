package com.sustech.regency.db.po;

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
    private Integer id;
    private Integer roomId;
    private Date dateStart;
    private Date dateEnd;
    private Integer payerId;
    private Integer fee;
    private Integer status;
    private String comment;
}
