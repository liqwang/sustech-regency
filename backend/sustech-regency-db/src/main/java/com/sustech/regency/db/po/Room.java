package com.sustech.regency.db.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Room {
    private Integer id;
    private Integer roomNum;
    private Float price;
    private Integer floor;
    private Integer typeId;
    private Integer hotelId;
    private Boolean isAvailable;
    private Float discount;
}
