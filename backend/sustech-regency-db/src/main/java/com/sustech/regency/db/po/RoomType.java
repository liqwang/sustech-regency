package com.sustech.regency.db.po;

import lombok.Data;

@Data
public class RoomType {
    private Integer id;
    private String name;
    private Integer room_num;
    private Integer capacity;
    private Integer toilet_num;
    private Boolean is_contain_living_room;
}
