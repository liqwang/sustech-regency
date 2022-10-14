package com.sustech.regency.db.util;

import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
@SuppressWarnings({"unchecked", "ConstantConditions", "unused"})
public class Redis {
	@Resource
	public RedisTemplate redisTemplate;

	public <T> void setObject(String key, T value){
		redisTemplate.opsForValue().set(key,value);
	}

	/**
	 * @param ttl 单位：秒
	 */
	public <T> void setObject(String key, T value, long ttl){
		redisTemplate.opsForValue().set(key,value,ttl,TimeUnit.SECONDS);
	}

	/**
	 * @param ttl 单位：秒
	 * @return 是否设置成功
	 */
	public boolean expire(String key, long ttl){
		return expire(key,ttl,TimeUnit.SECONDS);
	}

	/**
	 * @return 是否设置成功
	 */
	public boolean expire(String key, long ttl, TimeUnit timeUnit){
		return redisTemplate.expire(key,ttl,timeUnit);
	}

	public <T> T getObject(String key){
		return ((ValueOperations<String,T>)redisTemplate.opsForValue()).get(key);
	}

	/**
	 * @return 是否删除成功
	 */
	public boolean deleteObject(String key){
		return redisTemplate.delete(key);
	}

	/**
	 * @return 删除的对象个数
	 */
	public long deleteObjects(Collection<String> keys){
		return redisTemplate.delete(keys);
	}

	public <T> long setList(String key, List<T> list){
		return redisTemplate.opsForList().rightPushAll(key,list);
	}

	public <T> List<T> getList(String key){
		return redisTemplate.opsForList().range(key,0,-1);
	}

	/**
	 * @return 缓存的对象
	 */
	public <T> BoundSetOperations<String,T> setSet(String key,Set<T> set){
		BoundSetOperations<String,T> setOp = redisTemplate.boundSetOps(key);
		set.forEach(setOp::add);
		return setOp;
	}

	public <T> Set<T> getSet(String key){
		return redisTemplate.opsForSet().members(key);
	}

	public <T> void setMap(String key, Map<String,T> map){
		redisTemplate.opsForHash().putAll(key,map);
	}

	public <T> Map<String,T> getMap(String key){
		return redisTemplate.opsForHash().entries(key);
	}

	public <T> void setMapValue(String key, String hKey, T value){
		redisTemplate.opsForHash().put(key,hKey,value);
	}

	public <T> T getMapValue(String key, String hKey){
		return ((HashOperations<String,String,T>)redisTemplate.opsForHash()).get(key,hKey);
	}

	public <T> List<T> getMultiMapValue(String key, Collection<String> hKeys){
		return redisTemplate.opsForHash().multiGet(key,hKeys);
	}

	/**
	 * @param pattern 类似正则表达式
	 * @see <a href="https://redis.io/commands/keys">Redis文档: KEYS</a>
	 */
	public Collection<String> keys(String pattern){
		return redisTemplate.keys(pattern);
	}
}
