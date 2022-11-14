package com.sustech.regency.controller;

import com.sustech.regency.model.param.FindPasswordParam;
import com.sustech.regency.model.param.LoginParam;
import com.sustech.regency.model.param.RegisterParam;
import com.sustech.regency.model.vo.UserInfo;
import com.sustech.regency.service.UserService;
import com.sustech.regency.web.annotation.PathController;
import com.sustech.regency.web.vo.ApiResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Map;

import static com.sustech.regency.util.VerificationUtil.getUserId;

@PathController("/user")
public class UserController {
    @Resource
    private UserService userService;

    @ApiOperation("发送验证码到邮箱")
    @PostMapping("/send-verification-code")
    public ApiResponse sendVerificationCode(@ApiParam(required = true)
                                            @Email @NotEmpty @RequestParam String email) {
        userService.sendVerificationCode(email);
        return ApiResponse.success();
    }

    @ApiOperation(value = "注册")
    @PostMapping("/register")
    public ApiResponse<UserInfo> register(@Validated @RequestBody RegisterParam registerParam) {
        UserInfo userInfo =userService.register(registerParam.getVerificationCode(),
                                                registerParam.getEmail(),
                                                registerParam.getUsername(),
                                                registerParam.getPassword(),
                                                registerParam.getRoleId());
        return ApiResponse.success(userInfo);
    }

    @ApiOperation("找回密码")
    @PostMapping("/find-password")
    public ApiResponse findPassword(@Validated @RequestBody FindPasswordParam findPasswordParam) {
        userService.findPassword(findPasswordParam.getVerificationCode(),
                findPasswordParam.getEmail(),
                findPasswordParam.getNewPassword());
        return ApiResponse.success();
    }

    @ApiOperation(value = "登录")
    @PostMapping("/login")
    public ApiResponse<UserInfo> login(@Validated @RequestBody LoginParam loginParam) {
        UserInfo userInfo = userService.login(loginParam.getUsernameOrEmail(), loginParam.getPassword());
        return ApiResponse.success(userInfo);
    }

    @ApiOperation(value = "用户上传头像", notes = "**需要token验证**，返回文件上传成功后的获取url, 如https://quanquancho.com:8080/public/file/2022/09/30/2d02610787154be1af4816d5450b5ae8.jpg")
    @PostMapping("/upload-headshot")
    public ApiResponse<Map> uploadHeadShot(@ApiParam(required = true)
                                           @RequestParam MultipartFile picture) {
        String url = userService.uploadHeadShot(picture, getUserId());
        return ApiResponse.success(Map.of("url", url));
    }
}
