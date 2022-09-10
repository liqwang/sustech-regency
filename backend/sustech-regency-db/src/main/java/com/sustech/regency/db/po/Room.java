package com.sustech.regency.db.po;

import lombok.Data;

@Data
public class Room {
    private Integer id;
    private Integer room_id;
    private Integer price;
    private Integer floor;
    private Integer type_id;
    private Integer hotel_id;
    private Boolean is_available;
    private Float discount;
}
