package com.sustech.regency.updater;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sustech.regency.db.dao.OrderDao;
import com.sustech.regency.db.po.Order;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static com.sustech.regency.db.po.OrderStatus.NOT_PAYED;
import static com.sustech.regency.db.po.OrderStatus.TIMEOUT;
import static java.lang.Long.parseLong;

@Component
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {

	public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
		super(listenerContainer);
	}

	/**
	 * <a href="https://juejin.cn/post/7023543229337305124">Redis的key过期监视</a>
	 */
	@Resource
	private OrderDao orderDao;
	@Override
	public void onMessage(Message message, byte[] pattern){
		String key = message.toString();
		if(key.startsWith("order:")){ //如果15分钟后仍未付款，则订单超时
			Long orderId=parseLong(key.split(":")[1]);
			orderDao.update(new Order().setStatus(TIMEOUT),
							new LambdaQueryWrapper<Order>()
							   .eq(Order::getId,orderId)
							   .eq(Order::getStatus,NOT_PAYED));
		}
	}
}
