package com.sustech.regency.controller;

import com.sustech.regency.db.po.Hotel;
import com.sustech.regency.service.MerchantService;
import com.sustech.regency.service.UserService;
import com.sustech.regency.web.vo.ApiResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Validated //单参数校验时必须加上该注解才会生效:https://developer.aliyun.com/article/786719
@RestController
@RequestMapping("/merchant") //调用时会使用这个名字做前缀

public class MerchantController {
    @Autowired
    private MerchantService merchantService;
    @ApiOperation("商家获取自己下面所有酒店")
    @PostMapping("/hotel/all")
    public ApiResponse<List<Hotel>> getAllHotels(){
         int id = (int)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ApiResponse.success(merchantService.getAllHotels(id));
    }
}
