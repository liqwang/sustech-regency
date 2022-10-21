package com.sustech.regency.service;

import org.springframework.web.multipart.MultipartFile;

public interface ConsumerService {
	/**
	 * @return 文件上传成功后的获取URL
	 */
	String uploadCommentMedia(MultipartFile media, Integer orderId);

	void deleteCommentMedia(String mediaId, Integer orderId);
}
