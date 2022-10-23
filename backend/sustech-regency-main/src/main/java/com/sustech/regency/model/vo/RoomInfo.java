package com.sustech.regency.model.vo;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomInfo {
    private Integer id;
    private Integer roomNum;
    private Float price;
    private Integer floor;
    private Integer typeId;
    private Integer hotelId;
    private Boolean isAvailable;
    private Float discount;
    private String roomTypeName;
    private Integer capacity;
    private Integer toiletNum;
    private Boolean hasLivingRoom;
    private String coverUrl;
    private List<String> videoUrls;
    private List<String> pictureUrls;
}
