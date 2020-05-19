package micro.commons.enums;

import java.util.Collection;
import java.util.Map;

import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * 枚举单例设计模式,分布式架构下线程上下文实例
 * 
 * @author gewx
 **/
public enum ThreadContextEnum {

	/**
	 * 分布式日志追踪traceId
	 * **/
	TRACEID(new TransmittableThreadLocal<String>()),

	/**
	 * requestHeader
	 * **/
	HEADER(new TransmittableThreadLocal<Map<String, Collection<String>>>());

	@SuppressWarnings("rawtypes")
	ThreadContextEnum(ThreadLocal threadLocal) {
		this.threadLocal = threadLocal;
	}

	@SuppressWarnings("rawtypes")
	private final ThreadLocal threadLocal;

	/**
	 * 删除并获取上下文信息
	 * 
	 * @author gewx
	 * @return 线程本地副本内数据
	 **/
	public Object removeAndGetVal() {
		try {
			return threadLocal.get();
		} finally {
			threadLocal.remove();
		}
	}

	/**
	 * 获取上下文信息
	 * 
	 * @author gewx
	 * @return 线程本地副本内数据
	 **/
	public Object getVal() {
		return threadLocal.get();
	}
	
	/**
	 * 设置上下文数据
	 * 
	 * @author gewx
	 * @param obj 对象
	 * @return void
	 **/
	@SuppressWarnings("unchecked")
	public void setVal(Object obj) {
		threadLocal.set(obj);
	}
}
