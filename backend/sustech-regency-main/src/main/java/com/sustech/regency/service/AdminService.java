package com.sustech.regency.service;

import org.springframework.web.multipart.MultipartFile;

public interface AdminService {
    /**
     * @return 返回上传房型封面之后的获取url
     */
    String uploadRoomTypeCover(MultipartFile picture, Integer roomTypeId);

    /**
     * @return 返回上传房型封面之后的获取url
     */
    String uploadRoomTypeMedia(MultipartFile media, Integer roomTypeId);

    void deleteRoomTypeMedia(String mediaId, Integer roomTypeId);
}
