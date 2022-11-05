package com.sustech.regency.controller;

import com.sustech.regency.service.AdminService;
import com.sustech.regency.web.annotation.PathController;
import com.sustech.regency.web.vo.ApiResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;

@PathController("/admin")
public class AdminController {

    @Resource
    private AdminService adminService;

    @ApiOperation(value = "管理员上传房型封面", notes = "为指定的房型(roomTypeId)上传封面(jpg,jpeg,png),返回文件上传成功后的获取url, 如https://quanquancho.com:8080/public/file/2022/09/30/2d02610787154be1af4816d5450b5ae8.jpg")
    @PostMapping("/room-type/upload-cover")
    public ApiResponse<Map> uploadRoomTypeCover(@ApiParam(required = true)
                                                @RequestParam MultipartFile picture,

                                                @ApiParam(value = "房型id", required = true)
                                                @NotNull @RequestParam Integer roomTypeId) {
        String url = adminService.uploadRoomTypeCover(picture, roomTypeId);
        return ApiResponse.success(Map.of("url", url));
    }

    @ApiOperation(value = "管理员上传房型展示图片或视频", notes = "为指定的房型(roomTypeId)上传图片(jpg,jpeg,png)或视频(mp4),返回文件上传成功后的获取url, 如https://quanquancho.com:8080/public/file/2022/09/30/2d02610787154be1af4816d5450b5ae8.jpg")
    @PostMapping("/room-type/upload-media")
    public ApiResponse<Map> uploadRoomTypeMedia(@ApiParam(required = true)
                                                @RequestParam MultipartFile media,

                                                @ApiParam(value = "房型id", required = true)
                                                @NotNull @RequestParam Integer roomTypeId) {
        String url = adminService.uploadRoomTypeMedia(media, roomTypeId);
        return ApiResponse.success(Map.of("url", url));
    }

    @ApiOperation(value = "管理员删除房型展示图片或视频", notes = "为指定的房型(roomTypeId)删除图片或视频")
    @PostMapping("/room-type/delete-media")
    public ApiResponse deleteRoomTypeMedia(@ApiParam(required = true)
                                           @NotEmpty @RequestParam String mediaId,

                                           @ApiParam(value = "房型id", required = true)
                                           @NotNull @RequestParam Integer roomTypeId) {
        adminService.deleteRoomTypeMedia(mediaId, roomTypeId);
        return ApiResponse.success();
    }
}
