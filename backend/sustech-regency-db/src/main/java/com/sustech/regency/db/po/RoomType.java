package com.sustech.regency.db.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomType {
    private Integer id;
    private String name;
    private Integer roomNum;
    private Integer capacity;
    private Integer toiletNum;
    private Boolean hasLivingRoom;
}
