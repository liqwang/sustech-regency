package com.sustech.regency.service;

import com.sustech.regency.model.vo.UserInfo;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    /**
     * @return token以及用户的详细信息
     */
    UserInfo register(String verificationCode, String email, String username, String password, Integer roleId);

    /**
     * @return token以及用户的详细信息
     */
    UserInfo login(String usernameOrEmail, String password);

    void findPassword(String verificationCode, String email, String newPassword);

    void sendVerificationCode(String email);

    /**
     * @return 返回成功上传后的获取URL
     */
    String uploadHeadShot(MultipartFile file, Integer userId);
}
