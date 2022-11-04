package com.sustech.regency.controller;

import cn.hutool.core.io.FileUtil;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.sustech.regency.db.dao.CityDao;
import com.sustech.regency.db.dao.ProvinceDao;
import com.sustech.regency.db.dao.RegionDao;
import com.sustech.regency.db.po.*;
import com.sustech.regency.model.vo.Comment;
import com.sustech.regency.model.vo.HotelInfo;
import com.sustech.regency.model.vo.RoomInfo;
import com.sustech.regency.service.HideService;
import com.sustech.regency.service.PublicService;
import com.sustech.regency.web.annotation.PathController;
import com.sustech.regency.web.handler.ApiException;
import com.sustech.regency.web.vo.ApiResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.logging.log4j.util.Strings;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import java.io.File;
import java.io.IOException;
import java.util.List;

@PathController("/public")
public class PublicController {
    @Resource
    private PublicService publicService;

    @Resource
    private ProvinceDao provinceDao;

    @Resource
    private HideService hideService;

    @ApiOperation("获取所有省")
    @GetMapping("/province/all")
    public ApiResponse<List<Province>> getAllProvinces() {
        List<Province> provinces = provinceDao.selectList(null);
        return ApiResponse.success(provinces);
    }

    @Resource
    private CityDao cityDao;

    @ApiOperation("获取一个省的所有市")
    @GetMapping("/city/all")
    public ApiResponse<List<City>> getAllCities(@RequestParam(required = false) String province) {
        MPJLambdaWrapper<City> wrapper = new MPJLambdaWrapper<>();
        wrapper.selectAll(City.class)
                .innerJoin(Province.class, Province::getId, City::getProvinceId);
        if (Strings.isNotEmpty(province)) {
            wrapper.eq(Province::getName, province);
        }
        return ApiResponse.success(cityDao.selectJoinList(City.class, wrapper));
    }

    @Resource
    private RegionDao regionDao;

    @ApiOperation("获取一个城市的所有区")
    @GetMapping("/region/all")
    public ApiResponse<List<Region>> getAllRegions(@RequestParam(required = false) String province, @RequestParam(required = false) String city) {
        MPJLambdaWrapper<Region> wrapper = new MPJLambdaWrapper<>();
        wrapper.selectAll(Region.class)
                .innerJoin(City.class, City::getId, Region::getCityId)
                .innerJoin(Province.class, Province::getId, City::getProvinceId);
        if (Strings.isNotEmpty(province)) {
            wrapper.eq(Province::getName, province);
        }
        if (Strings.isNotEmpty(city)) {
            wrapper.eq(City::getName, city);
        }
        return ApiResponse.success(regionDao.selectJoinList(Region.class, wrapper));
    }

    @Value("${file-root-path}")
    private String fileRootPath; //保存文件的根路径

    @SuppressWarnings("CommentedOutCode")
    @ApiOperation(value = "获取文件", hidden = true)
    @GetMapping("/file/**")
    public void getFile(HttpServletResponse response, HttpServletRequest request) {
        String path = request.getRequestURI().replace("/public/file", "");
        File file = new File(fileRootPath + path);
        if (!file.exists()) {
            throw ApiException.badRequest("文件不存在");
        }
        response.reset();
        response.setContentLength((int) file.length());
        response.setContentType("application/octet-stream"); //MIME类型，这里表示除文本文件外的默认值
//		response.setCharacterEncoding("utf-8");
//		response.setHeader("Content-Disposition","inline"); //在浏览器中直接打开而不下载，但是对application/octet-stream的MIME类型无效
//		response.setHeader("Content-Disposition","attachment; filename="+newName); //作为附件下载，并重新命名为newName

        byte[] bytes = FileUtil.readBytes(file);
        try {
            response.getOutputStream().write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
            throw ApiException.INTERNAL_SEVER_ERROR;
        }
    }

    @ApiOperation("根据省市区酒店名字获得酒店信息")
    @GetMapping("/get-hotels-by-location")
    public ApiResponse<List<HotelInfo>> getHotels(@ApiParam(value = "省份名字") @RequestParam(required = false) String ProvinceName,
                                                  @ApiParam(value = "城市名字") @RequestParam(required = false) String CityName,
                                                  @ApiParam(value = "区的名字") @RequestParam(required = false) String RegionName,
                                                  @ApiParam(value = "酒店名字") @RequestParam(required = false) String HotelName) {
        return ApiResponse.success(publicService.getHotelsByLocation(ProvinceName, CityName, RegionName, HotelName));
    }

