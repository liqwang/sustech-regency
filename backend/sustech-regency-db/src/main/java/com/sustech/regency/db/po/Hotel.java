package com.sustech.regency.db.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Hotel {
    private Integer id;
    private Float latitude;
    private Float longitude;
    private Integer regionId;
    private Integer merchantId;
    private String name;
    private String tel;
    private String address;
}
