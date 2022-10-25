package com.sustech.regency.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date commentTime;
    private String comment;
    private String userName;
    private String userId;
    private String hotelName;
    private String roomType;
    private Float stars;
    private List<String> videoUrls;
    private List<String> pictureUrls;
}
