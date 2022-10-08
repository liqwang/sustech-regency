package com.sustech.regency.controller;

import com.sustech.regency.db.dao.CityDao;
import com.sustech.regency.db.dao.ProvinceDao;
import com.sustech.regency.db.dao.RegionDao;
import com.sustech.regency.db.po.City;
import com.sustech.regency.db.po.Province;
import com.sustech.regency.db.po.Region;
import com.sustech.regency.web.vo.ApiResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/info")
public class InfoController {

	@Resource
	private ProvinceDao provinceDao;
	@ApiOperation("获取所有省")
	@GetMapping("/province/all")
	public ApiResponse<List<Province>> getAllProvinces(){
		List<Province> provinces = provinceDao.selectList(null);
		return ApiResponse.success(provinces);
	}

	@Resource
	private CityDao cityDao;
	@ApiOperation("获取所有市")
	@GetMapping("/city/all")
	public ApiResponse<List<City>> getAllCities(){
		List<City> cities = cityDao.selectList(null);
		return ApiResponse.success(cities);
	}

	@Resource
	private RegionDao regionDao;
	@ApiOperation("获取所有区")
	@GetMapping("/region/all")
	public ApiResponse<List<Region>> getAllRegions(){
		List<Region> regions = regionDao.selectList(null);
		return ApiResponse.success(regions);
	}
}
