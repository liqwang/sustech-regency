package com.sustech.regency.db.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class City {
    private Integer id;
    private String name;
    private Integer provinceId;
    private Boolean isProvincialCapital;
}
