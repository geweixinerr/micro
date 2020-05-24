package micro.commons.concurrent;

import java.util.HashSet;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import micro.commons.annotation.ThreadSafe;
import micro.commons.exception.ConcurrentException;
import net.logstash.logback.encoder.org.apache.commons.lang3.ObjectUtils;

/**
 * 并发处理,基于Redis setNx控制
 * 
 * @author gewx
 **/
@Component
@ThreadSafe
@SuppressWarnings("static-access")
public final class ConcurrentLock {

	/**
	 * 分布式锁VALUE
	 **/
	private static final String VALUE = "TRUE";

	/**
	 * 分布式锁默认超时时间,单位:秒
	 **/
	private static final int DEFAULT_TIME_OUT = 15;

	/**
	 * 分布式锁Key
	 **/
	private static final ThreadLocal<String> KEY = new ThreadLocal<>();

	/**
	 * 分布式复合锁Key
	 **/
	private static final ThreadLocal<HashSet<String>> MULTIWAY = new ThreadLocal<>()
			.withInitial(() -> new HashSet<>(8));

	/**
	 * 分布式复合锁计数器
	 **/
	private static final ThreadLocal<Integer> COUNTER = new ThreadLocal<>().withInitial(() -> 0);

	/**
	 * 分布式锁超时数值,单位:秒
	 **/
	private static final ThreadLocal<Integer> TIME_OUT = new ThreadLocal<>();

	/**
	 * 分布式锁超时提示消息
	 **/
	private static final ThreadLocal<String> TIPS = new ThreadLocal<>();

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	public ConcurrentLock key(String key) {
		if (StringUtils.isNotBlank(KEY.get())) {
			MULTIWAY.get().add(KEY.get());
			MULTIWAY.get().add(key);
		}

		KEY.set(key);
		TIME_OUT.set(DEFAULT_TIME_OUT);
		return this;
	}

	/**
	 * 设置超时时间
	 * 
	 * @author gewx
	 * @param timeOut 超时时间,单位:秒
	 * @return ConcurrentLock对象
	 **/
	public ConcurrentLock timeOut(Integer timeOut) {
		TIME_OUT.set(timeOut);
		return this;
	}

	/**
	 * 设置并发异常提示信息
	 * 
	 * @author gewx
	 * @param tips 提示信息
	 * @return ConcurrentLock对象
	 **/
	public ConcurrentLock tips(String tips) {
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
		Exception exception = null;
		try {
			try {
				before();
				T t = execute.invoke();
				return t;
			} catch (Exception ex) {
				exception = ex;
				throw ex;
			}
		} finally {
			if (!(exception instanceof ConcurrentException)) {
				after();
			}
		}
	}

	/**
	 * 并发执行前置
	 **/
	private void before() {
		String key = KEY.get();
		if (StringUtils.isBlank(key)) {
			throw new ConcurrentException("concurrent Key is Empty~");
		}

		boolean setNx = redisTemplate.opsForValue().setIfAbsent(key, VALUE, TIME_OUT.get(), TimeUnit.SECONDS);
		if (!setNx) {
			throw new ConcurrentException(ObjectUtils.defaultIfNull(TIPS.get(), "concurrent Mode Fail~"));
		}
	}

	/**
	 * 并发执行后置
	 **/
	private void after() {
		if (MULTIWAY.get().isEmpty()) {
			try {
				redisTemplate.delete(KEY.get());
			} finally {
				KEY.remove();
				TIME_OUT.remove();
				TIPS.remove();
			}
		} else {
			COUNTER.set(COUNTER.get() + 1);
			if (COUNTER.get().intValue() == MULTIWAY.get().size()) {
				try {
					MULTIWAY.get().stream().forEach(val -> {
						redisTemplate.delete(val);
					});
				} finally {
					MULTIWAY.remove();
					COUNTER.remove();
					KEY.remove();
					TIME_OUT.remove();
					TIPS.remove();
				}
			}
		}
	}
}
