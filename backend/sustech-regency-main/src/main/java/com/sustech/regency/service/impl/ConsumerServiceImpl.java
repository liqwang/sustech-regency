package com.sustech.regency.service.impl;

import cn.hutool.core.io.FileUtil;
import com.sustech.regency.db.dao.CommentAttachmentDao;
import com.sustech.regency.db.po.CommentAttachment;
import com.sustech.regency.service.ConsumerService;
import com.sustech.regency.web.handler.ApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Service
public class ConsumerServiceImpl implements ConsumerService {

	private static final Set<String> VALID_PICTURE_SUFFIXES=Set.of("jpg","jpeg","png");
	private static final Set<String> VALID_VIDEO_SUFFIXES=Set.of("mkv","avi","mp4"); //Todo:暂定
	private static final SimpleDateFormat DATE_FORMAT=new SimpleDateFormat("/yyyy/MM/dd/");
	@Value("${file-root-path}")
	private String fileRootPath; //保存文件的根路径
	@Resource
	private CommentAttachmentDao commentAttachmentDao;
	@Override
	@SuppressWarnings("ResultOfMethodCallIgnored")
	public String uploadFile(MultipartFile file,Integer orderId){
		String originalFilename = file.getOriginalFilename();
		String suffix = FileUtil.getSuffix(originalFilename);
		CommentAttachment commentAttachment = new CommentAttachment();
		if(VALID_PICTURE_SUFFIXES.contains(suffix)){
			commentAttachment.setIsPicture(true);
		}else if(VALID_VIDEO_SUFFIXES.contains(suffix)){
			commentAttachment.setIsPicture(false);
		}else{throw ApiException.badRequest("文件格式不支持");}
		//为防止文件重名，使用UUID重命名
		String uuid = UUID.randomUUID().toString().replace("-", "");
		String newFileName = uuid + "." + suffix;

		String dateDir = DATE_FORMAT.format(new Date());
		@SuppressWarnings("ConstantConditions")
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String baseDir = fileRootPath + dateDir;
		File folder = new File(fileRootPath + dateDir);
		if(!folder.exists()){
			folder.mkdirs();
		}
		try {
			File dist = new File(baseDir + newFileName);
			FileUtil.writeBytes(file.getBytes(),dist);
		} catch (IOException e) {
			e.printStackTrace();
			throw ApiException.INTERNAL_SEVER_ERROR;
		}
		commentAttachment.setId(uuid);
		commentAttachment.setOrderId(orderId);
		commentAttachment.setUploadTime(new Date());
		commentAttachment.setSuffix(suffix);
		commentAttachmentDao.insert(commentAttachment);
		return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()+ "/info/file" + dateDir + newFileName;
	}
}
