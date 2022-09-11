package com.sustech.regency.db.po;

import lombok.Data;

@Data
public class RoomType {
    private Integer id;
    private String name;
    private Integer roomNum;
    private Integer capacity;
    private Integer toiletNum;
    private Boolean hasLivingRoom;
}
