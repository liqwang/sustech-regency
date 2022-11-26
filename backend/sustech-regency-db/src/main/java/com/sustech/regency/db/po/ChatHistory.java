package com.sustech.regency.db.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatHistory {
    private String fromName;
    private String toName;
    private String chatTime;
    private String content;
    private Integer hotelId;
}
