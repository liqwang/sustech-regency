package com.sustech.regency.service.impl;

import com.sustech.regency.db.dao.CommentAttachmentDao;
import com.sustech.regency.db.po.CommentAttachment;
import com.sustech.regency.service.ConsumerService;
import com.sustech.regency.util.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

import static com.sustech.regency.util.FileUtil.checkMediaSuffix;
import static com.sustech.regency.util.FileUtil.getUUID;

@Service
public class ConsumerServiceImpl implements ConsumerService {
	@Resource
	private CommentAttachmentDao commentAttachmentDao;
	@Resource
	private FileUtil fileUtil;
	@Override
	public String uploadFile(MultipartFile media,Integer orderId){
		checkMediaSuffix(media);
		String uuid = getUUID();
		String url = fileUtil.uploadFile(media,uuid);
		commentAttachmentDao.insert(new CommentAttachment(uuid,orderId));
		return url;
	}
}