    @ApiOperation("根据酒店ID获取对应所有的房间")
    @GetMapping("/get-rooms-by-hotel")
    public ApiResponse<List<Room>> getRoomsByHotel(@ApiParam(value = "酒店Id", required = true) @RequestParam @NotNull Integer hotelId) {
        return ApiResponse.success(publicService.getRoomsByHotel(hotelId));
    }

    @ApiOperation("根据酒店ID获取房间最低价格")
    @GetMapping("/get-min_price-by-hotel")
    public ApiResponse<Float> getMinPriceByHotel(@ApiParam(value = "酒店Id", required = true) @RequestParam @NotNull Integer hotelId) {
        return ApiResponse.success(publicService.getMinPriceOfHotel(hotelId));
    }

    @ApiOperation("根据酒店ID获取评论数量")
    @GetMapping("/get-comment_number-by-hotel")
    public ApiResponse<Integer> getCommentOfHotel(@ApiParam(value = "酒店Id", required = true) @RequestParam @NotNull Integer hotelId) {
        return ApiResponse.success(publicService.getCommentsNumberByHotel(hotelId));
    }

    @ApiOperation("根据房间Id获取房间信息")
    @GetMapping("/get-roomInfo-by-roomId")
    public ApiResponse<RoomInfo> getRoomInfoByRoomId(@ApiParam(value = "房间Id", required = true) @RequestParam @NotNull Integer roomId) {
        return ApiResponse.success(publicService.getRoomInfoByRoomId(roomId));
    }

    @ApiOperation("根据酒店Id获得所有房型信息")
    @GetMapping("/get-roomTypes-by-HotelId")
    public ApiResponse<List<RoomType>> getRoomTypesByHotelId(@ApiParam(value = "酒店Id", required = true) @RequestParam @NotNull Integer hotelId) {
        return ApiResponse.success(publicService.getRoomTypesByHotelId(hotelId));
    }

    @ApiOperation("根据酒店Id获取商家用户名")
    @GetMapping("/merchant-username")
    public ApiResponse<String> getMerchantUsernameByHotelId(@ApiParam(value = "酒店Id", required = true) @RequestParam @NotNull Integer hotelId) {
        return ApiResponse.success(publicService.getMerchantUsernameByHotelId(hotelId));
    }

    @ApiOperation("根据酒店Id获取所有评论")
    @GetMapping("/get-hotelComments")
    public ApiResponse<List<Comment>> getCommentsByHotelId(@ApiParam(value = "酒店Id", required = true) @RequestParam @NotNull Integer hotelId) {
        return ApiResponse.success(publicService.getCommentsByHotelId(hotelId));
    }

    @ApiOperation("根据酒店Id获取酒店信息")
    @GetMapping("/get-hotelInfo-byId")
    public ApiResponse<HotelInfo> getHotelInfoByHotelId(@ApiParam(value = "酒店Id", required = true) @RequestParam @NotNull Integer hotelId) {
        return ApiResponse.success(publicService.getOneHotelByHotelId(hotelId));
    }

    @ApiOperation("根据酒店ID和房号获得房间ID")
    @GetMapping("/get-roomId-byHotelWithRoomNUm")
    public ApiResponse<Integer> getRoomIdByHotelIdWithRoomNum(@ApiParam(value = "酒店Id", required = true) @RequestParam @NotNull Integer hotelId,
                                                              @ApiParam(value = "房间号", required = true) @RequestParam @NotNull Integer roomId) {
        return ApiResponse.success(publicService.getRoomIdByHotelWithRoomNum(hotelId,roomId));
    }

    @ApiOperation("根据酒店ID获得房间types")
    @GetMapping("/get-roomTypes-byHotelId")
    public ApiResponse<List<RoomType>> getRoomIdByHotelIdWithRoomNum(@ApiParam(value = "酒店Id", required = true) @RequestParam @NotNull Integer hotelId) {
        return ApiResponse.success(publicService.getRoomTypesByHotelId(hotelId));
    }

}
