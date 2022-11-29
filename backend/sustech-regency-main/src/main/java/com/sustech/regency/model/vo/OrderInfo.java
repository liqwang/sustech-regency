package com.sustech.regency.model.vo;

import com.sustech.regency.db.annotation.DateTimeField;
import com.sustech.regency.db.po.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderInfo {
    @DateTimeField
    private Order order;
    private HotelInfo hotelInfo;
    private RoomInfo roomInfo;


}