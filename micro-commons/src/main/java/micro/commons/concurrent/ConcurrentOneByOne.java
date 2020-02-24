package micro.commons.concurrent;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import micro.commons.exception.ConcurrentException;

/**
 * 并发处理,基于Redis setNx控制
 * 
 * @author gewx
 **/
@Component
public final class ConcurrentOneByOne {

	// 分布式锁VALUE
	private static final String VALUE = "TRUE";

	// 分布式锁默认超时时间,单位:秒
	private static final int DEFAULT_TIME_OUT = 15;

	// 分布式锁Key容器
	private static final ThreadLocal<String> KEY = new ThreadLocal<>();

	// 分布式锁超时数值容器
	private static final ThreadLocal<Integer> TIME_OUT = new ThreadLocal<>();

	// 分布式锁超时提示消息容器
	private static final ThreadLocal<String> TIPS = new ThreadLocal<>();

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	public ConcurrentOneByOne key(String key) {
		KEY.set(key);
		TIME_OUT.set(DEFAULT_TIME_OUT);
		return this;
	}

	/**
	 * 设置超时时间
	 * 
	 * @author gewx
	 * @param timeOut 超时时间,单位:秒
	 * @return ConcurrentOneByOne对象
	 **/
	public ConcurrentOneByOne timeOut(Integer timeOut) {
		TIME_OUT.set(timeOut);
		return this;
	}

	/**
	 * 设置并发异常提示信息
	 * 
	 * @author gewx
	 * @param tips 提示信息
	 * @return ConcurrentOneByOne对象
	 **/
	public ConcurrentOneByOne tips(String tips) {
		TIPS.set(tips);
		return this;
	}

	/**
	 * 并发执行过程
	 * 
	 * @author gewx
	 * @param execute 并发执行过程对象
	 * @return T 返回结果对象
	 **/
	public <T> T execute(OneByOne<T> execute) {
		try {
			before();
			T t = execute.invoke();
			return t;
		} finally {
			after();
		}
	}

	/**
	 * 并发执行前置
	 **/
	private void before() {
		String key = KEY.get();
		if (StringUtils.isBlank(key)) {
			throw new ConcurrentException("Concurrent Key is Empty~");
		}

		boolean isSet = redisTemplate.opsForValue().setIfAbsent(key, VALUE, TIME_OUT.get(), TimeUnit.SECONDS);
		if (!isSet) {
			String tips = TIPS.get();
			if (StringUtils.isNotBlank(tips)) {
				throw new ConcurrentException(tips);
			} else {
				throw new ConcurrentException("Concurrent Mode Fail~");
			}
		}
	}

	/**
	 * 并发执行后置
	 **/
	private void after() {
		try {
			redisTemplate.delete(KEY.get());
		} finally {
			KEY.remove();
			TIME_OUT.remove();
			TIPS.remove();
		}
	}
}
