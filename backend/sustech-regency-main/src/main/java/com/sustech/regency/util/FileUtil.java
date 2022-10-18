package com.sustech.regency.util;

import com.sustech.regency.db.dao.FileDao;
import com.sustech.regency.web.handler.ApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
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

import static cn.hutool.core.io.FileUtil.getSuffix;
import static com.sustech.regency.web.util.AssertUtil.asserts;

@Component
public class FileUtil {
	@Value("${file-root-path}")
	private String fileRootPath; //保存文件的根路径
	@Resource
	private FileDao fileDao;
	public static final Set<String> VALID_PICTURE_SUFFIXES=Set.of("jpg","jpeg","png");
	private static final Set<String> VALID_VIDEO_SUFFIXES=Set.of("mp4");

	private static final SimpleDateFormat DATE_FORMAT=new SimpleDateFormat("/yyyy/MM/dd/");

	/**
	 * @return 前端获取文件的URL
	 */
	@SuppressWarnings("ResultOfMethodCallIgnored")
	public String uploadFile(MultipartFile file, String uuid){
		String originalFilename = file.getOriginalFilename();
		String suffix = cn.hutool.core.io.FileUtil.getSuffix(originalFilename);

		String newFileName = uuid + "." + suffix;

		Date curTime = new Date();
		String dateDir = DATE_FORMAT.format(curTime);
		@SuppressWarnings("ConstantConditions")
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String baseDir = fileRootPath + dateDir;
		File folder = new File(fileRootPath + dateDir);
		if(!folder.exists()){
			folder.mkdirs();
		}
		try {
			File dist = new File(baseDir + newFileName);
			cn.hutool.core.io.FileUtil.writeBytes(file.getBytes(),dist);
		} catch (IOException e) {
			e.printStackTrace();
			throw ApiException.INTERNAL_SEVER_ERROR;
		}
		fileDao.insert(new com.sustech.regency.db.po.File(uuid,curTime,null,suffix));
		return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()+ "/public/file" + dateDir + newFileName;
	}

	public static String getUUID(){
		return UUID.randomUUID().toString().replace("-", "");
	}

	public static void checkMediaSuffix(MultipartFile file){
		String originalFilename = file.getOriginalFilename();
		String suffix = getSuffix(originalFilename);
		asserts(VALID_PICTURE_SUFFIXES.contains(suffix) || VALID_VIDEO_SUFFIXES.contains(suffix),
				"文件格式不支持，仅支持"+VALID_PICTURE_SUFFIXES+VALID_VIDEO_SUFFIXES);
	}

	public static String getUrl(com.sustech.regency.db.po.File file){
		@SuppressWarnings("ConstantConditions")
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String dateDir=DATE_FORMAT.format(file.getUploadTime());
		return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()+ "/public/file" + dateDir +file.getId()+"."+file.getSuffix();
	}
}
