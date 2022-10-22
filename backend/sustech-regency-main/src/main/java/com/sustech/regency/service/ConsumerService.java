package com.sustech.regency.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

public interface ConsumerService {
	/**
	 * @return 文件上传成功后的获取URL
	 */
	String uploadCommentMedia(MultipartFile media, Integer orderId);

	void deleteCommentMedia(String mediaId, Integer orderId);

	void RoomReservation(Integer roomId, Date startTime, Date endTime, Float price, Integer payerId, String payerName, String payerIdNumber, List<String> cohabitantIdNumbers, List<String> cohabitantNames);
}
