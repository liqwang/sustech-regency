package com.sustech.regency.db.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomType implements DisPlayable{
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private Integer capacity;
    private Integer toiletNum;
    private Boolean hasLivingRoom;
    private String coverId;
}
