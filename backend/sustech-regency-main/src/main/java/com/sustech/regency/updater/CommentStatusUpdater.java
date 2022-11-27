package com.sustech.regency.updater;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sustech.regency.db.dao.OrderDao;
import com.sustech.regency.db.po.Order;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.util.Date;

import static com.sustech.regency.db.po.OrderStatus.NOT_COMMENTED;
import static com.sustech.regency.db.po.OrderStatus.PAYED;

@Component
public class CommentStatusUpdater {

	@Resource
	private OrderDao orderDao;
	@Scheduled(cron = "1 0 12 * * ?") //每天的12:00:01开始
	public void updateCommentStatus(){
		orderDao.update(new Order().setStatus(NOT_COMMENTED),
						new LambdaQueryWrapper<Order>()
						   .eq(Order::getStatus,PAYED)
						   .lt(Order::getDateEnd,new Date())); //现在已经退房
	}
}
