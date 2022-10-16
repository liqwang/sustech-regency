package com.sustech.regency.service.impl;

import com.sustech.regency.db.dao.CommentAttachmentDao;
import com.sustech.regency.db.po.CommentAttachment;
import com.sustech.regency.service.ConsumerService;
import com.sustech.regency.util.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Set;

import static com.sustech.regency.util.FileUtil.getUUID;
import static cn.hutool.core.io.FileUtil.getSuffix;
import static com.sustech.regency.web.util.AssertUtil.asserts;

@Service
public class ConsumerServiceImpl implements ConsumerService {

	private static final Set<String> VALID_PICTURE_SUFFIXES=Set.of("jpg","jpeg","png");
	private static final Set<String> VALID_VIDEO_SUFFIXES=Set.of("mkv","avi","mp4","mov","wmv");
	@Resource
	private CommentAttachmentDao commentAttachmentDao;
	@Resource
	private FileUtil fileUtil;
	@Override
	public String uploadFile(MultipartFile file,Integer orderId){
		String originalFilename = file.getOriginalFilename();
		String suffix = getSuffix(originalFilename);
		asserts(VALID_PICTURE_SUFFIXES.contains(suffix) || VALID_VIDEO_SUFFIXES.contains(suffix),
				"文件格式不支持");
		String uuid = getUUID();
		String url = fileUtil.uploadFile(file,uuid);
		commentAttachmentDao.insert(new CommentAttachment(uuid,orderId));
		return url;
	}
}
