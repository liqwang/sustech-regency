package com.sustech.regency.service;

import org.springframework.web.multipart.MultipartFile;

public interface ConsumerService {
	/**
	 * @return 文件上传成功后的获取URL
	 */
	String uploadFile(MultipartFile file,Integer orderId);
}
