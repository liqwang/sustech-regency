package com.sustech.regency.db.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Room {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer roomNum;
    private Float price;
    private Integer floor;
    private Integer typeId;
    private Integer hotelId;
    private Boolean isAvailable;
    private Float discount;
}
