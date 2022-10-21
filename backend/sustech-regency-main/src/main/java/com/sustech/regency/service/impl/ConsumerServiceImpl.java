package com.sustech.regency.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sustech.regency.db.dao.CommentAttachmentDao;
import com.sustech.regency.db.dao.FileDao;
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
	public String uploadCommentMedia(MultipartFile media, Integer orderId){
		checkOrderAndOwner(orderId);
		return fileUtil.uploadDisplayMedia(media,commentAttachmentDao,
										   new CommentAttachment(null,orderId));
	}

	@Resource
	private FileDao fileDao;
	@Override
	public void deleteCommentMedia(String mediaId, Integer orderId) {
		checkOrderAndOwner(orderId);
        asserts(fileDao.selectById(mediaId)!=null,"该文件不存在");
		CommentAttachment commentAttachment =
				commentAttachmentDao.selectOne(
					new LambdaQueryWrapper<CommentAttachment>()
							.eq(CommentAttachment::getOrderId, orderId)
							.eq(CommentAttachment::getFileId, mediaId)
				);
		asserts(commentAttachment!=null,"该文件不是该评论的展示图片或视频");
        fileUtil.deleteFile(mediaId);
	}

	private void checkOrderAndOwner(Integer orderId){
		Order order = orderDao.selectById(orderId);
		asserts(order!=null,"订单id不存在");
		asserts(order.getPayerId().equals(getUserId()),"该订单属于别人");
	}
}
