package com.sustech.regency.util;

import org.springframework.security.core.context.SecurityContextHolder;

import static com.sustech.regency.web.util.AssertUtil.asserts;

public class VerificationUtil {

    /**
     * 生成六位数字验证码
     */
    public static String generateVerificationCode() {
        StringBuilder code = new StringBuilder((int) (Math.random() * (1e6)) + "");
        while (code.length() != 6) {
            code.insert(0, "0");
        }
        return code.toString();
    }

    public static void validateCode(String code, String trueCode) {
        asserts(trueCode != null, "验证码已过期，请重新发送");
        asserts(trueCode.equals(code), "验证码错误");
    }

    public static Integer getUserId() {
        return (int) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
