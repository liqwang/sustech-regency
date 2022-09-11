package com.sustech.regency.db.po;

import lombok.Data;

@Data
public class Room {
    private Integer id;
    private Integer roomId;
    private Integer price;
    private Integer floor;
    private Integer typeId;
    private Integer hotelId;
    private Boolean isAvailable;
    private Float discount;
}
