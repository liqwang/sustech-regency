package com.sustech.regency.controller;

import com.sustech.regency.service.AdminService;
import com.sustech.regency.web.vo.ApiResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Resource
	private AdminService adminService;
	@ApiOperation(value = "管理员上传房型封面",notes = "为指定的房型(roomTypeId)上传封面(jpg,jpeg,png),返回文件上传成功后的获取url, 如https://quanquancho.com:8080/public/file/2022/09/30/2d02610787154be1af4816d5450b5ae8.jpg")
	@PostMapping("/room-type/upload-cover")
	public ApiResponse<Map> uploadRoomTypeCover(@ApiParam(required = true)
                                                @NotNull(message = "Picture shouldn't be null")
                                                @RequestParam MultipartFile picture,

	                                            @ApiParam(value = "房型id",required = true)
                                                @NotNull(message = "roomTypeId shouldn't be null")
                                                @RequestParam Integer roomTypeId){
		String url = adminService.uploadRoomTypeCover(picture, roomTypeId);
		return ApiResponse.success(Map.of("url",url));
	}
}
