package com.sustech.regency.controller;

import com.sustech.regency.db.po.Hotel;
import com.sustech.regency.service.MerchantService;
import com.sustech.regency.web.vo.ApiResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.jdbc.Null;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Todo: API安全性校验是否是商家
@Validated //单参数校验时必须加上该注解才会生效:https://developer.aliyun.com/article/786719
@RestController
@RequestMapping("/merchant")
public class MerchantController {
    @Autowired
    private MerchantService merchantService;

    @ApiOperation("商家创建一个新的酒店")
    @PostMapping("/hotel/add")
    public ApiResponse<Boolean> addNewHotel(@ApiParam(value = "纬度", required = true) @RequestParam Float latitude,
                                            @ApiParam(value = "经度", required = true) @RequestParam Float longitude,
                                            @ApiParam(value = "所在区ID", required = true) @RequestParam Integer regionId,
                                            @ApiParam(value = "酒店老板ID", required = true) @RequestParam Integer merchantId,
                                            @ApiParam(value = "酒店名字", required = true) @RequestParam String name,
                                            @ApiParam(value = "酒店座机电话", required = true) @RequestParam @Length(min = 12, max = 12) String tel,
                                            @ApiParam(value = "区域内详细地址", required = true) @RequestParam String address
    ) {
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
                                            @ApiParam(value = "纬度") @RequestParam Float latitude,
                                            @ApiParam(value = "经度") @RequestParam Float longitude,
                                            @ApiParam(value = "所在城市对应ID", required = false) @RequestParam Integer cityId,
                                            @ApiParam(value = "酒店名字", required = false) @RequestParam String name,
                                            @ApiParam(value = "酒店座机电话", required = false) @RequestParam @Length(min = 12, max = 12) String tel) {
        int merchantId = (int) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return ApiResponse.success(merchantService.updateHotel(hotelId, latitude, longitude, cityId, merchantId, name, tel));

    }

    @ApiOperation("商家获取自己下面所有酒店")
    @GetMapping("/hotel/all")
    public ApiResponse<List<Hotel>> getAllHotels(@ApiParam(value = "所在城市对应ID", required = false) @RequestParam(required = false) Integer cityId,
                                                 @ApiParam(value = "所在区ID", required = true) @RequestParam Integer regionId
                                                 ) {
        int id = (int) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ApiResponse.success(merchantService.getAllHotels(id, cityId,regionId));
    }

    @ApiOperation("商家多参数查询酒店")
    @GetMapping("/hotel/get")
    public ApiResponse<Hotel> getOneHotel(@ApiParam(value = "酒店Id", required = false) @RequestParam Integer hotelId,
                                          @ApiParam(value = "纬度", required = false) @RequestParam Float latitude,
                                          @ApiParam(value = "经度", required = false) @RequestParam Float longitude,
                                          @ApiParam(value = "所在城市对应ID", required = false) @RequestParam Integer cityId,
                                          @ApiParam(value = "酒店名字", required = false) @RequestParam String name,
                                          @ApiParam(value = "酒店座机电话", required = false) @RequestParam @Length(min = 12, max = 12) String tel) {
        int merchantId = (int) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ApiResponse.success(merchantService.getOneHotel(hotelId, latitude, longitude, cityId, merchantId, name, tel));
    }
}
