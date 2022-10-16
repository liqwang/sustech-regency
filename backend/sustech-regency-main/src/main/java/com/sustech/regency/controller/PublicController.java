package com.sustech.regency.controller;

import cn.hutool.core.io.FileUtil;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.sustech.regency.db.dao.CityDao;
import com.sustech.regency.db.dao.ProvinceDao;
import com.sustech.regency.db.dao.RegionDao;
import com.sustech.regency.db.po.City;
import com.sustech.regency.db.po.Province;
import com.sustech.regency.db.po.Region;
import com.sustech.regency.db.po.Room;
import com.sustech.regency.model.param.LocationParam;
import com.sustech.regency.model.vo.HotelInfo;
import com.sustech.regency.service.PublicService;
import com.sustech.regency.web.handler.ApiException;
import com.sustech.regency.web.vo.ApiResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.logging.log4j.util.Strings;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/public")
public class PublicController {
    @Resource
    private PublicService publicService;

    @Resource
    private ProvinceDao provinceDao;

    public PublicController() {
    }

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
    public ApiResponse getFile(HttpServletResponse response, HttpServletRequest request) {
        String path = request.getRequestURI().replace("/info/file", "");
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
        return ApiResponse.success();
    }

    @ApiOperation("根据省市区酒店名字获得酒店信息")
    @GetMapping("/get-hotels-by-location")
    public ApiResponse<List<HotelInfo>> getHotels(@Validated @RequestBody LocationParam locationParam) {
        return ApiResponse.success(publicService.getHotelsByLocation(locationParam.getProvince(), locationParam.getCity(), locationParam.getRegion(), locationParam.getName()));
    }

    @ApiOperation("根据酒店ID获取对应所有的房间")
    @GetMapping("/get-rooms-by-hotel")
    public ApiResponse<List<Room>> getRoomsByHotel(@ApiParam(value = "酒店Id", required = true) @RequestParam @NotEmpty @NotNull Integer hotelId){
        return ApiResponse.success(publicService.getRoomsByHotel(hotelId));
    }
}
