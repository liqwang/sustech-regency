package com.sustech.regency.controller;

import com.sustech.regency.db.po.Hotel;
import com.sustech.regency.model.vo.HotelInfo;
import com.sustech.regency.service.MerchantService;
import com.sustech.regency.web.vo.ApiResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import java.util.List;

//Todo: API安全性校验是否是商家
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
        int merchantId = (int) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Hotel hotel = Hotel.builder().latitude(latitude)
                .longitude(longitude)
                .regionId(regionId)
                .merchantId(merchantId)
                .name(name)
                .tel(tel)
                .address(address)
                .build();
        return ApiResponse.success(merchantService.addNewHotel(hotel));
    }

    @ApiOperation("商家删除一个酒店")
    @PostMapping("/hotel/delete")
    public ApiResponse<Boolean> deleteHotel(@ApiParam(value = "酒店Id", required = true) @RequestParam Integer hotelId) {
        int merchantId = (int) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ApiResponse.success(merchantService.deleteHotel(merchantId, hotelId));
    }

    @ApiOperation("商家更新一个酒店信息")
    @PostMapping("/hotel/update")
    public ApiResponse<Boolean> updateHotel(@ApiParam(value = "酒店Id", required = true) @RequestParam Integer hotelId,
                                            @ApiParam(value = "纬度") @RequestParam(required = false) Float latitude,
                                            @ApiParam(value = "经度") @RequestParam(required = false) Float longitude,
                                            @ApiParam(value = "所在区ID") @RequestParam(required = false) Integer regionId,
                                            @ApiParam(value = "酒店名字") @RequestParam(required = false) String name,
                                            @ApiParam(value = "酒店电话") @RequestParam(required = false) String tel,
                                            @ApiParam(value = "区域内详细地址") @RequestParam(required = false) String address) {
        int merchantId = (int) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ApiResponse.success(merchantService.updateHotel(hotelId, latitude, longitude, regionId, merchantId, name, tel, address));
    }

    @ApiOperation("商家获取自己下面所有酒店信息")
    @GetMapping("/hotel/all")
    public ApiResponse<List<HotelInfo>> getAllHotels() {
        int merchantId = (int) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ApiResponse.success(merchantService.getAllHotelInfos(merchantId));
    }

    //TODO:通过省市区来查询
    @ApiOperation("商家多参数查询一个酒店")
    @GetMapping("/hotel/get")
    public ApiResponse<HotelInfo> getOneHotel(@ApiParam(value = "酒店Id") @RequestParam(required = false) Integer hotelId,
                                              @ApiParam(value = "纬度") @RequestParam(required = false) Float latitude,
                                              @ApiParam(value = "经度") @RequestParam(required = false) Float longitude,
                                              @ApiParam(value = "酒店名字") @RequestParam(required = false) String name,
                                              @ApiParam(value = "酒店电话") @RequestParam(required = false) String tel) {
        int merchantId = (int) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ApiResponse.success(merchantService.getOneHotel(hotelId, latitude, longitude, merchantId, name, tel));
    }
}
