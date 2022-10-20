package com.sustech.regency.service.impl;

import com.sustech.regency.db.dao.CommentAttachmentDao;
import com.sustech.regency.db.dao.OrderDao;
import com.sustech.regency.db.po.CommentAttachment;
import com.sustech.regency.db.po.Order;
import com.sustech.regency.service.ConsumerService;
import com.sustech.regency.util.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

import static com.sustech.regency.util.VerificationUtil.getUserId;
import static com.sustech.regency.web.util.AssertUtil.asserts;

@Service
public class ConsumerServiceImpl implements ConsumerService {
	@Resource
	private CommentAttachmentDao commentAttachmentDao;
	@Resource
	private OrderDao orderDao;
	@Resource
	private FileUtil fileUtil;
	@Override
	public String uploadFile(MultipartFile media,Integer orderId){
		checkOrderAndOwner(orderId);
		return fileUtil.uploadDisplayMedia(media,commentAttachmentDao,
										   new CommentAttachment(null,orderId));
	}

	private void checkOrderAndOwner(Integer orderId){
		Order order = orderDao.selectById(orderId);
		asserts(order!=null,"订单id不存在");
		asserts(order.getPayerId().equals(getUserId()),"该订单属于别人");
	}
}
