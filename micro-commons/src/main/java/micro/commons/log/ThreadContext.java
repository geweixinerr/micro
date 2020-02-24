package micro.commons.log;

import com.alibaba.ttl.TransmittableThreadLocal;

import micro.commons.annotation.ThreadSafe;

/**
 * 分布式日志框架,traceId数据容器
 * 
 * @author gewx
 **/
@ThreadSafe 
public final class ThreadContext {

	public static final TransmittableThreadLocal<String> TRACEID = new TransmittableThreadLocal<String>();

}
