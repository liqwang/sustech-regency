package com.sustech.regency.model.vo;

import com.sustech.regency.db.annotation.DateTimeField;
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
public class Comment {
    @DateTimeField
    private Date commentTime;
    private String comment;
    private String userName;
    private String hotelName;
    private String roomType;
    private Float stars;
    private List<String> videoUrls;
    private List<String> pictureUrls;
    private String headShotUrl;
}
