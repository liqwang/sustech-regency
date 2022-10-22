package com.sustech.regency.controller;

import com.sustech.regency.db.po.Hotel;
import com.sustech.regency.model.vo.HotelInfo;
import com.sustech.regency.service.MerchantService;
import com.sustech.regency.web.vo.ApiResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.sustech.regency.util.VerificationUtil.getUserId;

@Validated //单参数校验时必须加上该注解才会生效:https://developer.aliyun.com/article/786719
@RestController
@RequestMapping("/merchant")
public class MerchantController {
    @Resource
    private MerchantService merchantService;

    @ApiOperation("商家创建一个新的酒店")
    @PostMapping("/hotel/add")
    public ApiResponse<Boolean> addNewHotel(@ApiParam(value = "纬度", required = true) @RequestParam @NotNull Float latitude,
                                            @ApiParam(value = "经度", required = true) @RequestParam @NotNull Float longitude,
                                            @ApiParam(value = "所在区ID", required = true) @RequestParam @NotNull Integer regionId,
                                            @ApiParam(value = "酒店名字", required = true) @RequestParam @NotEmpty String name,
                                            @ApiParam(value = "酒店电话", required = true) @RequestParam @NotEmpty String tel,
                                            @ApiParam(value = "区域内详细地址", required = true) @RequestParam @NotEmpty String address
    ) {
        Hotel hotel = Hotel.builder().latitude(latitude)
                .longitude(longitude)
                .regionId(regionId)
                .merchantId(getUserId())
                .name(name)
                .tel(tel)
                .address(address)
                .build();
        return ApiResponse.success(merchantService.addNewHotel(hotel));
    }

    @ApiOperation("商家删除一个酒店")
    @PostMapping("/hotel/delete")
    public ApiResponse<Boolean> deleteHotel(@ApiParam(value = "酒店Id", required = true) @RequestParam Integer hotelId) {
        return ApiResponse.success(merchantService.deleteHotel(getUserId(), hotelId));
    }

    @ApiOperation("商家更新一个酒店信息")
    @PostMapping("/hotel/update")
    public ApiResponse<Boolean> updateHotel(@ApiParam(value = "酒店Id", required = true) @RequestParam Integer hotelId,
                                            @ApiParam(value = "纬度") @RequestParam(required = false) Float latitude,
                                            @ApiParam(value = "经度") @RequestParam(required = false) Float longitude,
                                            @ApiParam(value = "酒店名字") @RequestParam(required = false) String name,
                                            @ApiParam(value = "酒店电话") @RequestParam(required = false) String tel,
                                            @ApiParam(value = "区域内详细地址") @RequestParam(required = false) String address) {
        return ApiResponse.success(merchantService.updateHotel(hotelId, latitude, longitude,getUserId(), name, tel, address));
    }

    @ApiOperation("商家获取自己下面所有酒店信息")
    @GetMapping("/hotel/all")
    public ApiResponse<List<HotelInfo>> getAllHotels() {
        return ApiResponse.success(merchantService.getAllHotelInfos(getUserId()));
    }

    @ApiOperation("商家多参数查询一个酒店")
    @GetMapping("/hotel/get")
    public ApiResponse<HotelInfo> getOneHotel(@ApiParam(value = "酒店Id") @RequestParam(required = false) Integer hotelId,
                                              @ApiParam(value = "纬度") @RequestParam(required = false) Float latitude,
                                              @ApiParam(value = "经度") @RequestParam(required = false) Float longitude,
                                              @ApiParam(value = "酒店名字") @RequestParam(required = false) String name,
                                              @ApiParam(value = "酒店电话") @RequestParam(required = false) String tel) {
        return ApiResponse.success(merchantService.getOneHotel(hotelId, latitude, longitude, getUserId(), name, tel));
    }

    @ApiOperation(value = "商家上传酒店展示图片或视频",notes = "为指定的酒店(hotelId)上传展示图片(jpg,jpeg,png)或视频(mp4),返回文件上传成功后的获取url, 如https://quanquancho.com:8080/public/file/2022/09/30/2d02610787154be1af4816d5450b5ae8.jpg")
    @PostMapping("hotel/upload-media")
    public ApiResponse<Map> uploadHotelMedia (@ApiParam(required = true)
                                              @NotNull(message = "Picture or video shouldn't be null")
                                              @RequestParam MultipartFile media,

                                              @ApiParam(value = "酒店id",required = true)
                                              @NotNull(message = "hotelId shouldn't be null")
                                              @RequestParam Integer hotelId){
        String url = merchantService.uploadHotelMedia(media, hotelId);
        return ApiResponse.success(Map.of("url",url));
    }

    @ApiOperation(value = "商家上传酒店封面",notes = "为指定的酒店(hotelId)上传封面(jpg,jpeg,png),返回文件上传成功后的获取url, 如https://quanquancho.com:8080/public/file/2022/09/30/2d02610787154be1af4816d5450b5ae8.jpg")
    @PostMapping("hotel/upload-cover")
    public ApiResponse<Map> uploadHotelCover(@ApiParam(required = true)
                                             @NotNull(message = "Picture shouldn't be null")
                                             @RequestParam MultipartFile picture,

                                             @ApiParam(value = "酒店id",required = true)
                                             @NotNull(message = "hotelId shouldn't be null")
                                             @RequestParam Integer hotelId){
        String url = merchantService.uploadHotelCover(picture, hotelId);
        return ApiResponse.success(Map.of("url",url));
    }

    @ApiOperation(value = "商家删除酒店图片或视频",notes = "删除指定酒店(hotelId)的图片或视频(mediaId)")
    @PostMapping("hotel/delete-media")
    public ApiResponse deleteHotelMedia(@ApiParam(value = "图片或视频的32位uuid",required = true)
                                        @NotNull(message = "mediaId shouldn't be null")
                                        @RequestParam String mediaId,

                                        @ApiParam(value = "酒店id",required = true)
                                        @NotNull(message = "hotelId shouldn't be null")
                                        @RequestParam Integer hotelId){
        merchantService.deleteHotelMedia(mediaId,hotelId);
        return ApiResponse.success();
    }

    @ApiOperation("商家查询某个酒店的流水")
    @GetMapping("/hotel/get-HistoricalBills")
    public ApiResponse<List<Float>> getHistoricalBills(@ApiParam(value = "酒店Id", required = true) @RequestParam @NotEmpty @org.jetbrains.annotations.NotNull Integer hotelId,
                                          @ApiParam (value = "开始时间",required = true) @RequestParam @NotEmpty @NotNull Date startTime,
                                          @ApiParam (value = "结束时间",required = true) @RequestParam @NotEmpty @NotNull Date endTime){
        return ApiResponse.success(merchantService.getHotelHistoricalBills(hotelId,startTime,endTime));
    }
}
