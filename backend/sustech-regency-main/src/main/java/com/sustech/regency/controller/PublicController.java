package com.sustech.regency.controller;

import cn.hutool.core.io.FileUtil;
import com.sustech.regency.db.dao.CityDao;
import com.sustech.regency.db.dao.ProvinceDao;
import com.sustech.regency.db.dao.RegionDao;
import com.sustech.regency.db.po.City;
import com.sustech.regency.db.po.Province;
import com.sustech.regency.db.po.Region;
import com.sustech.regency.model.param.LocationParam;
import com.sustech.regency.model.vo.HotelInfo;
import com.sustech.regency.service.PublicService;
import com.sustech.regency.web.handler.ApiException;
import com.sustech.regency.web.vo.ApiResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/public")
public class PublicController {
	@Autowired
	private PublicService publicService;

	@Autowired
	private ProvinceDao provinceDao;
	@ApiOperation("获取所有省")
	@GetMapping("/province/all")
	public ApiResponse<List<Province>> getAllProvinces(){
		List<Province> provinces = provinceDao.selectList(null);
		return ApiResponse.success(provinces);
	}

	@Autowired
	private CityDao cityDao;
	@ApiOperation("获取所有市")
	@GetMapping("/city/all")
	public ApiResponse<List<City>> getAllCities(){
		List<City> cities = cityDao.selectList(null);
		return ApiResponse.success(cities);
	}

	@Autowired
	private RegionDao regionDao;
	@ApiOperation("获取所有区")
	@GetMapping("/region/all")
	public ApiResponse<List<Region>> getAllRegions(){
		List<Region> regions = regionDao.selectList(null);
		return ApiResponse.success(regions);
	}

	@Value("${file-root-path}")
	private String fileRootPath; //保存文件的根路径
	@SuppressWarnings("CommentedOutCode")
	@ApiOperation(value = "获取文件",hidden = true)
	@GetMapping("/file/**")
	public ApiResponse getFile(HttpServletResponse response, HttpServletRequest request){
		String path = request.getRequestURI().replace("/info/file", "");
		File file = new File(fileRootPath+path);
		if(!file.exists()){
			throw ApiException.badRequest("文件不存在");
		}
		response.reset();
		response.setContentLength((int)file.length());
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
	@GetMapping("/public/getHotelByLocation")
	public ApiResponse<List<HotelInfo>> getHotels(@Validated @RequestBody LocationParam locationParam){
		return ApiResponse.success(publicService.getHotelsByLocation(locationParam.getProvince(),locationParam.getCity(),locationParam.getRegion(),locationParam.getName()));
	}
}
