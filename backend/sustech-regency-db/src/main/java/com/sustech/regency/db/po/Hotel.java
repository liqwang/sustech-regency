package com.sustech.regency.db.po;

import lombok.Data;

@Data
public class Hotel {
    private Integer id;
    private Float latitude;
    private Float longitude;
    private Integer city_id;
    private Integer merchant_id;
}
