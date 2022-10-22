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
public class Hotel implements DisPlayable{
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Float latitude;
    private Float longitude;
    private Integer regionId;
    private Integer merchantId;
    private String name;
    private String tel;
    private String address;
    private Float stars; //表示几星级酒店
    private String coverId;
    
}
