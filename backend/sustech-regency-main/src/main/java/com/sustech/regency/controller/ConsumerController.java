package com.sustech.regency.controller;

import com.sustech.regency.service.ConsumerService;
import com.sustech.regency.web.vo.ApiResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Validated //单参数校验时必须加上该注解才会生效:https://developer.aliyun.com/article/786719
@RestController
@RequestMapping("/consumer")
public class ConsumerController {
	@Resource
	private ConsumerService consumerService;

	@ApiOperation(value = "上传评论图片或视频",notes = "为指定的订单(orderId)上传评论图片(jpg,jpeg,png)或视频(mp4),返回文件上传成功后的获取url, 如https://quanquancho.com:8080/public/file/2022/09/30/2d02610787154be1af4816d5450b5ae8.jpg")
	@PostMapping("/comment/upload-media")
	public ApiResponse<Map> uploadCommentMedia (@ApiParam(required = true)
												@NotNull(message = "Picture or video shouldn't be null")
												@RequestParam MultipartFile media,

												@ApiParam(value = "订单id",required = true)
												@NotNull(message = "orderId shouldn't be null")
												@RequestParam Integer orderId){
		String url = consumerService.uploadCommentMedia(media,orderId);
		return ApiResponse.success(Map.of("url",url));
	}

	@ApiOperation(value = "删除评论图片或视频",notes = "删除指定的订单评论(orderId)的图片或视频")
	@PostMapping("/comment/delete-media")
	public ApiResponse deleteCommentMedia(@ApiParam(required = true)
			                              @NotNull(message = "mediaId shouldn't be null")
			                              @RequestParam String mediaId,

			                              @ApiParam(value = "订单id",required = true)
			                              @NotNull(message = "orderId shouldn't be null")
			                              @RequestParam Integer orderId){
		consumerService.deleteCommentMedia(mediaId,orderId);
		return ApiResponse.success();
	}
}
