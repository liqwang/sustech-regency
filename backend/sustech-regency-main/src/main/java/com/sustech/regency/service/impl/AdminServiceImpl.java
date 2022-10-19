package com.sustech.regency.service.impl;

import com.sustech.regency.db.dao.RoomTypeDao;
import com.sustech.regency.service.AdminService;
import com.sustech.regency.util.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Service
public class AdminServiceImpl implements AdminService {

	@Resource
	private FileUtil fileUtil;
	@Resource
	private RoomTypeDao roomTypeDao;

	@Override
	public String uploadRoomTypeCover(MultipartFile picture, Integer roomTypeId) {
		return fileUtil.uploadDisplayCover(picture,roomTypeDao,roomTypeId);
	}
}
